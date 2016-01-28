package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class CellReference extends Value {
	public char colIdx; public int rowIdx;
	
	public CellReference(char c, int r) {
		colIdx = c; rowIdx = r;
	}
	
	@Override
	public Value resolve(Spreadsheet s) {
		return s.valueAt(colIdx, rowIdx);
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
