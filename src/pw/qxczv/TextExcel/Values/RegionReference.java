package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class RegionReference extends LValue {

	public char colStrtIdx;
	public int rowStrtIdx;
	public char colEndIdx;
	public int rowEndIdx;

	public RegionReference(char c, int r, char colEnd, int rowEnd) {
		// colEnd and rowEnd are inclusive
		colStrtIdx = c;
		rowStrtIdx = r;
		colEndIdx = colEnd;
		rowEndIdx = rowEnd;
	}

	public Value[] rawDeref(Spreadsheet s) {
		return getValueArray(s);
	}

	@Override
	public void assign(Spreadsheet s, Value v) {
		for (int row = rowStrtIdx; row <= rowEndIdx; row++) {
			for (int col = (int) colStrtIdx; col <= (int) colEndIdx; col++) {
				s.setValue((char) col, row, v);
				// (sigh) yes I know I used a setter :'(
			}
		}
	}

	@Override
	public int compareTo(Value o) {
		// Andrew says that they will never be compared. RIP comparable Regions
		return -1;
	}

	@Override
	public String toString() {
		return colStrtIdx + "" + rowStrtIdx + " - " + colEndIdx + rowEndIdx;
	}

	private Value[] getValueArray(Spreadsheet s) {
		//Perhaps one day this method will be used... XD
		Value[] arry = new Value[(((int) colEndIdx + 1) - ((int) colStrtIdx)) * (rowEndIdx + 1 - rowStrtIdx)];
		int i = 0;
		Value temp;
		for (int row = rowStrtIdx; row <= rowEndIdx; row++) {
			for (int col = (int) colStrtIdx; col <= (int) colEndIdx; col++) {
				temp = s.valueAt((char) col, row);
				if (temp != null) {
					arry[i] = temp;
				} else {
					arry[i] = null;
				}
				i++;
			}
		}
		return arry;
	}

	public Value sum(Spreadsheet s) {
		return new Number(totalNum(s));
	}
	
	public Value average(Spreadsheet s){
		double num = totalNum(s);
		int denom = (((int) colEndIdx + 1) - ((int) colStrtIdx)) * (rowEndIdx + 1 - rowStrtIdx);
		return new Number(num/((double)denom));
	}
	
	private double totalNum(Spreadsheet s){
		double returnedValue = 0;
		Number tempNum;
		Value tempValue;
		for (int row = rowStrtIdx; row <= rowEndIdx; row++) {
			for (int col = (int) colStrtIdx; col <= (int) colEndIdx; col++) {
				tempValue = s.valueAt((char) col, row);
				if (tempValue != null){
						tempValue.resolve(s);
						if(tempValue.getClass() == Number.class) {
						tempNum = (Number) tempValue;
						returnedValue += tempNum.v;
					}
				}
			}
		}
		return returnedValue;
	}
}
