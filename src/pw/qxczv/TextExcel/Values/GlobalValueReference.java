package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class GlobalValueReference extends LValue {
	private static final long serialVersionUID = 123456784L;
	
	public String name;
	
	public GlobalValueReference(String nm) {
		name = nm;
	}
	
	@Override
	public int compareTo(Value o) {
		return -1;
	}
	
	@Override
	public Value resolve(Spreadsheet s) {
		return s.valueFor(name);
	}

	@Override
	public void assign(Spreadsheet s, Value v) {
		s.setValue(name, v);
	}

	@Override
	public String toString() { return name; }
	
	@Override
	public String toCellRepString(Spreadsheet s) {
		return resolve(s).toString();
	}
	
}
