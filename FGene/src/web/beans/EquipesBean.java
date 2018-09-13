package web.beans;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Stats;
import core.Stats2;
import core.enums.Powers;

@Named
@RequestScoped
public class EquipesBean {

	private List<Equipe> equipes = new Vector<>();
	
	private Equipe selectedEquipe;
	
	private HashMap<String, Powers> powerMapping= new HashMap<>();
	
	@PostConstruct
	public void init() {
		this.equipes = FGene.getAllEquipes();
		powerMapping.put("AIR", Powers.AIR);
		powerMapping.put("POWER", Powers.POWER);
		powerMapping.put("SLOWDOWN", Powers.SLOWDOWN);
		powerMapping.put("GRIP", Powers.GRIP);
	}
	
	public Stats2 genStats2(Stats s) {
		return new Stats2(s);
	}
	
	public Integer getMedals(Equipe e) {
		return e.getMedalCampPiloto().getTotal() + e.getMedalCampEquipe().getTotal();
	}
	public Double getPlayoffEff(Equipe e) {
		return (e.getMedalCampEquipe().gold)/new Double(e.getMedalCampEquipe().chances);
	}
	public String calculateGrade(Equipe e) {
		DecimalFormat f = new DecimalFormat("+#;-#");
		int grade = 0;
		for(Powers p : e.powers.keySet()){
			grade += Math.round((e.powers.get(p)-p.def)/p.inc);
		}
		return f.format(grade);
	}

	public List<Equipe> getEquipes() {
		return equipes;
	}

	public void setEquipes(List<Equipe> equipes) {
		this.equipes = equipes;
	}

	public Equipe getSelectedEquipe() {
		return selectedEquipe;
	}

	public void setSelectedEquipe(Equipe selectedEquipe) {
		this.selectedEquipe = selectedEquipe;
	}

	public HashMap<String, Powers> getPowerMapping() {
		return powerMapping;
	}

	public void setPowerMapping(HashMap<String, Powers> powerMapping) {
		this.powerMapping = powerMapping;
	}

}
