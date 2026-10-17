package th1;

import java.io.File;

public class FileView {
public static void listFileExt(String folder,String ext) {
	File dir = new File(folder);
	File[] list = dir.listFiles();
	for (File f : list) {
		if(f.isFile() && f.getName().endsWith(ext))System.out.println(f.getName());;
	}
}
	
	public static void main(String[] args) {
	String dir = "E:\\LTNC";
	String ext = ".pptx";
	listFileExt(dir, ext);
	}
}
