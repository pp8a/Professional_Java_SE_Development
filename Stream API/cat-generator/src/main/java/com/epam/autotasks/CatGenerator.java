package com.epam.autotasks;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import com.epam.autotasks.Cat.Breed;

public class CatGenerator {
	private static final int MIN_FOOD_PORTION = 4; //The minimum portion of food is 4 grams.

    public static List<Cat> generateCats(int count) {
    	Random random = new Random(); 	
        return IntStream.range(0, count)
        		.mapToObj(i-> {
        			int index = random.nextInt(Breed.values().length);
        			return new Cat("Cat " + i, i + 1, Breed.values()[index]);    			
        		})
        		.collect(Collectors.toList());
    }

    public static long generateFood(int familySize, int skip) {
    	if(familySize < 0 || skip < 0) 
    		throw new IllegalArgumentException("Input arguments cannot be negative");    	
    	
    	if(skip >= familySize) 
    		return 0;
    	
        return LongStream.range(0, familySize)
        		.skip(skip)// represents a number of young cats who already got their food
        		.map(i -> {
        			long portion = (long) (MIN_FOOD_PORTION * Math.pow(2, i)); //каждая кошка ест в два раза больше предыдущей
        			if(portion > Long.MAX_VALUE - MIN_FOOD_PORTION) 
        				throw new ArithmeticException("long overflow");
        			return portion;
        		})
        		.sum();
    }
}
