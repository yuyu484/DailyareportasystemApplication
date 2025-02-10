package com.techacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

import com.techacademy.entity.Report;
import com.techacademy.entity.Employee;


public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByEmployee(Employee employee);
    boolean existsByEmployeeAndReportDate(Employee employee,LocalDate reportDate);

}
