package marchmadness;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class sortRatedTeams {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(new File("C:\\Users\\Koushik Pernati\\eclipse-workspace\\MarchMadness\\src\\marchmadness\\2018rated.txt"));
		HashMap<String, Integer> codenames = new HashMap<String, Integer>();
		
		while(in.hasNextLine()) {
			String line = in.nextLine();
			String college = line.substring(0, line.indexOf(":::::::"));
			int rating = Integer.parseInt(line.substring(line.indexOf(":::::::")+8));
			codenames.put(college, rating);
		}
		
		Set<Entry<String, Integer>> entries = codenames.entrySet();
		Comparator<Entry<String, Integer>> valueComparator = new Comparator<Entry<String,Integer>>() { 
			@Override public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) { 
				Integer v1 = e1.getValue(); Integer v2 = e2.getValue(); return v1.compareTo(v2); 
				} 
			}; 
		// Sort method needs a List, so let's first convert Set to List in Java 
			List<Entry<String, Integer>> listOfEntries = new ArrayList<Entry<String, Integer>>(entries); 
			// sorting HashMap by values using comparator 
			Collections.sort(listOfEntries, valueComparator); 
			LinkedHashMap<String, Integer> sortedByValue = new LinkedHashMap<String, Integer>(listOfEntries.size()); 
			// copying entries from List to Map 
			for(Entry<String, Integer> entry : listOfEntries){ 
				sortedByValue.put(entry.getKey(), entry.getValue()); 
				} 
			System.out.println("HashMap after sorting entries by values "); 
			Set<Entry<String, Integer>> entrySetSortedByValue = sortedByValue.entrySet(); 
			for(Entry<String, Integer> mapping : entrySetSortedByValue){ 
				System.out.println(mapping.getKey() + " ==> " + mapping.getValue()); 
					}
		
		in.close();
	}

}
