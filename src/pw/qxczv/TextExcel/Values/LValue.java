package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public abstract class LValue extends Value {
	private static final long serialVersionUID = 123456785L;
	public abstract void assign(Spreadsheet s, Value v);
}
