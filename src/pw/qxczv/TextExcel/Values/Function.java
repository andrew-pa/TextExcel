package pw.qxczv.TextExcel.Values;

import java.util.HashMap;
import java.util.List;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.AST.Term;

public class Function extends Value {
	public List<String> argnames;
	public Expression body;
	
	public Function(List<String> an, Expression b) {
		argnames = an;
		body = b;
	}
	
	public Value apply(Spreadsheet s, List<Expression> args) {
		HashMap<String,Value> iva = new HashMap<>();
		for(int i = 0; i < argnames.size(); ++i) {
			iva.put(argnames.get(i), args.get(i).evaluate(s).resolve(s));
		}
		s.pushScope(iva);
		Value v = body.evaluate(s).resolve(s);
		s.popScope();
		return v;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("{ ");
		if(argnames.size() > 0) {
			s.append("| ");
			for(String n : argnames) s.append(n + " ");
			s.append("| ");
		}
		s.append(body);
		s.append(" }");
		return s.toString();
	}
	
	@Override
	public String toCellRepString(Spreadsheet s) {
		return "<function>";
	}

	@Override
	public int compareTo(Value arg0) {
		return -1;
	}
}
