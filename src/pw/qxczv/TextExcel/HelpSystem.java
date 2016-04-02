package pw.qxczv.TextExcel;

import java.util.ArrayList;;

public class HelpSystem {
	public static class HelpPage {
		public String topic;
		public String contents;
		public HelpPage(String T, String C) {
			topic = T;
			contents = C;
		}
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("-- "+ topic + " --\n");
			sb.append(contents);
			return sb.toString();
		}
	}
	public static ArrayList<HelpPage> help_pages;
	
	public static void init() {
		//Initializes all help pages
		help_pages = new ArrayList<>();
		
		help_pages.add(new HelpPage("basics", 
				"* the prompt evaluates the expression given and prints the resulting value\n" +
				"* basic math works as expected (1+1 will print 2)\n"+
				"* strings can be specified using quotes: \"Hi\"\n"+
			    "* spreadsheets contain cells and global variables. global variables are simpily reference by their name\n"+
				"* cells in the spreadsheet can be referenced like: a1 or C9\n"+
				"* global variables and cells can be assigned like: aNumber = 7 or H3 = 288\n"+
				"* to insert a formula that is recalculated when the spreadsheet is printed, use parens like: a1 = (a2 + a3)\n"+
				"* Square brackets [] can also be used as parens, but don't automatically recalculate\n" +
				"* functions are invoked like: sum a1-a2\n"+
				"* see topic builtin_functions for more functions that are builtin\n" +
				"* subtracting to cell names results in the rectanglar region they enclose, which can be summed or averaged with functions sum or avg\n"+
				"* functions can be user defined, see user_functions topic\n" +
				"* branches and conditionals are also implemented, see topic branching\n"));
		help_pages.add(new HelpPage("user_functions", 
				"Users can define functions by assigning a lambda expression to a global variable\n" +
				"lambda expressions look like { | 'argument names' | 'body expression' }\n" +
				"for example, to make a function named 'square', type 'square = { | x | x*x }'\n"+
				"another example with branching: factorial = {|n| [eq n 0] ? 1 : n * [factorial n-1]}\n"));
		help_pages.add(new HelpPage("branching", 
				"branches are made with a tertiary branch expression like in C-like languages: 'condition' ? 'true expression' : 'false expression'\n" +
				"the following conditionals are provided as builtin functions: '<', '<eq', 'eq', '>', '>eq', '!eq'\n"+
				"when using a function (such as the builtin comparision functions) it is necessary to use square brackets around the condition expression in a branch\n"+
				"any value that is not null is considered to be true, and null is false\n"+
				"global variable 'T' is always bound to the true value, and global variable 'nil' to the null value\n"+
				"the not ('!') function returns null when given a non-null value and the 'T' value when given null\n"+
				"for example: '[<eq a1 0] ? a2 : a3'  will yield the value of a2 if the value of a1 is less than or equal to 0, or a3 otherwise\n"));
		help_pages.add(new HelpPage("builtin_functions", 
				"print -- prints the current spreadsheet\n"+
				"clear -- clears the current spreadsheet\n"+
				"clear 'a cell name' -- clears out only that cell in the spreadsheet\n"+
				"esc 'an expr' -- evaluate a expression\n"+
				"comparison functions: see topic branching\n"+
				"new 'number' + 'number' -- creates a new spreadsheet with specified dimensions\n"+
				"sum 'cell region' -- calculate the sum of a rectangular region in the spreadsheet, defined by subtracting the names of two cells, like a2-c3\n"+
				"avg 'cell region' -- calculate the average of a rectangular region in the spreadsheet, see above\n"+
				"sort 'row number' or sort 'column Letter' -- rewrites the spreadsheet putting the row/column in order left-right\top-bottom; must be all numbers/strings\n"+
				"file load/save: see topic files\n"
				));
		help_pages.add(new HelpPage("files", 
				"Users can save spreadsheets to their main drive.\n\n" +
				"save \"Name of Spreadsheet\" -- saves the spreadsheet under that name\n" +
				"load \"Name of Spreadsheet\" -- loads the spreadsheet under that name\n"+
				"delete \"Name of Spreadsheet\" -- deletes the spreadsheet under that name\n"+
				"kill -- deletes the entire folder SavedSpreadsheets\n"+
				"Users can feel free to view the SavedSpreadsheets in file explorer of their main drive"));
	}
	
	public static String mainHelpMessage() {
		if(help_pages == null) init();
		StringBuilder sb = new StringBuilder();
		sb.append("-- HELP --\n\n");
		sb.append("type \"help <topic>\" to access that topic's page\ntype \"help <topic> all\" to print all pages\n\n");
		sb.append("topics:\n");
		for(HelpPage hp : help_pages) {
			sb.append("\t");
			sb.append(hp.topic);
		}
		return sb.toString();
	}
	
	public static String helpMessage(String t) {
		if(help_pages == null) init();
		if(t != null){
			for(HelpPage page: help_pages){
				if(page.topic.equals(t)){
					return page.toString();
				}
			}
		}
		return "";
	}
}
