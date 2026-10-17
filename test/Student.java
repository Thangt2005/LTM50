package test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class Student {
	private int sid;
	private String name;
	private int bYear;
	private double grade;

	public Student(int sid, String name, int bYear, double grade) {
		this.sid = sid;
		this.name = name;
		this.bYear = bYear;
		this.grade = grade;
	}

	public Student() {
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYear() {
		return bYear;
	}

	public void setYear(int year) {
		this.bYear = year;
	}

	public double getGradle() {
		return grade;
	}

	public void setGradle(double gradle) {
		this.grade = gradle;
	}

	@Override
	public String toString() {
		return "Student [sid=" + sid + ", name=" + name + ", year=" + bYear + ", gradle=" + grade + "]";
	}

	public List<Student> loadData() {
		String stFile;
		String gradeFile;
		String charset;
		return null;

	}
}
