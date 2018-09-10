package core;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Vector;

import core.enums.Powers;

public class FileStreamController {
	
	private static final Path backupAddress = Paths.get("C:\\Users\\Anderson\\Desktop\\");

	public void start(){
		BufferedReader bf;
		try {
			if(FGene.getAllEquipes().isEmpty()){
				bf = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\start.txt"));
				String line;
				while((line = bf.readLine()) != null){
					String[] div = line.split(",");
					Piloto p1 = new Piloto(div[1],Integer.parseInt(div[2]));
					Piloto p2 = new Piloto(div[4],Integer.parseInt(div[5]));
					Equipe e = new Equipe(div[0],p1,Integer.parseInt(div[3]),p2,Integer.parseInt(div[6]));
					
					FGene.getAllPilots().add(p1);
					FGene.getAllPilots().add(p2);
					FGene.getAllEquipes().add(e);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void save(){
		try{
	         FileOutputStream pilotsOut = new FileOutputStream(System.getProperty("user.dir")+"\\saves\\pilots.gene");
	         FileOutputStream equipesOut = new FileOutputStream(System.getProperty("user.dir")+"\\saves\\equipes.gene");
	         FileOutputStream seasonsOut = new FileOutputStream(System.getProperty("user.dir")+"\\saves\\seasons.gene");
	         
	         ObjectOutputStream out = new ObjectOutputStream(equipesOut);
	         out.writeObject(FGene.getAllEquipes());
	         out.close();
	         
	         ArrayList<Piloto> ps = new ArrayList<>();
	         for(Piloto p : FGene.getAllPilots()){
	        	 if(p.careerLeft == 0){
	        		 ps.add(p);
	        	 }
	         }
	         
	         out = new ObjectOutputStream(pilotsOut);
	         out.writeObject(ps);
	         out.close();
	         
	         out = new ObjectOutputStream(seasonsOut);
	         out.writeObject(FGene.getAllSeasons());
	         out.close();
	         
	         pilotsOut.close();
	         equipesOut.close();
	         seasonsOut.close();
	         
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	
	public boolean load(){
	      try{
	         FileInputStream pilotsIn = new FileInputStream(System.getProperty("user.dir")+"\\saves\\pilots.gene");
	         FileInputStream equipesIn = new FileInputStream(System.getProperty("user.dir")+"\\saves\\equipes.gene");
	         FileInputStream seasonsIn = new FileInputStream(System.getProperty("user.dir")+"\\saves\\seasons.gene");
	         
	         ObjectInputStream in = new ObjectInputStream(equipesIn);
	         FGene.setAllEquipes((Vector<Equipe>) in.readObject());
	         in.close();
	         
	         for(Equipe e : FGene.getAllEquipes()){
	        	 FGene.getAllPilots().add(e.piloto1);
	        	 FGene.getAllPilots().add(e.piloto2);
	         }
	         
	         in = new ObjectInputStream(pilotsIn);
	         FGene.getAllPilots().addAll((ArrayList<Piloto>) in.readObject());
	         in.close();
	         
	         in = new ObjectInputStream(seasonsIn);
	         FGene.setAllSeasons((ArrayList<Season>) in.readObject());
	         in.close();
	         
	         
	         pilotsIn.close();
	         equipesIn.close();
	         seasonsIn.close();
	         
	         return true;
	         
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return false;
	      }catch(ClassNotFoundException c)
	      {
	         c.printStackTrace();
	         return false;
	      }
	}
	
	public ArrayList<Piloto> readDriverFiles() {
		try {
			File[] files = new File(System.getProperty("user.dir")+"\\Drivers\\").listFiles();
			ArrayList<Piloto> ps = new ArrayList<>();
			for(File f : files){
				Piloto p = new Piloto();
				p.name = f.getName().replace(".drv", "");
				//Piloto old = FGene.getPiloto(p.name);
				
				//FileInputStream in = new FileInputStream(f);
				//DataInputStream inData = new DataInputStream(in);
				RandomAccessFile raf = new RandomAccessFile(f, "r");
				
				//inData.skipBytes(40);
				raf.seek(40);
				p.totals.p1st = Integer.reverseBytes(raf.readInt());
				p.totals.p2nd = Integer.reverseBytes(raf.readInt());
				p.totals.p3rd = Integer.reverseBytes(raf.readInt());
				p.totals.p4th = Integer.reverseBytes(raf.readInt());
				p.totals.p5th = Integer.reverseBytes(raf.readInt());
				p.totals.p6th = Integer.reverseBytes(raf.readInt());
				raf.seek(84);
				p.AI = Integer.reverseBytes(raf.readInt());
				
				//inData.close();
				//in.close();
				raf.close();
				
				p.totals.updatePts();
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
	
	public void updateDriverAI(Piloto p) {
		try {
			//new File(System.getProperty("user.dir")+"\\DriversSub\\").mkdir();
			File file = new File(System.getProperty("user.dir")+"\\Drivers\\"+p.name+".drv");
			//FileOutputStream out = new FileOutputStream(System.getProperty("user.dir")+"\\DriversSub\\"+p.name+".drv");
			//FileInputStream in = new FileInputStream(file);
			//byte[] b = new byte[100];
			
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			
			raf.seek(84);
//			raf.writeByte(Integer.reverseBytes(p.AI));
			raf.writeByte(p.AI);
			raf.close();
			
//			in.read(b);
//			b[84] = p.AI.byteValue();
//			b[84] = i.byteValue();
//			out.write(b);
//			
//			in.close();
//			out.close();
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
	
	public void updateCarFile(){
		try {
			File file = new File(System.getProperty("user.dir")+"\\Cars\\cars.car");
			String[] equipes = {"Ferrari","Mclaren","Williams","Mercedez","Honda","Renault","Ford","Fiat","Hyunday","BMW","Kia","Toyota","Nissan",
					"Volkswagen","Chevrolet","Jaguar","Audi","Lamborghini"};
			Integer[] addresses = {5384,8,22856,16136,9416,18824,8072,6728,10760,2696,13448,20168,17480,21512,4040,12104,1352,14792};
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			
			for(int k=0;k<addresses.length;k++){
				Equipe e = FGene.getEquipe(equipes[k]);
				//raf.read(bytes);
				//f = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat();
				
				raf.seek(addresses[k]);
				System.out.println(raf.getFilePointer());
				raf.skipBytes(48);
				
				raf.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat((float)(double)e.powers.get(Powers.GRIP)).array());
				raf.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat((float)(double)e.powers.get(Powers.GRIP)).array());
				raf.skipBytes(48);
				raf.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat((float)(double)e.powers.get(Powers.SLOWDOWN)).array());
				raf.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat((float)(double)e.powers.get(Powers.SLOWDOWN)).array());
				raf.skipBytes(44);
				raf.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat((float)(double)e.powers.get(Powers.AIR)).array());
				raf.skipBytes(4);
				raf.write(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat((float)(double)e.powers.get(Powers.POWER)).array());
			}
			
			raf.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void movePilotoFile(Piloto p){
		File fileSource = new File(System.getProperty("user.dir")+"\\Drivers\\"+p.name+".drv");
		File fileTarget = new File(System.getProperty("user.dir")+"\\Backup_History\\Drivers_old\\"+p.name+".drv");
		try {
			Files.move(fileSource.toPath(), fileTarget.toPath(), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("erro no movePilotoFile");
		}
	}
}
