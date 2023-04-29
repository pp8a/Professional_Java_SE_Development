package com.epam.rd.autocode.bstprettyprint;

import java.util.Stack;

public class RunTree implements PrintableTree {
	private Node root;	
	
	
	@Override
	public void add(int data) {		
		if(root == null) {
			this.root = new Node(data);
		}else {
			this.root.insert(data);
		}
		
	}

	@Override
	public String prettyPrint() {
		/**
		 * example 1
		 */
//		Stack<Node> stack = new Stack<>();
//		stack.push(root);
//		String toSplitEx1=printEx1(stack, "", 1);	
		/**
		 * example 2
		 */		
//		String toSplitEx2 =printEx2(root, "", 1);
		/**
		 * example 3
		 */
		String toSplit = printEx3(root,"",1);
        String[] lines = toSplit.split("\n");
        StringBuilder builder = new StringBuilder();
        
        for (String line : lines) {
            if(line.charAt(line.length() - 1)==' ')
                builder.append(line, 1, line.length()-1);
            else
                builder.append(line.substring(1));
            builder.append("\n");
        }
        
        return builder.toString();

	}
	
	public String printEx3(Node root, String prefix, int dir) {
        if (root == null) {
            return "";
        }

//        String space = " ".repeat(("" + root.getData()).length());				
		StringBuilder bd = new StringBuilder();
		String space = bd.append(" ".repeat(String.valueOf(root.getData()).length())).toString();		
        
        return printEx3(root.getLeftChild(), prefix + "│  ".charAt(dir) + space, 2)
                + prefix + "└ ┌".charAt(dir) + root.getData()
                + " ┘┐┤".charAt((root.getRightChild() != null ? 2 : 0)
                + (root.getLeftChild() != null ? 1 : 0)) + "\n"
                + printEx3(root.getRightChild(), prefix + "  │".charAt(dir) + space, 0);
    }
	
	
	
	public String printEx2(Node temp, String prefix, int dir) {		
		 if (temp == null) {
	            return "";
	        }
		 
		String space = " ".repeat(("" + temp.getData()).length());
		StringBuilder sb = new StringBuilder();			
		
			String left = printEx2(temp.getLeftChild(), prefix+"|  ".charAt(dir)+space, 2);
			
			sb.append(left);			
			sb.append(prefix);

			if(temp.getLeftChild() == null && temp.getRightChild() == null && dir==2) {								
				sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				sb.append(temp.getData());
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			if(temp.getLeftChild() == null && temp.getRightChild() == null && dir == 0) {							
				sb.append(Pseudographic.LEFT_UP.getSymbol());
				sb.append(temp.getData());
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			
			if(temp.getLeftChild() != null && temp.getRightChild() != null) {
				if(dir == 2) sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				if(dir == 0) sb.append(Pseudographic.LEFT_UP.getSymbol());
				if(dir == 1) sb.append(" ");
				sb.append(temp.getData());
				sb.append(Pseudographic.UP_DOWN.getSymbol());				
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			
			if(temp.getLeftChild() !=null && temp.getRightChild() == null) {
				if(dir == 2) sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				if(dir == 0) sb.append(Pseudographic.LEFT_UP.getSymbol());
				sb.append(temp.getData());
				sb.append(Pseudographic.RIGHT_UP.getSymbol());	
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
		
			if(temp.getRightChild() != null && temp.getLeftChild() == null) {
				if(dir == 2) sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				if(dir == 0) sb.append(Pseudographic.LEFT_UP.getSymbol());				
				sb.append(temp.getData());
				sb.append(Pseudographic.RIGHT_DOWN.getSymbol());
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			
			String right = printEx2(temp.getRightChild(), prefix+"  |".charAt(dir)+space, 0);
			sb.append(right);
		
//		String ss = pr(node.getLeftChild(), prefix + "│  ".charAt(dir) + space, 2)
//        + prefix + "└ ┌".charAt(dir) + node.getData()
//        + " ┘┐┤".charAt((node.getRightChild() != null ? 2 : 0)
//        + (node.getLeftChild() != null ? 1 : 0)) + "\n"
//        + pr(node.getRightChild(), prefix + "  │".charAt(dir) + space, 0);
//		
//		sb.append(ss);
		return sb.toString();
	}
		
	StringBuilder sb = new StringBuilder();
	public String printEx1(Stack<Node> stack, String prefix, int dir) {
		
		while(!stack.isEmpty()) {	
			Node temp = stack.pop();	
			
			StringBuilder bd = new StringBuilder();
			String space = bd.append(" ".repeat(String.valueOf(temp.getData()).length())).toString();
			
			if(temp.getLeftChild() != null) {
				stack.push(temp.getLeftChild());				
				printEx1(stack, prefix+"|  ".charAt(dir)+space, 2);
			}			
		
			sb.append(prefix);
			
			if(temp.getLeftChild() == null && temp.getRightChild() == null && dir == 2) {								
				sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				sb.append(temp.getData());
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			if(temp.getLeftChild() == null && temp.getRightChild() == null && dir == 0) {								
				sb.append(Pseudographic.LEFT_UP.getSymbol());
				sb.append(temp.getData());
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			
			if(temp.getLeftChild() != null && temp.getRightChild() != null) {
				if(dir == 2) sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				if(dir == 0) sb.append(Pseudographic.LEFT_UP.getSymbol());
				if(dir == 1) sb.append(" ");
				sb.append(temp.getData());
				sb.append(Pseudographic.UP_DOWN.getSymbol());				
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
			
			if(temp.getLeftChild() !=null && temp.getRightChild() == null) {				
				if(dir == 2) sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				if(dir == 0) sb.append(Pseudographic.LEFT_UP.getSymbol());
				sb.append(temp.getData());
				sb.append(Pseudographic.RIGHT_UP.getSymbol());	
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
		
			if(temp.getRightChild() != null && temp.getLeftChild() == null) {				
				if(dir == 2) sb.append(Pseudographic.LEFT_DOWN.getSymbol());
				if(dir == 0) sb.append(Pseudographic.LEFT_UP.getSymbol());				
				sb.append(temp.getData());
				sb.append(Pseudographic.RIGHT_DOWN.getSymbol());
				sb.append(Pseudographic.NEW_LINE.getSymbol()); 
			}
						
			if(temp.getRightChild() != null) {
				stack.push(temp.getRightChild());				
				printEx1(stack, prefix+"  |".charAt(dir)+space, 0);
			}
		}
		
		return sb.toString(); 	
	}
}

