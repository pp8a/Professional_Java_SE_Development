package edu.epam.fop.lambdas.calculator;

import java.math.BigInteger;

/**
 * представляет собой неизменяемую (immutable) запись (record) с единственным полем coefficient, 
 * которое представляет страховой коэффициент.
 * Он имеет несколько статических полей MAX, MED и MIN, представляющих максимальный, средний и минимальный коэффициенты.
 * Он имеет неявный конструктор, который проверяет, что значение коэффициента находится в диапазоне [0; 100] 
 * и выбрасывает исключение, если это не так.
 * У него есть два статических метода of, которые позволяют создавать объекты InsuranceCoefficient 
 * из целочисленного значения или BigInteger, приводя его к типу int.
 */
public record InsuranceCoefficient(int coefficient) {

  public static final InsuranceCoefficient MAX = new InsuranceCoefficient(100);
  public static final InsuranceCoefficient MED = new InsuranceCoefficient(50);
  public static final InsuranceCoefficient MIN = new InsuranceCoefficient(0);

  public InsuranceCoefficient {
    if (coefficient < 0 || coefficient > 100) {
      throw new IllegalArgumentException("Coefficient must be in range [0; 100], but was " + coefficient);
    }
  }

  public static InsuranceCoefficient of(int coefficient) {
    return new InsuranceCoefficient(coefficient);
  }

  public static InsuranceCoefficient of(BigInteger coefficient) {
    return new InsuranceCoefficient(coefficient.intValueExact());
  }
}
