package core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import core.annotations.JTableAnno;
import core.enums.JTableMode;

public class Piloto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public String name;
	public Integer AI;
	public Integer potential=0;
	public Double xp=0.0;
	
	public Integer careerLeft = 18;
	
	private Medals medalCampPiloto = new Medals();
	private Medals medalCampEquipe = new Medals();
	
	public Stats totals = new Stats();
	public Stats season = new Stats();
	public Stats playoff = new Stats();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAI() {
		return AI;
	}

	public void setAI(Integer aI) {
		AI = aI;
	}

	public Integer getPotential() {
		return potential;
	}

	public void setPotential(Integer potential) {
		this.potential = potential;
	}

	public Double getXp() {
		return xp;
	}

	public void setXp(Double xp) {
		this.xp = xp;
	}

	public Integer getCareerLeft() {
		return careerLeft;
	}

	public void setCareerLeft(Integer careerLeft) {
		this.careerLeft = careerLeft;
	}

	public Stats getTotals() {
		return totals;
	}

	public void setTotals(Stats totals) {
		this.totals = totals;
	}

	public Stats getSeason() {
		return season;
	}

	public void setSeason(Stats season) {
		this.season = season;
	}

	public Stats getPlayoff() {
		return playoff;
	}

	public void setPlayoff(Stats playoff) {
		this.playoff = playoff;
	}

	public Stats getPlayoffEquipe() {
		return playoffEquipe;
	}

	public void setPlayoffEquipe(Stats playoffEquipe) {
		this.playoffEquipe = playoffEquipe;
	}

	public Medals getMedalCampPiloto() {
		return medalCampPiloto;
	}

	public void setMedalCampPiloto(Medals medalCampPiloto) {
		this.medalCampPiloto = medalCampPiloto;
	}

	public Medals getMedalCampEquipe() {
		return medalCampEquipe;
	}

	public void setMedalCampEquipe(Medals medalCampEquipe) {
		this.medalCampEquipe = medalCampEquipe;
	}

}
