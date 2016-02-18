package pw.qxczv.TextExcel.FileStreams;
import java.util.ArrayList;
import java.io.*;
public class SpreadsheetFiles {
	private static ArrayList<String> FilesFound;
	private static File directory = new File("bla.text");
	public static boolean exists(String name) {
		try{
			UpdateFiles();
			if(FileSearch(name)){
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
		
	}
	private static boolean FileSearch(String name) throws IOException {
		return false;
		
	}
	private static void UpdateFiles() throws IOException{
		if (!directory.exists()) {
			directory.createNewFile();
		}
	}

}
