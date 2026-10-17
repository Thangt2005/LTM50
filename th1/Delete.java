package th1;

import java.io.File;

public class Delete {
public static boolean delete(String path) {
	File file = new File(path);
	boolean re = false;
	File[] files = file.listFiles();
	if(files != null) {
		for (File f : files) {
			if(f.isDirectory()) {
				re = delete(f.getPath());//lấy ra file con nếu là thư mục
			}else {
				re = f.delete();
			}
		}
	}
	//re = file.delete();
	return re;
	
}
public static void main(String[] args) {
	String path = "E:\\ML";
	System.out.println(delete(path));
}
}
