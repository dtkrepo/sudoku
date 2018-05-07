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
    private int width, height, vert_spc, btnVspc, btnHspc, fontSize;
    private String page_title;
    controller opts;
    /* the 2 different views needed so far */
    private Stage mStage;

    public view(Stage primaryStage){
        /* sets view sizes */
        width = 500;
        height = 500;
        vert_spc = 20;
        fontSize = 20;
        btnVspc = 5; btnHspc = 5;
        page_title = "Sudoku";
        opts = new controller(this);
        build_window(primaryStage);
    }
    /* loads the program window */
    private void build_window(Stage introStage){
        mStage = introStage;
        mStage.setTitle(page_title);//set window title
        
        BorderPane ui = new BorderPane();
        VBox root = new VBox(vert_spc);
        ui.setCenter(root);
        //build in screen title
        TextFlow title = new TextFlow();
        Text game_title = new Text(page_title);
        game_title.setFont(new Font(fontSize));
        game_title.setWrappingWidth(200);
        title.setTextAlignment(TextAlignment.CENTER);
        
        title.getChildren().add(game_title);
        //borderpane top node is game title
        ui.setTop(title);
        Scene intro = new Scene(ui, width, height);
        intro.getStylesheets().add("styles/intro_styles.css");
        mStage.setScene(intro);
        scene_transition("");
        mStage.show();
    }
    /* loads the game screen */
    public void game_screen(){
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
        add_controls(ui);
        /* set the scene and build a number button handler for keyboard inputs */
        //Scene sc1 = opts.num_inputs();
        /* add scene and swap screen */
        Scene gm = new Scene(ui, width, height);
        opts.scene_keys(mStage, gm);
        gm.getStylesheets().add("styles/game_styles.css");
        mStage.setScene(gm);
    }
    private void add_controls(BorderPane ui){
        HBox controls = new HBox();
        controls.setAlignment(Pos.CENTER);
        controls.getChildren().add(opts.exit_button());
        ui.setBottom(controls);
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
                Path dir = FileSystems.getDefault().getPath("src/puzzles", "");
                System.out.println(dir.toString());
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
            case "settings":
                Text screen_title = new Text("Settings");
                screen_title.setFont(new Font(fontSize-2));
                screen_title.setWrappingWidth(200);
                screen_title.setTextAlignment(TextAlignment.CENTER);
                root.getChildren().add(screen_title);
                root.setAlignment(Pos.TOP_CENTER);
                break;
            default://start screen
                root.getChildren().add(opts.start_button());
                root.getChildren().add(opts.settings_button());
                root.getChildren().add(opts.exit_button());
                root.setAlignment(Pos.CENTER);
                /* build scene with css styles and show */
                break;
        }
        
    }
}