package com.techacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.EmployeeRepository;
import com.techacademy.repository.ReportRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;

    public ReportService(ReportRepository reportRepository, PasswordEncoder passwordEncoder) {
        this.reportRepository = reportRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** 全件を検索して返す */
    public List<Report> getUserList() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();

    }

    /** 全件を検索して返す */
    public List<Report> getReportList() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();

    }

    /** 全件を検索して返す */
    public List<Report> findById() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();

    }

    /** 全件を検索して返す
     * @param report */
    public List<Report> update(Report report) {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();
    }
}
