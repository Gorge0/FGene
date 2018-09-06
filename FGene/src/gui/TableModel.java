package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import core.enums.JTableMode;

public class TableModel extends AbstractTableModel{

	private ArrayList<String> colunas = new ArrayList<>();
	private ArrayList<String> data = new ArrayList<>();
	private int colCount;
	public JTableMode mode;
	
	public <A>TableModel(List<A> l, JTableMode mode) {
		this.mode = mode;
		this.colunas = mode.genColunas();
		this.data = mode.genData(l);
		this.colCount = mode.amount;
	}

    public String getColumnName(int col) {
        return colunas.get(col);
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
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
}
