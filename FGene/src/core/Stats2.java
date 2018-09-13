package core;

import java.io.Serializable;
import java.text.DecimalFormat;

import core.annotations.JTableAnno;
import core.enums.JTableMode;

public class Stats2 implements Serializable{
	
	private static final long serialVersionUID = 6L;

	public static final DecimalFormat format = new DecimalFormat("#.000");
	
	private Stats stats;
	
//	@JTableAnno(modes={JTableMode.PSEASONS})
//	public Double p1stPerSeason;
	
//	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON,JTableMode.EQSEASON})
//	public Double ptsPerRace;
	
//	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON,JTableMode.EQSEASON})
//	public Double overall;
	
	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON})
	public Double winRATE;
	
	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON})
	public Double ptRATE;
	
	@JTableAnno(modes={JTableMode.EQSEASON})
	public Double eqWinRATE;
	
	@JTableAnno(modes={JTableMode.EQSEASON})
	public Double eqPtRATE;
	
	public Stats2(Stats st){
		this.stats = st;
		update();
	}
	
	public void update(){
		Double races = new Double(stats.getTotalRaces());
		if(races != 0){
			winRATE = stats.p1st/races;
			eqWinRATE = stats.p1st/(races/2);
			ptRATE = (stats.pts/races)/8;
			eqPtRATE = (stats.pts/(races/2))/13;
		}else{
			winRATE = 0.0;
			eqWinRATE = 0.0;
			ptRATE = 0.0;
			eqPtRATE = 0.0;
		}
	}

	public Double getWinRATE() {
		return winRATE;
	}

	public void setWinRATE(Double winRATE) {
		this.winRATE = winRATE;
	}

	public Double getPtRATE() {
		return ptRATE;
	}

	public void setPtRATE(Double ptRATE) {
		this.ptRATE = ptRATE;
	}

	public Double getEqWinRATE() {
		return eqWinRATE;
	}

	public void setEqWinRATE(Double eqWinRATE) {
		this.eqWinRATE = eqWinRATE;
	}

	public Double getEqPtRATE() {
		return eqPtRATE;
	}

	public void setEqPtRATE(Double eqPtRATE) {
		this.eqPtRATE = eqPtRATE;
	}
}
