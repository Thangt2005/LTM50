package th4;

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

	public void writeRafst(RandomAccessFile raf, int NAME_LENGTH) throws IOException {
		raf.writeInt(this.sid);
		writeName(raf, NAME_LENGTH);
		raf.writeInt(this.bYear);
		raf.writeDouble(this.grade);
	}

	public void readRAFst(RandomAccessFile raf, int NAME_LENGTH) throws IOException {
		this.sid = raf.readInt();
		char c;
		String s = "";
		for (int i = 0; i < NAME_LENGTH; i++) {
			c = raf.readChar();
			s += c;
		}
		this.name = s.trim();
		this.bYear = raf.readInt();
		this.grade = raf.readDouble();
	}

	private void writeName(RandomAccessFile raf, int NAME_LENGTH) throws IOException {
		for (int i = 0; i < NAME_LENGTH; i++) {
			if (i < this.name.length()) {
				raf.writeChar(this.name.charAt(i));
			} else {
				raf.writeChar(' ');
			}
		}

	}

	public List<Student> loadData() {
		String stFile;
		String gradeFile;
		String charset;
		return null;

	}
}
