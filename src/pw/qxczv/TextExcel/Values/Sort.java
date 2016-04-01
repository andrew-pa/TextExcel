package pw.qxczv.TextExcel.Values;

import pw.qxczv.TextExcel.Spreadsheet;

public class Sort {
	public static void sort(int r, Spreadsheet s){
		Value[] array = new Value[s.colMax];
		for(int i = 0; i < s.colMax ; i ++){
			array[i] = s.valueAt((char)(65+i), r);
		}
		InsertionSort(array);
		for(int i = 0; i < s.colMax; i ++){
			s.setValue((char)(65+i), r, array[i]);
		}
	}
	
	public static void sort(char c, Spreadsheet s){
		Value[] array = new Value[s.rowMax];
		for(int i = 0; i < s.rowMax ; i ++){
			array[i] = s.valueAt(c, i);
		}
		InsertionSort(array);
		for(int i = 0; i < s.colMax; i ++){
			s.setValue(c, i, array[i]);
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
