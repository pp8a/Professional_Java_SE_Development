package com.efimchick.ifmo.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class PairStringList extends ArrayList<String>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String get(int index) {		
		return super.get(index);
	}

	@Override
	public String set(int index, String element) {
		super.set(index, element);		
		return index % 2==0 ? super.set(index+1, element) :  super.set(index-1,element);
	}

	@Override
	public boolean add(String e) {
		super.add(e);
		return super.add(e);
	}

	@Override
	public void add(int index, String element) {
		index = index % 2 == 0 ? index : index+1;		
		super.add(index, element);
		super.add(index, element);
	}

	@Override
	public String remove(int index) {
		index = index % 2 == 0 ? index : index-1;
		super.remove(index);
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		super.remove(o);
		return super.remove(o);
	}

	@Override
	public boolean addAll(Collection<? extends String> c) {
		c.forEach(t -> {
			this.add(t);			
		});
		return true;
	}

	@Override
	public boolean addAll(int index, Collection<? extends String> c) {
		List<String> list = new ArrayList<>();
		for (String string : c) {
			list.add(string);
			list.add(string);			
		}		
		index = index % 2 == 0 ? index : index+1;
		
		return super.addAll(index, list);
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return super.iterator();
	}

}
