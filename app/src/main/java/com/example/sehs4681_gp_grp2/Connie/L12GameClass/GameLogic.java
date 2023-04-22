package com.example.sehs4681_gp_grp2.Connie.L12GameClass;

import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameLogic {
    private int[][] gameBoard;

    //1st element --> row, 2nd element --> col, 3rd element --> line type
    private int[] winType = {-1, -1, -1};

    private Button playAgainBtn;
    private Button homeBtn;
    private TextView playerTurn;
    private boolean isPlayer = true;

    GameLogic() {
        gameBoard = new int[3][3];
        for (int r=0; r<3; r++) {
            for (int c=0; c<3; c++){
                gameBoard[r][c] = 0;
            }
        }
    }

    public boolean updateGameBoard(int row, int col) {
        if (gameBoard[row-1][col-1] == 0) {
            gameBoard[row-1][col-1] = 1;

            return true;
        } else {
            return false;
        }
    }

    public boolean winnerCheck(){
        boolean isWinner = false;

        //Horizontal check
        for (int r=0; r<3; r++){
            if (gameBoard[r][0] == gameBoard[r][1] && gameBoard[r][0] == gameBoard[r][2] && gameBoard[r][0] != 0){
                winType = new int[] {r, 0 ,1};
                isWinner = true;
            }
        }

        //Vertical Check
        for (int c=0; c<3; c++){
            if (gameBoard[0][c] == gameBoard[1][c] && gameBoard[2][c] == gameBoard[0][c] && gameBoard[0][c] != 0){
                winType = new int[] {0, c, 2};
                isWinner = true;
            }
        }

        // Negative diagonal check (winType == 3)
        if (gameBoard[0][0] == gameBoard[1][1] && gameBoard[0][0] == gameBoard[2][2] && gameBoard[0][0] != 0) {
            winType = new int[] {0, 2, 3};
            isWinner = true;
        }

        // Positive diagonal check (winType == 4)
        if (gameBoard[2][0] == gameBoard[1][1] && gameBoard[2][0] == gameBoard[0][2] && gameBoard[2][0] != 0) {
            winType = new int[] {2, 2, 4};
            isWinner = true;
        }

        int boardFilled = 0;

        for (int r=0; r<3; r++) {
            for(int c=0; c<3; c++){
                if (gameBoard[r][c] != 0){
                    boardFilled += 1;
                }
            }
        }

        if (isWinner && isPlayer){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setTextColor(Color.rgb(0, 153, 51));
            playerTurn.setText(("You Won!!!"));
            return true;
        } else if (isWinner) {
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setTextColor(Color.RED);
            playerTurn.setText(("You Lose!!!"));
            return true;
        } else if (boardFilled == 9){
            playAgainBtn.setVisibility(View.VISIBLE);
            homeBtn.setVisibility(View.VISIBLE);
            playerTurn.setText("Tie Game!!!!");
            return true;
        } else {
            return false;
        }
    }

    public void computerMove() {
        int row = -1, col = -1;
        Random rand = new Random();
        while (row < 0 ||  row > 2 ||   col < 0 || col > 2 || gameBoard[row][col] != 0) {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
        }
        gameBoard[row][col] = 2;
    }
    public void resetGame(){
        for (int r=0; r<3; r++) {
            for (int c = 0; c < 3; c++) {
                gameBoard[r][c] = 0;
            }
        }

        playAgainBtn.setVisibility(View.GONE);
        homeBtn.setVisibility(View.GONE);
        playerTurn.setText("");

    }

    public void setPlayAgainBtn(Button playAgainBtn){
        this.playAgainBtn = playAgainBtn;
    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setPlayerTurn(TextView playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public int[] getWinType() {
        return winType;
    }

    public void setPlayer(boolean player) { isPlayer = player; }
}
