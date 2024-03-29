package Models;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class OddsData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sport_key")
    @Expose
    private String sportKey;
    @SerializedName("sport_title")
    @Expose
    private String sportTitle;
    @SerializedName("commence_time")
    @Expose
    private String commenceTime;
    @SerializedName("home_team")
    @Expose
    private String homeTeam;
    @SerializedName("away_team")
    @Expose
    private String awayTeam;
    @SerializedName("bookmakers")
    @Expose
    private List<Bookmaker> bookmakers = null;

    private String home_odd;
    private String away_odd;
    private String db_id = "-1";

    public String getDb_id() {
        return db_id;
    }

    public void setDb_id(String db_id) {
        this.db_id = db_id;
    }

    public void setHome_odd(String home_odd) {
        this.home_odd = home_odd;
    }

    public void setAway_odd(String away_odd) {
        this.away_odd = away_odd;
    }

    public String getHome_odd() {
        return home_odd;
    }

    public String getAway_odd() {
        return away_odd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSportKey() {
        return sportKey;
    }

    public void setSportKey(String sportKey) {
        this.sportKey = sportKey;
    }

    public String getSportTitle() {
        return sportTitle;
    }

    public void setSportTitle(String sportTitle) {
        this.sportTitle = sportTitle;
    }

    public String getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(String commenceTime) {
        this.commenceTime = commenceTime;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public List<Bookmaker> getBookmakers() {
        return bookmakers;
    }

    public void setBookmakers(List<Bookmaker> bookmakers) {
        this.bookmakers = bookmakers;
    }

    @Override
    public String toString() {
        return "<html>" +
                "<h2>" + getSportTitle() + "</h2>" +
                "</br>" +
                "<p>" + getHomeTeam() + " vs " + getAwayTeam() + "</p>" +
                "</html>";
    }
}