package pw.qxczv.TextExcel.FileStreams;

import pw.qxczv.TextExcel.Spreadsheet;

public class SpreadsheetAccess {
	public Spreadsheet getSpreadsheet(String name){
		if(SpreadsheetFiles.exists(name)){
			return SpreadsheetFileStream.get(name);
		}else{
			System.out.println("<File Not Found>");
			return null;
		}
	}
}
