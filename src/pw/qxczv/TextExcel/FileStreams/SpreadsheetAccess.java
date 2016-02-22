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
	
	public static void delete(String name){
		SpreadsheetFileOutputStream.delete(name);
		//Note: ^ Returns Boolean if needed
	}
	
	public static void SaveNew(String name, Spreadsheet s){
		if(!SpreadsheetFiles.exists(name)){
			SpreadsheetFileOutputStream.newSaveFile(name,s);
			//Note: ^ Returns Boolean if needed
		}
	}
	
	public static void Save(String name, Spreadsheet s){
		if(SpreadsheetFiles.exists(name)){
			SpreadsheetFileOutputStream.rewriteSaveFile(name, s);
		}else{
			SaveNew(name,s);
		}
	}
	
	public static void closeOut(){
		SpreadsheetFileInputStream.closeCrap();
	}
}
