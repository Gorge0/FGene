package gui;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import core.enums.Telas;

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
		
		Telas.PILOTOS.createPanel();
		Telas.EQUIPES.createPanel();
		Telas.CHAMPS.createPanel();
		Telas.SEASON.createPanel();
		
		add(tabs);
					
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
