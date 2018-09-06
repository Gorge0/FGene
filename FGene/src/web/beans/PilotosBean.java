package web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import core.FGene;
import core.Piloto;

@Named
@ApplicationScoped
public class PilotosBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Piloto> pilotos = new ArrayList<>();
	
	private Piloto selectedPiloto;
	
	public List<Piloto> getPilotos() {
		return pilotos;
	}

	public void setPilotos(List<Piloto> pilotos) {
		this.pilotos = pilotos;
	}

	@PostConstruct
	public void init() {
		this.pilotos = FGene.getAllPilots();
	}

	public Piloto getSelectedPiloto() {
		return selectedPiloto;
	}

	public void setSelectedPiloto(Piloto selectedPiloto) {
		this.selectedPiloto = selectedPiloto;
	}
	
}
