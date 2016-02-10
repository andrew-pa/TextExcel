package pw.qxczv.TextExcel.GUI;

import java.awt.AWTEvent;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

import pw.qxczv.TextExcel.Spreadsheet;
import pw.qxczv.TextExcel.AST.AssignmentExpression;
import pw.qxczv.TextExcel.AST.Expression;
import pw.qxczv.TextExcel.AST.Identifier;
import pw.qxczv.TextExcel.AST.Parser;
import pw.qxczv.TextExcel.Values.Value;

public class SpreadsheetView extends Component implements KeyListener {
	
	public Spreadsheet model;
	
	int cur_col, cur_row;
	Parser p; StringBuffer curbuf;
	
	public SpreadsheetView(Spreadsheet m) {
		model = m;
		this.addKeyListener(this);
		cur_col = 0; cur_row = 1;
		p = new Parser();
		curbuf = new StringBuffer();
	}
	
	int column_width = 100;
	int row_height = 20;
	
	@Override
	public void paint(Graphics g) {
		int height = model.cells[0].length*row_height;
		int width = model.cells.length*column_width;
		for(int c = 0; c < model.cells.length; ++c) {
			g.drawLine(c*column_width, 0, c*column_width, height);
			for(int r = 0; r < model.cells[c].length; ++r) {
				if((curbuf.length() == 0 || c != cur_col || r != cur_row) &&  model.cells[c][r] != null) g.drawString(model.cells[c][r].toCellRepString(model), c*column_width + 4, r*row_height + 14);
				g.drawLine(0, r*row_height, width, r*row_height);
			}
		}
		Value cv = model.valueAt((char)('A'+cur_col), cur_row+1);
		if(cv != null) g.drawString(cv.toString(), 10, this.getHeight() - 30);
		g.setColor(Color.BLUE);
		g.drawRect(cur_col*column_width, cur_row*row_height, column_width, row_height);
		g.drawRect(cur_col*column_width-1, cur_row*row_height-1, column_width+2, row_height+2);
		g.drawString(curbuf.toString(), cur_col*column_width+4, cur_row*row_height+14);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		if(kc == KeyEvent.VK_LEFT || kc == KeyEvent.VK_RIGHT || kc==KeyEvent.VK_UP || kc==KeyEvent.VK_DOWN || kc==KeyEvent.VK_ENTER) {

			if(kc == KeyEvent.VK_LEFT) cur_col--;
			else if(kc == KeyEvent.VK_RIGHT) cur_col++;
			else if(kc == KeyEvent.VK_UP) cur_row--;
			else if(kc == KeyEvent.VK_DOWN) cur_row++;
			else if(kc == KeyEvent.VK_ENTER) cur_row++;
			if(curbuf.length() > 0) {
				String cbf = curbuf.toString().trim();
				if(cbf.length() > 0) {
					Expression v = null;
					try {
						v = p.parse(cbf);
					} catch(Exception exc) { }
					if(v != null) {
						//String cr = ('A'+cur_col) + "" + cur_row;
						model.setValue((char)('A'+cur_col), cur_row, v.evaluate(model));
						//v = new AssignmentExpression(new Identifier(cr), v);
						//v.evaluate(model).resolve(model);
						curbuf.setLength(0);
					}
				}
			}
		}
		this.repaint();	
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
			curbuf.append(e.getKeyChar());
		
	}
}
