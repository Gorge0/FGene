package web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import core.FGene;
import core.Piloto;
import core.Season;
import core.enums.JTableMode;

@Named
@RequestScoped
public class PilotosBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<Piloto> pilotos = new ArrayList<>();
	private List<Season> seasons = new ArrayList<>();
	
	private ArrayList<String> data = new ArrayList<>();
	
	private Piloto selectedPiloto;
	
	@PostConstruct
	public void init() {
		this.pilotos = FGene.getAllPilots();
		this.seasons = FGene.getAllSeasons();
		this.data = JTableMode.PPLAYOFF.genData(this.pilotos);
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

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(List<Season> seasons) {
		this.seasons = seasons;
	}

	public ArrayList<String> getData() {
		return data;
	}

	public void setData(ArrayList<String> data) {
		this.data = data;
	}
	
}
