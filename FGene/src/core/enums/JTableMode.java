package core.enums;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Season;
import core.Stats;
import core.Stats2;
import core.annotations.JTableAnno;

public enum JTableMode {

	//Stats absolutos em temporada regular de pilotos
	PSEASONS(Piloto.class, 16, 1400, 450),
	//Stats absolutos em uma temporada regular de pilotos; tela de season
	PSEASON(Piloto.class, 10, 700, 500),
	//Stats absolutos totais de pilotos;
	ALLPILOTS(Piloto.class, 8, 700, 350),
	//Stats absolutos de playoffs de pilotos;
	PPLAYOFF(Piloto.class, 8, 700, 350),
	//Stats Campeao e Runner-up de equipes;
	ALLEQUIPES(Equipe.class, 6, 600, 350),
	//Stats absolutos em temporada regular de equipes;
	EQSEASON(Equipe.class, 10, 1400, 300),
	ECONTRACTS(Equipe.class, 4, 400, 350){
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
	EPOWERS(Equipe.class, 6, 1000, 350){
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
	SGROUPS(Equipe.class,4, 300, 300){
		@Override
		public ArrayList<String> genColunas() {
			ArrayList<String> col = new ArrayList<>();
			col.add("name");
			col.add("piloto");
			col.add("AI");
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
			ps.sort((p2, p1) -> p1.season.pts.compareTo(p2.season.pts));
			
			ArrayList<String> data = new ArrayList<>();
			for(Piloto p : ps){
				data.add(FGene.getEquipeOfPiloto(eqs, p).name);
				data.add(p.name);
				data.add(p.AI.toString());
				data.add(p.season.pts.toString());
//				data.add(e.name);
//				data.add(e.piloto2.name);
//				data.add(e.piloto2.AI.toString());
//				data.add(e.piloto2.season.pts.toString());
			}
			return data;
		}
	},
	PGROUPS(Piloto.class,11, 800, 110){
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
	PEQGROUPS(Piloto.class,11, 600, 110){
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
	EQGROUPS(Equipe.class,4, 300, 110){
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
				data.add(Stats2.format.format(fakeStats.winRATE).toString());
				data.add(Stats2.format.format(fakeStats.ptRATE).toString());
				
			}
			return data;
		}
	},
	CHANGES(Season.class,7, 1000, 587){
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
	SEASONSPEQ(Season.class,7, 900, 300){
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
							data.add(FGene.getEquipeOfPiloto(s.equipes, p).toString() +" - "+ p.toString());
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
	//Campeoes do playoff de pilotos
	SEASONS(Season.class,7, 1400, 300){
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
						data.add(FGene.getEquipeOfPiloto(s.equipes, p).toString() +" - "+ p.toString());
						//ps.add(p.toString());
					}
					//data.addAll(eqs);
					//data.add(s.ano.toString());
					//data.addAll(ps);
				}
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
