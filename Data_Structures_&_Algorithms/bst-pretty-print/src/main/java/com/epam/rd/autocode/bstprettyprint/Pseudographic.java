package com.epam.rd.autocode.bstprettyprint;

public enum Pseudographic {
	UP_DOWN("┤"),
    VERTICAL("│"),
    LEFT_UP("└"),
    RIGHT_UP("┘"),
    LEFT_DOWN("┌"),
    RIGHT_DOWN("┐"),
    SPACE(" "),
    NEW_LINE("\n");
	
	String symbol;

	private Pseudographic(String symbol) {
		this.symbol = symbol;
	}

	public String getSymbol() {
		return symbol;
	}
}
