package pw.qxczv.TextExcel.AST;

import java.io.Serializable;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.Values.Value;

public abstract class Expression implements Serializable{
	private static final long serialVersionUID = 999888777666L;

	public abstract Value evaluate(Spreadsheet s);
}
