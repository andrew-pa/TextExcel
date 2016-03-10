package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.Value;
import pw.qxczv.TextExcel.Values.ErrorValue;
import pw.qxczv.TextExcel.Values.Number;

public class MulTerm extends Expression {
	private static final long serialVersionUID = 99912345676L;
	public Expression left, right;
	
	public MulTerm(Expression l, Expression r) {
		left = l; right = r;
	}
	
	@Override
	public Value evaluate(Spreadsheet s) {
		try {
			Number lv = (Number)(left.evaluate(s).resolve(s));
			Number rv = (Number)(right.evaluate(s).resolve(s));
			return new Number(lv.v * rv.v);
		} catch(Exception e) {
			return new ErrorValue(e);
		}
	}

	@Override
	public String toString() {
		return left + " * " + right;
	}
}
