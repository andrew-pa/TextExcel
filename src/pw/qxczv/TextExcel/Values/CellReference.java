package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class CellReference extends LValue {
	private static final long serialVersionUID = 123456780L;
	public char colIdx; public int rowIdx;
	
	public CellReference(char c, int r) {
		colIdx = c; rowIdx = r;
	}
	
	@Override
	public Value resolve(Spreadsheet s) {
		Value v = s.valueAt(colIdx, rowIdx);
		if(v == null) return null;		
		return v.resolve(s);
	}
	
	public Value rawDeref(Spreadsheet s) {
		return s.valueAt(colIdx, rowIdx);
	}
	
	@Override
	public void assign(Spreadsheet s, Value v) {
		s.setValue(colIdx, rowIdx, v);
	}
	
	@Override
	public int compareTo(Value o) {
		//Comparing a reference always returns less, there is no Spreadsheet to read a value from
		return -1;
	}

	@Override
	public String toString() {
		return colIdx + "" + rowIdx;
	}
	
}
