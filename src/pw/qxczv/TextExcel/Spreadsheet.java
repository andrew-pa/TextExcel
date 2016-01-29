package pw.qxczv.TextExcel;

import pw.qxczv.TextExcel.Values.Value;

/**
 * Created by s-apalmer on 1/27/2016.
 */
public class Spreadsheet {
	Value[/* columns */][/* rows */] cells;

	public Spreadsheet(int col, int row) {

		cells = new Value[col][row];

	}

	public Value valueAt(char c, int r) {
		return cells[65 - c][r]; // unfortunate reality is that a Spreadsheet is
									// a column-store
	}

	public String toString() {
		String returnedValue = "";
		for (int r = 0; r < cells[0].length + 1; ++r) {
			for (int c = 0; c < cells.length + 1; ++c) {
				if(r == 0 && c == 0){
					returnedValue += "            |";
				} else if (r == 0) {
					returnedValue += "|     " + ((char) c + 65) + "      |";
				} else if (c == 0) {
					returnedValue += "     " + r + "      ";
				} else {
					returnedValue += "|" +
							//Add Cell Value
							"|";
				}
			}
			//New Line:
			returnedValue +="\n";
			for(int i = 0; i < cells.length; i ++){
				returnedValue += "------------+";
			}
			returnedValue +="\n";
		}
		
		return returnedValue;
	}
	
	public void print(){
		System.out.print(this.toString());
	}

	public void setValue(char c, int r, Value rv) {
		cells[65 - c][r] = rv; // sad reality is that sometimes you just need a
								// setter, but these aren't so bad because they
								// provide translation between col char names
								// and indices
	}
}
