package core;

public class Medals {

	public Integer gold = 0;
	public Integer silver = 0;
	public Integer bronze = 0;
	
	public Integer chances = 0;
	
	public Integer getTotal() {
		return gold + silver + bronze;
	}
	
	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Integer getSilver() {
		return silver;
	}

	public void setSilver(Integer silver) {
		this.silver = silver;
	}

	public Integer getBronze() {
		return bronze;
	}

	public void setBronze(Integer bronze) {
		this.bronze = bronze;
	}

	public Integer getChances() {
		return chances;
	}

	public void setChances(Integer chances) {
		this.chances = chances;
	}

}
