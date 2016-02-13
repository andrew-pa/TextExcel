package pw.qxczv.TextExcel;
import java.util.ArrayList;
import java.util.List;

import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.Values.Function;
import pw.qxczv.TextExcel.Values.LValue;
import pw.qxczv.TextExcel.Values.RegionReference;
import pw.qxczv.TextExcel.Values.Value;

public class BuiltinFunctions {

	static class PrintFunc extends Function {
		public PrintFunc() {
			super(new ArrayList<String>(),null);
		}
		@Override
		public Value apply(Spreadsheet s, List<Expression> args) {
			s.print();
			return null;
		}
	}
	static class ClearFunc extends Function {
		public ClearFunc() {
			super(new ArrayList<String>(),null);
		}
		@Override
		public Value apply(Spreadsheet s, List<Expression> args) {
			if(args.size() > 0) {
				LValue v = (LValue)(args.get(0).evaluate(s));
				v.assign(s, null);
			}
			else s.clear();
			return null;
		}
	}
	static class EscFunc extends Function {
		public EscFunc() {
			super(new ArrayList<String>(), null);
			argnames.add("expression");
		}
		@Override
		public Value apply(Spreadsheet s, List<Expression> args) {
			return args.get(0).evaluate(s).resolve(s);
		}
	}
	static class sumReg extends Function{
		public sumReg() {
			super(new ArrayList<String>(), null);
		}
		
		@Override
		public Value apply(Spreadsheet s, List<Expression> args){
			RegionReference temp = (RegionReference) args.get(0).evaluate(s);
			return temp.sum(s);
		}
	}
	static class avgReg extends Function{
		public avgReg() {
			super(new ArrayList<String>(), null);
		}
		
		@Override
		public Value apply(Spreadsheet s, List<Expression> args){
			RegionReference temp = (RegionReference) args.get(0).evaluate(s);
			return temp.average(s);
		}
	}
	public static void apply(Spreadsheet s) {
		s.setValue("print", new PrintFunc());
		s.setValue("clear", new ClearFunc());
		s.setValue("esc", new EscFunc());
		s.setValue("sum", new sumReg());
		s.setValue("avg", new avgReg());
	}
}
