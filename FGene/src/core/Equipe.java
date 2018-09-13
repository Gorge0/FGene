package core;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Random;

import core.annotations.JTableAnno;
import core.enums.Bonus;
import core.enums.JTableMode;
import core.enums.Powers;

public class Equipe implements Serializable, Cloneable{

	private static final long serialVersionUID = 2L;
	
	public String name;
	
	public Piloto piloto1;
	public Piloto piloto2;
	public Integer contract1;
	public Integer contract2;
	
	private Medals medalCampPiloto = new Medals();
	private Medals medalCampEquipe = new Medals();
	
	public Bonus currentBonus = null;
	
	public EnumMap<Powers, Double> powers = new EnumMap<>(Powers.class);
	
	public Stats statsSeason = new Stats();
	public Stats statsPlayoffE = new Stats();
	
	public Equipe(String name){
		this.name = name;
		powers.put(Powers.AIR, 2.0);
		powers.put(Powers.SLOWDOWN, 0.1);
		powers.put(Powers.POWER, 400.0);
		powers.put(Powers.GRIP, 3.0);
	}
	
	public Equipe(String name, Piloto p1, Integer con1, Piloto p2, Integer con2){
		this(name);
		this.piloto1 = p1;
		this.piloto2 = p2;
		this.contract1 = con1;
		this.contract2 = con2;
	}

	public boolean isPilotoHere(Piloto p){
		if(p == piloto1 || p == piloto2){
			return true;
		}else{
			return false;
		}
	}
	
	public void addBonus(Bonus b, String pName){
		if(pName.equals(piloto1.name)){
			b.add(this, piloto1);
		}else{
			b.add(this, piloto2);
		}
		this.currentBonus = b;
	}
	public void removeBonus(){
		if(this.currentBonus != null){
			this.currentBonus.remove(this);
			this.currentBonus = null;
		}
	}
	
	public void genContract(Piloto p){
		int c = new Random().nextInt(FGene.MAXCONTRACT)+1;
		if(this.piloto1 == null){
			this.piloto1 = p;
			if(c > p.careerLeft){
				this.contract1 = p.careerLeft;
			}else{
				this.contract1 = c;
			}
		}else{
			if(this.piloto2 == null){
				this.piloto2 = p;
						if(c > p.careerLeft){
							this.contract2 = p.careerLeft;
						}else{
							this.contract2 = c;
						}
			}else{
				System.out.println("Erro no genContract");
			}
		}
	}
	public void changeContract(){
		int c = new Random().nextInt(FGene.MAXCONTRACT)+1;
		if(c > this.piloto1.careerLeft){
			this.contract1 = this.piloto1.careerLeft;
		}else{
			this.contract1 = c;
		}
		c = new Random().nextInt(FGene.MAXCONTRACT)+1;
		if(c > this.piloto2.careerLeft){
			this.contract2 = this.piloto2.careerLeft;
		}else{
			this.contract2 = c;
		}
	}
	
	public Integer getContract(Piloto p){
		if(p == this.piloto1){
			return contract1;
		}
		if(p == this.piloto2){
			return contract2;
		}
		return 0;
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

	public Piloto getPiloto1() {
		return piloto1;
	}

	public void setPiloto1(Piloto piloto1) {
		this.piloto1 = piloto1;
	}

	public Piloto getPiloto2() {
		return piloto2;
	}

	public void setPiloto2(Piloto piloto2) {
		this.piloto2 = piloto2;
	}

	public Integer getContract1() {
		return contract1;
	}

	public void setContract1(Integer contract1) {
		this.contract1 = contract1;
	}

	public Integer getContract2() {
		return contract2;
	}

	public void setContract2(Integer contract2) {
		this.contract2 = contract2;
	}

	public Bonus getCurrentBonus() {
		return currentBonus;
	}

	public void setCurrentBonus(Bonus currentBonus) {
		this.currentBonus = currentBonus;
	}

	public EnumMap<Powers, Double> getPowers() {
		return powers;
	}

	public void setPowers(EnumMap<Powers, Double> powers) {
		this.powers = powers;
	}

	public Stats getStatsSeason() {
		return statsSeason;
	}

	public void setStatsSeason(Stats statsSeason) {
		this.statsSeason = statsSeason;
	}

	public Stats getStatsPlayoffE() {
		return statsPlayoffE;
	}

	public void setStatsPlayoffE(Stats statsPlayoffE) {
		this.statsPlayoffE = statsPlayoffE;
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
