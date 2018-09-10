package core;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.stream.Collectors;

import core.enums.Bonus;
import core.enums.Powers;

public class Season implements Serializable{
	
	private static final long serialVersionUID = 3L;
	
	public Integer ano;
	public Bonus bonus = Bonus.get();
	
	public boolean isEnded = false; 
	
	public Vector<Equipe> equipes = new Vector<Equipe>(18);
	
	public ArrayList<Piloto> playoffs = new ArrayList<>();
	public ArrayList<Piloto> playoffsEquipe = new ArrayList<>();
	
	public Season(){
		this.ano = FGene.getAllSeasons().size()+1;
		this.equipes = FGene.getCopy(FGene.getAllEquipes());
		genGroups();
		
		for (Equipe e: this.equipes){
			e.piloto1.season.zerar();
			e.piloto1.playoff.zerar();
			e.piloto1.pureSeason = new Stats2(e.piloto1.season);
			e.piloto2.season.zerar();
			e.piloto2.playoff.zerar();
			e.piloto2.pureSeason = new Stats2(e.piloto2.season);
			e.stats.zerar();
			e.pureStats = new Stats2(e.stats);
		}
		
		FGene.getAllSeasons().add(0,this);
	}
	
	public void update(){
		if(!this.isEnded){
			FileStreamController ctrl = new FileStreamController();
			ArrayList<Piloto> psMem = ctrl.readDriverFiles();
			
			if(playoffs.isEmpty()){
				ArrayList<Piloto> pHere = pHere();
				int cont = 0;
				for(Piloto p: psMem){
					Piloto pHereAux = FGene.getPiloto(pHere, p.name);
					pHereAux.season = Stats.somarStats(p.totals, FGene.getPiloto(p.name).totals, false);
					pHereAux.totals = p.totals;
					pHereAux.pureSeason = new Stats2(pHereAux.season);
					
					if(!pHereAux.season.isEmpty()){
						cont++;
					}
				}
				
				for(Equipe e : equipes){
					e.stats = Stats.somarStats(e.piloto1.season, e.piloto2.season, true);
					e.pureStats = new Stats2(e.stats);
				}
				
				if(cont == pHere.size()){
					genPilotPlayoff();
					genEquipePlayoff();
				}
			}else{
				if(this.playoffs.get(0).playoff.pts == 0){
					for(Piloto p: playoffs){
						Piloto psMemAux = FGene.getPiloto(psMem, p.name);
						p.totals = psMemAux.totals;
						p.playoff = Stats.somarStats(p.totals, Stats.somarStats(p.season, FGene.getPiloto(p.name).totals, true), false);
					}
					playoffs.sort((p2,p1) -> p1.playoff.pts.compareTo(p2.playoff.pts));
				}else{
					for(Piloto p: playoffsEquipe){
						Piloto psMemAux = FGene.getPiloto(psMem, p.name);
						p.totals = psMemAux.totals;
						if(playoffs.contains(p)){
							p.playoffEquipe = Stats.somarStats(p.totals, Stats.somarStats(p.playoff,Stats.somarStats(p.season, FGene.getPiloto(p.name).totals, true),true), false);
						}else{
							p.playoffEquipe = Stats.somarStats(p.totals, Stats.somarStats(p.season, FGene.getPiloto(p.name).totals, true), false);
						}
					}
					playoffsEquipe.sort((p2,p1) -> p1.playoffEquipe.pts.compareTo(p2.playoffEquipe.pts));
				}
			}
		}
	}
	
//	public void genPilotPlayoff(){
//		ArrayList<Piloto> ps = new ArrayList<>();
//		for(int i=0;i<equipes.size();i++){
//			ps.add(equipes.get(i).piloto1);
//			ps.add(equipes.get(i).piloto2);
//			if((i+1)%3 == 0){
//				ps.stream().sorted((p2, p1) -> p1.season.pts.compareTo(p2.season.pts)).limit(1).forEach(s -> playoffs.add(s));
//				ps.clear();
//			}
//		}
//	}
	public void genPilotPlayoff(){
		//updated v6.7
		for(ArrayList<Piloto> ps : getGroups()){
			ps.stream().sorted(Comparator.comparing((Piloto p1)->p1.season.pts)
			          .thenComparing(p1->p1.season.p1st)
			          .thenComparing(p1->p1.season.p2nd)
			          .thenComparing(p1->p1.season.p3rd)
			          .thenComparing(p1->p1.season.p4th)
			          .thenComparing(p1->p1.season.p5th)
			          .thenComparingInt(p1->p1.season.p6th)
			          .reversed()).limit(1).forEach(s -> playoffs.add(s));
		}
	}
	public ArrayList<ArrayList<Piloto>> getGroups(){
		ArrayList<ArrayList<Piloto>> result = new ArrayList<>();
		ArrayList<Piloto> ps = new ArrayList<>();
		for(int i=0;i<equipes.size();i++){
			ps.add(equipes.get(i).piloto1);
			ps.add(equipes.get(i).piloto2);
			if((i+1)%3 == 0){
				result.add(ps);
				ps = new ArrayList<>();
			}
		}
		return result;
	}
	
	public void genEquipePlayoff(){
		this.equipes.stream().sorted((e2, e1) -> e1.stats.pts.compareTo(e2.stats.pts)).limit(3).forEach(s -> {playoffsEquipe.add(s.piloto1);playoffsEquipe.add(s.piloto2);});
	}
	
	public void end(){
		if(!this.playoffs.isEmpty()){
			if(this.playoffsEquipe.get(0).playoffEquipe.pts != 0){
				updateBonus();//gotta be first
				updatePowers();
				updateAI();
				updateEquipeStats();
				FGene.updateContracts();
				this.isEnded = true;
			}
		}
	}
	
	public ArrayList<Piloto> pHere(){
		ArrayList<Piloto> pHere = new ArrayList<>();
		for(Equipe e: this.equipes){
			pHere.add(e.piloto1);
			pHere.add(e.piloto2);
		}
		return pHere;
	}
	
	private void genGroups(){
		Collections.shuffle(this.equipes);
	}

	public void updateEquipeStats(){
		boolean flagP = true;
		boolean flagC = true;
		boolean flagR = true;
		for(Piloto p : pHere()){
			Piloto pFGene = FGene.getPiloto(p.name);
			Equipe e = FGene.getEquipeOfPiloto(FGene.getAllEquipes(), pFGene);
			
			pFGene.seasons++;
			
			pFGene.totals = p.totals;
			pFGene.season = Stats.somarStats(p.season, pFGene.season, true);
			pFGene.playoff = Stats.somarStats(p.playoff, Stats.somarStats(pFGene.playoff, p.playoffEquipe, true),true);
			
			pFGene.pureSeason = new Stats2(pFGene.season);
			
			if(this.playoffs.contains(p)){
				pFGene.playoffs++;
				e.playoffs++;
				if(this.playoffs.indexOf(p) == 0){
					pFGene.pChamp++;
					e.pChamps++;
				}
				if(this.playoffs.indexOf(p) == 1){
					pFGene.pRunnerUp++;
					e.pRunnerUps++;
				}
			}
			if(this.playoffsEquipe.contains(p)){
				pFGene.playoffs++;
				if(flagP){
					e.playoffs++;
					flagP = false;
				}
				if(this.getEqsPlayoff().indexOf(FGene.getEquipeOfPiloto(this.equipes, p)) == 0){
					pFGene.eChamp++;
					if(flagC){
						e.eChamps++;
						flagC = false;
					}
				}
				if(this.getEqsPlayoff().indexOf(FGene.getEquipeOfPiloto(this.equipes, p)) == 1){
					pFGene.eRunnerUp++;
					if(flagR){
						e.eRunnerUps++;
						flagR = false;
					}
				}
			}
		}
	}
	
	public void updatePowers(){
		//updateBonus();
		for(Equipe eHere : equipes){
			Equipe eFGene = FGene.getEquipe(eHere.name);
			int flag = 0, cont2 = 0, cont = 0;
			while(flag == 0 && cont < 20){
				Powers p = Powers.get();
				double atual = eFGene.powers.get(p);
				if((atual < (p.def + FGene.MAXPOWERCHANGES*Math.abs(p.inc))) && (atual > (p.def - FGene.MAXPOWERCHANGES*Math.abs(p.inc)))){
					if(playoffs.contains(eHere.piloto1) || playoffs.contains(eHere.piloto2)){
						if(eFGene.currentBonus != Bonus.VETO){
							eFGene.powers.put(p,atual-p.inc);
						}
						if(++cont2 == 2){
							cont2 = 0;
							flag = 1;
						}
					}else{
						eFGene.powers.put(p,atual+p.inc);
						flag = 1;
					}
				}else{
					cont++;
				}
			}
		}
		new FileStreamController().updateCarFile();
	}
	
	public void updateBonus(){
		Equipe pChamp = FGene.getEquipe(FGene.getEquipeOfPiloto(this.equipes, this.playoffs.get(0)).name);
		Equipe eChamp = FGene.getEquipe(this.getEqsPlayoff().get(0).name);
		Equipe rand = FGene.getEquipe(this.equipes.get(new Random().nextInt(this.equipes.size())).name);
//		for(Equipe e : this.equipes){
//			FGene.getEquipe(e.name).removeBonus();
//		}
		pChamp.addBonus(Bonus.CONTRATO, this.playoffs.get(0).name);
		Bonus[] powers = {Bonus.AIR,Bonus.GRIP,Bonus.POWER,Bonus.SLOWDOWN};
		powers[new Random().nextInt(powers.length)].add(eChamp, eChamp.piloto1);
		powers[new Random().nextInt(powers.length)].remove(rand);
		
//		if(pChamp == eChamp){
//			pChamp.addBonus(this.bonus, this.playoffs.get(0).name);
//		}else{
//			pChamp.addBonus(this.bonus, this.playoffs.get(0).name);
//			eChamp.addBonus(this.bonus, eChamp.piloto1.name);
//		}
	}
	
	
	public void updateAI(){
		for(Piloto p : pHere()){
			FGene.getPiloto(p.name).potential = 0;
			FGene.getPiloto(p.name).updateTimeAI();
		}
		
//		ArrayList<Piloto> ps = new ArrayList<>();
//		for(int i=0;i<equipes.size();i++){
//			ps.add(equipes.get(i).piloto1);
//			ps.add(equipes.get(i).piloto2);
			//System.out.println(equipes.get(i).piloto1.name+": "+equipes.get(i).piloto1.pts+"       -       "+equipes.get(i).piloto2.name+": "+equipes.get(i).piloto2.pts);
			for(ArrayList<Piloto> ps : getGroups()){
				ps.sort((p2, p1) -> p1.season.pts.compareTo(p2.season.pts));
				
				for(int j=1;j<6;j++){
					if(j<6){
						int diff = 0;
						//int after = ps.get(j).season.pts - ps.get(j+1).season.pts;
						int before = 200;
						if(j != 0){
							before = ps.get(j-1).season.pts - ps.get(j).season.pts;
						}else{
							before = 200;
						}
						//changed v6.5
//						if(after < before){
//							diff = after;
//						}else{
//						diff = before;
//						}
						diff = before;
						FGene.getPiloto(ps.get(j).name).updatePlacingAI(j, diff);
					}else{
						if(j == 5 || j == 4){
							//removed v6.5
//							FGene.repCheck(ps.get(j));
						}
					}
				}
//				ps.clear();
			}
		
		FGene.repCheck(this.playoffs);
		
		//added v6.5
		for(Piloto p : pHere()){
			FGene.getPiloto(p.name).applyPotential();
		}
		
		FileStreamController con = new FileStreamController();
		for(Piloto p : pHere()){
			con.updateDriverAI(FGene.getPiloto(p.name));
		}
		
	}
	
	public ArrayList<Equipe> getEqsPlayoff(){
		ArrayList<Equipe> eqs = new ArrayList<>();
		for(Equipe e : this.equipes){
			if(this.playoffsEquipe != null){
				if(this.playoffsEquipe.contains(e.piloto1) && this.playoffsEquipe.contains(e.piloto2)){
					eqs.add(e);
				}
			}
		}
		eqs.sort((e2,e1) -> (Stats.somarStats(e1.piloto1.playoffEquipe, e1.piloto2.playoffEquipe, true).pts).compareTo(Stats.somarStats(e2.piloto1.playoffEquipe, e2.piloto2.playoffEquipe, true).pts));
		return eqs;
	}
	
	@Override
	public String toString() {
		if(this.isEnded){
//			String pChamp = this.playoffs.get(0).name;
//			String eChamp = FGene.getEquipeOfPiloto(this.equipes,this.playoffs.get(0)).name;
//			String eqChamp = "";
//			if(this.playoffsEquipe != null){
//				eqChamp = this.getEqsPlayoff().get(0).name;
//			}else{
//				eqChamp = "";
//			}
//			return "Season "+this.ano+" "+"P: "+pChamp+" - "+eChamp+" E: "+eqChamp;
			return "SEASON "+this.ano;
		}else{
			return "SEASON "+this.ano;
		}
	}
	
	public Piloto getPilotByName(String name){
		for(Piloto p : pHere()){
			if(p.name.equals(name)){
				return p;
			}
		}
		return null;
	}
	public Equipe getEquipeByName(String name){
		for(Equipe e : this.equipes){
			if(e.name.equals(name)){
				return e;
			}
		}
		return null;
	}
	
	public Integer getPosPiloto(String name){
		if(this.isEnded){
			Integer value = 0;
			Piloto p = this.getPilotByName(name);
			if(p != null){
				if(this.playoffs.contains(p)){
					value = this.playoffs.size()- this.playoffs.indexOf(p) + 4;
				}else{
					ArrayList<ArrayList<Piloto>> groups = this.getGroups();
					for(ArrayList<Piloto> ps : groups){
						if(ps.contains(p)){
							ps.sort((p2,p1) -> p1.season.pts.compareTo(p2.season.pts));
							value = ps.size()- ps.indexOf(p) - 1;
						}
					}
				}
				return value;
			}
		}
		
		return null;
	}

	public Integer getPosEquipe(String name) {
		if(this.isEnded){
			Integer value = 0;
			Equipe e = this.getEquipeByName(name);
			if(e != null){
				List<Equipe> eqs = this.equipes.stream().sorted((e2,e1) -> e1.stats.pts.compareTo(e2.stats.pts)).collect(Collectors.toList());
				value = (eqs.size() - eqs.indexOf(e)) - 1;
				if(value > 14 && this.playoffsEquipe != null){
					value = (eqs.size() - getEqsPlayoff().indexOf(e))-1;
				}
			}
			return value;
		}
		return null;
	}

	
}
