/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package hex_game;

import org.newdawn.slick.*;
import static org.newdawn.slick.Input.*;
import org.newdawn.slick.state.*;

/**
 *
 * @author Michael
 */
public class Play extends BasicGameState{
    
    private Map map;
    
    public Play(int state)
    {
        map = new Map();
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException{
        
        
        map.init(gc, sbc);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        
        //draw background
        g.setColor(new Color(173,154,130));
        g.fillRect(0, 0, gc.getWidth(), gc.getHeight());
        
        //draw table
        g.setColor(new Color(40,73,7));
        map.render(gc, sbc, g);
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        Input input = gc.getInput();
        //update map
        map.update(gc, sbc, delta);
        
        if(input.isKeyDown(KEY_ESCAPE)){
            gc.exit();
        }
    }
    
    @Override
    public int getID(){
        return 1;
    }
    
    public Map getMap(){
        return map;
    }
}
