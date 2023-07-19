package edu.epam.fop.lambdas;

import java.util.Comparator;
import java.util.Objects;

public interface Comparators {
	
  /*
   * Совместное использование Comparator.nullsFirst() и Comparator.comparing() с Comparator.reverseOrder() 
   * позволяет создать компаратор, который сначала помещает объекты с null значением zipCode в начало сортировки, 
   * а затем сортирует оставшиеся объекты в обратном порядке по значению zipCode.
   */
  static Comparator<Address> ZIP_CODE_COMPARATOR = Comparator.nullsFirst(
          Comparator.comparing(
        		  Address::zipCode,
        		  Comparator.reverseOrder())
  );

  static Comparator<Address> STREET_COMPARATOR = Comparator.nullsFirst(
		  Comparator.<Address, String> comparing(Address::street, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Address::building, Comparator.nullsLast(Integer::compare))
		  .thenComparing(Address::apartment, Comparator.nullsLast(Comparator.naturalOrder()))
		  );  


  static Comparator<Address> REGION_COMPARATOR = Comparator.nullsFirst(
		  Comparator.<Address, String>comparing(Address::country, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Address::city, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Address::zipCode, Comparator.reverseOrder())
		  );

  static Comparator<Address> ADDRESS_COMPARATOR = Comparator.nullsLast(
		  Comparator.<Address, String> comparing(Address::country, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Address::city, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Address::zipCode, Comparator.reverseOrder())
		  .thenComparing(Address::street, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Address::building, Comparator.nullsLast(Integer::compare))
		  .thenComparing(Address::apartment, Comparator.nullsLast(Comparator.naturalOrder()))
		  );

  static Comparator<Person> FULL_NAME_COMPARATOR = Comparator.nullsFirst(
		  Comparator.<Person, String> comparing (Person::name, Comparator.naturalOrder())
		  .thenComparing(Person::surname, Comparator.naturalOrder())
		  );
  
//  static Comparator<Person> FULL_NAME_COMPARATOR = Comparator.nullsFirst(
//          Comparator.<Person, String>comparing(
//                  p -> Objects.requireNonNull(p.name(), "name must not be null"),
//                  Comparator.naturalOrder()
//          ).thenComparing(
//                  p -> Objects.requireNonNull(p.surname(), "surname must not be null"),
//                  Comparator.naturalOrder()
//          )
//  );
  
  static Comparator<Person> BIRTHDATE_COMPARATOR = Comparator.nullsFirst(
		  Comparator.comparing(Person::birthdate, Comparator.nullsLast(Comparator.reverseOrder()))
		  );

  static Comparator<Person> PERSON_COMPARATOR = Comparator.nullsLast(
		  Comparator.<Person, String> comparing(Person::name, Comparator.naturalOrder())
		  .thenComparing(Person::surname, Comparator.naturalOrder())
		  .thenComparing(Person::birthdate, Comparator.nullsLast(Comparator.reverseOrder()))
		  .thenComparing(Person::address, ADDRESS_COMPARATOR)
		  );

  static Comparator<Company> REGISTRATION_ID_COMPARATOR = Comparator.nullsLast(
		  Comparator.comparing(Company::registrationUuid, Comparator.naturalOrder())
		  );

  static Comparator<Company> COMPANY_COMPARATOR = Comparator.nullsLast(
		  Comparator.<Company, String> comparing(Company::name, Comparator.nullsLast(Comparator.naturalOrder()))
		  .thenComparing(Company::registrationUuid, Comparator.naturalOrder())		  
		  .thenComparing(Company::director, PERSON_COMPARATOR)
		  .thenComparing(Company::officeAddress, ADDRESS_COMPARATOR)
		  );
}

