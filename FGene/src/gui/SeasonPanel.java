package gui;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Season;
import core.enums.JTableMode;

public class SeasonPanel extends gui.Telas {

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
			MainPanel.form.tabs.addTab(getName(), new ImageIcon(System.getProperty("user.dir")+"\\Images\\label.gif"), newPanel);
		}
		
		JLabel title = new JLabel(getName()+":");
		top.add(title,genConstraint(1, 0, 1, 1));
//		JLabel bonus = new JLabel(s.bonus.name());
//		top.add(title,genConstraint(3, 0, 1, 1));
		
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
					MainPanel.form.createAllPanels();
//					for(int i=0;i<MainPanel.form.tabs.getTabCount();i++){
//						Telas.valueOf(MainPanel.form.tabs.getTitleAt(i)).createPanel();
//					}
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
		MyTable tablePilotos = new MyTable(new MyTableModel(l,JTableMode.PSEASON));
		tablePilotos.getColumnModel().getColumn(0).setMinWidth(80);
		//customizeTable(tablePilotos);
		JScrollPane scrollPane = new JScrollPane(tablePilotos);
		this.bot.add(scrollPane, genConstraint(0, 0, 1, 4));
		
		ArrayList<Equipe> eqs = new ArrayList<>();
		int aux = 0;
		for(int i=0;i<s.equipes.size();i++){
			eqs.add(s.equipes.get(i));
			if((i+1)%3 == 0){
				MyTable table = new MyTable(new MyTableModel(eqs,JTableMode.SGROUPS), tablePilotos, 1);
				//customizeTable(table);
				
				table.getColumnModel().getColumn(2).setMaxWidth(35);
				table.getColumnModel().getColumn(3).setMaxWidth(35);
				table.getColumnModel().getColumn(4).setMaxWidth(35);
				
//				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//					
//					@Override
//					public void valueChanged(ListSelectionEvent arg0) {
//						// TODO Auto-generated method stub
//						table.setSelectedRowsByName(tablePilotos, table.getSelectedRowsByName(1),0);
//						//PilotoPanel.selectedPiloto = getSelectedRowsByName(table, 1).get(0);
//					}
//				});
				
				this.bot.add(table, genConstraint(aux+1, i/9, 1, 1));
				if(++aux == 3){
					aux=0;
				}
				eqs.clear();
			}
		}
		MyTable tablePl = new MyTable(new MyTableModel(s.playoffs,JTableMode.PGROUPS));
		//customizeTable(tablePl);
		JScrollPane scrollPanePl = new JScrollPane(tablePl);
		this.bot.add(scrollPanePl, genConstraint(1, 2, 3, 1));
		
		if(s.playoffsEquipe != null){
			MyTable tableEqPlPil = new MyTable(new MyTableModel(s.playoffsEquipe,JTableMode.PEQGROUPS));
			//customizeTable(tableEqPlPil);
			JScrollPane scrollPanePil = new JScrollPane(tableEqPlPil);
			this.bot.add(scrollPanePil, genConstraint(1, 3, 2, 1));
			MyTable tableEqPlEq = new MyTable(new MyTableModel(s.getEqsPlayoff(),JTableMode.EQGROUPS));
			//customizeTable(tableEqPlEq);
			JScrollPane scrollPaneEq = new JScrollPane(tableEqPlEq);
			this.bot.add(scrollPaneEq, genConstraint(3, 3, 1, 1));
		}
		
		MyTable equipes = new MyTable(new MyTableModel(s.equipes,JTableMode.EQSEASON));
		//customizeTable(equipes);
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
		MyTable changes = new MyTable(new MyTableModel(ss,JTableMode.CHANGES));
		//customizeTable(changes);
		JScrollPane scrollPane = new JScrollPane(changes);
		this.bot.add(scrollPane, genConstraint(0, 0, 1, 1));
		
		bot.repaint();
		bot.revalidate();
	}
	
	@Override
	public <A> List<A> getList() {
		return (List<A>) s.pHere();
	}

	@Override
	public void getModes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SEASON";
	}
}
