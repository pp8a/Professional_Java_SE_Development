package com.efimchick.ifmo.collections;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class MedianQueue extends LinkedList<Integer>  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return super.size();
	}

	@Override
	public Integer peek() {
		List<Integer> list = new LinkedList<>(this);
		Collections.sort(list);
		int index = this.size()/2;
		if(this.size() % 2 == 0) {
			index--;
		}
		return list.get(index);
	}

	@Override
	public Integer poll() {
		List<Integer> list = new LinkedList<>(this);
		Collections.sort(list);
		int index = this.size()/2;
		if(this.size() % 2 == 0) {
			index--;
		}
		
		this.remove(list.get(index));
		
		return list.get(index);
	}

	@Override
	public boolean offer(Integer e) {
		// TODO Auto-generated method stub
		return super.offer(e);
	}

	@Override
	public Iterator<Integer> iterator() {
		// TODO Auto-generated method stub
		return super.iterator();
	}

}
