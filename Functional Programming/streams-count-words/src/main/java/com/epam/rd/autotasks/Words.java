package com.epam.rd.autotasks;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Words {

    public String countWords(List<String> lines) {    	   	
 		return Collections.list(new StringTokenizer(lines.toString(), "[] .,‘’(“—/:?!”;*)'\\\"-")).stream()
 	    		.map(t -> (String) t)
 	    		.map(t -> t.toLowerCase())
 	    		.filter(t -> t.length() > 3)    		
 	    		.collect(Collectors.groupingBy(Function.identity(), HashMap::new, Collectors.counting()))
 	    		.entrySet().stream()
 	    		.sorted(Entry.<String, Long>comparingByValue().reversed()
 	                    .thenComparing(Entry.comparingByKey()))
 	    		.filter(t -> t.getValue() > 9)
 		    	.map(e -> e.getKey()+" - "+e.getValue())
 		    	.collect(Collectors.joining("\n")).toString(); 	    		 
    }
    
    //Second solved
    public String countWords2(List<String> lines) {
    	Map<String, Integer> map = new HashMap<>();    	
    	Collections.list(new StringTokenizer(lines.toString(), "[] .,‘’(“—/:?!”;*)'\\\"-")).stream()
    		.map(t -> (String) t)
    		.map(t -> t.toLowerCase())
    		.filter(t -> t.length() > 3)    		
    		.forEach(str -> {
    			map.put(str, map.getOrDefault(str, 0) + 1);
    		});
    	
    	List<Entry<String, Integer>> list = new ArrayList<>(map.entrySet());	
    	
    	list.sort(Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Entry.comparingByKey()));
    	    	
 		return list.stream()
 	    		.filter(t -> t.getValue() > 9)
 	    		.map(e -> e.getKey()+" - "+e.getValue())
 	    		.collect(Collectors.joining("\n")).toString(); 
    }
}
