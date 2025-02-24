package com.example.adminpanel.entity;

import com.google.gson.annotations.SerializedName;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Group {
	
	@SerializedName("id")
	private int id;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("faculty")
	private String faculty;
	
	@SerializedName("programType")
	private String programType;
	
	@SerializedName("program")
	private String program;
	
	@SerializedName("studyForm")
	private String studyForm;
}
