package web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import core.FGene;
import core.Piloto;
import core.Stats;
import core.Stats2;

@Named
@RequestScoped
public class PilotosBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Piloto> pilotos = new ArrayList<>();
	
	private Piloto selectedPiloto;
	
	@PostConstruct
	public void init() {
		this.pilotos = FGene.getAllPilots();
	}
	
	public Stats2 genStats2(Stats s) {
		return new Stats2(s);
	}
	
	public Integer getMedals(Piloto p) {
		return p.getMedalCampPiloto().getTotal() + p.getMedalCampEquipe().getTotal();
	}
	public Double getPlayoffEff(Piloto p) {
		return (p.getMedalCampPiloto().getTotal())/new Double(p.getMedalCampPiloto().chances);
	}
	
	public List<Piloto> getPilotos() {
		return pilotos;
	}
	
	public void setPilotos(List<Piloto> pilotos) {
		this.pilotos = pilotos;
	}

	public Piloto getSelectedPiloto() {
		return selectedPiloto;
	}

	public void setSelectedPiloto(Piloto selectedPiloto) {
		this.selectedPiloto = selectedPiloto;
	}

}
