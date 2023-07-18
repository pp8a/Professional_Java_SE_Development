package edu.epam.fop.lambdas.kamino;

import edu.epam.fop.lambdas.kamino.equipment.Equipment;
import edu.epam.fop.lambdas.kamino.equipment.EquipmentFactory;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Iterator;

public class BatchPolicies {

  public interface BatchPolicy {

    CloneTrooper[] formBatchOf(CloneTrooper base, int count);
  }

  public static BatchPolicy getCodeAwarePolicy(String codePrefix, int codeSeed) {	  
    return (base, count) -> {
    	CloneTrooper[] clones = new CloneTrooper[count];
    	
    	int seed = codeSeed;
    	DecimalFormat decimalFormat = new DecimalFormat("0000");
    	for (int i = 0; i < clones.length; i++) {
    		String code = codePrefix + "-" + decimalFormat.format(seed++);
    		
    		CloneTrooper clone = new CloneTrooper(code);
//    		clone.setNickname(base.getNickname());
//    		clone.setEquipment(base.getEquipment());
    		
    		clones[i] = clone;    		
		}
    	
    	return clones;
    };
  }

  public static BatchPolicy addNicknameAwareness(Iterator<String> nicknamesIterator, BatchPolicy policy) {    
    return (base, count) -> {    	
    	CloneTrooper[] clones = policy.formBatchOf(base, count);
    	
    	int i = 0;
    	while (nicknamesIterator.hasNext() && i < clones.length) {
			String nickname = (String) nicknamesIterator.next();
			clones[i++].setNickname(nickname);		
		}    	
    	
    	return clones;
    };
  }

  public static BatchPolicy addEquipmentOrdering(Equipment equipmentExample, BatchPolicy policy) {    
    return (base, count) -> {
    	CloneTrooper[] clones = policy.formBatchOf(base, count);
    	for (CloneTrooper clone : clones) {
			Equipment copyEquipment = EquipmentFactory.orderTheSame(equipmentExample);			
			clone.setEquipment(copyEquipment);
		}
    	Arrays.stream(clones).forEach(System.out::println);
    	return clones;
    };
  }
}
