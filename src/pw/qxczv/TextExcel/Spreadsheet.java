package pw.qxczv.TextExcel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;


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
	public int colMax;
	public int rowMax;
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
			System.out.println("<That Index Is Out Of Bounds>");
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
		cells = new Value[Math.abs(col)][Math.abs(row)];
	}

	public void Save(String v) {
		SpreadsheetAccess.Save(v,this);
	}
	
	public void load(String n){
		cells = SpreadsheetAccess.getSpreadsheet(n).cells;
	}

	public static void deleteFolder() {
		SpreadsheetAccess.deleteFolder();
		
	}

	public void delete(String v) {
		SpreadsheetAccess.delete(v);
		
	}
	public void sort(int r){
		Value[] array = new Value[cells.length];
		for(int i = 0; i < cells.length ; i ++){
			array[i] = this.valueAt((char)(65+i), r);
		}
		InsertionSort(array);
		for(int i = 0; i < cells.length; i ++){
			this.setValue((char)(65+i), r, array[i]);
		}
	}
	
	public void sort(char c){
		Value[] array = new Value[cells[0].length];
		for(int i = 0; i < cells[0].length ; i ++){
			array[i] = this.valueAt(c, i+1);
		}
		InsertionSort(array);
		for(int i = 0; i < cells[0].length; i ++){
			this.setValue(c, i+1, array[i]);
		}

	}
	
	public static void InsertionSort(Value[] array) {
		//Written by me for the Sorting Comparison homework
		Value temp;
		for (int i = 1; i < array.length; i++) {
			temp = array[i];
			int j = 1;
			while (j != i + 1 && temp.compareTo(array[i - j]) == -1) {
				array[i + 1 - j] = array[i - j];
				j++;
			}
			array[i + 1 - j] = temp;
		}
	}
}
