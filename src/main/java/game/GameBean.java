package game;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.List; 
import java.util.Random;

public class GameBean implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private static final int GRID_SIZE = 3;

    
    public enum GameState {
        X, O, NULL 
    }

    
    public enum GamePlayer {
        HUMAN, COMPUTER
    }

    private GameState[][] gameStatus; 
    private GameState winner; 
    private GamePlayer currentPlayer; 
    private int movesMade; 

    public GameBean() {
        initGame();
    }

    
    public void initGame() {
        gameStatus = new GameState[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                gameStatus[i][j] = GameState.NULL; 
            }
        }
        winner = null;
        currentPlayer = GamePlayer.HUMAN; 
        movesMade = 0; 
    }

    
    public List<Integer> getGridLines() {
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < GRID_SIZE; i++) {
            lines.add(i);
        }
        return lines;
    }

    
    public List<Cell> getGridStatus(int line) {
        List<Cell> cells = new ArrayList<>();
        for (int col = 0; col < GRID_SIZE; col++) {
            cells.add(new Cell(gameStatus[line][col], line, col));
        }
        return cells;
    }

    public GameState getWinner() {
        return winner;
    }

    public void setWinner(GameState winner) {
        this.winner = winner;
    }

    public GamePlayer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(GamePlayer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

 
    public boolean makeMove(int line, int col) {
        
        if (line < 0 || line >= GRID_SIZE || col < 0 || col >= GRID_SIZE || gameStatus[line][col]!= GameState.NULL || winner!= null) {
            return false; 
        }

        
        gameStatus[line][col] = (currentPlayer == GamePlayer.HUMAN)? GameState.X : GameState.O;
        movesMade++; 

       
        if (checkWin(line, col)) {
            winner = gameStatus[line][col]; 
        } else if (isBoardFull()) {
            winner = GameState.NULL; 
        } else {
            
            currentPlayer = (currentPlayer == GamePlayer.HUMAN)? GamePlayer.COMPUTER : GamePlayer.HUMAN;
        }
        return true; 
    }
    

    public void makeComputerMove() {
        if (winner!= null || isBoardFull()) {
            return; 
        }

        Random rand = new Random();
        List<int[]> emptyCells = new ArrayList<>();
        
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (gameStatus[i][j] == GameState.NULL) {
                    emptyCells.add(new int[]{i, j});
                }
            }
        }

        if (!emptyCells.isEmpty()) {
            
            int[] move = emptyCells.get(rand.nextInt(emptyCells.size()));
            makeMove(move[0], move[1]);
        }
    }

    
    private boolean checkWin(int line, int col) {
        GameState playerState = gameStatus[line][col]; 

        
        boolean winRow = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (gameStatus[line][i]!= playerState) { 
                winRow = false;
                break;
            }
        }
        if (winRow) return true;

        
        boolean winCol = true;
        for (int i = 0; i < GRID_SIZE; i++) {
            if (gameStatus[i][col]!= playerState) { 
                winCol = false;
                break;
            }
        }
        if (winCol) return true;

        
        if (line == col) {
            boolean winDiag1 = true;
            for (int i = 0; i < GRID_SIZE; i++) {
                if (gameStatus[i][i]!= playerState) {
                    winDiag1 = false;
                    break;
                }
            }
            if (winDiag1) return true;
        }

        
        if (line + col == GRID_SIZE - 1) {
            boolean winDiag2 = true;
            for (int i = 0; i < GRID_SIZE; i++) {
                if (gameStatus[i][GRID_SIZE - 1 - i]!= playerState) {
                    winDiag2 = false;
                    break;
                }
            }
            if (winDiag2) return true;
        }

        return false; 
    }

    
    public boolean isBoardFull() {
        return movesMade == GRID_SIZE * GRID_SIZE;
    }
    
    
    public boolean hasEmptyCell(){
        for(int line = 0; line < GRID_SIZE; line++){
            for(int col = 0; col < GRID_SIZE; col++){
                if(this.gameStatus[line][col] == GameState.NULL){
                    return true;
                }
            }
        }
        return false;
    }
}