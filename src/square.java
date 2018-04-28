/* Author: Dmitriy
 * sudoku game
 * 
 * Specifies a square, which contains a value and whether it can be modified or not.
 */
 
public class square{
    private int value;
    private boolean mutable;
    
    /* Constructors */
    public square(){
        value = 0;
        mutable = true;
    }
    public square(int val){
        value = val;
    }
    public square(boolean mod){
        mutable = mod;
    }
    public square(int val, boolean mod){
        value = val;
        mutable = mod;
    }/* Accessors */
    public int get_value(){
        return this.value;
    }
    public boolean get_mutable(){
        return this.mutable;
    }/* Mutators */
    public void change_value(int changeTo){
        this.value = changeTo;
    }
    public void change_mutable(boolean changeTo){
        this.mutable = changeTo;
    }
}