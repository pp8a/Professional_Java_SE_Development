package com.epam.rd.autotasks;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class Words {

    public String countWords(List<String> lines) {    

    	StringTokenizer tokenizer = new StringTokenizer(lines.toString(), "[] .,‘’(“—/:?!”;*)'\\\"-");    	
   	
    	Map<String, Integer> hm = new HashMap<String, Integer>();
    	
    	while(tokenizer.hasMoreTokens()) {
    		String str = tokenizer.nextToken().toLowerCase();
    		if(hm.containsKey(str)) {
    			Integer value = hm.get(str);
    			hm.put(str, ++value);
    		}else if(str.length() > 3){ //words which contain less than 4 letters    			
    			hm.put(str, 1);
    		}
    	}
    	
    	LinkedList<Entry<String, Integer>> list = new LinkedList<>(hm.entrySet());
    	list.sort(new Comparator<Entry<String, Integer>>() {

			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				// sorted by their amount and then in alphabetical order
				 if (o1.getValue().equals(o2.getValue())) {
	                    return o1.getKey().compareTo(o2.getKey());
	                }
				return o2.getValue()-o1.getValue();
			}
		});
    	
    	StringBuilder builder = new StringBuilder();    	
    	boolean count = false;//for set "\n" if have still word corresponding to the condition
    	for (Iterator<Entry<String, Integer>> iterator = list.iterator(); iterator.hasNext();) {
			Entry<String, Integer> entry = (Entry<String, Integer>) iterator.next();
			
			if(entry.getValue() > 9 && count) {
				builder.append("\n");
			}			
			
			if(entry.getValue() > 9) {//words which appear less less than 10
				builder.append(entry.getKey())
					.append(" - ")
					.append(entry.getValue());
			}			
			count = true;			
		}
    	
		return builder.toString();    	
    }
}
