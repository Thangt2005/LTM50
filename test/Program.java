
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Program {

    public static List<Student> loadData(
            String stFile, String gradeFile, String charset)
            throws IOException {

        List<Student> list = new ArrayList<Student>();

        // Đọc file thông tin sinh viên
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(stFile),
                        Charset.forName(charset)))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                // Tách các trường bằng dấu Tab
                String[] data = line.split("\t", -1);

                // Bỏ qua dòng tiêu đề nếu có
                if (data[0].trim().equalsIgnoreCase("MSSV")) {
                    continue;
                }

                int id = Integer.parseInt(data[0].replace("\uFEFF", "").trim());
                String name = data[1].trim();
                int bYear = Integer.parseInt(data[2].trim());

                Student st = new Student(id, name, bYear, 0);
                list.add(st);
            }
        }

        // Đọc file điểm
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(gradeFile),
                        Charset.forName(charset)))) {

            String line;

            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split("\t", -1);

                // Bỏ qua dòng tiêu đề nếu có
                if (data[0].trim().equalsIgnoreCase("MSSV")) {
                    continue;
                }

                int id = Integer.parseInt(data[0].replace("\uFEFF", "").trim());

                double sum = 0;
                int count = 0;

                // Tính tổng và đếm số môn
                for (int i = 1; i < data.length; i++) {
                    if (!data[i].trim().isEmpty()) {
                        sum += Double.parseDouble(data[i].trim());
                        count++;
                    }
                }

                // Tìm sinh viên có MSSV tương ứng
                for (int i = 0; i < list.size(); i++) {

                    Student st = list.get(i);

                    if (st.getSid() == id) {

                        if (count > 0) {
                            double average = sum / count;
                            st.setGradle(average);
                        }

                        break;
                    }
                }
            }
        }

        return list;
    }

public static void export(
        List<Student> list, String textFile, String charset)
        throws IOException {

    try (BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(
                    new FileOutputStream(textFile),
                    Charset.forName(charset)))) {

        // Ghi dòng tiêu đề để Excel dễ nhận biết các cột
        bw.write("MSSV\tHọ Và Tên\tNăm sinh\tĐiểm trung bình");
        bw.newLine();

        for (int i = 0; i < list.size(); i++) {

            Student st = list.get(i);

            bw.write(
                    st.getSid() + "\t"
                    + st.getName() + "\t"
                    + st.getYear() + "\t"
                    + st.getGradle()
            );

            bw.newLine();
        }
    }
    
}

public static void main(String[] args) throws IOException {

    String stFile = "F:\\LTM50\\sv.txt";
    String gradeFile = "F:\\LTM50\\diem.txt";
    String textFile = "F:\\LTM50\\export.txt";

    String charset = "UTF-16BE";

    // Đọc dữ liệu từ hai file
    List<Student> list = loadData(
            stFile, gradeFile, charset);

    // Hiển thị danh sách để kiểm tra
    for (int i = 0; i < list.size(); i++) {
        System.out.println(list.get(i));
    }

    // Xuất danh sách ra file text
    export(list, textFile, charset);

    System.out.println("Export thanh cong!");
}

}
