/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex_game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author mike
 */
public class Button {
    private Image image;
    private Point p;
    
    
    public Button(Image i, int x, int y){
        this.image = i;
        this.p = new Point(x, y);
    }
   
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException{
        image.draw(p.x - (image.getWidth()/2), p.y - (image.getHeight()/2));
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
    }
    
    public boolean isLeftClicked(GameContainer gc, int xpos, int ypos){
        if((xpos > (p.x - (image.getWidth()/2)) 
                && xpos < (p.x + (image.getWidth()/2))) 
                && (ypos > (gc.getHeight() - p.y - (image.getHeight()/2)) // 40 is vertical distance from center
                && ypos < (gc.getHeight() - p.y + (image.getHeight()/2))
                && gc.getInput().isMousePressed(0))){
            return true;
        }else{
            return false;
        }
    }
    
    public int getX(){
        return p.x;
    }
    
    public void setX(int X){
        p.x = X;
    }
    
    public int getY(){
        return p.y;
    }
    
    public void setY(int Y){
        p.y = Y;
    }
    
    public Point getP(){
        return p;
    }
    
    public void setP(Point P){
        p = P;
    }
    
    public Point approach(Point goal,int distance){
        
        if(p.x < goal.x && p.y < goal.y){
            return new Point(p.x + distance, p.y + distance);
        }
        else if(p.x > goal.x && p.y > goal.y){
            return new Point(p.x - distance, p.y - distance);
        }
        else{
            return goal;
        }
    }
    
    public Point linInter(Point p0, Point p1, float t){
        if(t < 1.0){
            return new Point((int)(p0.x + t * (float)(p1.x - p0.x)), (int)(p0.y + t * (float)(p1.y - p0.y)));
        }
        else{
            return p1;
        }
    }
}
