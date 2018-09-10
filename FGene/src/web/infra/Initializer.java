package web.infra;

import java.util.ArrayList;

import core.Equipe;
import core.FGene;
import core.Piloto;
import core.Season;
import core.Stats;

public class Initializer {

	public static void updatePilots() {
		for(Piloto p : FGene.getAllPilots()){
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
						
						p.season = Stats.somarStats(p.season, pSeason.season, true);
						
						if(s.playoffs.contains(pSeason)){
							if(pSeason != null){
								p.playoff = Stats.somarStats(p.playoff, pSeason.playoff, true);
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
								p.playoffEquipe = Stats.somarStats(p.playoffEquipe, pSeason.playoffEquipe, true);
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
			p.pGold = gold;
			p.pSilver = silver;
			p.pBronze = bronze;
			p.pPlayoffs = playoff;
			
			p.eGold = goldE;
			p.eSilver = silverE;
			p.eBronze = bronzeE;
			p.ePlayoffs = playoffE;
		}
	}

}
