package gui;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.enums.JTableMode;

public abstract class Telas {

	public JTableMode mode[];
	public abstract <A>List<A> getList();
	public abstract void getModes();
	public abstract String getName();
	
//	public void setSize(JTable table){
//		table.setPreferredScrollableViewportSize(((TableModel)table.getModel()).mode.d);
//		//table.getColumnModel().getColumn(0).setMaxWidth(10);
//	}
	
	public void createPanel(){
		getModes();
		
		JPanel newPanel = new JPanel(new GridBagLayout());
		int cont = 0;
		for(JTableMode mode : this.mode){
			JLabel title = new JLabel(mode.name());
			
			MyTable table = new MyTable(new MyTableModel(this.getList(),mode));
			//customizeTable(table);
			JScrollPane scrollPane = new JScrollPane(table);
			if(cont < this.mode.length/2){
				newPanel.add(title,genConstraint(cont, 0, 1, 1));
				if(this.mode.length % 2 == 0){
					newPanel.add(scrollPane,genConstraint(cont++, 1, 1, 1));
				}else{
					newPanel.add(scrollPane,genConstraint(cont++, 1, 2, 1));
				}
			}else{
				newPanel.add(title,genConstraint(cont-(this.mode.length/2), 2, 1, 1));
				newPanel.add(scrollPane,genConstraint(cont-(this.mode.length/2), 3, 1, 1));
				cont++;
			}
		}
		
		JPanel oldPanel = (JPanel)getPanel();
		if(oldPanel != null){
			oldPanel.removeAll();
			oldPanel.add(newPanel);
			oldPanel.repaint();
			oldPanel.revalidate();
		}else{
			MainPanel.form.tabs.addTab(getName(), new ImageIcon(System.getProperty("user.dir")+"\\Images\\label.gif"), newPanel);
		}
	}
	
	protected GridBagConstraints genConstraint(int x, int y, int w, int h){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
		c.insets = new Insets(3, 3, 3, 3);
		return c;
	}
	
	protected Component getPanel(){
		try{
			JTabbedPane tabs = MainPanel.form.tabs;
			return tabs.getComponentAt(tabs.indexOfTab(getName()));
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	public static void setSelected(ArrayList<String> s){
		Piloto p = FGene.getPiloto(s.get(0));
		if(p != null){
			//PilotoPanel.selectedPilotos.clear();
			PilotoPanel.selectedPilotos = s;
			PilotoPanel.selectedPiloto = p.name;
			return;
		}
		Equipe e = FGene.getEquipe(s.get(0));
		if(e != null){
			EquipePanel.selectedEquipes = s;
			EquipePanel.selectedEquipe = e.name;
			return;
		}
	}
	
//	public ArrayList<String> getSelectedRowsByName(JTable t, Integer index){
//		ArrayList<String> names = new ArrayList<>();
//		for(int i : t.getSelectedRows()){
//			names.add((String)t.getValueAt(i, index));
//		}
//		return names;
//	}
//	
//	public void setSelectedRowsByName(JTable t, ArrayList<String> list, Integer index){
//		for(String s : list){
//			for(int i=0; i<t.getRowCount();i++){
//				if(((String)t.getValueAt(i, 0)).equals(s)){
//					t.changeSelection(i, 0, false, false);
//					break;
//				}
//			}
//		}
//	}
//	
//	protected void customizeTable(JTable t){
//		t.setAutoCreateRowSorter(true);
//		setSize(t);
//		DefaultTableCellRenderer r = new DefaultTableCellRenderer(){
//			@Override
//			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
//					boolean hasFocus, int row, int column) {
//				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//				setFont(getFont().deriveFont(Font.BOLD));
//				table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 18));
//				return this;
//			}
//		};
//        r.setHorizontalAlignment(JLabel.CENTER);
//        
//        for (int i = 0; i < t.getColumnCount(); i++){
//        	t.getColumnModel().getColumn(i).setCellRenderer(r);
//        	
////        	Size of Numbers columns of all tables
////        	if(t.getModel().getColumnClass(i).equals(Double.class)){
////        		t.getColumnModel().getColumn(i).setMaxWidth(35);
////        	}
//        	
//        	TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>)t.getRowSorter();
//        	rowSorter.setComparator(i, new Comparator<String>() {
//        		
//        		@Override
//        		public int compare(String o1, String o2){
//        			try{
//        				return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
//        			}catch(NumberFormatException e){
//        				try{
//        					return Double.compare(Double.parseDouble(o1) , Double.parseDouble(o2));
//        				}catch(NumberFormatException e2){
//        					return o1.compareToIgnoreCase(o2);
//        				}
//        			}
//        		}
//        	});
//        }
//	}
	
	
	public static class AllPilotoPanel extends Telas{
		@Override
		public <A> List<A> getList() {
			return (List<A>) FGene.getAllPilots();
		}

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "PILOTOS";
		}

		@Override
		public void getModes() {
			// TODO Auto-generated method stub
			JTableMode[] mos = {JTableMode.PPLAYOFF,JTableMode.ALLPILOTS,JTableMode.PSEASONS};
			this.mode = mos;
		}
	}
	
	public static class EquipesPanel extends Telas{
		@Override
		public <A> List<A> getList() {
			return (List<A>) FGene.getAllEquipes();
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "EQUIPES";
		}
		
		@Override
		public void getModes() {
			// TODO Auto-generated method stub
			JTableMode[] mos = {JTableMode.ALLEQUIPES,JTableMode.ECONTRACTS,JTableMode.EPOWERS};
			this.mode = mos;
		}
	}
	
	public static class ChampsPanel extends Telas{
		@Override
		public <A> List<A> getList() {
			return (List<A>) FGene.getAllSeasons();
		}
		
		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "CHAMPS";
		}
		
		@Override
		public void getModes() {
			// TODO Auto-generated method stub
			JTableMode[] mos = {JTableMode.SEASONS,JTableMode.SEASONSP,JTableMode.SEASONSPEQ,JTableMode.SEASONSEQ};
			this.mode = mos;
		}
	}
	
}
