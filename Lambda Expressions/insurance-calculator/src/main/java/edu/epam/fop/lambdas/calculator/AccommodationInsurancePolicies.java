package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Accommodation;
import edu.epam.fop.lambdas.insurance.Currency;
import edu.epam.fop.lambdas.insurance.RepeatablePayment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Period;
import java.util.Optional;

public final class AccommodationInsurancePolicies {

  private AccommodationInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  static InsuranceCalculator<Accommodation> rentDependentInsurance(BigInteger divider) {
	  /**
	   * Эта функция предназначена для вычисления страхового коэффициента для размещения (Accommodation), 
	   * зависящего от арендной платы (rent)
	   */
    return entity -> {
    	if(entity == null) {
    		return Optional.empty();//Проверяет, не является ли переданная сущность entity равной null.
    	}
    	Accommodation accommodation = entity;
    	//Преобразует сущность entity в тип Accommodation, чтобы получить доступ к информации об аренде размещения.
    	Optional<RepeatablePayment> rent = accommodation.rent();
    	//Если арендная плата не определена (не существует), возвращается пустое значение Optional
    	//Получает опциональное значение арендной платы rent из объекта accommodation
    	
    	if(rent.isPresent() 
    			&& rent.get().unit().equals(Period.ofMonths(1))
    			//единица арендной платы равна Period.ofMonths(1) (то есть плата взимается ежемесячно),
    			&& rent.get().currency() == Currency.USD // валюта арендной платы
    			&& rent.get().amount().compareTo(BigInteger.ZERO) > 0)// сумма арендной платы больше нуля
    	{  		//расчет страхового коэффициента:
    		
    		BigDecimal rentAmount = new BigDecimal(rent.get().amount());
    		//Создает BigDecimal значение rentAmount, представляющее сумму арендной платы из объекта rent.    		
            BigDecimal coefficientValue = rentAmount.divide(new BigDecimal(divider), 2, RoundingMode.HALF_UP)
            		.multiply(BigDecimal.valueOf(100));
            // метод setScale(2, RoundingMode.HALF_UP) для округления BigDecimal coefficientValue 
    		//до двух десятичных знаков, прежде чем умножить его на 100, чтобы перевести его в проценты %
            
            int cappedCoefficient = Math.min(coefficientValue.intValue(), InsuranceCoefficient.MAX.coefficient());
            // Проверяет, не превышает ли значение coefficientValue максимально допустимое значение для InsuranceCoefficient. 
            //Если превышает, то оставляет его равным максимальному значению.
    		return Optional.of(InsuranceCoefficient.of(cappedCoefficient));
    		//Возвращает опциональное значение страхового коэффициента, 
    		//создавая объект InsuranceCoefficient с cappedCoefficient в качестве значения коэффициента.
    	}    	
		return Optional.empty();  
		//Если условия для расчета страхового коэффициента не выполняются 
		//(например, арендная плата не определена или равна нулю, валюта аренды не USD и т.д.), возвращает пустое значение Optional.
    };
  }
  
//	BigInteger rentAmount = rent.get().amount();
//	BigInteger coefficientValue = rentAmount.divide(divider).multiply(BigInteger.valueOf(100));
//	int cappedCoefficient = coefficientValue.min(BigInteger.valueOf(InsuranceCoefficient.MAX.coefficient())).intValue();  


  static InsuranceCalculator<Accommodation> priceAndRoomsAndAreaDependentInsurance(BigInteger priceThreshold,
      int roomsThreshold, BigInteger areaThreshold) {
    return entity -> {
    	if(entity == null) return Optional.of(InsuranceCoefficient.MIN);
    	Accommodation accommodation = entity;
    	
    	Optional<BigInteger> price = Optional.ofNullable(accommodation.price());
    	Optional<Integer> rooms = Optional.ofNullable(accommodation.rooms());
    	Optional<BigInteger> area = Optional.ofNullable(accommodation.area());
    	
    	if(price.isPresent() && rooms.isPresent() && area.isPresent()
    			&& price.get().compareTo(priceThreshold) >= 0
    			&& rooms.get() >= roomsThreshold
    			&& area.get().compareTo(areaThreshold) >=0
    			) {
    		return Optional.of(InsuranceCoefficient.MAX);
    		
    	}
    	
    	return Optional.of(InsuranceCoefficient.MIN);
    };
  }
}
