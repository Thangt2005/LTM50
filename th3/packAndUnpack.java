package th3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;

public class packAndUnpack {
public static void pack(String folder,String packedFile) throws IOException {
	File sFolder = new File(folder);
	if(!sFolder.exists() || !sFolder.isDirectory()) return;//nếu không tồn tại và không phải là thư mục thì không làm gì với nó
	
	File[] files = sFolder.listFiles();//lấy tất cả file và thư mục con 
	if(files == null) return;
	
	File packedCanonical = new File(packedFile).getCanonicalFile();//Chuyển packedFile thành một đường dẫn tuyệt đối và chuẩn hóa, rồi lưu vào packedCanonical
	int fileNum = 0;
	for (File f : files) {
		if(f.isFile() && !f.getCanonicalFile().equals(packedCanonical)) {
			fileNum++;//đếm số phần tử con có trong folder
		}
	}
	try(RandomAccessFile raf = new RandomAccessFile(packedFile, "rw")){
		raf.setLength(0);
		raf.writeInt(fileNum);
		
		for (int i = 0; i < fileNum; i++) {
			raf.writeLong(0);
		}
		long[] fPos = new long[fileNum];
		int index = 0;
		
		for (File f : files) {
			if(f.isFile() && !f.getCanonicalFile().equals(packedCanonical)) {
				long pos = 4+8*index;
				long currentPos = raf.getFilePointer();
				
				raf.writeUTF(f.getName());
				raf.writeLong(f.length());
				
				try(InputStream is = new BufferedInputStream(new FileInputStream(f))){
					byte[] buff = new byte[102400];
					int byteRead;
					while((byteRead = is.read(buff)) != -1) {
						raf.write(buff,0,byteRead);
					}
				}
				long endPos = raf.getFilePointer();
                raf.seek(pos);
                raf.writeLong(currentPos);
                raf.seek(endPos);
                
                index++;
			}
		}
	}
}
public static void unpack(String packedFile, String extractFile, String destFile) throws IOException {
    File pFile = new File(packedFile);
    if (!pFile.isFile() || !pFile.exists()) return;

    try (RandomAccessFile raf = new RandomAccessFile(pFile, "r")) {
        int fileNum = raf.readInt();
        long[] fPos = new long[fileNum];
        
        for (int i = 0; i < fileNum; i++) {
            fPos[i] = raf.readLong();
        }

        for (int i = 0; i < fileNum; i++) {
            raf.seek(fPos[i]);
            String fName = raf.readUTF();
            
            if (fName.equalsIgnoreCase(extractFile)) {
                long fLength = raf.readLong();
                File dFile = new File(destFile);
                try (OutputStream os = new BufferedOutputStream(new FileOutputStream(dFile))) {
                    copyFile(raf, os, fLength);
                }
                return;
            }
        }
    }
}

private static boolean copyFile(RandomAccessFile raf, OutputStream os, long pSize) throws IOException {
    byte[] buff = new byte[102400];
    long remain = pSize;
    int byteMustRead;
    int byteRead;

    while (remain > 0) {
        byteMustRead = (int) (remain > buff.length ? buff.length : remain);
        byteRead = raf.read(buff, 0, byteMustRead);
        if (byteRead == -1) return false;
        os.write(buff, 0, byteRead);
        remain -= byteRead;
    }
    return true;
}

public static void main(String[] args) throws IOException {
    String folder = "F:\\LTM50";
    String packedFile = "F:\\LTM50\\abc.pack";
    String destFile = "F:\\LTM50\\test.pptx";
    
    //pack(folder, packedFile);
    unpack(packedFile, "Part 01 - Java Basics 1 - Variable.pptx", destFile);
}
}
