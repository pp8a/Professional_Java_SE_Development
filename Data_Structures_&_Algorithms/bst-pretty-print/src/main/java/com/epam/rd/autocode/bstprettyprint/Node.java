package com.epam.rd.autocode.bstprettyprint;

public class Node {
	private int data;
	private Node leftChild;
	private Node rightChild;	
	
	public Node(int data) {
		super();
		this.data = data;
	}

	public void insert(int data) {
		if(data > this.data) {
			if(this.rightChild == null) {
				this.rightChild = new Node(data);
			}else {
				this.rightChild.insert(data);
			}
		}else if(data < this.data){
			if(this.leftChild == null) {
				this.leftChild = new Node(data);
			}else {
				this.leftChild.insert(data);
			}
		}		
	}
	
	public Node getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Node leftChild) {
		this.leftChild = leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public void setRightChild(Node rightChild) {
		this.rightChild = rightChild;
	}
	public int getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Node [data=" + data + ", leftChild=" + leftChild.getData() + ", rightChild=" + rightChild.getData() + "]";
	}
	
	
	
}
