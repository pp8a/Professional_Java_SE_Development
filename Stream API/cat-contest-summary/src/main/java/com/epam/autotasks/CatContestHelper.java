package com.epam.autotasks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class CatContestHelper {

    public static final Integer CARRIER_THRESHOLD = 30;

    public Integer getCarrierNumber(List<Cat> cats) {    
    	//считаем суммарный вес всех котов
        BigDecimal totalWeight = cats.stream()
                .map(cat -> cat.getWeight() == null || cat.getWeight() == 0 ? BigDecimal.ONE : BigDecimal.valueOf(cat.getWeight()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //количество перевозчиков по 30 кг
        BigDecimal carriersNeeded = totalWeight.divide(BigDecimal.valueOf(CARRIER_THRESHOLD), 0, RoundingMode.CEILING);

        return carriersNeeded.intValue();
    }
    
    public String getCarrierId(List<Cat> cats) {
    	StringBuilder carrierIdBuilder = new StringBuilder("CF");
    	
    	cats.stream()
    		.filter(cat -> cat.getName() != null && cat.getBreed() != null)
    		.forEach(cat ->{
    			carrierIdBuilder.append(cat.getName().substring(0, Math.min(cat.getName().length(), 3)));
    			carrierIdBuilder.append(cat.getBreed().toString().substring(0, 3));
    		});
    	
		return carrierIdBuilder.toString().toUpperCase();
//        return "CF" + cats.stream()
//        		.filter(cat -> cat.getName() != null && cat.getBreed() != null)
//    			.map(cat -> cat.getName().substring(0, 3).toUpperCase() 
//    					+ cat.getBreed().toString().substring(0, 3).toUpperCase())
//    			.collect(Collectors.joining());
    }

    public Integer countTeamAwards(List<Cat> cats) {    	
        return cats.stream()
        		.mapToInt(cat -> cat.getAwards())
        		.sum();
    }
}