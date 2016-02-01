package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.CellReference;
import pw.qxczv.TextExcel.Values.GlobalValueReference;
import pw.qxczv.TextExcel.Values.Value;

public class Identifier extends Term {
	public String nm;
	public Identifier(String s) {
		nm = s;
	}
	
	@Override
	public Value evaluate(Spreadsheet s) {
		if(nm.length() > 1 && Character.isLetter(nm.charAt(0)) && Character.isDigit(nm.charAt(1))) { //this should be in the CellReference constructor, and also we should extend to >26 columns
			return new CellReference(Character.toUpperCase(nm.charAt(0)), Integer.parseInt(nm.substring(1)));
		}
		return new GlobalValueReference(nm);
	}
	
	@Override
	public String toString() {
		return nm;
	}

}
