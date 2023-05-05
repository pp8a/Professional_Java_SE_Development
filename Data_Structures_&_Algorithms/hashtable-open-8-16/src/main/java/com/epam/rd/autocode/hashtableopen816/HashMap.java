package com.epam.rd.autocode.hashtableopen816;

import java.util.Arrays;

public class HashMap implements HashtableOpen8to16{
	private int capacity;
	private int size;
	private final int MAX_SIZE = 16;
	private HashNode[] hashNode;
	
	public HashMap() {
		super();
		this.capacity = 8;
		this.size = 0;
		this.hashNode = new HashNode[this.capacity];		
	}

	// This implements hash function to find index for a key
	public int hashCode(int key) {
		if(key < 0) key = -key;//module value
        return key % this.capacity;
    }
	
	@Override
	public void insert(int key, Object value) {
		if (search(key) != null) {
			return;
		}
		//Maximum capacity is 16.
		if(this.size >= MAX_SIZE) throw new IllegalStateException();
		if(size == capacity) {
			capacity *= 2;
			//create array new size
			resize(capacity/2, capacity);
		}
		
		HashNode temp = new HashNode(key, value);
		
		// Apply hash function to find index for given key
		int hashIndex = hashCode(key);
		
		// find next free space
		while(hashNode[hashIndex] != null && hashNode[hashIndex].getKey() != key) {
			hashIndex++;
			hashIndex %=capacity;
		}
		
		// if new node to be inserted, increase the current size
		if(hashNode[hashIndex] == null) {
			size++;
		}
		
		this.hashNode[hashIndex] = temp;
	}
	
	public void resize(int capacity, int newCapacity) {
		HashNode[] tempCapacity = Arrays.copyOf(this.hashNode, capacity);
		this.hashNode = new HashNode[newCapacity];
					
		size = 0;//push against
		for (int i = 0; i < capacity; i++) {
			if(tempCapacity[i] != null) insert(tempCapacity[i].getKey(), tempCapacity[i].getValue());
		}
	}

	@Override
	public Object search(int key) {	
		//ТОЖЕ ПРАВИЛЬНОЕ РЕШЕНИЕ
//        int index = hashCode(key);
//        int indexTemp = index;
//        while (true) {
//            if(hashNode[indexTemp] != null && hashNode[indexTemp].getKey() == key){
//                break;
//            }
//            indexTemp++;
//            if (indexTemp == capacity) indexTemp = 0;
//            if (indexTemp == index) return null;
//
//        }
//        return hashNode[indexTemp].getValue();
		
				
		// Apply hash function to find index for given key
        int hashIndex = hashCode(key);
        
        int counter = 0;
        while(this.hashNode[hashIndex] != null) {        	
        	// If counter is greater than capacity to avoid infinite loop
            if (counter > this.capacity) {
                return null;
            }
            
            // if node found return its value
            if(hashNode[hashIndex].getKey() == key) {
            	return hashNode[hashIndex].getValue();
            }
            
            hashIndex++;
            hashIndex %= this.capacity;
            counter++; 
        }
        
        //если в массиве ячейка равна null 
        if(hashNode[hashIndex] == null) {
        	//проверяем весь массив на наличие данного ключа в другой ячейке
        	for (int i = 0; i < hashNode.length; i++) {
        		//игнорируя пустые
        		if(hashNode[i] != null) {
        			//если находим то возвращаем значение (чтоб не вставить одинаковые данные)
        			if(hashNode[i].getKey()==key) {
    					return hashNode[i].getValue();
    				}
        		}
				
			}        	
        }
        
        // If not found return null
        return null;
	}

	@Override
	public void remove(int key) {		
		// Apply hash function to find index for given key
        int hashIndex = hashCode(key);  
       //для прохождения массива от 0 до hashIndex
        int index = hashIndex;
      
        while(true) { //пока не встретим break или return;
        	if(hashNode[hashIndex] != null && hashNode[hashIndex].getKey() == key){
        		break;
        	}
     	
        	hashIndex++;
        	hashIndex %= this.capacity;  
        	//если дошли до конца массива обнуляем индекс
        	if(hashIndex == capacity) hashIndex = 0;
        	//если достигли начального индекса начав сначала массива после hashIndex = 0
        	if(hashIndex == index) return;
        	
        }
        
        hashNode[hashIndex] = null;       
		this.size--;
        
        if (size * 4 <= capacity && capacity != 2) {
            capacity = capacity/2;
            resize(capacity * 2, capacity);
        }
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public int[] keys() {
		//return an int array representing distribution of keys in hashtable bucket array.
		int [] array = new int[capacity];
	    for (int i = 0; i < capacity; i++) {
	    	if(hashNode[i] != null) 
	    		array[i] = hashNode[i].getKey();	    	  
	      }
      
      return array;		
	}

}
