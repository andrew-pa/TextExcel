package pw.qxczv.TextExcel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import pw.qxczv.TextExcel.FileStreams.SpreadsheetAccess;
import pw.qxczv.TextExcel.Values.TrueValue;
import pw.qxczv.TextExcel.Values.Value;

/**
 * Created by s-apalmer on 1/27/2016.
 */
public class Spreadsheet implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 123789L;
	Value[/* columns */][/* rows */] cells;
	LinkedList<HashMap<String, Value>> globalValues;

	public Spreadsheet(int col, int row) {
		cells = new Value[col][row];
		globalValues = new LinkedList<>();
		globalValues.add(new HashMap<>());
		
		//set up some important initial globals
		setValue("T", TrueValue.get());
		setValue("nil", null);
		
		//grab all the built-in functions
		BuiltinFunctions.apply(this);
	}

	public Value valueAt(char c, int r) {
		try {
		return cells[c-65][r-1]; // unfortunate reality is that a Spreadsheet is
									// a column-store
		} catch (IndexOutOfBoundsException e){
			System.out.println("<that index it out of bounds>");
			return null;
		}
	}
	
	public Value valueFor(String name) {
		for(HashMap<String,Value> s : globalValues)
			if(s.containsKey(name))
				return s.get(name);
		return null;
	}
	public void setValue(String name, Value v) {
		for(HashMap<String,Value> s : globalValues) //if we've already added this in a higher scope, set that value instead
			if(s.containsKey(name)) {
				s.put(name, v);
				return;
			}
		globalValues.getFirst().put(name, v); //add the value to the current, lowest scope
	}
	public void pushScope(HashMap<String, Value> initialValues) {
		globalValues.push(initialValues);
	}
	public void popScope() {
		globalValues.pop();
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
		//This will cap the string at 11 characters
		try{
		if(s.length() > 11){
			s = s.substring(0,11);
			s += ">";
		}
		while(s.length() < 12){
			s += " ";
		}
		return s;
		}catch (NullPointerException e) {
			return "";
		}
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
	
	public void newSize(int col, int row) {
		cells = new Value[col][row];
	}

	public void Save(String v) {
		SpreadsheetAccess.Save(v,this);
	}
}
