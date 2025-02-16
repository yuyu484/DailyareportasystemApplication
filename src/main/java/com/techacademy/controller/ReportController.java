package com.techacademy.controller;


import org.springframework.beans.factory.annotation.Autowired;
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
import com.techacademy.entity.Report;
import com.techacademy.entity.Employee;
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
    @GetMapping
    public String list(Model model) {
        // 全件検索結果をModelに登録
        model.addAttribute("listSize", reportService.findAll().size());
        model.addAttribute("reportList", reportService.findAll());
        // user/list.htmlに画面遷移
        return "reports/list";
    }

        // 日報新規登録画面
    @GetMapping(value = "/add")
    public String create(Report report, Model model, @AuthenticationPrincipal UserDetail userDetail) {

        report.setEmployee(userDetail.getEmployee());
        return "reports/new";
    }

        // 日報新規登録処理
    @PostMapping(value = "/add")
    public String add(@Validated Report report, BindingResult res, Model model, @AuthenticationPrincipal UserDetail userDetail) {
     // 入力チェック
        if (res.hasErrors()) {
            return create(report, model, userDetail);
         }
        ErrorKinds result = reportService.save(report, userDetail);


         return "redirect:/reports";
     }

        // 日報詳細画面
        @GetMapping(value = "/{id}/")
        public String detail(@PathVariable("id") Integer id, Model model) {

            model.addAttribute("report", reportService.findById(id));
            return "reports/detail";
        }

        // 日報更新画面
        @GetMapping(value = "/{id}/update")
        public String edit(@PathVariable("id") Integer integer,@ModelAttribute Report report,Model model) {
            model.addAttribute("report", reportService.findById(integer));

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
            // reportServiceの更新処理を呼び出し
            ErrorKinds result = reportService.update(report);
            if (ErrorMessage.contains(result)) {
                model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
                return "report/update";
            }

           int id2 = Integer.parseInt(id);
            model.addAttribute("report", reportService.findById(id2));
            if ("".equals(report.getContent())) {
                // 内容が空白だった場合
                model.addAttribute(ErrorMessage.getErrorName(ErrorKinds.BLANK_ERROR),
                        ErrorMessage.getErrorValue(ErrorKinds.BLANK_ERROR));

                return edit(report.getId(),report,model);
            }

                return "redirect:/reports";
            }

        // 日報削除処理
        @PostMapping(value = "/{id}/delete")
        public String delete(@PathVariable("id") int id2, @AuthenticationPrincipal UserDetail userDetail, Model model) {

            ErrorKinds result = reportService.delete(id2);

            if (ErrorMessage.contains(result)) {
                String id = null;
                int id21 = Integer.parseInt(id);
                model.addAttribute(ErrorMessage.getErrorName(result), ErrorMessage.getErrorValue(result));
                model.addAttribute("report", reportService.findById(id21));
                return detail(id21, model);
            }

            return "redirect:/reports";
        }

}


