# Using Spring GraphQL for a Read API

I recently made the decision to try to learn a little about the graphQL ecosystem, and as I'm an engineer with experience being in building spring boot microservices, I figured I would try out the spring wrapper around the GraphQL Java implementation. For this project, I also decided to use Kotlin as it's a language that I've been toying with recently. 

# The Project

The aim of the project was to find an example SQL database on the internet, build a typical spring data-access layer around it & then expose out some queries via a GraphQL api over REST.

The code written has been pushed to GitHub here: https://github.com/pjg-me/graphql-sandbox

I used spring initializer to bootstrap a kotlin project with spring-boot & the dependencies required & found an example SQL database to use as the foundational data source https://www.mysqltutorial.org/mysql-sample-database.aspx
 
Spinning up a docker image of mysql, connecting using MySQL Workbench & running this script was all it took to get a fully functioning database. 

I used [Telosys CLI](https://www.telosys.org/cli.html) to generate Java Entities & Spring boot repositories for the data & adapted them for use with Kotlin (Ctrl + Alt + Shift + K will do some of this work in IntelliJ).

# Functionality to expose using GraphQL
After considering the database and the data contained within, I decided to expose the following queries through the API. 

1. Fetch all Orders
2. Fetch all Customer information w/Orders
3. Fetch specific customer information w/Orders
4. Fetch a specific order for all/specific customer (parameter in an embedded object customer[foo].orders[bar]) #todo object the right word?
5. Return the highest value order for a customer ()

# Schema
The schema (inspired by the database structure) looks like this 
````
#Data Objects

type Order {
    comments: String
    orderDate: String!
    orderNumber: Int!
    requiredDate: String!
    shippedDate: String
    status: String!
    products: [OrderProduct]!
    orderTotal: Float!
}

type OrderProduct {
    productCode: String!
    priceEach: Float!
    quantityOrdered: Int!
    productName: String!
    productVendor: String!
    productDescription: String!
}

type SalesRepresentative {
    employeeNumber: String!
    firstName: String!
    lastName: String!
    extension: String!
    email: String!
    jobTitle: String!
}

type Customer {
    addressLine1: String!,
    addressLine2: String,
    city: String!,
    contactFirstName: String!,
    contactLastName: String!,
    country: String!,
    creditLimit: Float,
    customerName: String!,
    customerNumber: Int!,
    phone: String!,
    postalCode: String,
    state: String
    orders(orderNumber: Int, highestValue: Boolean): [Order],
    salesRepresentative: SalesRepresentative
}

#Query Object

type Query {
    orders: [Order]
    customers(customerNumber: Int): [Customer]
}
````

Each of the data Objects above has a corresponding DAO in the codebase [here](https://github.com/pjg-me/graphql-sandbox/tree/master/src/main/kotlin/me/pjg/graphqldemo/model/graphql)

## Fetch a simple data object - All Orders

In order to fetch all the orders in the database, A spring `@Controller` is needed with a method responsible for fetching the order list. 

````
@Controller
class GraphQLController(
    ...

    @QueryMapping
    fun orders(): List<Order?> {
        return orderService.fetchOrders()
    }
    ...
}

````

This calls down to the order service, which simply does a standard repository lookup. 

````

    fun fetchOrders(): List<Order?> {
        return orderRepository.findAll()
            .map(Order.Companion::fromEntity)
            .toList()
    }

...

    fun fromEntity(it: me.pjg.graphqldemo.model.entity.Order): Order {
        return Order(
            comments = it.comments,
            orderDate = it.orderDate.toString(),
            orderNumber = it.orderNumber,
            requiredDate = it.requiredDate.toString(),
            shippedDate = it.shippedDate.toString(),
            status = it.status,
            products = it.orderDetailsList.map(OrderProduct.Companion::fromEntity),
            orderTotal = it.orderDetailsList.sumOf { it.priceEach * it.quantityOrdered }
        )
    }
````

This allows us to make a query through the REST API with GraphQLs declarative query style to request as much or as little order information as we like...
````
POST http://localhost:8080/graphql

{
    orders{
        orderNumber
        orderTotal
        products {
            productCode
            priceEach
            quantityOrdered
        }
    }
}
````
returns
````
    "data": {
        "orders": [
            {
                "orderNumber": 10100,
                "orderTotal": 10223.829999999998,
                "products": [
                    {
                        "productCode": "S18_1749",
                        "priceEach": 136.0,
                        "quantityOrdered": 30
                    },
                    {
                        "productCode": "S18_2248",
                        "priceEach": 55.09,
                        "quantityOrdered": 50
                    },
...
````

## Fetch Data with a field provided by a DataLoader - Customers & Orders
This can be implemented in a similar way to the last example, with a `@QueryMapping` annotated method to return the customer information.

I decided not to expose order information through the `Customer` type by default, as it's possible that a user 
does not request this, and therefore we can avoid the database overhead of fetching all the extra data into the application layer,
to then discard it before we return it to the API caller. 

The Spring GraphQL framework provides a means to fetch the order information optionally through the use of a GraphQL DataLoader, implemented in the spring framework using the `@SchemaMapping` annotation. 

````
    @QueryMapping
    fun customers(): List<Customer?> {
        return customerService.fetchAllCustomers()
    }
````

````
    @SchemaMapping(typeName = "Customer", field = "orders")
    fun fetchOrdersForCustomer(
        customer: Customer
    ): List<Order> {
        return orderService.fetchOrdersByCustomer(customer)
    }
````

`@SchemaMapping(typeName = "Customer", field = "orders")` tells the framework that this method is responsible for loading data for the `orders` field on the `Customer` type.

If the API caller makes a query which doesn't request any of the order fields, then `fetchOrdersForCustomer` doesn't get called. Otherwise, it's called once per `Customer` returned by the `customers` function.
````
POST http://localhost:8080/graphql

{
    customers {
        contactFirstName,
        contactLastName
    }
}
````
returns
````
{
    "data": {
        "customers": [
            {
                "contactFirstName": "Carine ",
                "contactLastName": "Schmitt"
            },
            {
                "contactFirstName": "Jean",
                "contactLastName": "King"
            },
...
````
And an example of a query with Order information requested too:
````
POST http://localhost:8080/graphql

{
    customers {
        customerName,
        orders {
            orderTotal
        }
    }
}
````
returns
````
{
    "data": {
        "customers": [
            {
                "customerName": "Atelier graphique",
                "orders": [
                    {
                        "orderTotal": 14571.44
                    },
                    {
                        "orderTotal": 6066.78
                    },
                    {
                        "orderTotal": 1676.13
                    }
                ]
            },
            {
                "customerName": "Signal Gift Stores",
                "orders": [
                    {
                        "orderTotal": 32641.98
                    },
                    {
                        "orderTotal": 33347.88
                    },
                    {
                        "orderTotal": 14191.12
                    }
                ]
            },
...
````
## Fetch Data with a field provided by a DataLoader that needs to be batched - SalesRepresentative of a Customer
In the previous example, we perform a single Orders lookup for each Customer, this is because the Customer & Orders tables
are related as OneToMany, One Customer has Many Orders. Because of this, we can guarantee that for each call to `fetchOrdersForCustomer` 
we will be fetching unique data each time. 

For the example of a SalesRepresentative of a Customer, the relationship is different. There are a limited number of SalesRepresentatives, 
with each dealing with multiple customers. This means that if we use the same `@SchemaMapping` approach, we will be fetching the same data over and over again [N+1 Problem](https://medium.com/the-marcy-lab-school/what-is-the-n-1-problem-in-graphql-dd4921cb3c1a).

In order to solve this, spring-graphql gives us the `@BatchMapping` annotation. This lets us query for the `SalesRepresentative` information for every `Customer` in scope of our request all at once, avoiding many redundant database calls & increasing performance. 

e.g. 
````
    @BatchMapping(typeName = "Customer", field = "salesRepresentative")
    fun fetchSalesRepresentativesForCustomers(customers: List<Customer>): Map<Customer, SalesRepresentative?> {
        return customerService.fetchSalesRepresentativesForCustomers(customers)
    }
````
As before `(typeName = "Customer", field = "salesRepresentative")` tells the framework that the annotated method is responsible
for handing the `salesRepresentative` field on the `Customer` type, however the annotation type `BatchMapping` tells the framework to call this method once
when all of the `Customer` scope is known. The method signature required is `fun fetchSalesRepresentativesForCustomers(customers: List<Customer>): Map<Customer, SalesRepresentative?>`
because we need to compute the `Map<Customer, SalesRepresentative` based on the full `List<Customer>` provided. 

The following implementation does this, with only a single database lookup, fetching all of the `SalesRepresentative` objects in scope & allocating each one to it's `Customer`

````
    fun fetchSalesRepresentativesForCustomers(customers: List<Customer>): Map<Customer, SalesRepresentative?> {
        val uniqueEmployeeNumber = customers.map { it.salesRepEmployeeNumber }.toSet()
        val salesRepresentativeById =
            employeeRepository.findAllById(uniqueEmployeeNumber)
                .filterNotNull()
                .associateBy { it.employeeNumber }
                .mapValues { entry -> SalesRepresentative.fromEntity(entry.value) }

        return customers.associateWith { salesRepresentativeById[it.salesRepEmployeeNumber] }
    }
````
## Providing arguments
In order to allow filtering of results based on criteria, the framework allows arguments to be specified using an `@Argument` annotation.

In order to serve the following query:
````
````

the `customers` function from earlier can be updated to accept an `@Argument` as shown.
````
    @QueryMapping
    fun customers(@Argument customerNumber: Int?): List<Customer?> {
        return customerService.fetchCustomers(customerNumber)
    }
````
And the service layer can trigger different database queries depending on whether the argument is passed.
````
  fun fetchCustomers(customerNumber: Int?): List<Customer> {
        val results: List<me.pjg.graphqldemo.model.entity.Customer> = when (customerNumber) {
            null -> {
                customerRepository.findAll()
            }
            else -> {
                customerRepository.findById(customerNumber).toList()
            }
        }

        return results.map { Customer.fromEntity(it) }
    }
````