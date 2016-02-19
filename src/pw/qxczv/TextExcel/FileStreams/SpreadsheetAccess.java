package pw.qxczv.TextExcel.FileStreams;

import pw.qxczv.TextExcel.Spreadsheet;

public class SpreadsheetAccess {
	public Spreadsheet getSpreadsheet(String name){
		if(SpreadsheetFiles.exists(name)){
			return SpreadsheetFileInputStream.get(name);
		}else{
			System.out.println("<File Not Found>");
			return null;
		}
	}
	
	public static void closeOut(){
		SpreadsheetFileInputStream.closeCrap();
	}
}
