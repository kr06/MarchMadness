package marchmadness;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author kr06pern
 */
public class MarchMadness {
    public static HashMap<Integer, String> teamIntMap = 
            initTeamMap("C:\\Users\\Koushik Pernati\\eclipse-workspace\\MarchMadness\\src\\marchmadness\\Teams2019.csv");
    public static HashMap<String, Integer> teamNameMap = 
            nameTeamMap("C:\\Users\\Koushik Pernati\\eclipse-workspace\\MarchMadness\\src\\marchmadness\\Teams2019.csv");
    public static HashMap<Integer, Team> teamMap = initTeamObjMap();
     
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        //printHashMaps();
        simulateGames("C:\\Users\\Koushik Pernati\\eclipse-workspace\\MarchMadness\\src\\marchmadness\\finalConf2019.csv");
        
        
            try (Scanner in = new Scanner(System.in)) {
                System.out.println("PLEASE ENTER THE TWO TEAMS YOU WOULD LIKE TO "
                        + "SIMULATE!");
                
                while(true){
                    String firstTeam = in.nextLine();
                    
                    String secondTeam = in.nextLine();

                    if(firstTeam.equals("NO") || secondTeam.equals("NO")){
                        break;
                    }
                    //System.out.println(firstTeam+secondTeam);
                    getProbsOfTeam(firstTeam.toLowerCase(), secondTeam.toLowerCase());
                }
            }
        
    }
    
    static HashMap<Integer, String> initTeamMap(String fileName){
        HashMap<Integer, String> hmap = new HashMap<>();
        try{
            Scanner sc = new Scanner(new File(fileName));
                sc.nextLine();
                while(sc.hasNextLine()){
                    String [] data = sc.nextLine().split(",");
                    hmap.put(Integer.parseInt(data[0]) , data[1].toLowerCase());
                }
                sc.close();
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return hmap;
    }
    
    static HashMap<String, Integer> nameTeamMap(String fileName){
        HashMap<String, Integer> hmap = new HashMap<>();
        try{
            try (Scanner sc = new Scanner(new File(fileName))) {
                sc.nextLine();
                while(sc.hasNextLine()){
                    String [] data = sc.nextLine().split(",");
                    hmap.put(data[1].toLowerCase(), Integer.parseInt(data[0]));
                }
            }
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        return hmap;
    }
    
    static HashMap<Integer, Team> initTeamObjMap(){
        HashMap<Integer, Team> teams = new HashMap<>(); 
        int start = 1101; 
        int finish = 1466;
        
        for(int i = start; i<=finish;i++){
            teams.put(i, new Team(teamIntMap.get(i),i));
        }
        return teams;
    } 
    
    static void simulateGames(String fileName){
        try {
            Scanner in = new Scanner(new File(fileName));
            String header = in.nextLine();
            System.out.println(header);
            while(in.hasNextLine()){
                String str = in.nextLine();
                String[] data = str.split(",");
                int winTeam = Integer.parseInt(data[0]);
                int winScore = Integer.parseInt(data[1]);
                int loseTeam = Integer.parseInt(data[2]);
                int loseScore = Integer.parseInt(data[3]);
                System.out.println(teamIntMap.get(winTeam) 
                        + " vs " + teamIntMap.get(loseTeam) );
                gameRatingsResults(winTeam,loseTeam,winScore,loseScore);
            }
            in.close();
                  
            for (int teamNum: teamMap.keySet()){
                Team value = teamMap.get(teamNum);
                System.out.println(value.getName() + " ::::::: " + value.getRating());  
            }
        } catch (Exception ex) {
            System.out.println("In Simulate Games and catching the error - File not found!!");
        }
    }
    
    static void gameRatingsResults(int winTeam, int loseTeam, int winScore, int loseScore){
        int winTeamElo = teamMap.get(winTeam).getRating();
        int loseTeamElo = teamMap.get(loseTeam).getRating();
        
        double winScoreTeam = Math.pow(10.0, winTeamElo/400.0);
        double loseScoreTeam = Math.pow(10.0, loseTeamElo/400.0);
        
        double winTeamExpectedScore = winScoreTeam / 
                (winScoreTeam + loseScoreTeam);
        double loseTeamExpectedScore = loseScoreTeam / 
                (winScoreTeam + loseScoreTeam);
        
        double winTeamActual = 1.0;
        double loseTeamActual = 0.0;
        
        //K is the K-factor into considering the new rating
        double K = 32.0;
        
        int winTeamRating = (int) (winTeamElo + Math.round(K * 
                (winTeamActual - winTeamExpectedScore)));
        int loseTeamRating = (int) (loseTeamElo + Math.round(K * 
                (loseTeamActual - loseTeamExpectedScore)));
        
        Team one = teamMap.get(winTeam);
        one.setRating(winTeamRating);
        one.scores.add(winScore);
        Team two = teamMap.get(loseTeam);
        two.setRating(loseTeamRating);
        two.scores.add(loseScore);
        teamMap.put(winTeam, one);
        teamMap.put(loseTeam, two);
    }
    
    static void getProbsOfTeam(String fTeam, String sTeam){
        
        int winTeam = teamNameMap.get(fTeam);
        int loseTeam = teamNameMap.get(sTeam);
        
        int winTeamElo = teamMap.get(winTeam).getRating();
        int loseTeamElo = teamMap.get(loseTeam).getRating();
        
        double winScoreTeam = Math.pow(10.0, winTeamElo/400.0);
        double loseScoreTeam = Math.pow(10.0, loseTeamElo/400.0);
        
        double winTeamExpectedScore = winScoreTeam / 
                (winScoreTeam + loseScoreTeam);
        double loseTeamExpectedScore = loseScoreTeam / 
                (winScoreTeam + loseScoreTeam);
        
        System.out.println(fTeam.toUpperCase() + " has a "+ winTeamExpectedScore 
                + " probablility of winning");
        System.out.println(sTeam.toUpperCase() + " has a "
                + loseTeamExpectedScore + " probablility of winning");
        double avgO = 0.0, avgT = 0.0;
        for(int i = 0; i < teamMap.get(winTeam).scores.size(); i++){
        	avgO += teamMap.get(winTeam).scores.get(i);
        }
        avgO = avgO/teamMap.get(winTeam).scores.size();
        for(int i = 0; i < teamMap.get(loseTeam).scores.size(); i++){
        	avgT += teamMap.get(loseTeam).scores.get(i);
        }
        avgT = avgT/teamMap.get(loseTeam).scores.size();
        System.out.println(fTeam.toUpperCase() + " has a avg points score of "+ avgO);
        System.out.println(sTeam.toUpperCase() + " has a avg points score of " + avgT);
        System.out.println(fTeam.toUpperCase() + " has a avg points score of "+ winTeamElo);
        System.out.println(sTeam.toUpperCase() + " has a avg points score of " + loseTeamElo);
    }
         
    static void printHashMaps(){
        System.out.println(teamIntMap.values().toString());
        System.out.println("************************************************");
        System.out.println(teamNameMap.values().toString());
        System.out.println("************************************************");
        System.out.println(teamMap.values().toString());
    }
}