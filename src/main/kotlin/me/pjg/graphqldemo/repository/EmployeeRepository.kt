package me.pjg.graphqldemo.repository

import me.pjg.graphqldemo.model.entity.Employee
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : JpaRepository<Employee?, Int?>, JpaSpecificationExecutor<Employee?>