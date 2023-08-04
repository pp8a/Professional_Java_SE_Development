package com.epam.autotasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.epam.autotasks.Cat.Staff;

public class ShelterService {

    public void assignAttendants(List<ShelterRoom> rooms) {
//    	AtomicInteger i = new AtomicInteger(0);
    	Random random = new Random();
    	rooms.stream()
    		.flatMap(shelterRoom -> shelterRoom.getCats().stream())
    		.filter(cat -> cat.getAttendant() == null)
    		.forEach(cat -> {    			
//    			int index = i.getAndIncrement() % Staff.values().length;
    			int randomIndex = random.nextInt(Staff.values().length);
    			cat.setAttendant(Staff.values()[randomIndex]);
    		});
    }
    
    public List<Cat> getCheckUpList(List<ShelterRoom> rooms, LocalDate date) {
        return rooms.stream()
        		.flatMap(room -> room.getCats().stream())
        		.filter(cat -> cat.getLastCheckUpDate() != null && cat.getLastCheckUpDate().isBefore(date))
        		.collect(Collectors.toList());
    }

    public List<Cat> getCatsByBreed(List<ShelterRoom> rooms, Cat.Breed breed) {    	
        return rooms.stream()
        		.flatMap(room -> room.getCats().stream())
        		.filter(cat -> cat.getBreed() == breed)
        		.collect(Collectors.toList());
    }
}
