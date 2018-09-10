package core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import core.annotations.JTableAnno;
import core.enums.JTableMode;

public class Piloto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON,JTableMode.PPLAYOFF,JTableMode.ALLPILOTS})
	public String name;
	
	//@JTableAnno(modes={JTableMode.PSEASON})
	public Integer AI;
	
	public Integer potential=0;
	public Double xp=0.0;
	
	public Integer careerLeft;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer seasons = 0;
	
	//@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer pChamp = 0;
	
	//@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer pRunnerUp = 0;
	
	//@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer eChamp = 0;
	
	//@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer eRunnerUp = 0;
	
	//@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer playoffs = 0;
	
	@JTableAnno(modes={JTableMode.ALLPILOTS})
	public Stats totals = new Stats();
	
	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON})
	public Stats season = new Stats();
	
	@JTableAnno(modes={JTableMode.PSEASON,JTableMode.PSEASONS})
	public Stats2 pureSeason = new Stats2(season);
	
	@JTableAnno(modes={JTableMode.PPLAYOFF})
	public Stats playoff = new Stats();
	
	@JTableAnno(modes={})
	public Stats playoffEquipe = new Stats();
	
	public Piloto(String name, Integer caLeft){
		this.name = name;
		this.AI = 100;
//		genPotential();
		this.careerLeft = caLeft;
	}
	
	public Piloto(String name){
		this.name = name;
		this.AI = genGaussian(103, 3);
//		genPotential();
		this.careerLeft = 18;
	}
	
	public Piloto(){
	}
	
	public void updatePts(){
		totals.updatePts();
		season.updatePts();
		playoff.updatePts();
	}
	
	public Double genGaussian(Double mean, Double dev){
		return new Random().nextGaussian()*dev+mean;
	}
	public Integer genGaussian(Integer mean, Integer dev){
		return (int)(new Random().nextGaussian()*dev+mean);
	}
	
	public void updateDriverAI(){
		new FileStreamController().updateDriverAI(this);
	}
	
	public void updatePlacingAI(int placing, int diff){
		int ptsBase[] = {1,2,3,4,5};
		int pts = ptsBase[placing-1];
		this.potential += pts;
		
		if(diff <= 10) {
			this.potential += 3;
		}
//		int lvls = (pts) - (int)((pts/4.0)*(diff/(pts*2.0)));
		
//		int lvls = 4 - (int)((4.0*(diff/20.0)) - 0.1);
//		
//		if(lvls < 0){lvls = 0;}
//		this.AI += lvls;
		
//		String log = "Placing: "+this.name+": +"+lvls;
//		System.out.println(log);
	}
	
	//removed v6.5
//	public void genPotential(){
//		int[] pots = {1,1,1,1,2,2,2,3,3,4};
//		this.potential = pots[new Random().nextInt(pots.length)];
//	}
	public void applyPotential(){
//		double rand = Math.round(new Random().nextDouble() * 1000.0)/1000.0;
		double rand = genGaussian(0.5, 0.3);
		if(rand < 0) {
			rand = 0.0;
		}
		this.xp += rand * this.potential;
//		this.potential = pots[new Random().nextInt(pots.length)];
		
		//atualiza AI e xp com o valor real aumentado no AI
		double aux = xp;
		Integer xpInt = (int)aux;
		this.AI += xpInt;
		this.xp -= xpInt;
		
		//this.potential = 0;
	}
	
	public void updateTimeAI(){
		if(this.xp == null){
			this.xp = 0.0;
		}
		
		//removed v6.5
//		double rand = Math.round(new Random().nextDouble() * 1000.0)/1000.0;
//		this.xp += rand * this.potential;
		
//		// added v2.5; removed v4.0; added v6.5
		if(careerLeft > 12){
			this.AI += 3;
		}
		if(careerLeft > 9){
			this.potential += 1;
		}
		
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
	
}
