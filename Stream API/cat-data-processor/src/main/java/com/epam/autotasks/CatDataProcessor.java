package com.epam.autotasks;

import com.epam.autotasks.Cat.Breed;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

public class CatDataProcessor {

    public ImmutableTable<String, Cat.Breed, Integer> createCatTable(List<Cat> cats) {    	
        return cats.stream()
        		.filter(cat -> !cat.getName().isEmpty() && cat.getBreed() != null && cat.getContestResult().getSum() != null)
        		.collect(ImmutableTable.toImmutableTable(
        				Cat::getName, 
        				Cat::getBreed, 
        				cat -> cat.getContestResult().getSum()));
    }

    public JSONArray createCatJson(List<Cat> cats) {
        return new JSONArray(cats.stream()
        		.map(cat -> {
        			JSONObject catJson = new JSONObject();
        			catJson.put("name", cat.getName());
        			catJson.put("age", cat.getAge());
        			catJson.put("breed", cat.getBreed());
        			catJson.putOpt("weight", cat.getWeight());
        			catJson.put("awards", cat.getAwards());
        			
        			ContestResult result = cat.getContestResult();        			
        			JSONObject contestResultJson = new JSONObject();
    				
    				contestResultJson.put("running", result.getRunning());
    				contestResultJson.put("jumping", result.getJumping());
    				contestResultJson.put("purring", result.getPurring());
    				contestResultJson.put("sum", result.getSum());
    				
        			catJson.put("contestResult", contestResultJson);
        			return catJson;
        		})
        		.collect(Collectors.toList())
        	);
    }
}