package Thread;

import Models.Bookmaker;
import Models.Market;
import Models.OddsData;
import Models.Outcome;

import java.util.Objects;

public class CountOdds{
    private OddsData odd;
    private String team;
    public static float homeTeamOdds = 0;
    private int countOdds = 0;

    public static float awayTeamOdds = 0;
    public CountOdds(OddsData odd) {
        this.odd = odd;
    }

    public void countOdds() {
        for(Bookmaker bookmaker: odd.getBookmakers()){
            for(Market market: bookmaker.getMarkets()){
                for(Outcome outcome: market.getOutcomes()){
                    if(Objects.equals(outcome.getName(), odd.getHomeTeam())){
                        homeTeamOdds += outcome.getPrice();
                    } else if (Objects.equals(outcome.getName(), odd.getAwayTeam())){
                        awayTeamOdds += outcome.getPrice();
                    }
                    countOdds++;
                }
            }
        }
        homeTeamOdds /= countOdds;
        awayTeamOdds /= countOdds;
    }

}
