package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class RegionReference extends Value{

	public char colStrtIdx; public int rowStrtIdx; public char colEndIdx; public int rowEndIdx;
	
	public RegionReference (char c, int r, char colEnd, int rowEnd){
		//colEnd and rowEnd are inclusive
		colStrtIdx = c; rowStrtIdx = r; colEndIdx = colEnd; rowEndIdx = rowEnd;
	}
	
	@Override
	public Value resolve(Spreadsheet s) {
		Value[] arry = getValueArray(s);
	}
	
	public Value[] rawDeref(Spreadsheet s) {
		return getValueArray(s);
	}
	
	@Override
	public void assign(Spreadsheet s, Value[]) {
		
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
