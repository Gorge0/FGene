package gui;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;

public class MainPanel extends JPanel implements ActionListener{
	
	public static MainPanel form;
	
	public JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);

	public GridBagConstraints genConstraint(int x, int y, int w, int h){
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
		c.insets = new Insets(10, 10, 10, 10);
		return c;
	}
	
	
	public MainPanel() {
		MainPanel.form = this;
		createAllPanels();
		add(tabs);
		tabs.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(tabs.getSelectedIndex() == 4){
					new PilotoPanel().createPanel();
				}
				if(tabs.getSelectedIndex() == 5){
					new EquipePanel().createPanel();
				}
			}
		});
	}
	
	public void createAllPanels(){
		new Telas.AllPilotoPanel().createPanel();
		new Telas.EquipesPanel().createPanel();
		new Telas.ChampsPanel().createPanel();
		new SeasonPanel().createPanel();
		new PilotoPanel().createPanel();
		new EquipePanel().createPanel();
	}
	
	public void center(JTable t){
		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < t.getColumnCount(); i++){
            t.getColumnModel().getColumn(i).setCellRenderer(r);
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
