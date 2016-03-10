package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.Value;

public class ConditionalExpression extends Expression {
	private static final long serialVersionUID = 99912345672L;
	Expression cond, truex, falsex;
	
	public ConditionalExpression(Expression c, Expression t, Expression f) {
		cond = c; truex = t; falsex = f;
	}
	
	@Override
	public Value evaluate(Spreadsheet s) {
		Value c = cond.evaluate(s);
		if(c != null)
			return truex.evaluate(s).resolve(s);
		else
			return falsex.evaluate(s).resolve(s);
	}

	@Override
	public String toString() {
		return cond.toString() + " ? " + truex.toString() + " : " + falsex.toString();
	}
}
