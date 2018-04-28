/* Author: Dmitriy
 * sudoku game view
 *
 * Code used from 'JavaFX "Hello World!" tutorial'
 */

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.scene.text.*;

public class view {
    /* measurements of the window */
    int width, height, vert_spc, btnVspc, btnHspc;
    controller opts;
    /* the 2 different views needed so far */
    Stage mainStage, mStage;

    public view(Stage primaryStage){
        /* sets view sizes */
        width = 400; 
        height = 400;
        vert_spc = 20;
        btnVspc = 2; btnHspc = 2;
        opts = new controller(this);
        build_window(primaryStage);
    }
    /* loads the program window */
    private void build_window(Stage introStage){
        mStage = introStage;
        mStage.setTitle("Sudoku");//set window title
        
        BorderPane ui = new BorderPane();
        VBox root = new VBox(vert_spc);
        ui.setCenter(root);
        //build in screen title
        TextFlow title = new TextFlow();
        Text game_title = new Text("Sudoku");
        game_title.setFont(new Font(20));
        game_title.setWrappingWidth(200);
        title.setTextAlignment(TextAlignment.CENTER);
        
        title.getChildren().add(game_title);
        //borderpane top node is game title
        ui.setTop(title);
        Scene intro = new Scene(ui, width, height);
        intro.getStylesheets().add("styles/intro_styles.css");
        mStage.setScene(intro);           
        
        scene_transition("start");
        mStage.show();
    }
    /* loads the game screen */
    public void game_screen(){
        
        mainStage = new Stage();
        mainStage.setTitle("Sudoku");
        
        GridPane board = new GridPane();
        board.setAlignment(Pos.CENTER);
        board.setHgap(btnHspc);
        board.setVgap(btnVspc);
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                /* fills board with game buttons from the controller */
                board.add(opts.g_button(i, j), j, i);
            }
        }
        BorderPane ui = new BorderPane();
        ui.setCenter(board);
        
        //exit button
        
        /* set the scene and build a number button handler for keyboard inputs */
        //Scene sc1 = opts.num_inputs();
        /* add scene and swap screen */
        Scene gm = new Scene(ui, width, height);
        opts.scene_keys(mainStage, gm);
        gm.getStylesheets().add("styles/game_styles.css");
        mainStage.setScene(gm);
        mStage.hide();
        mainStage.show();
    }
    /* scene handler, ensures smooth and consistent transitions between scenes */
    public void scene_transition(String requested){
        //scene transitions have no consistency, this function will handle that now.
        Scene main = mStage.getScene();
        BorderPane ui = (BorderPane) main.getRoot();
        VBox root = (VBox) ui.getCenter();
        root.getChildren().clear();
        
        switch (requested){
            case "choose"://load puzzle selection screen
                /* next code snippet snagged from https://docs.oracle.com/javase/tutorial/essential/io/dirs.html */
                Path dir = FileSystems.getDefault().getPath("puzzles", "");
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                    for (Path file: stream) {
                        String fn = file.getFileName().toString();
                        if (!fn.equals("solutions")){
                            Button pzl = opts.puzzle_button(fn);
                            root.getChildren().add(pzl);
                        }
                    }
                } catch (IOException | DirectoryIteratorException x) {
                    // IOException can never be thrown by the iteration.
                    // In this snippet, it can only be thrown by newDirectoryStream.
                    System.err.println(x);
                }
                break;
            case "game"://load game
            
                break;
            case "settings"://load seetings screen
                Text screen_title = new Text("Settings");
                screen_title.setFont(new Font(16));
                screen_title.setWrappingWidth(200);
                screen_title.setTextAlignment(TextAlignment.CENTER);
                root.getChildren().add(screen_title);
                root.setAlignment(Pos.TOP_CENTER);
                break;
            default://load start screen
                Button strt_btn = opts.start_button();
                Button exit_btn = opts.exit_button();
                Button sett_btn = opts.settings_button();
                root.getChildren().add(strt_btn);
                root.getChildren().add(sett_btn);
                root.getChildren().add(exit_btn);
                root.setAlignment(Pos.CENTER);
                /* build scene with css styles and show */
                break;
        }
        
    }
}