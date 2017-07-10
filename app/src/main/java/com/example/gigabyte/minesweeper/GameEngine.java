package com.example.gigabyte.minesweeper;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.gigabyte.minesweeper.util.Generator;
import com.example.gigabyte.minesweeper.util.PrintGrid;
import com.example.gigabyte.minesweeper.views.grid.Cell;

/**
 * Created by Gigabyte on 2017/06/15.
 */

public class GameEngine {
    private static GameEngine instance;

    private Context context;

    public static final int BOMB_NUMBER = 20;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;

    private boolean firstClick;

    private Cell[][] MinesweeperGrid = new Cell[WIDTH][HEIGHT];

    public static GameEngine getInstance() {
        if(instance == null){
            instance = new GameEngine();
        }
        return instance;
    }

    private GameEngine(){

    }

    public void createGrid(Context context){
        this.context = context;
        firstClick = true;
        // create the grid and store it
        int[][] GeneratedGrid = Generator.generate(BOMB_NUMBER, WIDTH, HEIGHT);
        PrintGrid.print(GeneratedGrid, WIDTH, HEIGHT);
        setGrid(context, GeneratedGrid);
    }

    private void setGrid(final Context context, final int[][] grid){
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(MinesweeperGrid[x][y] == null){
                    MinesweeperGrid[x][y] = new Cell(context, x, y);
                }
                MinesweeperGrid[x][y].setValue(grid[x][y]);
                MinesweeperGrid[x][y].invalidate();
            }
        }
    }

    public Cell getCellAt(int position){
        int x = position % WIDTH;
        int y = position / WIDTH;

        return MinesweeperGrid[x][y];
    }

    public Cell getCellAt(int x, int y){
        return MinesweeperGrid[x][y];
    }

    public void click(int x, int y){
        if (x >= 0 && y >= 0 && x < WIDTH && y < HEIGHT && !getCellAt(x, y).isClicked()) {
            if(firstClick){
                int[][] GeneratedGrid = Generator.generateBomb(BOMB_NUMBER, WIDTH, HEIGHT, x, y);
                PrintGrid.print(GeneratedGrid, WIDTH, HEIGHT);
                setGrid(context, GeneratedGrid);
                firstClick = false;
            }

            getCellAt(x, y).setClicked();

            if (getCellAt(x, y).getValue() == 0) {
                for (int xt = -1; xt <= 1; xt++) {
                    for (int yt = -1; yt <= 1; yt++) {
                        if (x + xt >= 0 && y + yt >= 0 && x + xt < WIDTH && y + yt < HEIGHT) {
                            if (!getCellAt(x + xt, y + yt).isBomb()) {
                                click(x + xt, y + yt);
                            }
                        }
                    }
                }
            }

            if (getCellAt(x, y).isBomb() && !getCellAt(x, y).isFlagged()) {
                onGameLost();
            }
        }

        checkEnd();
    }

    private boolean checkEnd(){
        int bombNotFound = BOMB_NUMBER;
        int notRevealed = WIDTH * HEIGHT;
        for(int x = 0; x < WIDTH; x++){
            for(int y = 0; y < HEIGHT; y++){
                if(getCellAt(x, y).isRevealed() || getCellAt(x, y).isFlagged()){
                    notRevealed--;
                }

                if(getCellAt(x, y).isFlagged() && getCellAt(x, y).isBomb()){
                    bombNotFound--;
                }
            }
        }

        if(bombNotFound == 0 && notRevealed == 0){
            Toast.makeText(context, "Game won", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void flag(int x, int y){
        if(!getCellAt(x, y).isRevealed()) {
            boolean isFlagged = getCellAt(x, y).isFlagged();
            getCellAt(x, y).setFlagged(!isFlagged);
            getCellAt(x, y).invalidate();
        }
    }

    private void onGameLost(){
        // handle lost game
        Toast.makeText(context, "Game lost", Toast.LENGTH_SHORT).show();

        for(int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                getCellAt(x, y).setRevealed();
            }
        }
    }
}
