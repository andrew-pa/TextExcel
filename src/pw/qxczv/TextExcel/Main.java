package pw.qxczv.TextExcel;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;

import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.AST.Parser;
import pw.qxczv.TextExcel.Values.*;
import pw.qxczv.TextExcel.Values.Number;

public class Main {

    public static void main(String[] args) {
    	Spreadsheet s = new Spreadsheet(7,12);
    	for(int c = 0; c < 7; ++c) {
    		for(int r = 1; r < 13; ++r) {
    			s.setValue((char)(c+'A'), r, new Number(c+r));
    		}
    	}
    	JFrame frm = new JFrame("Spreadsheet");
    	pw.qxczv.TextExcel.GUI.SpreadsheetView sv = new pw.qxczv.TextExcel.GUI.SpreadsheetView(s);
    	frm.add(sv);
    	frm.setMinimumSize(new Dimension(650,450));
    	frm.setVisible(true);
    	sv.requestFocus();
    	Scanner userInput = new Scanner(System.in);
    	Parser p = new Parser();
    	while(true) {
    		frm.repaint();
    		System.out.print("> ");
    		String ln = userInput.nextLine();
    		if (ln.equals("exit")){
    			System.out.print("--Goodbye--");
    			break;
    		}
    		else {
	    		try {
					Expression x = p.parse(ln);
					Value v = x.evaluate(s);
					if(v != null) {
						if(v.getClass() == CellReference.class) {
							Value nv = ((CellReference)v).rawDeref(s);
							System.out.println(v.toString() + " = " + ((nv == null) ? "<empty>" : nv.toString())) ;
						}
						else {
							Value r = v.resolve(s);
							
							if(r == null) System.out.println("<empty>"); //silly special cases
							else if((r.getClass() == Function.class || r.getClass().getSuperclass() == Function.class) &&
									((Function)r).argnames.size() == 0) {
								Value rs = ((Function)r).apply(s, new ArrayList<Expression>());
								if(rs != null) System.out.println(rs);
							}
							else System.out.println(v.resolve(s));
						}
					}
				} catch (Exception e) {
					//Change when ship
					e.printStackTrace();
				}
    		}
    		
    	}
    	
    	userInput.close();
    }
}
