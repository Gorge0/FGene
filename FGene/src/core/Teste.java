package core;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.enums.Bonus;
import core.enums.Powers;

public class Teste {

	
		public static void main(String[] args) {
			FileStreamController ctrl = new FileStreamController();
			if(!ctrl.load()){
				ctrl.start();
			}
			 
			//patchPowers(FGene.getAllEquipes());
			//write(FGene.getAllEquipes());
			 //updateDriverAI(1500);
			 //list();
			//readDriverFiles();
			 //System.out.println(Integer.BYTES );
			
			
//			for(int i = 0;i<10;i++){
//				System.out.println((Math.round(new Random().nextDouble() * 1000.0)/1000.0));
//			}
			
//			updateStats2(FGene.getAllPilots());
//			for(Season s : FGene.getAllSeasons()){
//				updateStats2(s.equipes);
//				updateStats2(s.pHere());
//			}
			for(Piloto p : FGene.getAllPilots()){
				if(p.name.equals("Bharata")){
					p.careerLeft=8;
				}
				if(p.name.equals("Fotis")){
					p.careerLeft=8;
				}
				if(p.name.equals("Flores")){
					p.careerLeft=14;
				}
				if(p.name.equals("Walter")){
					p.careerLeft=13;
				}
			}
//			for(Piloto p : FGene.getAllSeasons().get(0).pHere()){
//				if(p.name.equals("Ainsley")){
//					p.AI+=1;
//				}
//				if(p.name.equals("Bharata")){
//					p.AI+=1;
//				}
//			}
			
			
//			changeCareers(FGene.getAllPilots());
//			System.out.println("---------------------------------");
//			changeContracts(FGene.getAllEquipes());
			
			//System.out.println(Double.BYTES);
			ctrl.save();
		       
		}
		
		public static void updateStats2(ArrayList<Piloto> ps){
			for(Piloto p : ps){
				p.pureSeason = new Stats2(p.season);
			}
		}
		public static void updateStats2(List<Equipe> eqs){
			for(Equipe e : eqs){
				e.pureStats = new Stats2(e.stats);
			}
		}

		public static void changeContracts(List<Equipe> eqs){
			for(Equipe e : eqs){
				e.changeContract();
				System.out.println(e.contract1+" "+e.contract2);
			}
		}
		public static void changeCareers(ArrayList<Piloto> pilotos){
			ArrayList<Piloto> ps = new ArrayList<>();
			for(Piloto p : pilotos){
				if(p.careerLeft == 0){
					continue;
				}
				boolean b = false;
				for(Piloto p2 : ps){
					if(p2.careerLeft == p.careerLeft){
						b = true;
						break;
					}
				}
				if(!b){
					ps.add(p);
				}
			}
			System.out.println(ps.size());
			int i = 13;
			for(Piloto p : ps){
				System.out.println(p.name);
				p.careerLeft = i++;
				System.out.println(p.careerLeft);
				if(i == 19){
					i = 13;
				}
			}
		}
		
		public static void write(List<Equipe> eqs){
			for(Equipe e : eqs){
				System.out.println(e.powers);
			}
		}
		
		public static void patchPowers(List<Equipe> eqs, boolean isOld){
			for(Equipe e : eqs){
				if(!isOld){
					if(e.powers.get(Powers.AIR) == 2.333){
						e.powers.put(Powers.AIR, 2.01);
					}
					if(e.powers.get(Powers.AIR) == 1.677 || e.powers.get(Powers.AIR) == 1.667){
						e.powers.put(Powers.AIR, 1.99);
					}
					
					if(e.powers.get(Powers.POWER) == 425.0){
						e.powers.put(Powers.POWER, 402.0);
					}
					if(e.powers.get(Powers.POWER) == 375.0){
						e.powers.put(Powers.POWER, 398.0);
					}
					
					if(Powers.format.format(e.powers.get(Powers.SLOWDOWN)).equals("0.3")){
						e.powers.put(Powers.SLOWDOWN, 0.1);
					}
					if(Powers.format.format(e.powers.get(Powers.SLOWDOWN)).equals("0.2")){
						e.powers.put(Powers.SLOWDOWN, 0.09);
					}
					if(Powers.format.format(e.powers.get(Powers.SLOWDOWN)).equals("0.4")){
						e.powers.put(Powers.SLOWDOWN, 0.11);
					}
					if(Powers.format.format(e.powers.get(Powers.SLOWDOWN)).equals("0.5")){
						e.powers.put(Powers.SLOWDOWN, 0.12);
					}
					
					if(Powers.format.format(e.powers.get(Powers.GRIP)).equals("2.7")){
						e.powers.put(Powers.GRIP, 3.0);
					}
					if(Powers.format.format(e.powers.get(Powers.GRIP)).equals("2.6")){
						e.powers.put(Powers.GRIP, 2.99);
					}
					if(Powers.format.format(e.powers.get(Powers.GRIP)).equals("2.8")){
						e.powers.put(Powers.GRIP, 3.01);
					}
					if(Powers.format.format(e.powers.get(Powers.GRIP)).equals("2.5")){
						e.powers.put(Powers.GRIP, 2.98);
					}
					if(Powers.format.format(e.powers.get(Powers.GRIP)).equals("2.9")){
						e.powers.put(Powers.GRIP, 3.02);
					}
					
				}
				
//				e.powers.remove(Powers.SPEED);
//				e.powers.remove(Powers.SLIDING);
//				
//				if(e.currentBonus == Bonus.SPEED || e.currentBonus == Bonus.SLIDING){
//					e.currentBonus = null;
//				}
			}
		}
		
		public static void simulateStats(ArrayList<Piloto> ps, Integer pts){
			for(Piloto p : ps){
				p.totals.p1st = pts;
			}
		}
		
		public static void patch3x0(){
			for(Equipe e : FGene.getAllEquipes()){
				e.eChamps = 0;
				e.eRunnerUps = 0;
				e.pChamps = 0;
				e.pRunnerUps = 0;
			}
			
			for(Piloto p : FGene.getAllPilots()){
				p.seasons = 1;
				p.eChamp = 0;
				p.eRunnerUp = 0;
				p.pChamp = 0;
				p.pRunnerUp = 0;
				p.playoffEquipe = new Stats();
			}
			
			FGene.getPiloto(FGene.getAllSeasons().get(1).playoffs.get(0).name).pChamp++;
			FGene.getPiloto(FGene.getAllSeasons().get(1).playoffs.get(1).name).pRunnerUp++;
			FGene.getEquipe(FGene.getEquipeOfPiloto(FGene.getAllSeasons().get(1).equipes, FGene.getAllSeasons().get(1).playoffs.get(0)).name).pChamps++;
			FGene.getEquipe(FGene.getEquipeOfPiloto(FGene.getAllSeasons().get(1).equipes, FGene.getAllSeasons().get(1).playoffs.get(1)).name).pRunnerUps++;
			
			for(Season s : FGene.getAllSeasons()){
				for(Piloto p : s.pHere()){
					p.playoffEquipe = new Stats();
				}
			}
			
			FGene.getAllSeasons().get(0).playoffsEquipe = new ArrayList<>();
		}
		
		public static void updateDriverAI(Integer n) {
			try {
				new File(System.getProperty("user.dir")+"\\DriversSub\\").mkdir();
				File file = new File(System.getProperty("user.dir")+"\\Cars\\"+"Huppert"+".drv");
				//FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")+"\\DriversSub\\"+"Huppert"+".drv");
				//FileInputStream in = new FileInputStream(file);
				//DataInputStream inData = new DataInputStream(in);
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				
				raf.seek(84);
				raf.writeInt(Integer.reverseBytes(n));
				raf.close();
				
				//byte[] before = new byte[84];
				//byte[] after = new byte[20];
				
//				in.read(before);
//				b[84] = p.AI.byteValue();
//				b[84] = i.byteValue();
//				out.write(b);
				
				//in.close();
				//out.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("erro no updateDriversAI");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("erro no updateDriversAI");
			}
		}
		
		public static ArrayList<Piloto> readDriverFiles() {
			try {
				File[] files = new File(System.getProperty("user.dir")+"\\Cars\\").listFiles();
				ArrayList<Piloto> ps = new ArrayList<>();
				for(File f : files){
					Piloto p = new Piloto();
					p.name = f.getName().replace(".drv", "");
					//Piloto old = FGene.getPiloto(p.name);
//					FileInputStream in = new FileInputStream(f);
//					DataInputStream inData = new DataInputStream(in);
//					inData.skipBytes(40);
//					p.totals.p1st = Integer.reverseBytes(inData.readInt());
//					p.totals.p2nd = Integer.reverseBytes(inData.readInt());
//					p.totals.p3rd = Integer.reverseBytes(inData.readInt());
//					p.totals.p4th = Integer.reverseBytes(inData.readInt());
//					p.totals.p5th = Integer.reverseBytes(inData.readInt());
//					p.totals.p6th = Integer.reverseBytes(inData.readInt());
//					inData.skipBytes(20);
//					p.AI = Integer.reverseBytes(inData.readInt());
//					
//					inData.close();
//					in.close();
//					
//					p.totals.updatePts();
					
					RandomAccessFile raf = new RandomAccessFile(f, "r");
					
					raf.seek(40);
					System.out.println(Integer.reverseBytes(raf.readInt()));
					System.out.println(Integer.reverseBytes(raf.readInt()));
					System.out.println(Integer.reverseBytes(raf.readInt()));
					System.out.println(Integer.reverseBytes(raf.readInt()));
					System.out.println(Integer.reverseBytes(raf.readInt()));
					System.out.println(Integer.reverseBytes(raf.readInt()));
					raf.seek(84);
					System.out.println(Integer.reverseBytes(raf.readInt()));
					raf.close();
					
					ps.add(p);
				}
				return ps;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("erro no readDrivers");
			return null;
		}
		
		public static void list() {
			try {
				File file = new File(System.getProperty("user.dir")+"\\Cars\\"+"Huppert"+".drv");
				//FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")+"\\DriversSub\\"+"Huppert"+".drv");
				FileInputStream in = new FileInputStream(file);
				DataInputStream inData = new DataInputStream(in);
				int c =0;
				while(inData.available() != 0){
					System.out.println(c+++" "+Integer.reverseBytes(inData.readInt()));
				}
				
				inData.close();
				in.close();
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("erro no updateDriversAI");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("erro no updateDriversAI");
			}
		}
		
}
