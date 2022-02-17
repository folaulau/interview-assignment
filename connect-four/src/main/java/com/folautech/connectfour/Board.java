package com.folautech.connectfour;

import java.util.Scanner;

public class Board {

    private Character[][] grid;
    private Player        playerX;
    private Player        playerY;
    private Scanner       in;
    private Integer       numOfRows;
    private Integer       numOfColumns;

    public Board(Player playerX, Player playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public void play() {

        if ((numOfRows == null || numOfRows < 0) || (numOfColumns == null || numOfColumns < 0)) {
            in = new Scanner(System.in);

            System.out.print("Enter number of rows: ");
            numOfRows = in.nextInt();

            System.out.print("Enter number of columns: ");
            numOfColumns = in.nextInt();
        }

        grid = new Character[numOfRows][numOfColumns];
        initialize();

        int turn = 1;
        int totalTurns = numOfRows * numOfColumns;
        Player player = playerX;
        boolean winner = false;

        // play game
        while (winner == false && turn <= totalTurns) {

            boolean validPlay;
            int play;

            do {

                displayBoard();

                System.out.print("Player " + player.getSymbol() + ", select a column: ");

                play = in.nextInt();

                // validate play
                validPlay = validate(play);

            } while (validPlay == false);

            // populate slot
            populateSlot(player, play);

            // check if there is a winner
            winner = isWinner(player);

            // switch players
            if (player == playerX) {
                player = playerY;
            } else {
                player = playerX;
            }

            turn++;
        }

        displayBoard();

        if (winner) {
            if (player == playerX) {
                System.out.println(playerY.getSymbol() + " won");
            } else {
                System.out.println(playerX.getSymbol() + " won");
            }
        } else {
            System.out.println("Tie game");
        }

        System.out.print("Play again[y/n]: ");
        String playAgain = in.next();

        if (playAgain != null && playAgain.equalsIgnoreCase("y")) {
            play();
        } else {
            System.out.println("Thank you for playing!");
        }
    }

    private void populateSlot(Player player, int play) {
        for (int row = grid.length - 1; row >= 0; row--) {
            if (grid[row][play] == '*') {
                grid[row][play] = player.getSymbol();
                break;
            }
        }
    }

    private void initialize() {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                grid[row][col] = '*';
            }
        }
    }

    private void displayBoard() {
        int numberOfColumns = grid[0].length;

        printNumber(numberOfColumns);

        printLine(numberOfColumns * 2);

        for (int row = 0; row < grid.length; row++) {
            System.out.print("|");
            for (int col = 0; col < grid[0].length; col++) {
                System.out.print(grid[row][col]);
                System.out.print("|");
            }

            System.out.println();
            printLine(numberOfColumns * 2);

        }
        printNumber(numberOfColumns);
        System.out.println();
    }

    private void printLine(int numOfLines) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i <= numOfLines; i++) {
            str.append("-");
        }
        System.out.println(str.toString());

    }

    private void printNumber(int number) {
        for (int i = 0; i < number; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
    }

    private boolean validate(int column) {
        // column valid?
        if (column < 0 || column > grid[0].length) {
            return false;
        }

        try {
            // column taken?
            if (grid[0][column] != '*') {
                System.out.println("Spot taken already. Try again.");
                return false;
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Invalid column number. Try again.");
            return false;
        }

        return true;
    }

    private boolean isWinner(Player player) {
        // check for 4 across
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player.getSymbol() && grid[row][col + 1] == player.getSymbol() && grid[row][col + 2] == player.getSymbol() && grid[row][col + 3] == player.getSymbol()) {
                    return true;
                }
            }
        }
        // check for 4 up and down
        for (int row = 0; row < grid.length - 3; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == player.getSymbol() && grid[row + 1][col] == player.getSymbol() && grid[row + 2][col] == player.getSymbol() && grid[row + 3][col] == player.getSymbol()) {
                    return true;
                }
            }
        }
        // check upward diagonal
        for (int row = 3; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player.getSymbol() && grid[row - 1][col + 1] == player.getSymbol() && grid[row - 2][col + 2] == player.getSymbol()
                        && grid[row - 3][col + 3] == player.getSymbol()) {
                    return true;
                }
            }
        }
        // check downward diagonal
        for (int row = 0; row < grid.length - 3; row++) {
            for (int col = 0; col < grid[0].length - 3; col++) {
                if (grid[row][col] == player.getSymbol() && grid[row + 1][col + 1] == player.getSymbol() && grid[row + 2][col + 2] == player.getSymbol()
                        && grid[row + 3][col + 3] == player.getSymbol()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Integer getNumOfRows() {
        return numOfRows;
    }

    public void setNumOfRows(Integer numOfRows) {
        this.numOfRows = numOfRows;
    }

    public Player getPlayerX() {
        return playerX;
    }

    public void setPlayerX(Player playerX) {
        this.playerX = playerX;
    }

    public Player getPlayerY() {
        return playerY;
    }

    public void setPlayerY(Player playerY) {
        this.playerY = playerY;
    }

}
