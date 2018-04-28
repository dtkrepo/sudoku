/* Author: Dmitriy
 * sudoku game
 * model
 */
 
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
 
public class model{
    /* follows singleton pattern to prevent multiple instances of the game */
    private static model game = new model();
    
    private static square grid[][];
    private static int sol[][];
    private static int length;
    
    /* initializes the grid */
    private model(){
        init_grid();
    }
    /* returns singleton model */
    public static model getInstance(){
        return game;
    }
    
    
    /* initializes the grid with zero values and mutability available */
    private void init_grid(){
        length = 9;
        grid = new square[length][length];
        /*
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                grid[i][j] = new square(0, true);
            }
        }
        */
        //load_puzzle("1.txt");
    }
    /* loads the puzzle from the passed string, searches in puzzles folder
    NEEDS extention included in passed string, ex: file.txt, this.dat, etc. */
    public void load_puzzle(String puzzle){
        System.out.println("Loading puzzle " + puzzle);
        FileReader input;
        BufferedReader buf;
        String line;
        try {
            input = new FileReader("src/puzzles/" + puzzle);
            buf = new BufferedReader(input);
            try {
                int count = 0;
                while ((line = buf.readLine()) != null){
                    char[] temp = line.toCharArray();
                    for (int i = 0; i < length; i++){
                        int square_val = Character.getNumericValue(temp[i]);
                        if (square_val == 0)
                            grid[count][i] = new square(square_val, true);
                        else
                            grid[count][i] = new square(square_val, false);
                    }
                    count++;
                }
            } catch (NumberFormatException e){
                System.out.printf("Exception " + e);
            } finally {
                buf.close();
                input.close();
            }
        } catch (IOException e) {
            System.out.printf("Exception " + e);
        }
    }
    /* loads solution grid. almost identical to the function above, combine as soon as possible */
    private void load_sol(String puzzle){
        sol = new int[length][length];
        //System.out.println("Loading puzzle " + puzzle);
        FileReader input;
        BufferedReader buf;
        String line;
        try {
            input = new FileReader("src/puzzles/solutions/" + puzzle);
            buf = new BufferedReader(input);
            try {
                int count = 0;
                while ((line = buf.readLine()) != null){
                    char[] temp = line.toCharArray();
                    for (int i = 0; i < length; i++){
                        sol[count][i] = Character.getNumericValue(temp[i]);
                    }
                    count++;
                }
            } catch (NumberFormatException e){
                System.out.printf("Exception " + e);
            } finally {
                buf.close();
                input.close();
            }
        } catch (IOException e) {
            System.out.printf("Exception " + e);
        }
    }
    /* checks the grid against the solution, returns true if it matches, false otherwise */
    public boolean verify_solution(){
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                if (grid[i][j].get_value() != sol[i][j])
                    return false;
            }
        }
        return true;
    }
    /* requests & returns grid value from square class */
    public int get_grid(int i, int j){
        return grid[i][j].get_value();
    }
    /* requests & returns grid mutability from square class */
    public boolean get_grid_mute(int i, int j){
        return grid[i][j].get_mutable();
    }
    /* adds the value to the grid as long as it is between 1-9 */
    public void put_number(int i, int j, int val){
        grid[i][j].change_value(val);
    }
    /* takes in a 2D grid position and checks the value at that position
    against the values in the same row/column and within the 3x3 sub-grid */
    public boolean check_guess(int i, int j){
        int temp = get_grid(i,j);
        for (int k = 0; k < length; k++){
            if (temp == get_grid(i,k) || temp == get_grid(k,j))
                return false;
        }
        for (int k = (i%3)*3; k < ((i%3)+1)*3; k++){
            for (int l = (j%3)*3; l < ((j%3)+1)*3; l++){
                if (temp == get_grid(k,j))
                    return false;
            }
        }
        return true;
    }
}