package com.epam.rd.autocode.concurrenttictactoe;

import java.util.Arrays;
import java.util.stream.IntStream;

public class TicTacToeImpl implements TicTacToe {
	private char[][] board;
    private char lastMark;
    private static final char WHITESPACE = ' ';
    
	public TicTacToeImpl() {
		super();
		int size = 3;
		this.board = new char [size][size];
		IntStream.range(0, 3)
        	.forEach(i -> Arrays.fill(board[i], WHITESPACE));
		
		this.lastMark = 'O';
	}

	@Override
	public void setMark(int x, int y, char mark) {
		if (board[x][y] == WHITESPACE) {
	        board[x][y] = mark;
	        lastMark = mark;
	    } 
	}

	@Override
	public  char[][] table() {
		return Arrays.stream(board)
				.map(char[]::clone)
				.toArray(char[][]::new);
	}

	@Override
	public char lastMark() {
		return lastMark;		
	}		
}
