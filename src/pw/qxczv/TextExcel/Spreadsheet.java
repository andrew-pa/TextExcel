package pw.qxczv.TextExcel;

import java.util.HashMap;

import pw.qxczv.TextExcel.Values.Value;

/**
 * Created by s-apalmer on 1/27/2016.
 */
public class Spreadsheet {
	Value[/* columns */][/* rows */] cells;
	public HashMap<String, Value> globalValues;

	public Spreadsheet(int col, int row) {
		cells = new Value[col][row];
		globalValues = new HashMap<>();
	}

	public Value valueAt(char c, int r) {
		return cells[c-65][r-1]; // unfortunate reality is that a Spreadsheet is
									// a column-store
	}

	public String toString() {
		String returnedValue = "";
		for (int r = 0; r < cells[0].length + 1; ++r) {
			for (int c = 0; c < cells.length + 1; ++c) {
				if(r == 0 && c == 0){
					returnedValue += "            |";
				} else if (r == 0) {
					returnedValue += "     " + ((char) (c + 64)) + "      |";
				} else if (c == 0) {
					String ofR = r + "";
					for(int i = 0;i < (6 - ofR.length()/2) - ofR.length()%2; i ++){
						returnedValue += " ";
					}
					returnedValue += ofR;
					for(int i = 0;i < (6 - ofR.length()/2); i ++){
						returnedValue += " ";
					}
					returnedValue += "|";
				} else {
					if(cells[c - 1][r - 1] == null){ returnedValue += format("") + "|"; }
					else{ returnedValue += format(cells[c - 1][r - 1].toCellRepString(this)) + "|"; }
				}
			}
			//New Line:
			returnedValue +="\n";
			for(int i = 0; i < cells.length + 1; i ++){
				returnedValue += "------------+";
			}
			returnedValue +="\n";
		}
		
		return returnedValue;
	}
	
	public String format(String s){
		//This will cap the string at 12 characters
		if(s.length() > 11){
			s = s.substring(0,11);
			s += ">";
		}
		while(s.length() < 12){
			s += " ";
		}
		return s;
	}
	
	public void print(){
		System.out.print(this.toString());
	}
	

	public void setValue(char c, int r, Value rv) {
		cells[c-65][r-1] = rv; // sad reality is that sometimes you just need a
								// setter, but these aren't so bad because they
								// provide translation between col char names
								// and indices
	}
	
	public void clear(char c, int r){
		cells[c-65][r-1] = null;
	}

	public void clear() {
		for(int c = 0; c < cells.length; c ++){
			for(int r = 0; r < cells[c].length; r ++){
				cells[c][r] = null;
			}
		}
	}
}
