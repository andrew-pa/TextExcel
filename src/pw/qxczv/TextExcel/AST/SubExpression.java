package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.Value;
import pw.qxczv.TextExcel.Values.CellReference;
import pw.qxczv.TextExcel.Values.ErrorValue;
import pw.qxczv.TextExcel.Values.Number;
import pw.qxczv.TextExcel.Values.RegionReference;

public class SubExpression extends Expression {
	private static final long serialVersionUID = 99912345677L;
	public Expression left, right;
	
	public SubExpression(Expression l, Expression r) {
		left = l; right = r;
	}
	
	@Override
	public Value evaluate(Spreadsheet s) {
		try {
			Value lv = left.evaluate(s);
			Value rv = right.evaluate(s);
			Number ln = (Number)(lv.resolve(s));
			Number rn = (Number)(rv.resolve(s));
			return new Number(ln.v - rn.v);
		} catch (Exception e){
			return new ErrorValue(e);
		}
	}

	@Override
	public String toString() {
		return left + " - " + right;
	}
}
