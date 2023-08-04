package com.epam.autotasks;

import lombok.Builder;
import lombok.Data;

@Data //генерирует стандартные методы toString, equals, hashCode, getter и setter для всех полей класса.
@Builder //создает шаблон "Builder" для класса, который облегчает создание объектов с множеством необязательных полей.
public class Cat {

    private String name;
    private Integer age;
    private Breed breed;

    public enum Breed {

        BRITISH(0),
        MAINE_COON(1),
        MUNCHKIN(2),
        PERSIAN(3),
        SIBERIAN(4);

        final int code;

        Breed(int code) {
            this.code = code;
        }

        public static Breed getBreedByCode(int code) {
            for (Breed breed : values()) {
                if (breed.code == code) {
                    return breed;
                }
            }
            return null;
        }
    }
    
    /*
     * создание объекта Person происходит с использованием удобного синтаксиса "Builder", 
     * который позволяет устанавливать значения полей объекта последовательно.
     * Person person = Person.builder()
                     .name("John")
                     .age(30)
                     .email("john@example.com")
                     .build();
     */
}
