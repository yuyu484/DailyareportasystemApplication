package com.techacademy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.constants.ErrorKinds;
import com.techacademy.constants.ErrorMessage;
import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

@Controller
@RequestMapping("reports")
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
        }

    /** 一覧画面を表示 */
    @GetMapping("/list")
    public String getList(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("reportlist", reportService.getReportList());
        // user/list.htmlに画面遷移
        return "reports/list";
    }

        // 日報新規登録画面
        @GetMapping(value = "/add")
        public String create(@ModelAttribute Report report) {

            return "reports/new";
        }

        // 日報新規登録処理
        @PostMapping(value = "/add")
        public String add(@Validated Report report, BindingResult res, Model model) {

            if ("".equals(report.getreportDate())) {
                // パスワードが空白だった場合
                model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.BLANK_ERROR),
                        ErrorMessage.getErrorValue(ErrorKinds.BLANK_ERROR));

                return create(report);
         }

         // 入力チェック
            if (res.hasErrors()) {
            return create(report);
         }
            return "redirect:/reports";
         }

        // 日報詳細画面
        @GetMapping(value = "/{id}/")
        public String detail(@PathVariable("id") Integer id, Model model) {

            model.addAttribute("report", reportService.findById());
            return "reports/detail";
        }

        // 日報更新画面
        @GetMapping(value = "/{id}/update")
        public String edit(@PathVariable("id") Integer integer,@ModelAttribute Report report,Model model) {
            model.addAttribute("report", reportService.findById());

            return "reports/update";
        }

        // 日報更新処理
        @PostMapping(value = "/{id}/update")
        public String update(@PathVariable("id") String id,@Validated Report report, BindingResult res, Model model) {
            // 入力チェック
            if (res.hasErrors()) {
                model.addAttribute("report",report);
                return "reports/update";

            }

            model.addAttribute("report", reportService.findById());
            if ("".equals(report.getName())) {
                // 氏名が空白だった場合
                model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.BLANK_ERROR),
                        ErrorMessage.getErrorValue(ErrorKinds.BLANK_ERROR));

                return edit(report.getId(),report,model);
            }

                return "redirect:/reports";
            }
}

