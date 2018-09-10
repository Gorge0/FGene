package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Season;
import core.enums.JTableMode;

public class MyTable extends JTable{
	
	public MyTable(MyTableModel mod) {
		super(mod);
		customizeTable();
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				ArrayList<String> sel = getSelectedRowsByName(0);
				if(sel.size() != 0){
					PilotoPanel.setSelected(sel);
				}
			}
		});
	}
	
	public MyTable(MyTableModel mod, MyTable t, Integer col) {
		super(mod);
		customizeTable();
		getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				// TODO Auto-generated method stub
				setSelectedRowsByName(t, getSelectedRowsByName(col),0);
				PilotoPanel.setSelected(getSelectedRowsByName(col));
			}
		});
	}

	public ArrayList<String> getSelectedRowsByName(Integer index){
		ArrayList<String> names = new ArrayList<>();
		for(int i : getSelectedRows()){
			names.add((String)getValueAt(i, index));
		}
		return names;
	}
	
	public void setSelectedRowsByName(JTable t, ArrayList<String> list, Integer index){
		for(String s : list){
			for(int i=0; i<t.getRowCount();i++){
				if(((String)t.getValueAt(i, 0)).equals(s)){
					t.changeSelection(i, 0, false, false);
					break;
				}
			}
		}
	}
	
	protected void customizeTable(){
		setAutoCreateRowSorter(true);
		setPreferredScrollableViewportSize(((MyTableModel)getModel()).mode.d);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setFont(getFont().deriveFont(Font.BOLD));
				table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 18));
				
				MyTableModel model = (MyTableModel)getModel();
				if(model.mode.equals(JTableMode.PPPERPLAYOFF) || model.mode.equals(JTableMode.EPPERPLAYOFFPILOTO)){
					loop:
						for(int k=0;k<model.getRowCount();k++){
							Season s = FGene.getSeasonByAno(model.getValueAt(k, 0).toString());
							Piloto p = null;
							if(model.mode.equals(JTableMode.PPPERPLAYOFF)){
								p = s.getPilotByName(PilotoPanel.selectedPiloto);
							}else{
								Equipe e = s.getEquipeByName(EquipePanel.selectedEquipe);
								p = e.piloto1;
								if(!s.playoffs.contains(p)){
									p = e.piloto2;
								}
							}
							if(s.playoffs.contains(p)){
								if(row == k){
									switch (s.playoffs.indexOf(p)) {
									case 0:
										c.setBackground(Color.decode("#FFD700"));
										break loop;
									case 1:
										c.setBackground(Color.decode("#C0C0C0"));
										break loop;
									case 2:
										c.setBackground(Color.decode("#CD7F32"));
										break loop;
									}
								}else{
									c.setBackground(Color.WHITE);
								}
							}
						}
				}
				if(model.mode.equals(JTableMode.EPPERPLAYOFF) || model.mode.equals(JTableMode.PPPERPLAYOFFEQUIPE)){
					loop:
						for(int k=0;k<model.getRowCount();k++){
							Season s = FGene.getSeasonByAno(model.getValueAt(k, 0).toString());
							ArrayList<Equipe> eqs = s.getEqsPlayoff(); 
							Equipe e = null;
							if(model.mode.equals(JTableMode.EPPERPLAYOFF)){
								e = s.getEquipeByName(EquipePanel.selectedEquipe);
							}else{
								e = FGene.getEquipeOfPiloto(s.equipes, FGene.getPiloto(s.pHere(), PilotoPanel.selectedPiloto));
							}
							if(eqs.contains(e)){
								if(row == k){
									switch (eqs.indexOf(e)) {
									case 0:
										c.setBackground(Color.decode("#FFD700"));
										break loop;
									case 1:
										c.setBackground(Color.decode("#C0C0C0"));
										break loop;
									case 2:
										c.setBackground(Color.decode("#CD7F32"));
										break loop;
									}
								}else{
									c.setBackground(Color.WHITE);
								}
							}
						}
				}
				if(model.mode.equals(JTableMode.SEASONS) || model.mode.equals(JTableMode.SEASONSP) || 
						model.mode.equals(JTableMode.SEASONSEQ) || model.mode.equals(JTableMode.SEASONSPEQ)){
					switch (column) {
					case 1:
						c.setBackground(Color.decode("#FFD700"));
						break;
					case 2:
						c.setBackground(Color.decode("#C0C0C0"));
						break;
					case 3:
						c.setBackground(Color.decode("#CD7F32"));
						break;
					default:
						c.setBackground(Color.WHITE);
						break;
					}
				}
				if(model.mode.equals(JTableMode.ALLEQUIPES) || model.mode.equals(JTableMode.PPLAYOFF)){
					switch (column) {
					case 1:
						c.setBackground(Color.decode("#FFD700"));
						break;
					case 2:
						c.setBackground(Color.decode("#C0C0C0"));
						break;
					case 3:
						c.setBackground(Color.decode("#CD7F32"));
						break;
					case 5:
						c.setBackground(Color.decode("#FFD700"));
						break;
					case 6:
						c.setBackground(Color.decode("#C0C0C0"));
						break;
					case 7:
						c.setBackground(Color.decode("#CD7F32"));
						break;
					default:
						c.setBackground(Color.WHITE);
						break;
					}
				}
					return this;
				}
		};
        r.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < getColumnCount(); i++){
        	getColumnModel().getColumn(i).setCellRenderer(r);
        	
//        	Size of Numbers columns of all tables
//        	if(t.getModel().getColumnClass(i).equals(Double.class)){
//        		t.getColumnModel().getColumn(i).setMaxWidth(35);
//        	}
        	
        	TableRowSorter<MyTableModel> rowSorter = (TableRowSorter<MyTableModel>)getRowSorter();
        	rowSorter.setComparator(i, new Comparator<String>() {
        		
        		@Override
        		public int compare(String o1, String o2){
        			try{
        				return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
        			}catch(NumberFormatException e){
        				try{
        					return Double.compare(Double.parseDouble(o1) , Double.parseDouble(o2));
        				}catch(NumberFormatException e2){
        					return o1.compareToIgnoreCase(o2);
        				}
        			}
        		}
        	});
        }
	}
}
