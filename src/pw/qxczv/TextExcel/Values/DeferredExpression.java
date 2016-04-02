package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.AST.Expression;

public class DeferredExpression extends Value {
	private static final long serialVersionUID = 123456781L;
	public Expression xpr;
	
	public DeferredExpression(Expression x) {
		xpr = x;
	}
	
	@Override
	public Value resolve(Spreadsheet s) {
		return xpr.evaluate(s).resolve(s);
	}
	
	@Override
	public int compareTo(Value o) {
		return -1;
	}

	@Override
	public String toString() {
		return "( " + xpr.toString() + " )";
	}
	
	@Override
	public String toCellRepString(Spreadsheet s) {
		Value holdForException = resolve(s);
		if(holdForException == null){ return "ERROR"; }
		return resolve(s).toCellRepString(s);
	}
}
