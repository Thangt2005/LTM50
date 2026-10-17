package th4;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Program {
	public static int NAME_LENGTH = 50;

	public static void addStudent(String path, Student st) throws IOException, IOException {
		File file = new File(path);
		
		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
			int studentNumber = 0;
			int recSize = 4 + NAME_LENGTH * 2 + 4 + 8;// sid(int = 4byte) + name(50*2) + bYear(int = 4byte)+ gradle
														// (double = 8byte)
			if (raf.length() > 0) {
				studentNumber = raf.readInt();// chiều dài của raf > 0 tức danh sách sinh viên đã có dữ liệu
				recSize = raf.readInt();

			}
			// header
			raf.seek(0);// ghi header mới từ đầu(vị trí số 0
			raf.writeInt(studentNumber + 1);// tăng 1 phần tử sinh viên
			raf.writeInt(recSize);

			// Data
			raf.seek(8 + studentNumber * recSize);// di chuyển xuống cuối để ghi tiếp thông tin sinh viên
			st.writeRafst(raf, NAME_LENGTH);// bổ sung hàm phụ ghi name
		}
	}

	public static Student getStudentByIndex(String path, int index) throws IOException, IOException {
		File file = new File(path);
		if (!file.exists())
			return null;

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
			// đọc header
			int studentNumber = raf.readInt();
			int recSize = raf.readInt();

			if (index < 0 || index >= studentNumber)
				return null;

			int recPos = 8 + index * recSize;
			raf.seek(recPos);

			Student st = new Student();
			st.readRAFst(raf, NAME_LENGTH);
			return st;
		}
	}

	public static Student getStudentByID(String path, int id) throws IOException, IOException {
		File file = new File(path);
		if (!file.exists())
			return null;
		try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
			int studentNumber = raf.readInt();
			int recSize = raf.readInt();

			for (int i = 0; i < studentNumber; i++) {
				int recPos = 8 + i * recSize;
				raf.seek(recPos);
				int sid = raf.readInt();
				if (sid == id) {
					raf.seek(recPos);// quay lại từ đầu để đọc trọn vẹn danh sách
					Student st = new Student();
					st.readRAFst(raf, NAME_LENGTH);
					return st;

				}

			}
		}
		return null;
	}

	public static void updateStudent(String path, Student st, int index) throws IOException, IOException {
		File file = new File(path);
		if (!file.exists())
			return;

		try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
			int studentNum = raf.readInt();
			int recSize = raf.readInt();

			if (index < 0 || index >= studentNum)
				return;

			int recpos = 8 + index * recSize;
			raf.seek(recpos);
			st.writeRafst(raf, recpos);
		}
	}

	public static void main(String[] args) throws IOException {
		Student st1 = new Student(123, "Nguyen Van A", 2008, 10);
		Student st2 = new Student(234, "Tran Van B", 2007, 9);
		Student st3 = new Student(345, "Huynh Thi C", 2006, 8);
		Student st4 = new Student(456, "Nguyen Thi D", 2006, 7);

		Program program = new Program();
		String path = "F:\\ML\\dssv.txt";

		Program.addStudent(path, st1);
		Program.addStudent(path, st2);
		Program.addStudent(path, st3);
		Program.addStudent(path, st4);

		System.out.println(Program.getStudentByIndex(path, 0));
		System.out.println(Program.getStudentByID(path, 456));

		Student stUpdated = new Student(123, "Tran Van B", 2007, 9.0);
		Program.updateStudent(path, stUpdated, 1);
		System.out.println("before update : " + Program.getStudentByIndex(path, 1));
	}
}
