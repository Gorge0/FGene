package core;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import core.enums.Bonus;
import gui.MainPanel;

public class FGene {

	//maximo de anos que um piloto pode ter de contrato
	public static final Integer MAXCONTRACT = 8;
	
	//maximo de vezes que um power pode ser aumentado ou reduzido
	public static final Integer MAXPOWERCHANGES = 10;
	
	//maximo de vezes que um power pode ser aumentado ou reduzido
	public static final Integer RACESPERSEASON = 19;
	
	private static Vector<Equipe> allEquipes = new Vector<>(18);
	private static ArrayList<Piloto> allPilots = new ArrayList<>();
	private static ArrayList<Season> allSeasons = new ArrayList<>();
	private static Season selectedSeason = null;
	
	public static synchronized void setAllEquipes(Vector<Equipe> allEquipes) {
		FGene.allEquipes = allEquipes;
	}
	public static synchronized void setAllPilots(ArrayList<Piloto> allPilots) {
		FGene.allPilots = allPilots;
	}
	public static synchronized void setAllSeasons(ArrayList<Season> allSeasons) {
		FGene.allSeasons = allSeasons;
	}
	public static synchronized Vector<Equipe> getAllEquipes(){
		return allEquipes;
	}
	public static synchronized ArrayList<Piloto> getAllPilots(){
		return allPilots;
	}
	public static synchronized ArrayList<Season> getAllSeasons(){
		return allSeasons;
	}
	public static Season getSelectedSeason() {
		return selectedSeason;
	}
	public static void setSelectedSeason(Season selectedSeason) {
		FGene.selectedSeason = selectedSeason;
	}
	
//	public Equipe getEquipe(String name){
//		for(Equipe e : allEquipes){
//			if(e.name.equals(name)){
//				return e;
//			}
//		}
//		return null;
//	}
	
	public static Equipe getEquipe(String name){
		return getEquipe(getAllEquipes(), name);
	}
	public static Equipe getEquipe(Vector<Equipe> eqs, String name){
		for(Equipe eq : eqs){
			if(eq.name.equals(name)){
				return eq;
			}
		}
		System.out.println("erro no getPiloto");
		return null;
	}
	
	public static Equipe getEquipeOfPiloto(List<Equipe> eqs, Piloto p){
		for(Equipe e : eqs){
			if(e.isPilotoHere(p)){
				return e;
			}
		}
		return null;
	}
	public static String getEquipeOfPilotoName(String name){
		for(Equipe e : allEquipes){
			if(name.equals(e.piloto1.name) || name.equals(e.piloto2.name)){
				return e.name;
			}
		}
		return null;
	}
	
	public static Season getSeasonByAno(String ano){
		for(Season s : allSeasons){
			if(s.ano.toString().equals(ano)){
				return s;
			}
		}
		return null;
	}
	
	public static Piloto getPiloto(String name){
		return getPiloto(getAllPilots(), name);
	}
	public static Piloto getPiloto(ArrayList<Piloto> ps, String name){
		for(Piloto p : ps){
			if(p.name.equals(name)){
				return p;
			}
		}
		System.out.println("Piloto nao encontrado: FGene.getPiloto("+name+")");
		return null;
	}
	
	public static <A> A getCopy(A obj){
		try {
		ByteArrayOutputStream out1 = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(out1);
		out.writeObject(obj);
		out.close();
		out1.close();
		byte[] byteData = out1.toByteArray();
		
		ByteArrayInputStream in1 = new ByteArrayInputStream(byteData);
		ObjectInputStream in = new ObjectInputStream(in1);
        A result = (A) in.readObject();
        in.close();
        in1.close();
        
        return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("erro no getCopy");
		return null;
	}
	
	public static ArrayList<Piloto> genNewPilotos(int amount){
		ArrayList<Piloto> psNew = new ArrayList<>();
		try {
			for(String s : new InternetController().getRandomNames(amount)){
				psNew.add(new Piloto(s));
				//FGene.getAllPilots().add(e)
			}
		} catch (NullPointerException e) {
			String s[] = {"Duke","Julio"};
			for(int i=0;i<amount;i++){
				psNew.add(new Piloto(s[i]));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Erro no genNewPilotos");
		}
		return psNew;
	}
	
	public static void updateContracts(){
		ArrayList<Piloto> noContract = new ArrayList<>();
		ArrayList<Equipe> noPiloto = new ArrayList<>();
		for(Equipe e: FGene.getAllEquipes()){
			e.piloto1.careerLeft--;
			if(--e.contract1 == 0){
				if(e.piloto1.careerLeft != 0){
					noContract.add(e.piloto1);
				}else{
					new FileStreamController().movePilotoFile(e.piloto1);
				}
				//e.piloto1 = null;
				noPiloto.add(e);
			}
			
			e.piloto2.careerLeft--;
			if(--e.contract2 == 0){
				if(e.piloto2.careerLeft != 0){
					noContract.add(e.piloto2);
				}else{
					new FileStreamController().movePilotoFile(e.piloto2);
				}
				//e.piloto2 = null;
				noPiloto.add(e);
			}
		}
		
		noContract.addAll(FGene.genNewPilotos(noPiloto.size()-noContract.size()));
		noContract.sort((p2, p1) -> p1.AI.compareTo(p2.AI));
		
		for(Piloto p : noContract){
			Equipe e = FGene.getEquipeOfPiloto(noPiloto, p);
			ArrayList<Equipe> aux = new ArrayList<>();
			
			if(e != null){
				if(e.currentBonus == Bonus.NEWCONTRATO){
					aux.add(0, e);
					aux.add(0, e);
					e.currentBonus = null;
				}
				aux.add(0, e);
				aux.add(0, e);
			}
			
			noPiloto.addAll(0, aux);
			Equipe sorted = noPiloto.get(new Random().nextInt(noPiloto.size()));
			for(Equipe e2 : aux){
				noPiloto.remove(e2);
			}
			noPiloto.remove(sorted);
			
			if(sorted.contract1 == 0){
				sorted.piloto1 = null;
			}else{
				sorted.piloto2 = null;
			}
			
			sorted.genContract(p);
		}
	}
	
	//Add AI para pilotos q ficaram em ultimo repetidamente
	public static void repCheck(Piloto p) {
		String log = "repCheck: "+p.name+": +";
		int adding = 1;
		for(int i = 1; i<FGene.getAllSeasons().size(); i++){
			boolean toBeCont = false;
			Season s = FGene.getAllSeasons().get(i);
			
			A:
			for(ArrayList<Piloto> ps : s.getGroups()){
				ps.sort((p2, p1) -> p1.season.pts.compareTo(p2.season.pts));
				for(Piloto p2 : ps){
					if(p.name.equals(p2.name)){
						if(ps.indexOf(p2) == 5){
							FGene.getPiloto(p.name).AI += adding;
							log += adding+" +";
							adding++;
							toBeCont = true;
						}else{
							if(ps.indexOf(p2) == 4){
								FGene.getPiloto(p.name).AI += 1;
								log += adding+" +";
								toBeCont = true;
							}
						}
						break A;
					}
				}
			}
			
			if(toBeCont){
				continue;
			}else{
				break;
			}
		}
		System.out.println(log);
	}
	//Reduce AI para pilotos q foram para os playoffs repetidamente
	public static void repCheck(ArrayList<Piloto> playoff) {
		boolean first = true;
		boolean firstToBeCont = true;
		for(Piloto p : playoff){
			String log = "repCheck: "+p.name+": -";
			
			//added v6.5
			Season s = FGene.getAllSeasons().get(1);
			if(first && p.name.equals(s.playoffs.get(0).name)){
				FGene.getPiloto(p.name).AI -= 4;
			}
				
//			for(int i = 1; i<FGene.getAllSeasons().size(); i++){
//				Season s = FGene.getAllSeasons().get(i);
//				
//				//Repeated Champ
//				if(firstToBeCont){
//					if(first && p.name.equals(s.playoffs.get(0).name)){
//						log += i+" -";
//						FGene.getPiloto(p.name).AI -= i;
//					}else{
//						firstToBeCont = false;
//					}
//				}
				
				//Repeated Playoffs
				//removed v6.5
//				boolean toBeCont = false;
//				for(Piloto p2 : s.playoffs){
//					if(p.name.equals(p2.name)){
//						log += i+" -";
//						FGene.getPiloto(p.name).AI -= i;
//						toBeCont = true;
//					}
//				}
//				if(toBeCont){
//					continue;
//				}else{
//					break;
//				}
//			}
			
			first = false;
			System.out.println(log);
		}
	}
	
	public static void main(String[] args) {

		FileStreamController ctrl = new FileStreamController();
		if(!ctrl.load()){
			ctrl.start();
		}
		
		// Run GUI codes in the Event-Dispatching thread for thread safety
	      SwingUtilities.invokeLater(new Runnable() {
	         @Override
	         public void run() {
	        	 JFrame frame = new JFrame("Formula Gene");
	        	 frame.setBounds(20, 20, 1300, 650);
	     		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	             MainPanel f = new MainPanel();  // Let the constructor do the job
	             JScrollPane scroll = new JScrollPane(f);
	             frame.add(scroll);
	             frame.pack();
	             frame.setVisible(true);
	             //MainForm.form.tabs.addTab("Equipes", new JScrollPane());
	             //FGene.allPilots.get(0).champ = 1;
	         }
	      });
	      
	      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	          public void run() {
	        	  ctrl.save();
	          }
	      }, "Shutdown-thread"));
	      
//	      Season a = new Season();
//	      a.update();
//	      System.out.println();
//	      
//	      a.genPlayoff();
//	      System.out.println();
//	      
//	      a.update();
//	      System.out.println();
//	      
//	      a.updatePowers();
//	      System.out.println();
//	      
//	      a.updateAI();
//	      System.out.println();
//	      
//	      FGene.updateContracts();
//	      System.out.println();
	      
	}

}
