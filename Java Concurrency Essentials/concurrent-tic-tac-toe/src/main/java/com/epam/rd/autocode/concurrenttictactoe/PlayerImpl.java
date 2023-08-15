package com.epam.rd.autocode.concurrenttictactoe;


public class PlayerImpl implements Player {
	private TicTacToe ticTacToe;
    private char mark;
    private PlayerStrategy strategy; 
    private static final char WHITESPACE = ' ';
    
	public PlayerImpl(TicTacToe ticTacToe, char mark, PlayerStrategy strategy) {
		this.ticTacToe = ticTacToe;
		this.mark = mark;
		this.strategy = strategy;		
	}

	@Override
	public void run() {
		while (checkWin()) {
			synchronized (ticTacToe) {   				
	        	if (checkWin() && ticTacToe.lastMark() != mark) {
	                Move move = strategy.computeMove(mark, ticTacToe);	                
	                ticTacToe.setMark(move.row, move.column, mark);	                
	            }
	        }
		}  
	}

	private boolean checkWin() {
        char[][] table = ticTacToe.table();
        for (int i = 0; i < 3; i++) {
            if (table[i][0] == table[i][1] && table[i][1] == table[i][2]) { 
            	//проверяем, если все элементы в текущей строке i равны между собой, это означает, что игрок победил в этой строке. 
                return table[i][0] == WHITESPACE;//а если в этой строке пробел то true и игра продолжается иначе false и конец игры
            } else if (table[0][i] == table[1][i] && table[1][i] == table[2][i]) {
            	// Если все элементы в столбце i равны между собой, это означает победа в этом столбце
                return table[0][i] == WHITESPACE;
            }
        }
        if (table[0][0] == table[1][1] && table[1][1] == table[2][2]) {
        	//Если все элементы на главной диагонали равны между собой, это означает победу в этой диагонали.
            return table[0][0] == WHITESPACE;
        } else if (table[0][2] == table[1][1] && table[1][1] == table[2][0]) {
        	// Если все элементы на побочной диагонали равны между собой, это означает победу в этой диагонали.
            return table[0][2] == WHITESPACE;
        }
        return true;// Если ни одно из условий выше не выполнено, это означает, что на доске нет победных комбинаций,
        //и игра не завершилась победой. В этом случае возвращаем true.
    }
	
}
