/* Author: Dmitriy
 * sudoku game
 * 
 */

import javafx.application.Application;
import javafx.stage.Stage;

public class sudoku extends Application {
    /* launches JavaFX application */
    /************ MAIN *************/
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage introStage) {
        view out = new view(introStage);
    }
}