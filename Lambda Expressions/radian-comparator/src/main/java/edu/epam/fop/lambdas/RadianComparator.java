package edu.epam.fop.lambdas;

import java.util.Comparator;

public final class RadianComparator {

  /*
   * Compares 2 angles (passed in radians). The angle counts from 0 up to 2π, the period must
   * be ignored, i.e. if angle is greater than 2π, then it must be converted to the range [0; 2π).
   * Precision must be 0.001 (delta), so if |a - b| < 0.001, then a == b.
   * 0 == 2π
   */
	
	private static final double PERIOD = 2 * Math.PI;
    private static final double PRECISION = 0.001;
	
  public static Comparator<Double> get() {    
    return (o1, o2) -> {
    	if(o1 == null && o2 == null) {
    		return 0; // Two null values are equal
    	} else if (o1 == null) {
    		return -1; // null is less than non-null
    	} else if (o2 == null) {
    		return 1; //// non-null is greater than null
    	}
    	
    	// Приводим углы к диапазону [0, 2π)
        double adjustedA = adjustAngle(o1);
        double adjustedB = adjustAngle(o2);
    	
    	// Сравниваем углы с заданной точностью
        if (Math.abs(adjustedA - adjustedB) < PRECISION) {
            return 0; // o1 равно o2
        } else if (adjustedA < adjustedB) {
            return -1; // o1 меньше o2
        } else {
            return 1; // o1 больше o2
        }
    };
  }
  
  private static double adjustAngle(double angle) {
	  /*
	   *  углы o1 и o2 приводятся к диапазону [0, 2π)
	   */
      double adjustedAngle = angle % PERIOD; //Оператор % используется для обертывания углов, превышающих 2π, обратно в этот диапазон.
      
      if (adjustedAngle < 0) {
          adjustedAngle += PERIOD; // Приводим отрицательные углы к диапазону [0, 2π)
      }
      return adjustedAngle;
  }
}
