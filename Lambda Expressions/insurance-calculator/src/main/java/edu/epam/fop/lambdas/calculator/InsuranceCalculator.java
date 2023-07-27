package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Subject;
import java.util.Optional;
/**  
 *  Позволяет нам создавать функции для расчета страховых коэффициентов для объектов, 
 *  которые соответствуют Subject или его подклассам.
 *  
 * @param <S> принимает параметризованный тип S, где S должен быть подклассом или реализацией интерфейса Subject 
 * (или одним из разрешенных классов Person, Car или Accommodation).
 */
@FunctionalInterface
public interface InsuranceCalculator<S extends Subject> {
	/*
	 * функциональный интерфейс, который определяет коэффициент страхования на основе переданного экземпляра S.
	 */
  Optional<InsuranceCoefficient> calculate(S entity);
  
}
