package pw.qxczv.TextExcel.AST;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import pw.qxczv.TextExcel.Values.*;
import pw.qxczv.TextExcel.Values.Number;

public class Parser {
	String inp; int i;
	
	public Parser() { inp = null; i = -1; }
	
	public Expression parse(String s) throws Exception {
		inp = s;
		i = 0;
		return parse(true);
	}
	
//--private parser stuff------------------------------------------------
	/* 	AST:
	 	<expr> := <term> | <expr> + <expr> | <expr> - <expr> | '['<expr>']'
				| <id> '=' <expr> | <literal> (<expr>' ')+ | <expr> '?' <expr> ':' <expr>
		<id> :=  #cell_ref | #name
		<literal> := #number | #string | <id> | '(' <expr> ')' | '{' ('|' (#name',')+ '|') <expr> '}'
		<term> := <literal> | <expr> * <expr> | <expr> / <expr>
	*/
	
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

    boolean endChar(char c) {
    	return c == '+' || c == '-' || c == '*' || c == '/' || c == '=' 
                || c == '(' || c == ')' || c == ':' || c == '[' || c == ']' 
                || c == ',' || c == '{' || c == '|' || c == '}' || c == '$' || c == '?' || c == ':';
    }
    boolean endOfExprp() {
        char c = currChar();
        return !moreCharp() 
                || c == '+' || c == '-' || c == '*' || c == '/' || c == '=' 
                        || c == ')' || c == ':' || c == ']' 
                        || c == ',' || c == '|' || c == '}' || c == '$' || c == ':';
    }
    boolean endOfTokenp() {
        char c = currChar();
        return !moreCharp() || Character.isWhitespace(c) || endChar(c);
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
    		Term v = new ValueTerm(new DeferredExpression(parse(true)));
    		nextWhitespace();
    		assert currChar() == ')';
    		nextChar();
    		return v;
    	} else if(currChar() == '[') {
    		nextChar();
    		xp = parse(true);
    		nextWhitespace();
    		assert currChar() == ']';
    		nextChar();
    	} else if(currChar() == '{') {
    		nextChar();
    		nextWhitespace();
    		ArrayList<String> an = new ArrayList<>();
    		if(currChar() == '|') {
    			nextChar();
				StringBuffer sb = new StringBuffer();
    			while(currChar() != '|') {
        			nextWhitespace();
    	    		while(!endOfTokenp()) {
    	    			sb.append(currChar());
    	    			nextChar();
    	    		}
    	    		an.add(sb.toString());
    	    		sb.setLength(0);
    			}
    			assert currChar() == '|';
    			nextChar();
    			nextWhitespace();
    		}
    		xp = new ValueTerm(new Function(an, parse(true)));
    		nextWhitespace();
    		assert currChar() == '}';
    		nextChar();
    	} else if(Character.isDigit(currChar()) || currChar() == '-' && Character.isDigit(peekChar())) {
    		StringBuffer sb = new StringBuffer();
    		sb.append(currChar()); nextChar();
    		while(!endOfTokenp()) {
    			sb.append(currChar());
    			nextChar();
    		}
    		xp = new ValueTerm(new Number(Double.parseDouble(sb.toString())));
    	} else if(currChar() == '"') {
    		nextChar();
    		StringBuffer sb = new StringBuffer();
    		while(currChar() != '"') {
    			sb.append(currChar());
    			nextChar();
    		}
    		nextChar();
    		xp = new ValueTerm(new StringValue(sb.toString()));
    	} else {
    		//grab a token
    		StringBuffer sb = new StringBuffer();
    		while(!endOfTokenp()) {
    			sb.append(currChar());
    			nextChar();
    		}
			xp = new Identifier(sb.toString());
			
    	}
    	nextWhitespace();
    	if(currChar() == '*') {
    		nextChar();
    		xp = new MulTerm(xp, parseTerm());
    	} else if(currChar() == '/') {
    		nextChar();
    		xp = new DivTerm(xp, parseTerm());
    	}
    	
    	return xp;
    }
    
    Expression parse(boolean allowFuncInk) throws Exception {
    	nextWhitespace();
    	Expression xp = parseTerm();
    	nextWhitespace();
    	if(currChar() == '+') {
    		nextChar(); 
    		xp = new AddExpression(xp, parse(true));
    	} else if(currChar() == '-') {
    		nextChar();
    		xp = new SubExpression(xp, parse(true));
    	} else if(currChar() == '=') {
    		nextChar();
    		xp = new AssignmentExpression(xp, parse(true));
    	} 
    	if(moreCharp() && !endOfExprp()) {
    		nextWhitespace();
    		if(currChar() == '?') {
    			nextChar();
    			Expression tx = parse(true);
    			nextWhitespace();
    			assert currChar() == ':';
    			nextChar();
    			Expression fx = parse(true);
    			xp = new ConditionalExpression(xp, tx, fx);
    		}
    		else if(allowFuncInk) {
	    		LinkedList<Expression> args = new LinkedList<>();
	    		while(!endOfExprp()) {
	    			nextWhitespace();
	    			args.add(parse(false));
	    		}
	    		xp = new FunctionInvocationExpression(xp, args);
	    	}
    	}
    	return xp;
    }
}
