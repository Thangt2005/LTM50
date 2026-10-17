package th1;

import java.io.*;

public class Copy {
	public static void copyFile(String source, String dest) throws IOException {
		FileInputStream fis = new FileInputStream(source);
		FileOutputStream fos = new FileOutputStream(dest);
		int data;
		byte[] buffer = new byte[65536];
		long startTime = System.currentTimeMillis();
		while ((data = fis.read(buffer)) != -1) {
			fos.write(buffer,0,data);
		}
		fos.flush();
		fis.close();
		fos.close();
		long endTime = System.currentTimeMillis();
		System.out.println("done in" + (endTime - startTime) + "ms");
	}

	public static void main(String[] args) throws IOException {
		String source = "E:\\ML\\idea-2026.2.2.exe";
		String dest = "E:\\ML\\ide_copy.exe";
		copyFile(source, dest);
	}

}
