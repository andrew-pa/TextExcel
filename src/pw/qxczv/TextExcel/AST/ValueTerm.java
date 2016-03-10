package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.Value;

public class ValueTerm extends Term {
	private static final long serialVersionUID = 99912345679L;
	public Value v;
	
	public ValueTerm(Value _v) { v = _v; }
	
	
	@Override
	public Value evaluate(Spreadsheet s) {
		return v;
	}

	@Override
	public String toString() {
		return v.toString();
	}
}
