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
	
	public Integer potential;
	public Double xp;
	
	public Integer careerLeft;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer seasons = 0;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer pChamp = 0;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer pRunnerUp = 0;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer eChamp = 0;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
	public Integer eRunnerUp = 0;
	
	@JTableAnno(modes={JTableMode.PSEASONS})
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
		genPotential();
		this.careerLeft = caLeft;
	}
	
	public Piloto(String name){
		this.name = name;
		this.AI = genGaussian(103, 3);
		genPotential();
		this.careerLeft = 18;
	}
	
	public Piloto(){
	}
	
	public void updatePts(){
		totals.updatePts();
		season.updatePts();
		playoff.updatePts();
	}
	
	public Integer genGaussian(Integer mean, Integer dev){
		return (int)(new Random().nextGaussian()*dev+mean);
	}
	
	public void updateDriverAI(){
		new FileStreamController().updateDriverAI(this);
	}
	
	public void updatePlacingAI(int placing, int diff){
//		int ptsBase[] = {8,5,3,2};
		int ptsBase[] = {4,3,2,1};
		int pts = ptsBase[placing];
		int lvls = (pts) - (int)((pts/4.0)*(diff/(pts*2.0)));
		if(lvls < 0){lvls = 0;}
		//lvls /= 2; // added v2.5
		String log = "Placing: "+this.name+": +"+lvls;
		this.AI += lvls;
		
		System.out.println(log);
	}
	
	public void genPotential(){
		int[] pots = {1,1,1,1,2,2,2,3,3,4};
		this.potential = pots[new Random().nextInt(pots.length)];
	}
	
	public void updateTimeAI(){
		
		
		if(this.xp == null){
			this.xp = 0.0;
		}
		
		double rand = Math.round(new Random().nextDouble() * 1000.0)/1000.0;
		this.xp += rand * this.potential;
		
		double aux = xp;
		Integer xpInt = (int)aux;
		String log = "Time: "+this.name+": +"+xpInt;
		this.AI += xpInt;
		this.xp -= xpInt;
		
		//removed v4.0
//		double lvls = (genGaussian(10, 5)/100.0) * (potential - AI);
//		lvls /= 2; // added v2.5
//		this.AI += (int)lvls;
		
//		// added v2.5; removed v4.0
//		if(careerLeft > 12){
//			this.AI += 2;
//		}
		System.out.println(log);
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

	public Integer getSeasons() {
		return seasons;
	}

	public void setSeasons(Integer seasons) {
		this.seasons = seasons;
	}

	public Integer getpChamp() {
		return pChamp;
	}

	public void setpChamp(Integer pChamp) {
		this.pChamp = pChamp;
	}

	public Integer getpRunnerUp() {
		return pRunnerUp;
	}

	public void setpRunnerUp(Integer pRunnerUp) {
		this.pRunnerUp = pRunnerUp;
	}

	public Integer geteChamp() {
		return eChamp;
	}

	public void seteChamp(Integer eChamp) {
		this.eChamp = eChamp;
	}

	public Integer geteRunnerUp() {
		return eRunnerUp;
	}

	public void seteRunnerUp(Integer eRunnerUp) {
		this.eRunnerUp = eRunnerUp;
	}

	public Integer getPlayoffs() {
		return playoffs;
	}

	public void setPlayoffs(Integer playoffs) {
		this.playoffs = playoffs;
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

	public Stats2 getPureSeason() {
		return pureSeason;
	}

	public void setPureSeason(Stats2 pureSeason) {
		this.pureSeason = pureSeason;
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
	
}
