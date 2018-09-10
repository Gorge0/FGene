package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.Equipe;
import core.FGene;
import core.enums.JTableMode;

public class EquipePanel extends Telas{
	
	public static String selectedEquipe;
	public static ArrayList<String> selectedEquipes = new ArrayList<>();
	
	private JPanel panel;
	
	private Point point = new Point(0, 0);
	private Integer curCol = 0;
	private Integer pos = 0;
	Integer[] cols = {};

	@Override
	public void createPanel() {
		JPanel newPanel = new JPanel(new GridBagLayout());
		
		//changed v6.5
//		JComboBox<Equipe> combo = new JComboBox<>(FGene.getAllEquipes());
		JComboBox<Object> combo = new JComboBox<>(FGene.getAllEquipes().stream().sorted((p1,p2) -> p1.name.compareTo(p2.name)).toArray());
		combo.setSelectedItem(FGene.getEquipe(selectedEquipe));
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectedEquipe = ((Equipe)combo.getSelectedItem()).name;
				selectedEquipes.add(0, selectedEquipe);
				new EquipePanel().createPanel();
			}
		});
		panel = newPanel;
		//newPanel.add(new JLabel(selectedEquipe), genConstraint(0, 0, 1, 1));
		newPanel.add(combo, genConstraint(0, 0, 2, 1));
		JScrollPane scroll = new JScrollPane(new JLabel(new ImageIcon(genGraph())),
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setPreferredSize(new Dimension(1050, 425));
		newPanel.add(scroll, genConstraint(0, 1, 2, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.EPPERSEASON))), genConstraint(0, 2, 1, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.EPPERPLAYOFF))), genConstraint(0, 3, 1, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.EPPERPLAYOFFPILOTO))), genConstraint(1, 3, 1, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.EPPERERA))), genConstraint(1, 2, 1, 1));
		
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
	
	
	@Override
	public <A> List<A> getList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getModes() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "EQUIPE";
	}
	
	public Image genGraph(){
		int w = 75 * FGene.getAllSeasons().size();
		int h = 400;
		int hOffset = h-15;
		int lineOffset = 30;
		int wOffset = 2;
		
		BufferedImage img = new BufferedImage(w+5, h+5, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)img.getGraphics();
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10));
		//g.fillRect(0, 0, w, h);
		//g.drawRect(0, 0, w, h);
		g.drawLine(lineOffset, wOffset, lineOffset, hOffset);
		g.drawLine(lineOffset, hOffset, w, hOffset);
		
		int stepH = hOffset/18;
		int stepW = 75;
		int[] lines = {18*stepH,17*stepH,16*stepH,15*stepH,14*stepH,13*stepH,12*stepH,11*stepH,10*stepH,9*stepH,8*stepH,7*stepH,6*stepH,5*stepH,4*stepH,3*stepH,2*stepH,stepH};
		
		ArrayList<Integer> aux = new ArrayList<>();
		for(int k=0;k<FGene.getAllSeasons().size();k++){
			aux.add((k+1)*stepW);
		}
		cols = aux.toArray(cols);

		g.drawString("1st", wOffset, lines[17]+5);
		g.drawString("2nd", wOffset, lines[16]+5);
		g.drawString("3rd", wOffset, lines[15]+5);
		g.drawString("4th", wOffset, lines[14]+5);
		g.drawString("5th", wOffset, lines[13]+5);
		g.drawString("6th", wOffset, lines[12]+5);
		g.drawString("7th", wOffset, lines[11]+5);
		g.drawString("8th", wOffset, lines[10]+5);
		g.drawString("9th", wOffset, lines[9]+5);
		g.drawString("10th", wOffset, lines[8]+5);
		g.drawString("11th", wOffset, lines[7]+5);
		g.drawString("12th", wOffset, lines[6]+5);
		g.drawString("13th", wOffset, lines[5]+5);
		g.drawString("14th", wOffset, lines[4]+5);
		g.drawString("15th", wOffset, lines[3]+5);
		g.drawString("16th", wOffset, lines[2]+5);
		g.drawString("17th", wOffset, lines[1]+5);
		g.drawString("18th", wOffset, lines[0]+5);
		
		for(String sel : EquipePanel.selectedEquipes){
			//g.setPaint(new Color((int)(Math.random() * 0x1000000)));
			FGene.getAllSeasons().stream().sorted((s1,s2) -> s1.ano.compareTo(s2.ano)).forEach(s -> {
				pos = s.getPosEquipe(sel);
				if(pos != null){
					if(this.point.x != 0 && this.point.y != 0){
						g.drawLine(this.point.x, this.point.y, cols[curCol], lines[pos]);
					}
					this.point = new Point(cols[curCol], lines[pos]);
					g.fillOval(point.x-5, point.y-5, 10, 10);
					g.drawString(s.ano.toString(), this.point.x, h);
					curCol++;
					
					float[] dash = {10.0f};
					g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,dash,0.0f));
					g.drawLine(lineOffset, point.y, point.x, point.y);
					g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10));
				}
			});
			curCol = 0;
			break; //I considered going through all selected but it was too hard for the amount of information provided; So only the 1st;
		}
		
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.25f);
		Composite original= g.getComposite();
		g.setComposite(alpha);
		g.setPaint(Color.GREEN);
		g.fillRect(lineOffset+2, wOffset, w, h/6+7);
		g.setPaint(Color.RED);
		g.fillRect(lineOffset+2, h/6+7, w, hOffset-(h/6+7));
		g.setComposite(original);
		
		return img;
	}
}
