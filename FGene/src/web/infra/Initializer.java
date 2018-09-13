package web.infra;

import java.util.ArrayList;

import core.Equipe;
import core.FGene;
import core.Medals;
import core.Piloto;
import core.Season;
import core.Stats;
import core.Stats2;

public class Initializer {

	public static void updatePilots() {
		prepareEquipesForInit();
		for(Piloto p : FGene.getAllPilots()){
			int gold = 0;
			int silver = 0;
			int bronze = 0;
			int playoff = 0;
			int goldE = 0;
			int silverE = 0;
			int bronzeE = 0;
			int playoffE = 0;
			
			int seasons = 0;
			
			p.season = new Stats();
			p.playoff = new Stats();
			p.playoffEquipe = new Stats();
			p.setMedalCampPiloto(new Medals());
			p.setMedalCampEquipe(new Medals());
			
			for(Season s : FGene.getAllSeasons()){
				if(s.isEnded){
					Piloto pSeason = FGene.getPiloto(s.pHere(), p.name);
					if(pSeason != null){
						Equipe eSeason = FGene.getEquipeOfPiloto(s.equipes, pSeason);
						Equipe e = FGene.getEquipe(eSeason.name);
						seasons++;
						p.season = Stats.somarStats(p.season, pSeason.season, true);
						e.statsSeason = Stats.somarStats(eSeason.statsSeason, pSeason.season, true);
						
						if(s.playoffs.contains(pSeason)){
							if(pSeason != null){
								p.playoff = Stats.somarStats(p.playoff, pSeason.playoff, true);
								switch(s.playoffs.indexOf(pSeason)){
								case 0:
									gold++;
									e.getMedalCampPiloto().gold++;
									break;
								case 1:
									silver++;
									e.getMedalCampPiloto().silver++;
									break;
								case 2:
									bronze++;
									e.getMedalCampPiloto().bronze++;
									break;
								}
								playoff++;
								e.getMedalCampPiloto().chances++;
							}
						}
						
						if(s.playoffsEquipe != null){
							ArrayList<Equipe> eqsPlayoff = s.getEqsPlayoff();
							if(eqsPlayoff.contains(eSeason)){
								p.playoffEquipe = Stats.somarStats(p.playoffEquipe, pSeason.playoffEquipe, true);
								e.statsPlayoffE = Stats.somarStats(eSeason.statsPlayoffE, pSeason.playoffEquipe, true);
								switch(eqsPlayoff.indexOf(eSeason)){
								case 0:
									goldE++;
									//this will increment twice, but we divide by 2 later
									e.getMedalCampEquipe().gold++;
									break;
								case 1:
									silverE++;
									//this will increment twice, but we divide by 2 later
									e.getMedalCampEquipe().silver++;
									break;
								case 2:
									bronzeE++;
									//this will increment twice, but we divide by 2 later
									e.getMedalCampEquipe().bronze++;
									break;
								}
								playoffE++;
								//this will increment twice, but we divide by 2 later
								e.getMedalCampEquipe().chances++;
							}
						}
						
					}
				}
			}
			p.getMedalCampPiloto().gold = gold;
			p.getMedalCampPiloto().silver = silver;
			p.getMedalCampPiloto().bronze = bronze;
			p.getMedalCampPiloto().chances = playoff;
			
			p.getMedalCampEquipe().gold = goldE;
			p.getMedalCampEquipe().silver = silverE;
			p.getMedalCampEquipe().bronze = bronzeE;
			p.getMedalCampEquipe().chances = playoffE;
			
		}
		fixEMedals();
	}

	private static void fixEMedals() {
		for(Equipe e : FGene.getAllEquipes()) {
			e.getMedalCampEquipe().gold /= 2;
			e.getMedalCampEquipe().silver/=2;
			e.getMedalCampEquipe().bronze/=2;
			e.getMedalCampEquipe().chances/=2;
			
//			e.stats2Season = new Stats2(e.statsSeason);
//			e.stats2PlayoffE = new Stats2(e.statsPlayoffE);
		}
	}

	private static void prepareEquipesForInit() {
		for(Equipe e : FGene.getAllEquipes()) {
			e.setMedalCampPiloto(new Medals());
			e.setMedalCampEquipe(new Medals());
			e.statsSeason = new Stats();
		}
	}

}
