package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Car;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

public final class CarInsurancePolicies {

  private CarInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /** 
   * Все расчеты должны быть выполнены относительно этой даты. Точность вычислений - 1 день.
   * @param baseDate дата, которая используется как опорная точка для расчетов. 
   * @return возвращает коэффициент страхования в соответствии с возрастом автомобиля, 
   * относительно его даты производства.
   */
  public static InsuranceCalculator<Car> ageDependInsurance(LocalDate baseDate) {
    return car -> {
    	if(car == null) return Optional.empty();
    	
    	LocalDate manufactureDate = car.manufactureDate();//дата производства
    	
    	if(manufactureDate == null) return Optional.empty(); //Если дата производства автомобиля неизвестна
    	
    	long yearsDifference = ChronoUnit.YEARS.between(manufactureDate, baseDate);       	
    	
    	if(yearsDifference <= 1) {//Если дата производства автомобиля меньше 1 года относительно baseDate
    		return Optional.of(InsuranceCoefficient.MAX);
    	} else if(yearsDifference <= 5) {
    		return Optional.of(InsuranceCoefficient.of(70));
    	} else if(yearsDifference <= 10) {
    		return Optional.of(InsuranceCoefficient.of(30));
    	} else { //Если автомобиль старше 10 лет
    		return Optional.of(InsuranceCoefficient.MIN);
    	}
    };
  }

  /**
   * 
   * @param baseDate -  дата, используемая в качестве опорной точки для расчетов.
   * @param priceThreshold - пороговое значение цены, которое должно быть достигнуто или превышено ценой автомобиля.
   * @param owningThreshold - минимальный срок владения автомобилем (период между датой покупки и baseDate), 
   * который должен быть удовлетворен.
   * @return возвращает коэффициент страхования для свежих автомобилей (которые не были проданы)
   *  в соответствии с их ценой и заданным пороговым значением цены.
   */
  public static InsuranceCalculator<Car> priceAndOwningOfFreshCarInsurance(LocalDate baseDate,
      BigInteger priceThreshold, Period owningThreshold) {
    return car -> {
    	//Если автомобиль равен null или был продан (присутствует дата продажи soldDate)
    	if(car == null || car.soldDate().isPresent()) return Optional.empty();
    	
    	LocalDate purchaseDate = car.purchaseDate();//дата покупки
    	if(purchaseDate == null || purchaseDate.plus(owningThreshold).isBefore(baseDate)) {
    		//purchaseDate + owningThreshold must be >= baseDate.
    		return Optional.empty(); //  так как не соблюдается условие, что период владения достаточно долгий.
    	}
    	
    	BigInteger carPrice = car.price();
    	if(carPrice == null || carPrice.compareTo(priceThreshold) < 0) {//Если цена автомобиля меньше priceThreshold,
    		return Optional.empty(); // Check if car price is >= priceThreshold  не соблюдается условие, что цена должна быть больше или равна пороговому значению.
    	} else if(carPrice.compareTo(priceThreshold.multiply(BigInteger.valueOf(3))) >= 0) {
    		return Optional.of(InsuranceCoefficient.MAX); // Check if price >= 3 * priceThreshold
    	} else if(carPrice.compareTo(priceThreshold.multiply(BigInteger.valueOf(2))) >= 0) {
    		return Optional.of(InsuranceCoefficient.of(50)); // Check if 3 * priceThreshold > price >= 2 * priceThreshold
    	} else {
    		return Optional.of(InsuranceCoefficient.MIN);
    	}
    };
  }
}
