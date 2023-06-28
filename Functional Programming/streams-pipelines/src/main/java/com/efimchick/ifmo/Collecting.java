package com.efimchick.ifmo;


import com.efimchick.ifmo.util.CourseResult;
import com.efimchick.ifmo.util.Person;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Collecting {
	private static final Map<Double, String> gradeMap = new LinkedHashMap<>();

    static {
        gradeMap.put(90.0, "A");
        gradeMap.put(83.0, "B");
        gradeMap.put(75.0, "C");
        gradeMap.put(68.0, "D");
        gradeMap.put(60.0, "E");
    }

    public int sum(IntStream intStream) {
    	return intStream.reduce(Integer::sum).getAsInt();
    }

    public int production(IntStream intStream) {
    	return intStream.reduce((acc, x) -> acc * x).getAsInt();
    }

    public int oddSum(IntStream intStream) {
    	return intStream.filter(value -> value % 2 != 0)
    			.reduce(0, Integer::sum);
    }

    public Map<Integer, Integer> sumByRemainder(int divider, IntStream intStream) {
    	return intStream.boxed()
    			.collect(Collectors.groupingBy(t -> t % divider, 
    					Collectors.summingInt(Integer::intValue)));
    }
        
    public Map<Person, Double> totalScores(Stream<CourseResult> results) {
    	List<CourseResult> list = results.collect(Collectors.toList());        
        return list.stream().collect(Collectors.groupingBy(
        		CourseResult::getPerson,
        		Collectors.averagingDouble(value -> value.getTaskResults()
        				.entrySet()
        				.stream()
        				.map(t -> t.getValue())
        				.mapToDouble(Integer::doubleValue).sum()/getCountTask(list))        				
        		));
    }

    private long getCountTask(List<CourseResult> list) {
        return list.stream()
            //.flatMap(result -> result.getTaskResults().keySet().stream())
        	.map(CourseResult::getTaskResults)
        	.flatMap(t -> t.keySet().stream())
            .distinct().count();
    }

    public double averageTotalScore(Stream<CourseResult> results) {
    	List<CourseResult> list = results.collect(Collectors.toList());
    	return list.stream().collect(Collectors.summingDouble(value -> value.getTaskResults()
    			.values().stream()
    			.mapToDouble(Integer::intValue).sum()/(getCountPerson(list)*getCountTask(list))
    			)); 
    	
//    	return  totalScores(list.stream()).values().stream().reduce(0.0, Double::sum)/getCountPerson(list);

    }
    
    private long getCountPerson(List<CourseResult> list) {
        return list.stream().map(CourseResult::getPerson).distinct().count();
    }

    public Map<String, Double> averageScoresPerTask(Stream<CourseResult> results) {	
    	List<CourseResult> list = results.collect(Collectors.toList());
		return list.stream()
				.flatMap(task -> task.getTaskResults().entrySet().stream())
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,						
						Collectors.summingDouble(value -> value.getValue()/(double)getCountPerson(list))
						));

    }

    public Map<Person, String> defineMarks(Stream<CourseResult> results) {
    	List<CourseResult> list = results.collect(Collectors.toList());
    	return list.stream().collect(Collectors.toMap(
    			CourseResult::getPerson, 
    			t ->{
    				Double score = t.getTaskResults().entrySet().stream()
    						.map(value -> value.getValue())
    						.mapToDouble(Integer::doubleValue).sum()/getCountTask(list);    				
    				return calculateGrade(score);
//    				if (score > 90) {
//                        return "A";
//                    } else if (score >= 83) {
//                        return "B";
//                    } else if (score >= 75) {
//                        return "C";
//                    } else if (score >= 68) {
//                        return "D";
//                    } else if (score >= 60) {
//                        return "E";
//                    } else {
//                        return "F";
//                    }
    			}
    	));
    }
    
    public static String calculateGrade(double score) {
        Optional<String> result = gradeMap.entrySet().stream()
                .filter(entry -> score >= entry.getKey())
                .findFirst()
                .map(Map.Entry::getValue);

        return result.orElse("F");
    }


    public String easiestTask(Stream<CourseResult> results) {
    	List<CourseResult> list = results.collect(Collectors.toList());    	
		return list.stream()
				.flatMap(task -> task.getTaskResults().entrySet().stream())
				.collect(Collectors.groupingBy(
						Map.Entry::getKey,
						Collectors.summingDouble(Map.Entry::getValue)))
				.entrySet().stream()				
				.max(Comparator.comparingDouble(Map.Entry::getValue))
				.map(Map.Entry::getKey).orElse("");
    }

    public Collector<CourseResult, ?, String> printableStringCollector() {    	
		return Collectors.collectingAndThen(
				Collectors.groupingBy(result -> result.getPerson().getLastName(),
						TreeMap::new,
						Collectors.toList()						
						),
				this::formatPrintableString);

    }
    
    private String formatPrintableString(Map<String, List<CourseResult>> groupedResults) {    	
//    	int maxLength = groupedResults.entrySet().stream()
//    			.map(t -> t.getKey())
//    			.mapToInt(String::length).max().getAsInt();
//    	System.out.println(maxLength);    	
    	
    	int maxNameLength = groupedResults.values().stream()
    	        .flatMap(results -> results.stream()
    	        		.map(g -> g.getPerson())
    	                .map(t -> t.getLastName()+" "+t.getFirstName())
    	                .mapToInt(String::length)
    	                .boxed())  // Convert IntStream to Stream<Integer>
    	        .max(Integer::compareTo)
    	        .orElse(0);
    	
    	List<String> taskNames = groupedResults.values().stream()
    		.flatMap(results -> results.stream()
    				.flatMap(result -> result.getTaskResults().keySet().stream()))
    				.distinct()
    				.sorted()
    				.collect(Collectors.toList());
    	
    	List<CourseResult> sortedResults = groupedResults.values().stream()
    		.flatMap(List::stream)
    		.sorted(Comparator.comparing(result -> result.getPerson().getLastName()))
    		.collect(Collectors.toList());
    		
    	Map<String, Double> taskAverages = calculateTaskAverages(sortedResults, taskNames);
    	calculateTaskAverages(sortedResults, taskNames);    	
    	double averageTotalScore = calculateAverageTotalScore(sortedResults);
    	
    	StringBuilder sb = new StringBuilder();
    	appendHeader(sb, maxNameLength, taskNames);
    	appendResults(sb, sortedResults, maxNameLength, taskNames); 
    	appendAverageRow(sb, taskAverages, averageTotalScore, taskNames, maxNameLength);
    	  	
    	System.out.println(sb.toString());
		return sb.toString();    	
    }
    
    private double calculateAverageTotalScore(List<CourseResult> list) {
    	return  totalScores(list.stream()).values().stream().reduce(0.0, Double::sum)/getCountPerson(list);
	}

	private Map<String, Double> calculateTaskAverages(List<CourseResult> list, List<String> taskNames) {
    	return taskNames.stream()
                .collect(Collectors.toMap(
                        String::toString,//task
                        task -> list.stream()
	                        .flatMap(result -> result.getTaskResults().entrySet().stream())
	                        	.filter(entry -> entry.getKey().equals(task))
	                        	.mapToInt(Map.Entry::getValue)	                        	
	                        	.sum() / (double) getCountPerson(list)                        
                ));		
	}
    
	public void appendHeader(StringBuilder sb, int maxNameLength, List<String> taskName) {
    	sb.append(padRight("Student", maxNameLength));
    	taskName.forEach(task -> sb.append(String.format("| %s ", task)));
    	sb.append(String.format("| %s | %s |\n", "Total", "Mark"));
    }
    
    public void appendResults(StringBuilder sb, List<CourseResult> results, int maxNameLength, List<String> taskNames) {
    	DecimalFormat decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
    	
        results.forEach(result -> {
            Person person = result.getPerson();
            sb.append(padRight(person.getLastName() + " " + person.getFirstName(), maxNameLength));

            taskNames.stream().forEach(task -> {
                int score = result.getTaskResults().getOrDefault(task, 0);
                int length = task.length();
                sb.append(String.format("| %"+length+"d ", score));
            });
            
            double totalScore = result.getTaskResults().values().stream()
            		.mapToInt(Integer::intValue)
            		.sum();
            sb.append(String.format("| %s ", decimalFormat.format(totalScore/getCountTask(results))));
            sb.append(String.format("| %"+"Mark".length()+"s |", calculateGrade(totalScore/getCountTask(results))));

            sb.append("\n");
        });
    }    
    
    public void appendAverageRow(StringBuilder sb, Map<String, Double> taskAverages, double averageTotalScore, 
    		List<String> taskNames, int maxNameLength) {
    	DecimalFormat decimalFormat = new DecimalFormat("#0.00", new DecimalFormatSymbols(Locale.US));
    	
    	sb.append(padRight("Average", maxNameLength));
    	
    	taskNames.stream().forEach(task ->{
    		double taskAverage = taskAverages.get(task);
    		int length = task.length();
    		sb.append(String.format("| %"+length+"s ", decimalFormat.format(taskAverage)));
    	});
    	
    	
    	sb.append(String.format("| %s ", decimalFormat.format(averageTotalScore)));
    	sb.append(String.format("| %"+"Mark".length()+"s |", calculateGrade(averageTotalScore)));
    	
    }
    
    public String padRight(String s, int length) {
    	return String.format("%-"+length+"s ", s);
    }
}