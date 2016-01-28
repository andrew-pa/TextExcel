package pw.qxczv.TextExcel.AST;

import pw.qxczv.TextExcel.Values.*;
import pw.qxczv.TextExcel.Values.Number;

public class Parser {
	String inp; int i;
	
	public Parser() { inp = null; i = -1; }
	
	public Expression parse(String s) throws Exception {
		inp = s;
		i = 0;
		return parse();
	}
	
//--private parser stuff------------------------------------------------
    char nextChar() {
        if(!moreCharp()) return '\0';
        return inp.charAt(i++);
    }

    char peekChar() {
        if((i+1) > inp.length()) return '\0';
        return inp.charAt(i+1);
    }

    void nextWhitespace() {
        while(moreCharp() && Character.isWhitespace(currChar())) i++;
    }

    char currChar() {
        if(!moreCharp()) return '\0';
        return inp.charAt(i);
    }

    boolean endOfTokenp() {
        char c = currChar();
        return !moreCharp() || Character.isWhitespace(c)
                || c == '+' || c == '-' || c == '*' || c == '/' || c == '$' 
                || c == '(' || c == ')' || c == ':' || c == '[' || c == ']' || c == ',' || c == '}';
    }

    boolean moreCharp() {
        return i < inp.length();
    }
    
    Expression parseTerm() throws Exception {
    	Expression xp = null;
    	nextWhitespace();
    	if(currChar() == '(') {
    		nextChar();
    		nextWhitespace();
    		Term v = new ValueTerm(new DeferredExpression(parse()));
    		nextWhitespace();
    		assert currChar() == ')';
    		nextChar();
    		return v;
    	}
    	else if(currChar() == '[') {
    		nextChar();
    		xp = parse();
    		nextWhitespace();
    		assert currChar() == ']';
    		nextChar();
    	} else if(Character.isDigit(currChar()) || currChar() == '-' && Character.isDigit(peekChar())) {
    		StringBuffer sb = new StringBuffer();
    		while(!endOfTokenp()) {
    			sb.append(currChar());
    			nextChar();
    		}
    		return new ValueTerm(new Number(Double.parseDouble(sb.toString())));
    	}
    	return null;
    }
    
    Expression parse() throws Exception {
    	nextWhitespace();
    	
    	Expression xp = parseTerm();
    	return xp;
    }
}
