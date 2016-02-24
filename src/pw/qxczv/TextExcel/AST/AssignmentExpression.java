package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.ErrorValue;
import pw.qxczv.TextExcel.Values.LValue;
import pw.qxczv.TextExcel.Values.Value;

public class AssignmentExpression extends Expression {

	public Expression lhs;
	public Expression rhs;
	
	public AssignmentExpression(Expression l, Expression r) {
		lhs = l; rhs = r;
	}
	
	@Override
	public Value evaluate(Spreadsheet s) {
		Value rv = rhs.evaluate(s);
		LValue lv = (LValue)lhs.evaluate(s);
		if(lv == null) {
			return new ErrorValue("Left hand side invalid");
		}
		lv.assign(s, rv);
		return null;
	}

	@Override
	public String toString() {
		return lhs + " = " + rhs;
	}
	
}
