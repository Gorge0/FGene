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
	
	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON,JTableMode.EQSEASON})
	public Double winRATE;
	
	@JTableAnno(modes={JTableMode.PSEASONS,JTableMode.PSEASON,JTableMode.EQSEASON})
	public Double ptRATE;
	
	public Stats2(Stats st){
		this.stats = st;
		update();
	}
	
	public void update(){
		Double races = new Double(stats.getTotalRaces());
		if(races != 0){
			winRATE = stats.p1st/races;
			ptRATE = (stats.pts/races)/8;
		}else{
			winRATE = 0.0;
			ptRATE = 0.0;
		}
	}
}
