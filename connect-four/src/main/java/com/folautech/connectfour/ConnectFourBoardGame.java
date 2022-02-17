package com.folautech.connectfour;


public class ConnectFourBoardGame {

    public static void main(String[] args) {
        Player playerX = new Player('X');
        Player playerY = new Player('Y');
        Board board = new Board(playerX, playerY);
        board.play();
    }

}
