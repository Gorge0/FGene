package gui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
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
import core.Piloto;
import core.enums.JTableMode;

public class PilotoPanel extends Telas{
	
	public static String selectedPiloto;
	public static ArrayList<String> selectedPilotos = new ArrayList<>();
	
	private JPanel panel;
	
	private Point point = new Point(0, 0);
	private Integer curCol = 0;
	private Integer pos = 0;

	@Override
	public void createPanel() {
		JPanel newPanel = new JPanel(new GridBagLayout());
		
		
		//changed v6.5
//		JComboBox<Object> combo = new JComboBox<>(FGene.getAllPilots().toArray());
		JComboBox<Object> combo = new JComboBox<>(FGene.getAllPilots().stream().sorted((p1,p2) -> p1.name.compareTo(p2.name)).toArray());
		combo.setSelectedItem(FGene.getPiloto(selectedPiloto));
		combo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectedPiloto = ((Piloto)combo.getSelectedItem()).name;
				selectedPilotos.add(0, selectedPiloto);
				new PilotoPanel().createPanel();
			}
		});
		panel = newPanel;
		//newPanel.add(new JLabel(selectedPiloto), genConstraint(0, 0, 1, 1));
		newPanel.add(combo, genConstraint(0, 0, 2, 1));
		newPanel.add(new JLabel(new ImageIcon(genGraph())), genConstraint(0, 1, 2, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.PPPERSEASON))), genConstraint(0, 2, 1, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.PPPERPLAYOFF))), genConstraint(0, 3, 1, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.PPPERPLAYOFFEQUIPE))), genConstraint(1, 3, 1, 1));
		newPanel.add(new JScrollPane(new MyTable(new MyTableModel(FGene.getAllSeasons(),JTableMode.PPPEREQUIPE))), genConstraint(1, 2, 1, 1));
		
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
		return "PILOTO";
	}
	
	public Image genGraph(){
		int w = 1050;
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
		
		int stepH = hOffset/11;
		int stepW = w/19;
		int[] lines = {11*stepH,10*stepH,9*stepH,8*stepH,7*stepH,6*stepH,5*stepH,4*stepH,3*stepH,2*stepH,stepH};
		int[] cols = {stepW,2*stepW,3*stepW,4*stepW,5*stepW,6*stepW,7*stepW,8*stepW,9*stepW,10*stepW,11*stepW,12*stepW,13*stepW,14*stepW,15*stepW,16*stepW,17*stepW,18*stepW};
		
		g.drawString("1st", wOffset, lines[10]+5);
		g.drawString("2nd", wOffset, lines[9]+5);
		g.drawString("3rd", wOffset, lines[8]+5);
		g.drawString("4th", wOffset, lines[7]+5);
		g.drawString("5th", wOffset, lines[6]+5);
		g.drawString("6th", wOffset, lines[5]+5);
		g.drawString("2nd*", wOffset, lines[4]+5);
		g.drawString("3rd*", wOffset, lines[3]+5);
		g.drawString("4th*", wOffset, lines[2]+5);
		g.drawString("5th*", wOffset, lines[1]+5);
		g.drawString("6th*", wOffset, lines[0]+5);
		
		for(String sel : PilotoPanel.selectedPilotos){
			//g.setPaint(new Color((int)(Math.random() * 0x1000000)));
			FGene.getAllSeasons().stream().sorted((s1,s2) -> s1.ano.compareTo(s2.ano)).forEach(s -> {
				pos = s.getPosPiloto(sel);
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
		g.fillRect(lineOffset+2, wOffset, w, h/2+25);
		g.setPaint(Color.RED);
		g.fillRect(lineOffset+2, h/2+25, w, hOffset-(h/2+25));
		g.setComposite(original);
		
		return img;
	}
}
