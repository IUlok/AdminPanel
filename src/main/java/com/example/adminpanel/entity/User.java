package com.example.adminpanel.entity;

import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private String firstName;

    private String lastName;

    private String patronymic;

    private String role;

    private Date enabledFrom;

    private Date enabledUntil;

    private String reimbursement;

    private String groupName;

    private String department;

    private String academicTitle;

    private String academicDegree;
}
