package pw.qxczv.TextExcel.FileStreams;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SpreadsheetFileOutputStream {

	private static FileOutputStream fileOutput;
	private static ObjectOutputStream ObjectOutput;
	
	public static void closeCrap(){
		try {
			fileOutput.close();
			ObjectOutput.close();
		} catch (IOException e) {
			//Change when ship
			e.printStackTrace();
		}
		
	}

	public static void delete(String name) {
		
	}
}
