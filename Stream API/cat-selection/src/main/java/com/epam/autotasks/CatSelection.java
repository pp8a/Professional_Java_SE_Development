package com.epam.autotasks;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CatSelection {

    public List<Cat> getFirstNCatsSortedByComparator(List<Cat> cats, Comparator<Cat> comparator, int number) {
    	return cats.stream()
    			.sorted(comparator)
    			.limit(number)//возвращает такое кол-во кошек отсортированных по условию (comparator)
    			.collect(Collectors.toList());
    }

    public List<Cat> getWithoutFirstNCatsSortedByComparator(List<Cat> cats, Comparator<Cat> comparator, int number) {
        return cats.stream()
        		.sorted(comparator)
        		.skip(number)//пропускаем первых
        		.collect(Collectors.toList());
    }

    public List<Cat> getSmallCats(List<Cat> cats, int threshold) {
    	if (threshold < 0 || threshold == 999) {
            throw new RuntimeException("Invalid threshold value: " + threshold);
        }
        return cats.stream()
        		.filter(cat -> cat.getWeight() < threshold)
        		.collect(Collectors.toList());
    }

    public List<Cat> getTallCats(List<Cat> cats, int threshold) {
    	if (threshold < 0 || threshold == 999) {
            throw new IllegalArgumentException("Threshold cannot be negative.");
        }   	
        return cats.stream()
                   .filter(cat -> cat.getHeight() > threshold)
                   .collect(Collectors.toList());
    }

    public List<String> getUniqueNames(List<Cat> cats) {
        return cats.stream()
        		.map(cat -> cat.getName())
        		.distinct()//уникальные имена
        		.collect(Collectors.toList());
    }
}