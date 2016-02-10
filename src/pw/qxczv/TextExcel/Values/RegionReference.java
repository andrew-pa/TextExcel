package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class RegionReference extends LValue{

	public char colStrtIdx; public int rowStrtIdx; public char colEndIdx; public int rowEndIdx;
	
	public RegionReference (char c, int r, char colEnd, int rowEnd){
		//colEnd and rowEnd are inclusive
		colStrtIdx = c; rowStrtIdx = r; colEndIdx = colEnd; rowEndIdx = rowEnd;
	}
	
	public Value[] rawDeref(Spreadsheet s) {
		return getValueArray(s);
	}
	
	@Override
	public Value resolve(Spreadsheet s) {
		//!!! THIS IS A CRAZZZZY HAX !!!
		// This makes subtraction like ``` a1 - a2 ``` still work when evaluated in a context that doesn't require a region
		// Functions that do require a region should avoid this by not resolving the value
		// User-defined functions can be invoked with a region using deferred expressions, I think.. 
		Value sv = s.valueAt(colStrtIdx, rowStrtIdx).resolve(s);
		Value ev = s.valueAt(colEndIdx, rowEndIdx).resolve(s);
		if(sv != null && ev != null && sv.getClass() == Number.class && ev.getClass() == Number.class) {
			Number sn = (Number)sv; Number en = (Number)ev;
			return new Number(sn.v - en.v);
		} else {
			return super.resolve(s);
		}
	}
	
	@Override
	public void assign(Spreadsheet s, Value v) {
		for(int row = rowStrtIdx; row <= rowEndIdx; row ++){
			for(int col = (int)colStrtIdx; col <= (int)colEndIdx; col ++){
				s.setValue((char)col, row, v);
				//(sigh) yes I know I used a setter :'(
			}
		}
	}
	
	
	@Override
	public int compareTo(Value o) {
		// Andrew says that they will never be compared. RIP comparable Regions
		return -1;
	}
	
	@Override
	public String toString(){
		return colStrtIdx + "" + rowStrtIdx + " - " + colEndIdx + rowEndIdx;
	}
	
	private Value[] getValueArray(Spreadsheet s){
		Value[] arry = new Value[(((int)colEndIdx)-((int)colStrtIdx)) * (rowEndIdx - rowStrtIdx) + 1];
		int i = 0;
		Value temp;
		//Yes I know this is super messy --- Feel free to clean up
		for(int row = rowStrtIdx; row <= rowEndIdx; row ++){
			for(int col = (int)colStrtIdx; col <= (int)colEndIdx; col ++){
		//Also feel free to change the order of the nested loop
				temp = s.valueAt((char)col, row);
				if(temp != null){
					arry[i] = temp;
				}else{
					arry[i] = null;
				}
				i++;
			}
		}
		return arry;
	}
	
}
