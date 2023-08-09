package com.epam.autotasks;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;




public class CatLibrary {

    public static final String EMPTY_STRING = "";
    
    public Map<String, Cat> mapCatsByName(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getName() != null)
                .collect(Collectors.groupingBy(
                        Cat::getName,
                        LinkedHashMap::new, // Для сохранения порядка
                        Collectors.collectingAndThen(//чтобы обернуть внутренний коллектор Collectors.toList() 
                        		//и возвращать только первого кота из списка для уникальных имен, а для дубликатов вернуть null. 
                                Collectors.toList(),
                                catsList -> catsList.size() == 1 ? catsList.get(0) : null // Условие для фильтрации уникальных имен
                        )
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    
    //тоже решение
//    public Map<String, Cat> mapCatsByName1(List<Cat> cats) {
//        return cats.stream()
//                .filter(cat -> cat.getName() != null)
//                .collect(Collectors.groupingBy(Cat::getName))// Map<String, List<Cat>> 
//                //Ключ - это имя кошки, а значение - список всех кошек с этим именем.
//                .entrySet().stream()
//                .filter(entry -> entry.getValue().size() == 1) // Здесь мы фильтруем записи, оставляя только те, 
//                //у которых размер списка кошек с этим именем равен 1.
//                //Это означает, что у нас есть только уникальные имена кошек без дубликатов.
//                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
//    }
//
//    //тоже решение
//    public Map<String, Cat> mapCatsByName_1(List<Cat> cats) {
//        // Find duplicate names
//        Map<String, List<Cat>> groupedByName = cats.stream()
//                .filter(cat -> cat.getName() != null)
//                .collect(Collectors.groupingBy(Cat::getName));
//
//        // Print duplicate names if any
//      groupedByName.entrySet().stream()
//              .filter(entry -> entry.getValue().size() > 1)
//              .forEach(entry -> {
//                  System.out.println("Duplicate key found for: " + entry.getKey());
//                  entry.getValue().forEach(System.out::println);
//              });
//        
//        // Remove duplicates and create the map
//        Map<String, Cat> catMap = groupedByName.entrySet().stream()
//                .filter(entry -> entry.getValue().size() == 1)
//                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().get(0)));
//
//        return catMap;
//    }

      
    public Map<Cat.Breed, Set<Cat>> mapCatsByBreed(List<Cat> cats) {
        return cats.stream()
        		.filter(cat -> cat.getBreed() != null)
        		.collect(Collectors.groupingBy(
        				Cat::getBreed,
        				Collectors.mapping(
                                Function.identity(),
                                Collectors.toSet()
                        )
        				));
    }

    public Map<Cat.Breed, String> mapCatNamesByBreed(List<Cat> cats) {
        return cats.stream()
        		.filter(cat -> cat.getBreed() != null && !cat.getName().isEmpty())
        		.collect(Collectors.groupingBy(
        				Cat::getBreed,
        				Collectors.mapping(cat -> cat.getName(),
        						Collectors.joining(", ", "Cat names: ", "."))        						
        				))
//        		.entrySet().stream()
//        		.collect(Collectors.toMap(
//        				Map.Entry::getKey,
//        				entry -> "Cat names: " + entry.getValue() + "."
//        				))
        		;
    }

    public Map<Cat.Breed, Double> mapAverageResultByBreed(List<Cat> cats) {
    	return cats.stream()
        		.filter(cat -> cat.getBreed() != null && cat.getContestResult().getSum() != null)
        		.collect(Collectors.groupingBy(
        				Cat::getBreed,
        				Collectors.averagingDouble(value -> value.getContestResult().getSum())
        				))
        		.entrySet().stream()
        		.collect(Collectors.toMap(
        				Map.Entry::getKey,
        				entry -> BigDecimal.valueOf(entry.getValue())
        								.setScale(2, RoundingMode.HALF_UP).doubleValue()
        				));
    }
    

    public SortedSet<Cat> getOrderedCatsByContestResults(List<Cat> cats) {
    	
    	Comparator<Cat> catComparator = 
    			Comparator.<Cat, Integer> comparing(cat -> cat.getContestResult().getSum(), Comparator.reverseOrder())
        		.thenComparing(Cat::getName); // Compare by name if sums are equal

    	SortedSet<Cat> result = cats.stream()
                .filter(cat -> cat.getContestResult() != null && cat.getContestResult().getSum() != null)
                .sorted(catComparator)
                .collect(Collectors.toCollection(() -> new TreeSet<>(catComparator)
                ));
//                .collect(Collectors.toCollection(TreeSet::new));
    	
    	return result;

    }
    
    

}