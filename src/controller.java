/* Author: Dmitriy
 * sudoku game
 * 
 * Builds and returns buttons to view class
 * Specifies button behavior
 *
 */
 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class controller{
    model game; /* controller directly interacts with the model */
    view parClass; /* parent class */
    Popup menu;
    
    public controller(view parent){
        parClass = parent;
        game = model.getInstance();
    }
    
    /* returns a start button that initializes the model and lets the view update */
    public Button start_button(){
        Button strt_btn = new Button("Start");
        strt_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parClass.scene_transition("choose");
            }
        });
        return strt_btn;
    }
    /* returns a settings button */
    public Button settings_button(){
        Button sett_btn = new Button("Settings");
        sett_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                parClass.scene_transition("settings");
            }
        });
        return sett_btn;
    }
    /* returns an exit button that closes the program */
    public Button exit_button(){
        Button exit_btn = new Button("Exit");
        exit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);    
            }
        });
        return exit_btn;
    }
    /* returns a puzzle button that launches the game screen with selected puzzle */
    public Button puzzle_button(String pzl_name){
        Button pzl_btn = new Button(pzl_name);
        pzl_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                game.load_puzzle(pzl_name);
                parClass.game_screen();
            }
        });
        return pzl_btn;
    }
    /* returns a game button, when clicked or pressed increments the value by 1 */
    public Button g_button(int i, int j){
        Button g_btn = new Button(Integer.toString(game.get_grid(i, j)));
        g_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(event.toString());
                if (game.get_grid_mute(i, j)){
                    int input = game.get_grid(i, j);
                    if (++input > 9)
                        input = 1;
                    game.put_number(i, j, input);
                    g_btn.setText(Integer.toString(input));
                }
            }
        });
        g_btn.setOnKeyTyped(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                try {
                    String key_in = event.getCharacter();
                    /* parse only if regexp matches (only numbers 1-9) & grid is mutable */
                    if (key_in.matches("[1-9]") && game.get_grid_mute(i, j)){
                        int input = Integer.parseInt(key_in);
                        game.put_number(i, j, input);
                        g_btn.setText(Integer.toString(input));    
                    }
                    event.consume();
                    
                } catch (NumberFormatException ex) {
                    System.out.printf("Exception " + ex);
                }
            }
        });
        return g_btn;
    }
    
    public void scene_keys(Stage mS, Scene inc){
        inc.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                try { 
                    if (event.getCode() == KeyCode.ESCAPE){
                        /* NOT FINISHED YET */
                        menu = new Popup();
                        menu.show(mS, 100, 100);
                    }
                } catch (Exception ex) {}
            }
        });
    }
}