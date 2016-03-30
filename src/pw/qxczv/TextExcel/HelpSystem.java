package pw.qxczv.TextExcel;

import java.util.ArrayList;;

public class HelpSystem {
	public static class HelpPage {
		public String topic;
		public String[] contents;
		public HelpPage(String T, String[] C) {
			topic = T;
			contents = C;
		}
	}
	public static ArrayList<HelpPage> help_pages;
	
	public static void init() {
		help_pages = new ArrayList<>();
	}
	
	public static String mainHelpMessage() {
		if(help_pages == null) init();
		StringBuilder sb = new StringBuilder();
		sb.append("-- HELP --\n\n");
		sb.append("type \"help <topic>\" to access that topic's page\ntype \"help <topic> <page>\" to obtain pages beyond page 0\ntype \"help <topic> all\" to print all pages\n\n");
		sb.append("topics:\n");
		for(HelpPage hp : help_pages) {
			sb.append("\t");
			sb.append(hp.topic);
			sb.append(" (" + hp.contents.length + " pages)\n");
		}
		return sb.toString();
	}
}
