package ajs.dragonguyman.playlistextract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains methods used for file operations.
 */
public class FileManipulator {
	
	/**
	 * Creates a new file based off given path name
	 * 
	 * @param pathname Name of file and its path. Shouldn't be a null value.
	 * @throws IOException If I/O failure occurs.
	 */
	public static void createFile(String pathname) throws IOException {
		
		File file = new File(pathname);
		
		if (file.createNewFile()) {
			System.out.println("File created at " + file.getAbsolutePath());
		}
		else {
			System.out.println("This file already exists.");
		}
	}
	
	/**
	 * Writes strings in an array list to a file. Each string is put on its own line.
	 * 
	 * @param stringList List of strings to put on separate lines
	 * @param pathname Path of file to write to
	 * @throws IOException For problems with I/O
	 */
	public static void onePerLine (ArrayList<String> stringList, String pathname) throws 
			IOException {
		
		FileWriter file = new FileWriter(pathname);
		BufferedWriter output = new BufferedWriter(file);
		
		for (String text : stringList) {
			output.write(text + "\n");
		}
		
		output.close();
	}
}
