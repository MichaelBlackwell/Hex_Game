
package hex_game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/**
 * @author mike
 */
public class Counter {
    
    private final String name;
    private final int player;
    private final int type;
    private final int formation;
    private final int startMove;
    private int move;
    private final int fp;
    private Hex hex;
    private Point p;
    private Point start;
    private Point goal;
    private float t;
    
    public Counter(String n, int p, int t, int f, int fp_, int m, Hex h){
        this.name = n;
        this.player = p;
        this.type = t;
        this.formation = f;
        this.startMove = m;
        this.move = m;
        this.fp = fp_;
        this.hex = h;
        this.p = new Point(h.getX(),h.getY());
        this.start = this.p;
        this.goal = this.p;
        this.t = 0;
    }
    
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException
    {
    
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException
    {
        
        
        
        if(formation > 2){
            g.setColor(Color.lightGray);
        }
        else{
            g.setColor(new Color(141,167,99));
        }
        g.fillRect(this.getX() - 3, 
                this.getY() + 8, 35, 35);
        
        g.setLineWidth(1);
        
        /*
        g.setColor(Color.darkGray);
        g.drawRect(this.getHex().getPolygon().getPoint(4)[0] - 3, 
                this.getHex().getPolygon().getPoint(4)[1] + 8, 35, 35);
        */
        if(formation == 0){
            g.setColor(Color.green);
        }else if(formation == 1){
            g.setColor(Color.blue);
        }else if(formation == 2){
            g.setColor(Color.yellow);
        }else if(formation == 3){
            g.setColor(new Color(255,195,156));
        }else if(formation == 4){
            g.setColor(new Color(255,51,47));
        }else if(formation == 5){
            g.setColor(new Color(74,182,255));
        }else if(formation == 6){
            g.setColor(new Color(232,194,54));
        }else if(formation == 7){
            g.setColor(Color.white);
        }else if(formation == 8){
            g.setColor(Color.gray);
        }
        
        //draw type on counter
        if(type == 0){
            g.fillRect(this.getX(), 
                    this.getY() + 11, 29, 19);
            g.setColor(Color.darkGray);
            g.drawRect(this.getX(), 
                    this.getY() + 11, 29, 19);
            g.drawLine(this.getX(),
                    this.getY() + 11,
                    this.getX() + 29,
                    this.getY() + 11 + 19);
            
            g.drawLine(this.getX(),
                    this.getY() + 11 + 19,
                    this.getX() + 29,
                    this.getY() + 11);
            
        }else if(type == 1){
            g.fillOval(this.getX(), 
                    this.getY() + 11, 29, 19);
            g.setColor(Color.darkGray);
            g.drawOval(this.getX(), 
                    this.getY() + 11, 29, 19);
        }
        
        g.setColor(Color.black);
        g.drawString("\n" + this.getFP() + "-" + this.getMove(), 
                this.getX() - 3, 
                this.getY() + 8);
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException
    {
        //this.setP(this.linInter(start, goal, t));
        //t += 0.001;
    }
    
    public String getName(){
        return name;
    }
    
    public int getMove(){
        return move;
    }
    
    public int getFP(){
        return fp;
    }
    
    public Hex getHex(){
        return hex;
    }
    
    public void setHex(Hex h){
        
        start.x = this.hex.getX();
        start.y = this.hex.getY();
        
        hex = h;
        
        t = 0;
        
        goal.x = h.getX();
        goal.y = h.getY();
    }
    
    public void move(){
        move -= hex.getMoveCost();
    }
    
    public void moveOne(){
        move--;
    }
    
    public void moveEmpty(){
        move = 0;
    }
    
    public int getFormation(){
        return formation;
    }
    
    public int getPlayer(){
        return player;
    }
    
    public void endMove(){
        move = startMove;
    }
    
    public int getX(){
        return p.x;
    }
    
    public int getY(){
        return p.y;
    }
    
    public Point getP(){
        return p;
    }
    
    public Point getS(){
        return start;
    }
    
    public Point getG(){
        return goal;
    }
    
    public void setX(int Y){
        p.y = Y;
    }
    
    public void setY(int X){
        p.x = X;
    }
    
    public void setP(Point P){
        p = P;
    }
    
    public void setS(Point S){
        start = S;
    }
    
    public void setG(Point G){
        goal = G;
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


