package demo;

public class MapInputs {

    private String teamName;
    private int year;
    private double winPercent;
    private long epochTime;
     String title;
     int nominations;
     int awards;
     boolean isWinner;

    public MapInputs(long epochTime,String teamName, int year, double winPercent){
        this.epochTime = epochTime;
        this.teamName = teamName;
        this.year = year;
        this.winPercent = winPercent;
    }

    public MapInputs(long epochTime2, int year2, String title, int nominations, int awards, boolean isWinner) {
        this.epochTime = epochTime2;
        this.title = title;
        this.year = year2;
        this.nominations = nominations;
        this.awards = awards;
        this.isWinner =isWinner;
    }

    public String getTeamNames(){
        return this.teamName;
    }

    public int getYear(){
        return this.year;
    }

    public double getWinPercentage(){
        return this.winPercent;
    }
    
    public long getEpochTime() {
       
        return epochTime;
    }

    public String getTitle() {
        return title;        
    }

    public int getNomination() {
        return nominations;
    }

    public int getAwards() {
        return awards;
    }

    public boolean getisWinner() {
        return isWinner;
    }

}
