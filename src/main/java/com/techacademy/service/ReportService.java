package com.techacademy.service;

import java.time.LocalDate;
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
import com.techacademy.service.UserDetail;
@Service
public class ReportService {

    private final ReportRepository reportRepository;


    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }


    // 日報保存
    @Transactional
    public ErrorKinds save(Report report,UserDetail userDetail) {
        boolean check = reportRepository.existsByEmployeeAndReportDate(userDetail.getEmployee(),report.getReportDate());
        if (check == true) {
            return ErrorKinds.DATECHECK_ERROR;
        }


    // 社員番号（ログイン中の従業員の社員番号
        report.setEmployee(userDetail.getEmployee());
        report.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        report.setUpdatedAt(now);

        reportRepository.save(report);
        return ErrorKinds.SUCCESS;
    }

    // 日報一覧表示処理（ログインユーザーのみ）
    public List<Report> findByEmployee(UserDetail userDetail) {
        return reportRepository.findByEmployee(userDetail.getEmployee());
    }
    // 日報一覧表示処理（従業員削除用）
    public List<Report> findByEmployee(Employee employee) {
        return reportRepository.findByEmployee(employee);
    }

    // 日報一覧表示処理
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    // 1件を検索
    public Report findById(Integer id) {
        // findByIdで検索
        Optional<Report> option = reportRepository.findById(id);
        // 取得できなかった場合はnullを返す
        Report report = option.orElse(null);
        return report;
    }


    // 更新（追加）を行なう

    @Transactional
    public ErrorKinds update(Report report,UserDetail userDetail) {

     // 元々の日報を取得する
        Report report_org = findById(report.getId());
        if (report.getReportDate().equals(report_org.getReportDate())){
            boolean check = reportRepository.existsByEmployeeAndReportDate(userDetail.getEmployee(),report.getReportDate());
            if (check == true) {
            return ErrorKinds.DATECHECK_ERROR;
            }

        }

        Report rep = findById(report.getId());

        rep.setTitle(report.getTitle());
        rep.setDeleteFlg(false);

        LocalDateTime now = LocalDateTime.now();
        report.setCreatedAt(now);
        rep.setUpdatedAt(now);

        reportRepository.save(rep);
        return ErrorKinds.SUCCESS;
    }

    // 日報削除
    @Transactional
    public ErrorKinds delete(int id) {
        Report report = findById(id);
        LocalDateTime now = LocalDateTime.now();
        report.setUpdatedAt(now);
        report.setDeleteFlg(true);

        return ErrorKinds.SUCCESS;
    }
}
