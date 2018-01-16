package hex_game;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Michael
 */
public class Menu extends BasicGameState{
    
    private Button playButton;
    private Button exitButton;
    
    private Button playTest;
    
    private Point goal;
    private Point start;
    
    private float t;
    
    public Menu(int state){
       goal = new Point(25,25);
       start = new Point(600,400);
       t = 0;
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException{
        
        
        // We need a manager to handle every tween.

        
        
        playButton = new Button(new Image("res/SB-Buttons_1.png"), gc.getWidth()/2, gc.getHeight()/2);
        exitButton = new Button(new Image("res/SB-Buttons_2.png"), gc.getWidth()/2, gc.getHeight()/2 + 50);
        
        playTest = new Button(new Image("res/SB-Buttons_1.png"), Mouse.getX(), Mouse.getY());
        
        
    }
    
    //draw
    @Override
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
        g.setColor(new Color(118,150,181));
        
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        playButton.render(gc,sbc,g);
        exitButton.render(gc,sbc,g);
        
        g.setColor(Color.darkGray);
        g.drawString("HEX WAR", gc.getWidth()/2 - 50, gc.getHeight()/2 - 120);
        g.drawString("LININTERPOLATION: " + t, gc.getWidth()/2 - 50, gc.getHeight()/2 - 160);
    }
    
    //change values and images by delta
    @Override
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        t += 0.01;
        
        playButton.setP(playButton.linInter(new Point(gc.getWidth() + 100, gc.getHeight()/2), new Point(gc.getWidth()/2, gc.getHeight()/2), t));
        exitButton.setP(exitButton.linInter(new Point(-100, gc.getHeight()/2 + 50), new Point(gc.getWidth()/2, gc.getHeight()/2 + 50), t));
        
        int xpos = Mouse.getX();
        int ypos = Mouse.getY();
        
        //mouse controller for play button
        if(playButton.isLeftClicked(gc, xpos, ypos)){
            sbc.enterState(1);
        }
        
        //mouse controller for exit button
        if(exitButton.isLeftClicked(gc, xpos, ypos)){
            gc.exit();
        }
    }
    
    @Override
    public int getID(){
        return 0; // change to correct corresponding state
    }
}
