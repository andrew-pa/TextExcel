package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public abstract class LValue extends Value {
	public abstract void assign(Spreadsheet s, Value v);
}
