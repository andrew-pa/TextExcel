package pw.qxczv.TextExcel;

import java.util.Scanner;

import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.AST.Parser;

public class Main {

    public static void main(String[] args) {
    	Scanner s = new Scanner(System.in);
    	Parser p = new Parser();
    	while(s.hasNextLine()) {
    		String ln = s.nextLine();
    		try {
				Expression x = p.parse(ln);
				System.out.println(x);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		
    	}
    }
}
