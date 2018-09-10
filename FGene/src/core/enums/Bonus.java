package core.enums;
import java.util.Random;

import core.Equipe;
import core.FGene;
import core.Piloto;

public enum Bonus {

	CONTRATO("+1 ano de contrato para piloto") {
		@Override
		public void add(Equipe e, Piloto p) {
			if(e.piloto1 == p){
				if(e.contract1 < e.piloto1.careerLeft){
					e.contract1++;
				}
			}else{
				if(e.contract2 < e.piloto2.careerLeft){
					e.contract2++;
				}
			}
		}

		@Override
		public void remove(Equipe e) {}
	},
	
	NEWCONTRATO("+2 bonus sorteio de novo contrato para pilotos da casa") {
		@Override
		public void add(Equipe e, Piloto p) {}
		@Override
		public void remove(Equipe e) {}
	},
	
	LVL("+2 lvl para piloto") {
		@Override
		public void add(Equipe e, Piloto p) {
			p.AI += 2;
		}

		@Override
		public void remove(Equipe e) {}
	},
	
	AIR("power-up Air por uma temporada") {
		@Override
		public void add(Equipe e, Piloto p) {
			addPowers(e,true);
		}

		@Override
		public void remove(Equipe e) {
			addPowers(e,false);
		}
	},
	SLOWDOWN("power-up slowdown por uma temporada") {
		@Override
		public void add(Equipe e, Piloto p) {
			addPowers(e,true);
		}

		@Override
		public void remove(Equipe e) {
			addPowers(e,false);
		}
	},
	POWER("power-up power por uma temporada") {
		@Override
		public void add(Equipe e, Piloto p) {
			addPowers(e,true);
		}

		@Override
		public void remove(Equipe e) {
			addPowers(e,false);
		}
	},
//	SPEED("power-up top speed por uma temporada") {
//		@Override
//		public void add(Equipe e, Piloto p) {
//			addPowers(e,true);
//		}
//
//		@Override
//		public void remove(Equipe e) {
//			addPowers(e,false);
//		}
//	},
//	SLIDING("power-up sliding por uma temporada") {
//		@Override
//		public void add(Equipe e, Piloto p) {
//			addPowers(e,true);
//		}
//
//		@Override
//		public void remove(Equipe e) {
//			addPowers(e,false);
//		}
//	},
	GRIP("power-up grip por uma temporada") {
		@Override
		public void add(Equipe e, Piloto p) {
			addPowers(e,true);
		}

		@Override
		public void remove(Equipe e) {
			addPowers(e,false);
		}
	},
	VETO("veto power-down") {
		@Override
		public void add(Equipe e, Piloto p) {}
		@Override
		public void remove(Equipe e) {}
	},
	
	POTENCIAL("+1 potencial para piloto") {
		@Override
		public void add(Equipe e, Piloto p) {
			if(p.potential < 4){
				p.potential += 1;
			}
		}

		@Override
		public void remove(Equipe e){}
	};
	
	public String sig;
	
	private Bonus(String name){
		this.sig = name;
	}
	public abstract void add(Equipe e, Piloto p);
	public abstract void remove(Equipe e);
	
	protected final void addPowers(Equipe e, boolean isSoma){
		Powers p = Powers.getByBonus(this);
		double atual = e.powers.get(p);
		if((atual < (p.def + FGene.MAXPOWERCHANGES*Math.abs(p.inc))) && (atual > (p.def - FGene.MAXPOWERCHANGES*Math.abs(p.inc)))){
			if(isSoma){
				e.powers.put(p,atual+p.inc);
			}else{
				e.powers.put(p,atual-p.inc);
			}
		}
	}
	
	//Fazer um metodo generico para rodar powers tambem
	public static Bonus get(){
		return Bonus.values()[new Random().nextInt(Bonus.values().length)];
	}
}
