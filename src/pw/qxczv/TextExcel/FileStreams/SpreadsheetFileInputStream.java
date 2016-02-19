package pw.qxczv.TextExcel.FileStreams;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.File;
import java.io.FileInputStream;

import pw.qxczv.TextExcel.Spreadsheet;



public class SpreadsheetFileInputStream {
	
private static FileInputStream fileInput;
private static ObjectInputStream ObjectInput;

	public static Spreadsheet get(String name) {
		try{
			File saveFile = new File("/SavedSpreadsheets/" + name + ".qxczvSp");
			fileInput = new FileInputStream(saveFile);
			ObjectInput = new ObjectInputStream(fileInput);
			return (Spreadsheet) ObjectInput.readObject();
		}catch (IOException e){
			//Change when ship
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("<Error--BadFile>");
		}
		return null;
	}
	
	public static void closeCrap(){
		try {
			fileInput.close();
			ObjectInput.close();
		} catch (IOException e) {
			//Change when ship
			e.printStackTrace();
		}
		
	}

}
