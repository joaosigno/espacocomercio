package net.danielfreire.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class FileUtil {
	
	private static FileUtil file = new FileUtil();
	
	public static synchronized FileUtil getInstance() {
		return file;
	}

	public void createFile(String location, String fileName, String content) throws Exception {
		File dir = new File(location);
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		File newFile = new File(dir, fileName);
		if (newFile.exists()) {
			newFile.delete();
		} 
		newFile.createNewFile();
		
		FileWriter fileWriter = new FileWriter(newFile, true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print(content);
		printWriter.flush();
		printWriter.close();
	}
 }
