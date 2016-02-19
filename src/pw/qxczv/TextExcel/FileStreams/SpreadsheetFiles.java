package pw.qxczv.TextExcel.FileStreams;
import java.util.ArrayList;
import java.io.*;
public class SpreadsheetFiles {
	private static String[] FilesFound;
	private static File directory = new File("/SavedSpreadsheets");
	
	public static boolean exists(String name) {
		try{
			UpdateFiles();
			return FileSearch(name);
		} catch (IOException e) {
			//Change when Ship
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	private static boolean FileSearch(String name) throws IOException {
		for(int i = 0; i < FilesFound.length; i ++){
			if(FilesFound[i].equals(name)){
				return true;
			}
		}
		return false;
	}
	
	private static void UpdateFiles() throws IOException{
		if (!directory.mkdir()){
			 FilesFound = directory.list();
		}
	}

}
