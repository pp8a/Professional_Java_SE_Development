package com.epam.rd.autotasks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Words {

    public String countWords(List<String> lines) { 
    	StringTokenizer tokenizer = new StringTokenizer(lines.toString(), "[] .,‘’(“—/:?!”;*)'\\\"-");    	
   	
    	Map<String, Integer> hm = new HashMap<String, Integer>();
    	
    	while(tokenizer.hasMoreTokens()) {
    		String str = tokenizer.nextToken().toLowerCase();
//    		if(hm.containsKey(str)) {
//    			Integer value = hm.get(str);
//    			hm.put(str, ++value);
//    		}else if(str.length() > 3){ //words which contain less than 4 letters    			
//    			hm.put(str, 1);
//    		}
    		
    		if(str.length() > 3) {
    			//Возвращает значение, соответствующее ключу key. Если такой ключ не существует — возвращает значение по умолчанию.
    			Integer value = hm.getOrDefault(str, 0);
    			hm.put(str, ++value);
    		}
    	}    	

    	List<Entry<String, Integer>> list = new ArrayList<>(hm.entrySet());
//    	list.sort(new Comparator<Entry<String, Integer>>() {
//
//			@Override
//			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
//				// sorted by their amount and then in alphabetical order
//				 if (o1.getValue().equals(o2.getValue())) {
//	                    return o1.getKey().compareTo(o2.getKey());
//	                }
//				return o2.getValue()-o1.getValue();
//			}
//		});
    	
    	list.sort(Map.Entry.<String, Integer>comparingByValue().reversed()
                .thenComparing(Map.Entry.comparingByKey()));
    	
    	StringJoiner joiner = new StringJoiner("\n");
    	for (Map.Entry<String, Integer> entry : list) {
    		if(entry.getValue() > 9) {
    			joiner.add(entry.getKey()+" - "+entry.getValue());
    		}			
		}
    	
		return joiner.toString();    	
    }
}
