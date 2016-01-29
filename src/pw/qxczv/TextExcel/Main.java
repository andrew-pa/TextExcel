package pw.qxczv.TextExcel;

import java.util.Scanner;

import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.AST.Parser;

public class Main {

    public static void main(String[] args) {
    	Spreadsheet s = new Spreadsheet(7,12);
    	Scanner userInput = new Scanner(System.in);
    	Parser p = new Parser();
    	while(userInput.hasNextLine()) {
    		String ln = userInput.nextLine();
    		if(ln.equals("print")){
    			s.print();
    		}
    		else if (ln.equals("quit")){
    			break;
    		} 
    		else if (ln.equals("clear")){
    			s.clear();
    		} else {
	    		try {
					Expression x = p.parse(ln);
					System.out.println(x);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    		
    	}
    	
    	userInput.close();
    }
}
