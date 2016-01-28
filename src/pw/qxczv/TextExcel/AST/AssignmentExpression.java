package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.CellReference;
import pw.qxczv.TextExcel.Values.Value;

public class AssignmentExpression extends Expression {

	public CellReference lhs;
	public Expression rhs;
	
	public AssignmentExpression(CellReference l, Expression r) {
		lhs = l; rhs = r;
	}
	
	@Override
	public Value evaluate(Spreadsheet s) {
		Value rv = rhs.evaluate(s);
		s.setValue(lhs.colIdx, lhs.rowIdx, rv);
		return rv;
	}

}
