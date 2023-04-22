package com.epam.rd.autocode.floodfill;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.google.common.primitives.Chars;

public interface FloodFill {	
    void flood(final String map, final FloodLogger logger);

    static FloodFill getInstance() {
		return new FloodFill() {
			
			@Override
			public void flood(String map, FloodLogger logger) {
				logger.log(map);
				
				if(map.indexOf(LAND) < 0) {
					return;
				}
				
				char[] chars = map.toCharArray();
				
				StringTokenizer tokenizer = new StringTokenizer(map, "\n");
		    	int number = tokenizer.countTokens();
		    	String[] tokensArray = new String[number];
		    	int n = 0;
		    	while(tokenizer.hasMoreTokens()) {
		    		String str =tokenizer.nextToken();
		    		tokensArray[n++] = str;
		    	}
		    	
		    	for (int j = 0; j < tokensArray.length; j++) {	
		    		String str = tokensArray[j];
		    		int length = str.length();
		    		int line = length+1;//с учетом перехода на новую строку \n
					for (int i = 0; i < length; i++) {						
						if(str.charAt(i)==WATER) {
							int ch = (length*j)+i+j;//текущая позиция
							int left = ch-1;
							int right = ch+1;																									
							int top = ch-line; 
							int down = ch+line;					
							
							if((left - j*line) >= 0) {			
								chars[left] = WATER;
							}
							if((right - j*line) < line-1) {	
								chars[right] = WATER;
							}								
							if(top >= 0) {	
								chars[top] = WATER;
							}								
							if(down < map.length()) {
								chars[down] = WATER;
							}					
						}						
					}	
				
		    	}	           
	            flood(String.valueOf(chars), logger);  
			}
		};
        
    }

    char LAND = '█';
    char WATER = '░';
}
