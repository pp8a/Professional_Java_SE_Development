package com.epam.autotasks;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class CatContestAnalyzer {

    public static final Integer DEFAULT_VALUE = -1;

    public Integer getMaxResult(List<Cat> cats) {
    	
        return cats.stream()
        		.filter(cat -> cat.getContestResult() != null && cat.getContestResult().getSum() != null)
                .map(cat -> cat.getContestResult().getSum())
                .filter(Objects::nonNull) //возвращает true, если переданный объект не равен null, и false в противном случае.
                //удаляет из потока все элементы, которые равны null
                .mapToInt(Integer::intValue) // Convert to IntStream
                //.max()//Находит максимальное значение в IntStream и возвращает его в виде OptionalInt.
                .reduce(Integer::max)
                .orElse(DEFAULT_VALUE);
        
    }

    public Integer getMinResult(List<Cat> cats) {
        return cats.stream()
        		.filter(cat -> cat.getContestResult() != null && cat.getContestResult().getSum() != null)
        		.map(cat -> cat.getContestResult().getSum())
        		.filter(sum -> sum != null && sum > 0)        		
        		.min(Integer::compare) //вернет максимальное значение в виде Optional<Integer>.
    			 //работает только для потоков, элементы которых являются сравнимыми. 
        		.orElse(DEFAULT_VALUE)
        		;
    }

    public OptionalDouble getAverageResultByBreed(List<Cat> cats, Cat.Breed breed) {
    	OptionalDouble average = cats.stream()
                .filter(cat -> cat.getBreed() == breed)
                .map(cat -> cat.getContestResult().getSum())
                .filter(Objects::nonNull) // Remove null values
                .mapToDouble(Double::valueOf) // Convert values to double using the default locale (with dot as decimal separator)
                .average();

    	if (average.isPresent()) {
    		 // Round to two decimal places using BigDecimal
            average = OptionalDouble.of(new BigDecimal(average.getAsDouble())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue()); 
        }
    	
        return average;
    }

    public Optional<Cat> getWinner(List<Cat> cats) {
        return cats.stream()
        		.filter(cat -> cat.getContestResult() != null && cat.getContestResult().getSum() != null)
        		.max(Comparator.comparing(cat -> cat.getContestResult().getSum()))
        		;
    }
       
    public List<Cat> getThreeLeaders(List<Cat> cats) {
        return cats.stream()                
                .sorted(Comparator.comparing(cat -> cat.getContestResult().getSum(), Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());
    }


    public boolean validateResultSumNotNull(List<Cat> cats) {
        return cats.stream()
        		.allMatch(cat -> cat.getContestResult().getSum() != null 
        			&& cat.getContestResult().getSum() > 0);
    }

    public boolean validateAllResultsSet(List<Cat> cats) {
        return cats.stream()
        		.noneMatch(cat -> cat.getContestResult().getSum() == 0);
    }

    public Optional<Cat> findAnyWithAboveAverageResultByBreed(List<Cat> cats, Cat.Breed breed) {
    	
    	OptionalDouble average = cats.stream()
    		.filter(cat -> cat.getBreed() == breed)
    		.map(cat -> cat.getContestResult().getSum())
    		.mapToDouble(Double::valueOf)
    		.average();
    	
    	Optional<Cat> result = cats.stream()
    		.filter(cat -> cat.getBreed() == breed && cat.getContestResult().getSum() != null
    				&& cat.getContestResult().getSum() > average.getAsDouble())
    		.findFirst();
    	
    	
    	return result.isPresent() ? result : Optional.empty();
//    	OptionalDouble average = getAverageResultByBreed(cats, breed);
//        return average.isPresent() ?
//                cats.stream()
//                        .filter(cat -> cat.getBreed() == breed && cat.getContestResult().getSum() != null
//                                && cat.getContestResult().getSum() > average.getAsDouble())
//                        .findAny()
//                : Optional.empty();
    }
}