package hex_game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{
    
    public static final String gameName = "Hex War v0.0.3";
    public static final int menu = 0;
    public static final int play = 1;
    public static final int WIDTH = 1300;
    public static final int HEIGHT = 700;
    public static final boolean FULLSCREEN = false;
    
    public Game(String gameName){
        super(gameName);
        this.addState(new Menu(menu));
        this.addState(new Play(play));
        //this.addState(new End(end));
        
    }
    
    public static void main(String[] args) {
        AppGameContainer appgc;
        try{
            appgc = new AppGameContainer(new Game(gameName));
            appgc.setDisplayMode(WIDTH, HEIGHT, FULLSCREEN);
            appgc.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException{
        this.getState(menu).init(gc, this);
        this.getState(play).init(gc, this);
        //this.getState(end).init(gc, this);
        
        this.enterState(menu);
    }
    
    
}
