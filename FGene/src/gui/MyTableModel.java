package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import core.enums.JTableMode;

public class MyTableModel extends AbstractTableModel{

	private ArrayList<String> colunas = new ArrayList<>();
	private ArrayList<String> data = new ArrayList<>();
	private int colCount;
	public JTableMode mode;
	
	public <A>MyTableModel(List<A> l, JTableMode mode) {
		this.mode = mode;
		this.colunas = mode.genColunas();
		this.data = mode.genData(l);
		this.colCount = mode.amount;
	}
	
    public String getColumnName(int col) {
        return colunas.get(col);
    }
    
    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
    	// TODO Auto-generated method stub
    	super.fireTableRowsUpdated(firstRow, lastRow);
    }
    
	@Override
	public int getColumnCount() {
		return colunas.size();
	}

	@Override
	public int getRowCount() {
		return data.size()/colCount;
	}

	@Override
	public Object getValueAt(int x, int y) {
		return data.get(this.colCount*x + y);
	}
	
	@Override
	public Class<?> getColumnClass(int arg0) {
		// TODO Auto-generated method stub
//		String s = (String)getValueAt(0, arg0);
//		try{
//			Double.parseDouble(s);
//			
//		}catch(NumberFormatException e){
//			return String.class;
//		}
		return Double.class;
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
}
