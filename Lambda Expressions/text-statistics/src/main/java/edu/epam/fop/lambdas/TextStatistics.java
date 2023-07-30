package edu.epam.fop.lambdas;

import edu.epam.fop.lambdas.Token.Type;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public final class TextStatistics {

  private TextStatistics() {
    throw new UnsupportedOperationException();
  }
  /**
   * Карта должна содержать токены в качестве ключей и количество раз, когда они встречаются в последовательности.   * 
   * @return Считаем количество tokens в map
   */
  public static TokenStatisticsCalculator<Token, Long> countTokens() {
    return (statistics, tokens) -> {
    	
    	while(tokens.hasNext()) {
    		Token token = tokens.next();
//    		System.out.println(token);    		
    		statistics.compute(token, (key, value) -> (value == null) ? 1 : value+1);    		
    	}
    	
//    	for (Entry<Token, Long> entry : statistics.entrySet()) {
//			Token key = entry.getKey();
//			Long val = entry.getValue();
//			System.out.println("key = " + key + " value = " + val);			
//		}    	
    };
  }
  /**
   * Подсчитывает токены, уже присутствующие на карте.
   * @param maxLimit 
   * @return Если токен встречается больше раз, чем в "maxLimit", соответствующее значение на карте не должно изменяться.
   */
  public static TokenStatisticsCalculator<Token, Long> countKnownTokensWithMaxLimit(int maxLimit) {
    return (statistics, tokens) -> {
    	while (tokens.hasNext()) {
			Token token = (Token) tokens.next();			
			statistics.computeIfPresent(token, (key, value) -> value < maxLimit ? value + 1 : value);
		}    	   
    };
  }
  
  /**
   * Помечаем токены которых нет на карте и которые относятся к определенному типу
   * @param types Если токен имеет тип, указанный в аргументе types, значение должно быть установлено в true.
   * @return
   */
  public static TokenStatisticsCalculator<Token, Boolean> findUnknownTokensOfTypes(Set<Type> types) {
    return (statistics, tokens) -> {
    	while (tokens.hasNext()) {
			Token token = (Token) tokens.next();
			statistics.computeIfAbsent(token, t -> types.contains(t.type()) ? true : null);
		}
    	/*
    	 * Важно отметить, что возвращаемое значение null будет означать,
    	 *  что токен не удовлетворяет условиям и не будет добавлен в карту,
    	 */
    	
    	for (Entry<Token, Boolean> entry : statistics.entrySet()) {
			Token key = entry.getKey();
			Boolean val = entry.getValue();
			System.out.println("key = " + key + " value = " + val);			
		} 
    };
  }  
  
  public static TokenStatisticsCalculator<Token, Integer> combinedSearch(int maxLimit, Set<Type> types) {
    return (statistics, tokens) -> {
    	while (tokens.hasNext()) {
			Token token = (Token) tokens.next();
			if(types.contains(token.type())) {
				statistics.computeIfPresent(token, (key, value) -> value < maxLimit ? 0 : 1);
			} else {
				statistics.computeIfPresent(token, (key, value) -> value < maxLimit ? 2 : 3);
			}
			
			statistics.computeIfAbsent(token, key -> -1);
		}
    };
  }
  
}

/*
 Map<Token, Long> map = new HashMap<>();
List<Token> sequence = List.of(
  new Token("name"),
  new Token("name"),
  new Token("name"),
  new Token("surname"),
  new Token("surname"),
  new Token("age")
);
TextStatistics.coundTokens().calculate(map, sequence.iterator());
assert map.get("name") == 3
assert map.get("surname") == 2
assert map.get("age") == 1
 */

  /*
Данные методы принадлежат интерфейсу Map в Java и предназначены для манипуляций с элементами в коллекции типа Map.  

merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction): 
Этот метод используется для объединения значения, связанного с определенным ключом, с другим значением с помощью указанной функции.
Если ключ не существует, то заданное значение просто добавляется в Map. 
Если ключ уже существует, то применяется функция для объединения старого и нового значений, 
и результат становится новым значением для ключа.

public class MergeExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        
        scores.put("Alice", 10);
        scores.put("Bob", 20);

        scores.merge("Alice", 5, (oldValue, newValue) -> oldValue + newValue);
        // Теперь в коллекции значение для ключа "Alice" равно 15

        scores.merge("Carl", 15, (oldValue, newValue) -> oldValue + newValue);
        // Теперь в коллекции есть новая пара ключ-значение: "Carl" - 15
    }
}

compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction): 
Этот метод вычисляет новое значение для заданного ключа с помощью указанной функции. 
Если ключ не существует, то новое значение добавляется в Map. 
Если ключ уже существует, то функция применяется к существующему ключу и его значению, 
чтобы вычислить новое значение, которое затем становится новым значением для ключа.

public class ComputeExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        scores.put("Alice", 10);
        scores.put("Bob", 20);

        scores.compute("Alice", (key, value) -> value + 5);
        // Теперь в коллекции значение для ключа "Alice" равно 15

        scores.compute("Carl", (key, value) -> (value == null) ? 10 : value + 5);
        // Теперь в коллекции есть новая пара ключ-значение: "Carl" - 10
    }
}

computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction): 
Этот метод вычисляет значение для заданного ключа, только если ключ не существует в Map. 
Если ключ уже присутствует, то метод не делает ничего.

public class ComputeIfAbsentExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        
        scores.put("Alice", 10);
        scores.put("Bob", 20);

        scores.computeIfAbsent("Alice", key -> 15);
        // Значение для ключа "Alice" остается без изменений (10), так как ключ уже существует

        scores.computeIfAbsent("Carl", key -> 25);
        // Теперь в коллекции есть новая пара ключ-значение: "Carl" - 25
    }
}
computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction): 
Этот метод вычисляет новое значение для заданного ключа, только если ключ уже существует в Map.
Если ключ отсутствует, то метод не делает ничего.

public class ComputeIfPresentExample {
    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        
        scores.put("Alice", 10);
        scores.put("Bob", 20);

        scores.computeIfPresent("Alice", (key, value) -> value + 5);
        // Теперь в коллекции значение для ключа "Alice" равно 15

        scores.computeIfPresent("Carl", (key, value) -> value + 5);
        // Так как ключ "Carl" отсутствует в коллекции, метод ничего не делает
    }
}

 */


