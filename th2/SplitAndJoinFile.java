package th2;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SplitAndJoinFile {
	public static void split(String source, long pSize) throws IOException {
		InputStream is = new FileInputStream(source);
		OutputStream os;
		String dest;
		int count = 1;
		while (true) {
			dest = source + createExt(count);
			os = new BufferedOutputStream(new FileOutputStream(dest));
			boolean hashMore = copyFile(is, os, pSize);
			os.close();
			if (!hashMore)
				break;

			count++;
		}
		is.close();
	}

	public static void join(String source) throws IOException {
		File file = new File(source);
		if (file.exists()) {
			System.out.println("file này đã tồn tại");
			return;
		}
		OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
		InputStream is;
		String partFileName;
		int count = 1;
		while (true) {
			partFileName = source + createExt(count);
			File partFile = new File(partFileName);
			if (!partFile.exists())
				break;
			is = new BufferedInputStream(new FileInputStream(partFile));
			copyFile(is, os, partFile.length());
			is.close();
			count++;
		}
		os.close();
	}

	private static boolean copyFile(InputStream is, OutputStream os, long pSize) throws IOException {
		byte[] buffer = new byte[102400];
		long remain = pSize;
		int byteRead;
		while (remain > 0) {
			int byteMustRead = (int) (remain > buffer.length ? buffer.length : remain);
			byteRead = is.read(buffer, 0, byteMustRead);
			if (byteRead == -1)
				return false;
			os.write(buffer, 0, byteRead);
			remain -= byteRead;
		}
		return true;
	}

	private static String createExt(int count) {
		if(count<10) 
			return ".00"+count;
		
		if(count < 	100) 
			return ".0"+count;
		
			return "."+count;
	}
	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		String source = "E:\\ML\\Part 01 - Java Basics 1 - Variable.pptx"; 
		long pSize = 102400;
		//split(source, pSize);
		join(source);
		long end = System.currentTimeMillis();
		long result = end - start;
		System.out.println(result / 1000.0 + "s");
	}
}
