package pw.qxczv.TextExcel;

import pw.qxczv.TextExcel.Values.Value;

/**
 * Created by s-apalmer on 1/27/2016.
 */
public class Spreadsheet {
    Value[/*columns*/][/*rows*/] cells;
    
    public Value valueAt(char c, int r) {
    	return cells[65-c][r]; //unfortunate reality is that a Spreadsheet is a column-store
    }
}
