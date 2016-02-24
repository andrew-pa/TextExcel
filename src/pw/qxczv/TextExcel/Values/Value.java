package pw.qxczv.TextExcel.Values;

import java.io.Serializable;

import pw.qxczv.TextExcel.Spreadsheet;

/**
 * Created by s-apalmer on 1/27/2016.
 */
public abstract class Value implements Comparable<Value>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 123789L;

	public Value resolve(Spreadsheet s) { return this; }
    
    public String toCellRepString(Spreadsheet s) { return toString(); }
}
