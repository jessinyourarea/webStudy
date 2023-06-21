package com.sist.temp;

//관련된 데이터를 모아서 관리할 목적 / 전송할 목적
/*
 	변수: private => 은닉화의 목적
 	접근 시 처리: setXxx(), getXxx() , isXxx()=>boolean
 	----------------------------------------------------
 	  = 캡슐화 방식 
 	빈즈는 자바클래스를 JSP에서 연동되기 만드는 기능을 가지고 있다
 */
public class SawonBean {
	private String name,gender,dept,job;
	private int pay;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	
}
