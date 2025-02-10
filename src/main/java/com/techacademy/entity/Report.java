package com.techacademy.entity;

import java.time.LocalDateTime;
import java.time.LocalDate;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reports")
@SQLRestriction("delete_flg = false")
public class Report {

    // ID
    @Id
    @Column(length = 600)
    @NotEmpty
    @Length(max = 600)
    private Integer id;


    // 名前
    @Column(length = 20, nullable = false)
    @NotEmpty
    @Length(max = 20)
    private String name;

    // 日付
    @NotNull
    @Column(nullable = false)
    private LocalDate getreportDate;


    // 内容
    @Column(length = 600,columnDefinition="LONGTEXT", nullable=false)
    @NotEmpty
    @Length(max=600)
    private String content;

    // 社員番号 (Employee_code)
    @ManyToOne
    @JoinColumn(name="employee_code",referencedColumnName="code",nullable=false)
    private Employee employee;

    // 削除フラグ(論理削除を行うため)
    @Column(columnDefinition="TINYINT", nullable = false)
    private boolean deleteFlg;

    // 登録日時
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 更新日時
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public Object getreportDate() {
        // TODO 自動生成されたメソッド・スタブ
        return null;
    }

}

