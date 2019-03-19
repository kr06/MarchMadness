package marchmadness;

import java.util.*;
/*
 * This class is used to represent a team
 */
public class Team {
	
	//Instance variables
	private String name;
	private int number;
	private int rating;
	public ArrayList<Integer> scores = new ArrayList<Integer>();
	
	/*
	 * Constructor for Team class
	 * @param name - The name of the team
	 * @param number - The number of the team
	 */
	public Team(String name, int number){
		this.name = name;
		this.number = number;
		this.rating = 1200;
	}
	
	/*
	 * Gets the name of the team
	 * @return name of the team
	 */
	public String getName(){
		return this.name;
	}
	
	/*
	 * Gets the number of the team
	 * @return the number of the team
	 */
	public int getNumber(){
		return this.number;
	}
	
	/*
	 * Gets the rating of the team
	 * @return the rating of the team
	 */
	public int getRating(){
		return this.rating;
	}
	
	/*
	 * Sets the rating of the team
	 * @param rating - rating of the team to be set
	 */
	public void setRating(int rating){
		this.rating = rating;
	}
        @Override 
        public String toString(){
            return("[ "+"Name:"+name+" Number:"+number+" ELOrating:"
                    +rating+" ]");
        }

}