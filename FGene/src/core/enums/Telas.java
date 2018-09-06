package core.enums;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Season;
import gui.MainPanel;
import gui.TableModel;

public enum Telas {

	PILOTOS(JTableMode.PSEASONS,JTableMode.ALLPILOTS,JTableMode.PPLAYOFF) {
		@Override
		public <A> List<A> getList() {
			return (List<A>) FGene.getAllPilots();
		}
	},
	EQUIPES(JTableMode.EPOWERS,JTableMode.ECONTRACTS,JTableMode.ALLEQUIPES) {
		@Override
		public <A> List<A> getList() {
			return (List<A>) FGene.getAllEquipes();
		}
	},
	CHAMPS(JTableMode.SEASONS,JTableMode.SEASONSPEQ,JTableMode.SEASONSEQ) {
		@Override
		public <A> List<A> getList() {
			return (List<A>) FGene.getAllSeasons();
		}
	},
	SEASON() {
		public Season s;
		public JPanel bot;
		
		@Override
		public void createPanel() {
			JPanel newPanel = new JPanel(new BorderLayout());
			
			JPanel top = new JPanel(new GridBagLayout());
			newPanel.add(top, BorderLayout.NORTH);
			bot = new JPanel(new GridBagLayout());
			newPanel.add(bot, BorderLayout.CENTER);
			JPanel evenBot = new JPanel(new GridBagLayout());
			newPanel.add(evenBot, BorderLayout.SOUTH);
			
			JPanel oldPanel = (JPanel)getPanel();
			if(oldPanel != null){
				oldPanel.removeAll();
				oldPanel.add(newPanel);
				oldPanel.repaint();
				oldPanel.revalidate();
			}else{
				MainPanel.form.tabs.addTab(this.name(), new ImageIcon(System.getProperty("user.dir")+"\\Images\\label.gif"), newPanel);
			}
			
			JLabel title = new JLabel(this.name()+":");
			top.add(title,genConstraint(1, 0, 1, 1));
//			JLabel bonus = new JLabel(s.bonus.name());
//			top.add(title,genConstraint(3, 0, 1, 1));
			
			JButton create = new JButton("Create");
			create.setEnabled(false);
			JButton update = new JButton("Update");
			JButton changes = new JButton("Changes");
			changes.setEnabled(false);
			JButton end = new JButton("End");
			end.setEnabled(false);
			
			Season[] array = new Season[FGene.getAllSeasons().size()];
			JComboBox<Season> allSeasons = new JComboBox<>(FGene.getAllSeasons().toArray(array));
			allSeasons.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					s = (Season) allSeasons.getSelectedItem();
					FGene.setSelectedSeason(s);
					updatePanel();
					if(FGene.getAllSeasons().indexOf(s) != FGene.getAllSeasons().size()-1){
						changes.setEnabled(true);
					}else{
						changes.setEnabled(false);
					}
					if(!s.isEnded){
						end.setEnabled(true);
					}else{
						end.setEnabled(false);
						if(FGene.getAllSeasons().indexOf(s) == 0){
							create.setEnabled(true);
						}
					}
				}
			});
			top.add(allSeasons, genConstraint(2, 0, 1, 1));
			
			
			if(FGene.getAllSeasons().isEmpty()){
				create.setEnabled(true);
			}
			
			create.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(s != null){
						if(s.isEnded){
							allSeasons.insertItemAt(new Season(), 0);
							allSeasons.setSelectedIndex(0);
							create.setEnabled(false);
							end.setEnabled(true);
						}
					}else{
						allSeasons.insertItemAt(new Season(), 0);
						allSeasons.setSelectedIndex(0);
						create.setEnabled(false);
						end.setEnabled(true);
					}
				}
			});
			update.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(s != null){
						s.update();
						updatePanel();
					}
				}
			});
			changes.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(s != null){
						updatePanelChanges();
					}
				}
			});
			end.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					s.end();
					if(s.isEnded){
						create.setEnabled(true);
						end.setEnabled(false);
						for(int i=0;i<MainPanel.form.tabs.getTabCount();i++){
							Telas.valueOf(MainPanel.form.tabs.getTitleAt(i)).createPanel();
						}
					}
				}
			});
			
			top.add(create, genConstraint(0, 1, 1, 1));
			top.add(update, genConstraint(1, 1, 1, 1));
			top.add(changes, genConstraint(2, 1, 1, 1));
			top.add(end, genConstraint(3, 1, 1, 1));
		}

		public void updatePanel(){
			this.bot.removeAll();
			List<Piloto> l = this.getList();
			JTable tablePilotos = new JTable(new TableModel(l,JTableMode.PSEASON));
			customizeTable(tablePilotos);
			JScrollPane scrollPane = new JScrollPane(tablePilotos);
			this.bot.add(scrollPane, genConstraint(0, 0, 1, 4));
			
			ArrayList<Equipe> eqs = new ArrayList<>();
			int aux = 0;
			for(int i=0;i<s.equipes.size();i++){
				eqs.add(s.equipes.get(i));
				if((i+1)%3 == 0){
					JTable table = new JTable(new TableModel(eqs,JTableMode.SGROUPS));
					customizeTable(table);
					this.bot.add(table, genConstraint(aux+1, i/9, 1, 1));
					if(++aux == 3){
						aux=0;
					}
					eqs.clear();
				}
			}
			JTable tablePl = new JTable(new TableModel(s.playoffs,JTableMode.PGROUPS));
			customizeTable(tablePl);
			JScrollPane scrollPanePl = new JScrollPane(tablePl);
			this.bot.add(scrollPanePl, genConstraint(1, 2, 3, 1));
			
			if(s.playoffsEquipe != null){
				JTable tableEqPlPil = new JTable(new TableModel(s.playoffsEquipe,JTableMode.PEQGROUPS));
				customizeTable(tableEqPlPil);
				JScrollPane scrollPanePil = new JScrollPane(tableEqPlPil);
				this.bot.add(scrollPanePil, genConstraint(1, 3, 2, 1));
				JTable tableEqPlEq = new JTable(new TableModel(s.getEqsPlayoff(),JTableMode.EQGROUPS));
				customizeTable(tableEqPlEq);
				JScrollPane scrollPaneEq = new JScrollPane(tableEqPlEq);
				this.bot.add(scrollPaneEq, genConstraint(3, 3, 1, 1));
			}
			
			JTable equipes = new JTable(new TableModel(s.equipes,JTableMode.EQSEASON));
			customizeTable(equipes);
			JScrollPane scrollPane2 = new JScrollPane(equipes);
			this.bot.add(scrollPane2, genConstraint(0, 4, 4, 1));
			
			bot.repaint();
			bot.revalidate();
		}
		
		public void updatePanelChanges(){
			this.bot.removeAll();
			ArrayList<Season> ss = new ArrayList<>();
			ss.add(s);
			ss.add(FGene.getAllSeasons().get(FGene.getAllSeasons().indexOf(s)+1));
			JTable changes = new JTable(new TableModel(ss,JTableMode.CHANGES));
			customizeTable(changes);
			JScrollPane scrollPane = new JScrollPane(changes);
			this.bot.add(scrollPane, genConstraint(0, 0, 1, 1));
			
			bot.repaint();
			bot.revalidate();
		}
		
		@Override
		public <A> List<A> getList() {
			return (List<A>) s.pHere();
		}
	};
	
	public JTableMode mode[];
	
	public abstract <A>List<A> getList();
	
	
	public void setSize(JTable table){
		table.setPreferredScrollableViewportSize(((TableModel)table.getModel()).mode.d);
	}
	
	public void createPanel(){
		JPanel newPanel = new JPanel(new GridBagLayout());
		int cont = 0;
		for(JTableMode mode : this.mode){
			JLabel title = new JLabel(mode.name());
			
			JTable table = new JTable(new TableModel(this.getList(),mode));
			customizeTable(table);
			JScrollPane scrollPane = new JScrollPane(table);
			if(cont == 0){
				newPanel.add(title,genConstraint(cont, 0, 1, 1));
				newPanel.add(scrollPane,genConstraint(cont++, 1, 2, 1));
			}else{
				newPanel.add(title,genConstraint(cont-1, 2, 1, 1));
				newPanel.add(scrollPane,genConstraint(cont-1, 3, 1, 1));
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
			MainPanel.form.tabs.addTab(this.name(), new ImageIcon(System.getProperty("user.dir")+"\\Images\\label.gif"), newPanel);
		}
	}
	
	private Telas(JTableMode... mode){
		this.mode = mode;
	}
	
	protected GridBagConstraints genConstraint(int x, int y, int w, int h){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}
	
	protected void customizeTable(JTable t){
		t.setAutoCreateRowSorter(true);
		setSize(t);
		DefaultTableCellRenderer r = new DefaultTableCellRenderer(){
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				setFont(getFont().deriveFont(Font.BOLD));
				table.getTableHeader().setFont(new Font("Serif", Font.BOLD, 18));
				return this;
			}
		};
        r.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < t.getColumnCount(); i++){
        	t.getColumnModel().getColumn(i).setCellRenderer(r);
        	TableRowSorter<TableModel> rowSorter = (TableRowSorter<TableModel>)t.getRowSorter();
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
	
	protected Component getPanel(){
		try{
			JTabbedPane tabs = MainPanel.form.tabs;
			return tabs.getComponentAt(tabs.indexOfTab(this.name()));
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}
}
