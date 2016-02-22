package pw.qxczv.TextExcel;
import java.util.ArrayList;
import java.util.List;

import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.Values.Function;
import pw.qxczv.TextExcel.Values.LValue;
import pw.qxczv.TextExcel.Values.TrueValue;
import pw.qxczv.TextExcel.Values.RegionReference;
import pw.qxczv.TextExcel.Values.StringValue;
import pw.qxczv.TextExcel.Values.Value;
import pw.qxczv.TextExcel.Values.Number;

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
	static class CompFunc extends Function {
		public static enum ComparisonOp {
			less, lesseq, eq, grtr, grtreq, noteq
		}
		public ComparisonOp op;
		public CompFunc(ComparisonOp OP) {
			super(new ArrayList<String>(), null);
			argnames.add("op1");
			argnames.add("op2");
			op = OP;
		}
		@Override
		public Value apply(Spreadsheet s, List<Expression> args) {
			Value a = args.get(0).evaluate(s).resolve(s);
			Value b = args.get(1).evaluate(s).resolve(s);
			switch(op) {
			case less:			return a.compareTo(b) < 0 ? TrueValue.get() : null;
			case lesseq:		return a.compareTo(b) <= 0 ? TrueValue.get() : null;
			case eq:			return a.compareTo(b) == 0 ? TrueValue.get() : null;
			case grtr:			return a.compareTo(b) > 0 ? TrueValue.get() : null;
			case grtreq:		return a.compareTo(b) >= 0 ? TrueValue.get() : null;
			case noteq:			return a.compareTo(b) != 0 ? TrueValue.get() : null;
			default: 			return null;
			}
		}
	}
	static class BoolNotFunc extends Function {
		public BoolNotFunc() {
			super(new ArrayList<String>(), null);
			argnames.add("expression");
		}
		@Override
		public Value apply(Spreadsheet s, List<Expression> args) {
			return args.get(0).evaluate(s).resolve(s) == null ? TrueValue.get() : null;
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
	static class newSheet extends Function{
		public newSheet(){
			super(new ArrayList<String>(), null);
		}
		
		@Override
		public Value apply(Spreadsheet s, List<Expression> args){
			s.newSize((int)(((Number) args.get(0).evaluate(s).resolve(s)).v),(int)(((Number) args.get(1).evaluate(s).resolve(s)).v));
			return null;
		}
	}
	static class saveSheet extends Function{
		public saveSheet(){
			super(new ArrayList<String>(), null);
		}
		
		@Override
		public Value apply(Spreadsheet s, List<Expression> args){
			s.Save(((StringValue) args.get(0).evaluate(s).resolve(s)).v);
			return null;
		}
	}
	public static void apply(Spreadsheet s) {
		s.setValue("print", new PrintFunc());
		s.setValue("clear", new ClearFunc());
		s.setValue("esc", new EscFunc());
		s.setValue("<", new CompFunc(CompFunc.ComparisonOp.less));
		s.setValue("<eq", new CompFunc(CompFunc.ComparisonOp.lesseq));
		s.setValue("eq", new CompFunc(CompFunc.ComparisonOp.eq));
		s.setValue(">", new CompFunc(CompFunc.ComparisonOp.grtr));
		s.setValue(">eq", new CompFunc(CompFunc.ComparisonOp.grtreq));
		s.setValue("!eq", new CompFunc(CompFunc.ComparisonOp.noteq));
		s.setValue("!", new BoolNotFunc());
		s.setValue("sum", new sumReg());
		s.setValue("avg", new avgReg());
		s.setValue("new", new newSheet());
		s.setValue("save", new saveSheet());
	}
}
