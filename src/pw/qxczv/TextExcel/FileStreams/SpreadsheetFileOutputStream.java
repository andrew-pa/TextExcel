package pw.qxczv.TextExcel.FileStreams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import pw.qxczv.TextExcel.Spreadsheet;

public class SpreadsheetFileOutputStream {

	private static FileOutputStream fileOutput;
	private static ObjectOutputStream ObjectOutput;

	public static void closeCrap() {
		try {
			fileOutput.close();
			ObjectOutput.close();
		} catch (IOException e) {
			// Change when ship
			e.printStackTrace();
		}

	}

	public static boolean delete(String name) {
		File deletedFile = new File("/SavedSpreadsheets/" + name + ".qxczvSp");
		return deletedFile.delete();
		// Note: ^Returns a boolean if needed
	}

	public static boolean newSaveFile(String name, Spreadsheet s) {
		File newSaveFile = new File("/SavedSpreadsheets/" + name + ".qxczvSp");
		try {
			boolean saved = newSaveFile.createNewFile();
			save(newSaveFile, s);
			return saved;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void rewriteSaveFile(String name, Spreadsheet s) {
		File newSaveFile = new File("/SavedSpreadsheets/" + name + ".qxczvSp");
		try {
			save(newSaveFile, s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void save(File newSaveFile, Spreadsheet s) throws IOException {
		fileOutput = new FileOutputStream(newSaveFile);
		ObjectOutput = new ObjectOutputStream(fileOutput);
		ObjectOutput.writeObject(s);
		ObjectOutput.flush();
	}

}
