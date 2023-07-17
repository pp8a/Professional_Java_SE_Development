package edu.epam.fop.lambdas;

import java.util.HashSet;
import java.util.Set;

// TODO write your implementation here
public interface IntArrayReducers {

  static IntArrayReducer SUMMARIZER = array ->{
	  int sum = 0;
	  for (int i : array) {
		sum+=i;
	  }
	  return sum;
  };

  static IntArrayReducer MULTIPLIER = array ->{
	  int mult = 1;
	  for (int i : array) {
		mult*=i;
	  }
	  return mult;	  
  };

  static IntArrayReducer MIN_FINDER = array ->{
	  int min = Integer.MAX_VALUE;
	  for (int i : array) {
		if(i < min) {
			min = i;
		}
	  }
	  return min;
  };

  static IntArrayReducer MAX_FINDER = array -> {
	  int max = Integer.MIN_VALUE;
	  for (int i : array) {
		if(i > max) {
			max = i;
		}
	  }
	  return max;
  };

  static IntArrayReducer AVERAGE_CALCULATOR = array ->{
	  int avg = 0;
	  for (int i : array) {
		avg+=i;
	  }
	  return (int) Math.round((double) avg / array.length);
  };

  static IntArrayReducer UNIQUE_COUNTER = array ->{
	  Set<Integer> set = new HashSet<>();
	  
	  for (int i : array) {
		  set.add(i);		
	  }
	  
	  return set.size();	  
  };

  static IntArrayReducer SORT_DIRECTION_DEFINER = array ->{
	  boolean isAsc = false;
	  boolean isDesc = false;
	  
	  for (int i = 0; i < array.length-1; i++) {
		if(array[i] < array[i+1]) {
			isAsc = true;			
		}
		
		if(array[i] > array[i+1]) {
			isDesc = true;
		}		
	  }	  
	 
	  if(isAsc && !isDesc) {
		  return 1;
	  } else if (isDesc && !isAsc) {
		  return -1;
	  } else {
		  return 0;
	  }
  };
  

}
