package core.enums;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Season;
import core.Stats;
import core.Stats2;
import core.annotations.JTableAnno;
import gui.EquipePanel;
import gui.PilotoPanel;

public enum JTableMode {

	//Stats absolutos em temporada regular de pilotos
	PSEASONS(Piloto.class, 11, 600, 350),
	//Stats absolutos em uma temporada regular de pilotos; tela de season
	PSEASON(Piloto.class, 10, 400, 500),
	//Stats absolutos totais de pilotos;
	ALLPILOTS(Piloto.class, 8, 450, 350),
	//Stats absolutos de playoffs de pilotos;
	PPLAYOFF(Piloto.class, 22, 1050, 450){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = super.genColunas();
			col.add(1,"G-p");
			col.add(2,"S-p");
			col.add(3,"B-p");
			col.add(4,"OFF-p");
			col.add(5,"G-e");
			col.add(6,"S-e");
			col.add(7,"B-e");
			col.add(8,"OFF-e");
			col.add(9,"Medals");
			col.add("W");
			col.add("PT");
			col.add("OFF");
			col.add("G");
			col.add("OFF-EFF");
			return col;
		}
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
//			ArrayList<String> data = new ArrayList<>();
			ArrayList<String> data = super.genData(a);
			int cont = 1;
			for(A a2 : a){
				Piloto p = (Piloto)a2;
				double win = 0.0;
				double pt = 0.0;
				
				int gold = 0;
				int silver = 0;
				int bronze = 0;
				int playoff = 0;
				int goldE = 0;
				int silverE = 0;
				int bronzeE = 0;
				int playoffE = 0;
				
				for(Season s : FGene.getAllSeasons()){
					if(s.isEnded){
						Piloto pSeason = FGene.getPiloto(s.pHere(), p.name);
						if(pSeason != null){
							Stats2 stat = new Stats2(pSeason.playoff);
							Stats2 statEq = new Stats2(pSeason.playoffEquipe);
							win += stat.winRATE;
							win += statEq.winRATE;
							pt += stat.ptRATE;
							pt += statEq.ptRATE;
							
							if(s.playoffs.contains(pSeason)){
								if(pSeason != null){
									switch(s.playoffs.indexOf(pSeason)){
									case 0:
										gold++;
										break;
									case 1:
										silver++;
										break;
									case 2:
										bronze++;
										break;
									}
									playoff++;
								}
							}
							
							if(s.playoffsEquipe != null){
								ArrayList<Equipe> eqsPlayoff = s.getEqsPlayoff();
								Equipe eSeason = FGene.getEquipeOfPiloto(s.equipes, pSeason);
								if(eqsPlayoff.contains(eSeason)){
									switch(eqsPlayoff.indexOf(eSeason)){
									case 0:
										goldE++;
										break;
									case 1:
										silverE++;
										break;
									case 2:
										bronzeE++;
										break;
									}
									playoffE++;
								}
							}
							
						}
					}
				}
				data.add(cont*22-21,""+gold);
				data.add(cont*22-20,""+silver);
				data.add(cont*22-19,""+bronze);
				data.add(cont*22-18,""+playoff);
				data.add(cont*22-17,""+goldE);
				data.add(cont*22-16,""+silverE);
				data.add(cont*22-15,""+bronzeE);
				data.add(cont*22-14,""+playoffE);
				data.add(cont*22-13,""+(gold+silver+bronze+goldE+silverE+bronzeE));

				data.add(cont*22-5, Stats2.format.format(win/p.pPlayoffs).toString());
				data.add(cont*22-4, Stats2.format.format(pt/p.pPlayoffs).toString());
				
				data.add(cont*22-3, Stats2.format.format(new Double((p.pPlayoffs)/18.0)).toString());
				data.add(cont*22-2, Stats2.format.format(new Double((gold+goldE)/18.0)).toString());
				data.add(cont*22-1, Stats2.format.format(new Double((gold+silver+bronze+goldE+silverE+bronzeE)/new Double(p.pPlayoffs))).toString());
				
				cont++;
			}
			return data;
		}
		
	},
	//Stats Campeao e Runner-up de equipes;
	ALLEQUIPES(Equipe.class, 13, 1050, 350){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = super.genColunas();
			col.add("G-p");
			col.add("S-p");
			col.add("B-p");
			col.add("OFF-p");
			col.add("G-e");
			col.add("S-e");
			col.add("B-e");
			col.add("OFF-e");
			col.add("medals");
			col.add("OFF");
			col.add("G");
			col.add("OFF EFF");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Equipe e = (Equipe)a2;
				int gold = 0;
				int silver = 0;
				int bronze = 0;
				int playoffs = 0;
				int goldE = 0;
				int silverE = 0;
				int bronzeE = 0;
				int playoffsE = 0;
				
				for(Season s : FGene.getAllSeasons()){
					if(s.isEnded){
						Equipe eSeason = FGene.getEquipe(s.equipes, e.name);
						Piloto pSeason = null;
						if(s.playoffs.contains(eSeason.piloto1)){
							pSeason = eSeason.piloto1;
						}
						if(s.playoffs.contains(eSeason.piloto2)){
							pSeason = eSeason.piloto2;
						}
						if(pSeason != null){
							playoffs++;
							switch(s.playoffs.indexOf(pSeason)){
							case 0:
								gold++;
								break;
							case 1:
								silver++;
								break;
							case 2:
								bronze++;
								break;
							}
						}
						
						if(s.playoffsEquipe != null){
							ArrayList<Equipe> eqsPlayoff = s.getEqsPlayoff();
							if(eqsPlayoff.contains(eSeason)){
								playoffsE++;
								switch(eqsPlayoff.indexOf(eSeason)){
								case 0:
									goldE++;
									break;
								case 1:
									silverE++;
									break;
								case 2:
									bronzeE++;
									break;
								}
							}
						}
					}
				}
				
				data.add(e.name);
				data.add(""+gold);
				data.add(""+silver);
				data.add(""+bronze);
				data.add(""+playoffs);
				data.add(""+goldE);
				data.add(""+silverE);
				data.add(""+bronzeE);
				data.add(""+playoffsE);
				data.add(""+(gold+silver+bronze+goldE+silverE+bronzeE));
				data.add(Stats2.format.format(new Double((playoffs+playoffsE)/new Double(((FGene.getAllSeasons().size()-1)*2)-1))).toString());
				data.add(Stats2.format.format(new Double((gold+goldE)/new Double(((FGene.getAllSeasons().size()-1)*2)-1))).toString());
				data.add(Stats2.format.format(new Double((gold+silver+bronze+goldE+silverE+bronzeE)/new Double(playoffs+playoffsE))).toString());
			}
			return data;
		}
	},
	//Stats absolutos em temporada regular de equipes;
	EQSEASON(Equipe.class, 10, 1050, 300),
	ECONTRACTS(Equipe.class, 4, 300, 350){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = super.genColunas();
			col.add("career");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Equipe e = (Equipe)a2;
				data.add(e.name);
				data.add(e.piloto1.name);
				data.add(e.contract1.toString());
				data.add(e.piloto1.careerLeft.toString());
				data.add(e.name);
				data.add(e.piloto2.name);
				data.add(e.contract2.toString());
				data.add(e.piloto2.careerLeft.toString());
			}
			return data;
		}
	},
	EPOWERS(Equipe.class, 6, 675, 350){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("name");
			col.add("AIR");
			col.add("POWER");
			//col.add("SPEED");
			//col.add("SLIDING");
			col.add("SLOWDOWN");
			col.add("GRIP");
			col.add("GRADE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			DecimalFormat f = new DecimalFormat("+#;-#");
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				int grade = 0;
				Equipe e = (Equipe)a2;
				data.add(e.name);
				for(Powers p : e.powers.keySet()){
					data.add(Powers.format.format(e.powers.get(p)).toString());
					grade += Math.round((e.powers.get(p)-p.def)/p.inc);
				}
				data.add(f.format(grade));
			}
			return data;
		}
	},
	SGROUPS(Equipe.class,5, 225, 300){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("name");
			col.add("piloto");
			col.add("AI");
			col.add("PtRate");
			col.add("pts");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			List<Equipe> eqs = (List<Equipe>) a;
			ArrayList<Piloto> ps = new ArrayList<>();
			for(Equipe e : eqs){
				ps.add(e.piloto1);
				ps.add(e.piloto2);
			}
			//updated v6.7
//			ps.sort((p2, p1) -> p1.season.pts.compareTo(p2.season.pts));
			ps.sort(Comparator.comparing((Piloto p1)->p1.season.pts)
			          .thenComparing(p1->p1.season.p1st)
			          .thenComparing(p1->p1.season.p2nd)
			          .thenComparing(p1->p1.season.p3rd)
			          .thenComparing(p1->p1.season.p4th)
			          .thenComparing(p1->p1.season.p5th)
			          .thenComparingInt(p1->p1.season.p6th)
			          .reversed());
			
			
			ArrayList<String> data = new ArrayList<>();
			for(Piloto p : ps){
				data.add(FGene.getEquipeOfPiloto(eqs, p).name);
				data.add(p.name);
				data.add(p.AI.toString());
				data.add(Stats2.format.format(calculatePtRate(p)).toString());
				data.add(p.season.pts.toString());
//				data.add(e.name);
//				data.add(e.piloto2.name);
//				data.add(e.piloto2.AI.toString());
//				data.add(e.piloto2.season.pts.toString());
			}
			return data;
		}
		
		public Double calculatePtRate(Piloto p){
			Season actual = FGene.getSelectedSeason();
			ArrayList<Season> allS =FGene.getAllSeasons(); 
			Stats stat = new Stats();
			Integer i = allS.indexOf(actual)+1;
			for(;i<allS.size();i++){
				Piloto p2 = allS.get(i).getPilotByName(p.name);
				if(p2 != null){
					stat = Stats.somarStats(stat, p2.season, true);
				}else{
					break;
				}
			}
			return new Stats2(stat).ptRATE;
		}
	},
	PGROUPS(Piloto.class,11, 600, 110){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("equipe");
			col.add("piloto");
			col.add("pts");
			
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			col.add("Win RATE");
			col.add("PT RATE");
			
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Piloto p = (Piloto)a2;
				data.add(FGene.getEquipeOfPiloto(FGene.getSelectedSeason().equipes, p).name);
				data.add(p.name);
				data.add(p.playoff.pts.toString());
				
				data.add(p.playoff.p1st.toString());
				data.add(p.playoff.p2nd.toString());
				data.add(p.playoff.p3rd.toString());
				data.add(p.playoff.p4th.toString());
				data.add(p.playoff.p5th.toString());
				data.add(p.playoff.p6th.toString());
				Stats2 fakeStats = new Stats2(p.playoff);
				data.add(Stats2.format.format(fakeStats.winRATE).toString());
				data.add(Stats2.format.format(fakeStats.ptRATE).toString());
				
			}
			return data;
		}
	},
	PEQGROUPS(Piloto.class,11, 450, 110){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("equipe");
			col.add("piloto");
			col.add("pts");
			
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			col.add("Win RATE");
			col.add("PT RATE");
			
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Piloto p = (Piloto)a2;
				data.add(FGene.getEquipeOfPiloto(FGene.getSelectedSeason().equipes, p).name);
				data.add(p.name);
				data.add(p.playoffEquipe.pts.toString());
				
				data.add(p.playoffEquipe.p1st.toString());
				data.add(p.playoffEquipe.p2nd.toString());
				data.add(p.playoffEquipe.p3rd.toString());
				data.add(p.playoffEquipe.p4th.toString());
				data.add(p.playoffEquipe.p5th.toString());
				data.add(p.playoffEquipe.p6th.toString());
				Stats2 fakeStats = new Stats2(p.playoffEquipe);
				data.add(Stats2.format.format(fakeStats.winRATE).toString());
				data.add(Stats2.format.format(fakeStats.ptRATE).toString());
				
			}
			return data;
		}
	},
	EQGROUPS(Equipe.class,4, 225, 110){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("equipe");
			col.add("pts");
			
			col.add("Win RATE");
			col.add("PT RATE");
			
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Equipe e = (Equipe)a2;
				data.add(e.name);
				Stats st = Stats.somarStats(e.piloto1.playoffEquipe, e.piloto2.playoffEquipe, true);
				data.add(st.pts.toString());
				
				Stats2 fakeStats = new Stats2(st);
				data.add(Stats2.format.format(fakeStats.eqWinRATE).toString());
				data.add(Stats2.format.format(fakeStats.eqPtRATE).toString());
				
			}
			return data;
		}
	},
	CHANGES(Season.class,7, 750, 587){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("piloto");
			col.add("old AI");
			col.add("new AI");
			col.add("old equipe");
			col.add("new equipe");
			col.add("contrato");
			col.add("potential");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			Season sNew = (Season)a.get(0);
			Season sOld = (Season)a.get(1);
			for(Piloto p : sNew.pHere()){
				Piloto pOld = sOld.getPilotByName(p.name);
				String oldAI = "-";
				String oldEq = "-";
				if(pOld != null){
					oldAI = pOld.AI.toString();
					oldEq = FGene.getEquipeOfPiloto(sOld.equipes, pOld).name;
				}
				
				data.add(p.name);
				data.add(oldAI);
				data.add(p.AI.toString());
				data.add(oldEq);
				data.add(FGene.getEquipeOfPiloto(sNew.equipes, p).name);
				data.add(FGene.getEquipeOfPiloto(sNew.equipes, p).getContract(p).toString());
				data.add(p.potential.toString());
			}
			return data;
		}
	},
	//Pilotos Campeoes do playoff de equipes
	SEASONSPEQ(Season.class,7, 525, 300){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Season s = (Season)a2;
				if(s.playoffsEquipe != null){
					if(s.isEnded){
						//ArrayList<String> eqs = new ArrayList<>();
						//ArrayList<String> ps = new ArrayList<>();
						data.add(s.ano.toString());
						for(Piloto p : s.playoffsEquipe){
							data.add(p.toString());
//							data.add(FGene.getEquipeOfPiloto(s.equipes, p).toString() +" - "+ p.toString());
							//ps.add(p.toString());
						}
						//data.addAll(eqs);
						//data.add(s.ano.toString());
						//data.addAll(ps);
					}
				}
			}
			return data;
		}
	},
	//Equipes Campeas do playoff de equipes
	SEASONSEQ(Season.class,4, 300, 300){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Season s = (Season)a2;
				if(s.playoffsEquipe != null){
					if(s.isEnded){
						ArrayList<String> eqs = new ArrayList<>();
						data.add(s.ano.toString());
						for(Equipe e : s.getEqsPlayoff()){
							data.add(e.name);
						}
					}
				}
			}
			return data;
		}
	},
	//Pilotos Campeoes do playoff de pilotos
	SEASONS(Season.class,7, 525, 300){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Season s = (Season)a2;
				if(s.isEnded){
					//ArrayList<String> eqs = new ArrayList<>();
					//ArrayList<String> ps = new ArrayList<>();
					data.add(s.ano.toString());
					for(Piloto p : s.playoffs){
//						data.add(FGene.getEquipeOfPiloto(s.equipes, p).toString() +" - "+ p.toString());
						data.add(p.toString());
						//ps.add(p.toString());
					}
					//data.addAll(eqs);
					//data.add(s.ano.toString());
					//data.addAll(ps);
				}
			}
			return data;
		}
	},
	//Equipes Campeoes do playoff de pilotos
	SEASONSP(Season.class,7, 525, 300){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			for(A a2 : a){
				Season s = (Season)a2;
				if(s.isEnded){
					//ArrayList<String> eqs = new ArrayList<>();
					//ArrayList<String> ps = new ArrayList<>();
					data.add(s.ano.toString());
					for(Piloto p : s.playoffs){
						data.add(FGene.getEquipeOfPiloto(s.equipes, p).toString());
//						data.add(p.toString());
						//ps.add(p.toString());
					}
					//data.addAll(eqs);
					//data.add(s.ano.toString());
					//data.addAll(ps);
				}
			}
			return data;
		}
	},
	
	PPPERSEASON(Season.class,11, 637, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("AI");
			col.add("PTS");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(PilotoPanel.selectedPiloto != null){
				for(A a2 : a){
					Season s = (Season)a2;
					Piloto p = s.getPilotByName(PilotoPanel.selectedPiloto);
					if(s.isEnded){
						if(p != null){
							data.add(s.ano.toString());
							data.add(p.AI.toString());
							data.add(p.season.pts.toString());
							data.add(p.season.p1st.toString());
							data.add(p.season.p2nd.toString());
							data.add(p.season.p3rd.toString());
							data.add(p.season.p4th.toString());
							data.add(p.season.p5th.toString());
							data.add(p.season.p6th.toString());
							data.add(Stats2.format.format(p.pureSeason.winRATE).toString());
							data.add(Stats2.format.format(p.pureSeason.ptRATE).toString());
						}
					}
				}
			}
			return data;
		}
	},
	
	PPPERPLAYOFF(Season.class,10, 637, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("PTS");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(PilotoPanel.selectedPiloto != null){
				for(A a2 : a){
					Season s = (Season)a2;
					Piloto p = s.getPilotByName(PilotoPanel.selectedPiloto);
					if(s.isEnded){
						if(p != null && s.playoffs.contains(p)){
							data.add(s.ano.toString());
							data.add(p.playoff.pts.toString());
							data.add(p.playoff.p1st.toString());
							data.add(p.playoff.p2nd.toString());
							data.add(p.playoff.p3rd.toString());
							data.add(p.playoff.p4th.toString());
							data.add(p.playoff.p5th.toString());
							data.add(p.playoff.p6th.toString());
							Stats2 stats = new Stats2(p.playoff);
							data.add(Stats2.format.format(stats.winRATE).toString());
							data.add(Stats2.format.format(stats.ptRATE).toString());
						}
					}
				}
			}
			return data;
		}
	},
	
	PPPERPLAYOFFEQUIPE(Season.class,6, 375, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("Equipe");
			col.add("PTS");
			col.add("p1st");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(PilotoPanel.selectedPiloto != null){
				for(A a2 : a){
					Season s = (Season)a2;
					Piloto p = s.getPilotByName(PilotoPanel.selectedPiloto);
					if(s.isEnded){
						if(s.playoffsEquipe != null){
							if(p != null && s.playoffsEquipe.contains(p)){
								Equipe e = FGene.getEquipeOfPiloto(s.equipes, p);
								Stats st = Stats.somarStats(e.piloto1.playoffEquipe, e.piloto2.playoffEquipe, true);
								data.add(s.ano.toString());
								data.add(e.name);
								data.add(st.pts.toString());
								data.add(st.p1st.toString());
								Stats2 stats = new Stats2(st);
								data.add(Stats2.format.format(stats.eqWinRATE).toString());
								data.add(Stats2.format.format(stats.eqPtRATE).toString());
							}
						}
					}
				}
			}
			return data;
		}
	},
	
	PPPEREQUIPE(Season.class,6, 375, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("Equipe");
			col.add("Seasons");
			col.add("p1st");
			col.add("PTS");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(PilotoPanel.selectedPiloto != null){
				String equipe = "";
				Integer seasons = 0;
				Integer p1st = 0;
				Integer pts = 0;
				Double ptrate = 0.0;
				Double winrate = 0.0;
//				for(A a2 : a){
				for(int k = 0;k<a.size();k++){
					Season s = (Season)a.get(k);
					if(s.isEnded){
						Piloto p = s.getPilotByName(PilotoPanel.selectedPiloto);
						if(p != null){
							String str = FGene.getEquipeOfPiloto(s.equipes, p).name;
							if(str.equals(equipe) || equipe.equals("")){
								equipe = str;
								seasons++;
								p1st += p.season.p1st;
								pts += p.season.pts;
								winrate += p.pureSeason.winRATE;
								ptrate += p.pureSeason.ptRATE;
							}else{
								data.add(equipe);
								data.add(seasons.toString());
								data.add(p1st.toString());
								data.add(pts.toString());
								data.add(Stats2.format.format(new Double(winrate/seasons)).toString());
								data.add(Stats2.format.format(new Double(ptrate/seasons)).toString());
								
								equipe = str;
								p1st = p.season.p1st;
								pts = p.season.pts;
								winrate = p.pureSeason.winRATE;
								ptrate = p.pureSeason.ptRATE;
								seasons = 1;
							}
						}
					}
				}
				data.add(equipe);
				data.add(seasons.toString());
				data.add(p1st.toString());
				data.add(pts.toString());
				data.add(Stats2.format.format(new Double(winrate/seasons)).toString());
				data.add(Stats2.format.format(new Double(ptrate/seasons)).toString());
			}
			return data;
		}
	},
	EPPERSEASON(Season.class,12, 637, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("Piloto 1");
			col.add("Piloto 2");
			col.add("PTS");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(EquipePanel.selectedEquipe != null){
				for(A a2 : a){
					Season s = (Season)a2;
					Equipe e = s.getEquipeByName(EquipePanel.selectedEquipe);
					if(s.isEnded){
						if(e != null){
							data.add(s.ano.toString());
							data.add(e.piloto1.toString());
							data.add(e.piloto2.toString());
							data.add(e.stats.pts.toString());
							data.add(e.stats.p1st.toString());
							data.add(e.stats.p2nd.toString());
							data.add(e.stats.p3rd.toString());
							data.add(e.stats.p4th.toString());
							data.add(e.stats.p5th.toString());
							data.add(e.stats.p6th.toString());
							data.add(Stats2.format.format(e.pureStats.eqWinRATE).toString());
							data.add(Stats2.format.format(e.pureStats.eqPtRATE).toString());
						}
					}
				}
			}
			return data;
		}
	},
	
	EPPERPLAYOFF(Season.class,10, 637, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("PTS");
			col.add("p1st");
			col.add("p2nd");
			col.add("p3rd");
			col.add("p4th");
			col.add("p5th");
			col.add("p6th");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(EquipePanel.selectedEquipe != null){
				for(A a2 : a){
					Season s = (Season)a2;
					Equipe e = s.getEquipeByName(EquipePanel.selectedEquipe);
					if(s.isEnded){
						if(s.playoffsEquipe != null){
							if(e != null && s.playoffsEquipe.contains(e.piloto1)){
								Stats st = Stats.somarStats(e.piloto1.playoffEquipe, e.piloto2.playoffEquipe, true);
								data.add(s.ano.toString());
								data.add(st.pts.toString());
								data.add(st.p1st.toString());
								data.add(st.p2nd.toString());
								data.add(st.p3rd.toString());
								data.add(st.p4th.toString());
								data.add(st.p5th.toString());
								data.add(st.p6th.toString());
								Stats2 stats = new Stats2(st);
								data.add(Stats2.format.format(stats.eqWinRATE).toString());
								data.add(Stats2.format.format(stats.eqPtRATE).toString());
							}
						}
					}
				}
			}
			return data;
		}
	},
	EPPERPLAYOFFPILOTO(Season.class,6, 375, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("ano");
			col.add("Piloto");
			col.add("PTS");
			col.add("p1st");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(EquipePanel.selectedEquipe != null){
				for(A a2 : a){
					Season s = (Season)a2;
					Equipe e = s.getEquipeByName(EquipePanel.selectedEquipe);
					if(s.isEnded){
						if(e != null && s.playoffs.contains(e.piloto1) || s.playoffs.contains(e.piloto2)){
							data.add(s.ano.toString());
							Stats st = null;
							if(s.playoffs.contains(e.piloto1)){
								st = e.piloto1.playoff;
								data.add(e.piloto1.name);
							}
							if(s.playoffs.contains(e.piloto2)){
								st = e.piloto2.playoff;
								data.add(e.piloto2.name);
							}
							data.add(st.pts.toString());
							data.add(st.p1st.toString());
							Stats2 stats = new Stats2(st);
							data.add(Stats2.format.format(stats.winRATE).toString());
							data.add(Stats2.format.format(stats.ptRATE).toString());
						}
					}
				}
			}
			return data;
		}
	},
	
	EPPERERA(Season.class,7, 375, 190){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("Piloto 1");
			col.add("Piloto 2");
			col.add("Seasons");
			col.add("p1st");
			col.add("PTS");
			col.add("WINRATE");
			col.add("PTRATE");
			return col;
		}
		
		@Override
		public <A> ArrayList<String> genData(List<A> a) {
			ArrayList<String> data = new ArrayList<>();
			if(EquipePanel.selectedEquipe != null){
				String piloto1 = "";
				String piloto2 = "";
				Integer seasons = 0;
				Integer p1st = 0;
				Integer pts = 0;
				Double ptrate = 0.0;
				Double winrate = 0.0;
//				for(A a2 : a){
				for(int k = 0;k<a.size();k++){
					Season s = (Season)a.get(k);
					if(s.isEnded){
						Equipe e = s.getEquipeByName(EquipePanel.selectedEquipe);
						if(e != null){
							String str1 = e.piloto1.name;
							String str2 = e.piloto2.name;
							if((str1.equals(piloto1) &&  str2.equals(piloto2)) || piloto1.equals("")){
								piloto1 = str1;
								piloto2 = str2;
								seasons++;
								p1st += e.stats.p1st;
								pts += e.stats.pts;
								winrate += e.pureStats.eqWinRATE;
								ptrate += e.pureStats.eqPtRATE;
							}else{
								data.add(piloto1);
								data.add(piloto2);
								data.add(seasons.toString());
								data.add(p1st.toString());
								data.add(pts.toString());
								data.add(Stats2.format.format(new Double(winrate/seasons)).toString());
								data.add(Stats2.format.format(new Double(ptrate/seasons)).toString());
								
								piloto1 = str1;
								piloto2 = str2;
								p1st = e.stats.p1st;
								pts = e.stats.pts;
								winrate = e.pureStats.eqWinRATE;
								ptrate = e.pureStats.eqPtRATE;
								seasons = 1;
							}
						}
					}
				}
				data.add(piloto1);
				data.add(piloto2);
				data.add(seasons.toString());
				data.add(p1st.toString());
				data.add(pts.toString());
				data.add(Stats2.format.format(new Double(winrate/seasons)).toString());
				data.add(Stats2.format.format(new Double(ptrate/seasons)).toString());
			}
			return data;
		}
	};
	
	public Class cl;
	public int amount;
	public Dimension d;
	
	public ArrayList<String> genColunas(){
		ArrayList<String> cols = new ArrayList<>();
		for(Field f : cl.getDeclaredFields()){
			JTableAnno anno = f.getAnnotation(JTableAnno.class);
			if(anno != null){
				for(JTableMode mode : anno.modes()){
					if(mode == this){
						if(f.getType() == Stats.class){
							for(Field f2 : Stats.class.getDeclaredFields()){
								if(!f2.getName().equals("serialVersionUID")){
									cols.add(f2.getName());
								}
							}
						}else{
							if(f.getType() == Stats2.class){
								for(Field f2 : Stats2.class.getDeclaredFields()){
									JTableAnno anno2 = f2.getAnnotation(JTableAnno.class);
									if(anno2 != null){
										for(JTableMode mode2 : anno2.modes()){
											if(mode2 == this){
												cols.add(f2.getName());
											}
										}
									}
								}
							}else{
								cols.add(f.getName());
							}
						}
						break;
					}
				}
			}
		}
		//Ordenacao aki caso seja necessario
		return cols;
	}
	public <A> ArrayList<String> genData(List<A> a){
		ArrayList<String> rows = new ArrayList<>();
		try {
			for(A a2 : a){
				for(Field f : cl.getDeclaredFields()){
					JTableAnno anno = f.getAnnotation(JTableAnno.class);
					if(anno != null){
						for(JTableMode mode : anno.modes()){
							if(mode == this){
								if(f.getType() == Stats.class){
									for(Field f2 : Stats.class.getDeclaredFields()){
										if(!f2.getName().equals("serialVersionUID")){
											rows.add(f2.get(f.get(a2)).toString());
										}
									}
								}else{
									if(f.getType() == Stats2.class){
										for(Field f2 : Stats2.class.getDeclaredFields()){
											JTableAnno anno2 = f2.getAnnotation(JTableAnno.class);
											if(anno2 != null){
												for(JTableMode mode2 : anno2.modes()){
													if(mode2 == this){
														rows.add(Stats2.format.format(f2.get(f.get(a2))).toString());
													}
												}
											}
										}
									}else{
										rows.add(f.get(a2).toString());
									}
								}
								break;
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rows;
	}
	
	private JTableMode(Class cl, int amount, int w, int h){
		this.cl = cl;
		this.amount = amount;
		this.d = new Dimension(w, h);
	}
}
