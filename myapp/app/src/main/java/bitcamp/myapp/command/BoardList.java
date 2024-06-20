package bitcamp.myapp.command;

import bitcamp.myapp.vo.Board;

public class BoardList {
    private static final int MAX_SIZE = 100;
    private static Board[] boards = new Board[MAX_SIZE];
    private static int boardLength = 0;

    public static void add(Board board){
        boards[boardLength++] = board;
    }

    public static Board[] listBoard(){
        Board[] boardArr = new Board[boardLength];
        for(int i = 0; i < boardLength; i++){
            boardArr[i] = boards[i];
        }
        return boardArr;
    }

    public static Board findByNo(int boardNo) {
        for (int i = 0; i < boardLength; i++) {
            Board board = boards[i];
            if (board.getNo() == boardNo) {
                return board;
            }
        }
        return null;
    }

    public static int indexOf(Board board) {
        for (int i = 0; i < boardLength; i++) {
            if (boards[i] == board) {
                return i;
            }
        }
        return -1;
    }

    public static Board delete(int boardNo){
        Board deletedBoard = findByNo(boardNo);
        if (deletedBoard == null) {
            return null;
        }
        int index = indexOf(deletedBoard);
        for (int i = index + 1; i < boardLength; i++) {
            boards[i - 1] = boards[i];
        }
        boards[--boardLength] = null;
        return deletedBoard;
    }
}
