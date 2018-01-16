/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex_game;

import java.util.ArrayList;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author mike
 */
class Hex
{
    private String name;
    private Point p;
    private final int q;
    private final int r;
    private final int s;
    private Polygon polygon;
    private Color color;
    private int type;
    private int shift;

    private Counter counter;
    private ArrayList<Counter> counters;
    
    public Hex(int q, int r, int s)
    {
        this.name = "";
        this.q = q;
        this.r = r;
        this.s = s;

        this.counter = null;
        this.counters = new ArrayList<Counter>();

    }
    
    
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException{
        
        
        

        
    }
    

    static public Hex add(Hex a, Hex b)
    {
        return new Hex(a.q + b.q, a.r + b.r, a.s + b.s);
    }


    static public Hex subtract(Hex a, Hex b)
    {
        return new Hex(a.q - b.q, a.r - b.r, a.s - b.s);
    }


    static public Hex scale(Hex a, int k)
    {
        return new Hex(a.q * k, a.r * k, a.s * k);
    }

    static public ArrayList<Hex> directions = new ArrayList<Hex>(){{add(new Hex(1, 0, -1)); add(new Hex(1, -1, 0)); add(new Hex(0, -1, 1)); add(new Hex(-1, 0, 1)); add(new Hex(-1, 1, 0)); add(new Hex(0, 1, -1));}};

    static public Hex direction(int direction)
    {
        return Hex.directions.get(direction);
    }


    static public Hex neighbor(Hex hex, int direction)
    {
        return Hex.add(hex, Hex.direction(direction));
    }
    
    public int isNeighbor(Hex hex1, Hex hex2){
        for(int i = 0; i < 6; i++){
            if(hex1.neighbor(hex1,i).getQ() == hex2.getQ() && hex1.neighbor(hex1,i).getR() == hex2.getR()){
                return i;
            }
        }
        return -1;
    }

    static public ArrayList<Hex> diagonals = new ArrayList<Hex>(){{add(new Hex(2, -1, -1)); add(new Hex(1, -2, 1)); add(new Hex(-1, -1, 2)); add(new Hex(-2, 1, 1)); add(new Hex(-1, 2, -1)); add(new Hex(1, 1, -2));}};

    static public Hex diagonalNeighbor(Hex hex, int direction)
    {
        return Hex.add(hex, Hex.diagonals.get(direction));
    }


    static public int length(Hex hex)
    {
        return (int)((Math.abs(hex.q) + Math.abs(hex.r) + Math.abs(hex.s)) / 2);
    }


    static public int distance(Hex a, Hex b)
    {
        return Hex.length(Hex.subtract(a, b));
    }
    
    public int getX(){
        return (int)this.getPolygon().getPoint(4)[0];
    }
    
    public void setX(int x){
        this.p.x = x;
    }
    
    public int getY(){
        return (int)this.getPolygon().getPoint(4)[1];
    }
    
    public void setY(int y){
        this.p.y = y;
    }
    
    public int getQ(){
        return q;
    }
    
    public int getR(){
        return r;
    }
    
    public int getS(){
        return s;
    }
    
    public Polygon getPolygon(){
        return polygon;
    }
    
    public void setPolygon(Polygon p){
        polygon = p;
    }

    public Color getColor(){
        return color;
    }
    
    public void setColor(Color c){
        color = c;
    }
    
    public int getType(){
        return type;
    }
    
    public String getTypeString(){
        switch (type){
            case 0:
                return "Grassland";
            case 1:
                return "Woods";
            case 2:
                return "Hills";
            case 3:
                return "Forested Hills";
            case 4:
                return "City";
            case 5:
                return "Marsh";
            case 6:
                return "Sea";
            case 7:
                return "Fortification";
            case 8:
                return "Mountain";
            default:
                return "Grassland";
        }
    }
    
    public void setType(int t){
        
        switch(t){
            case 0:
                setColor(new Color(203,216,183));
                setShift(0);
                break;
            case 1:
                setColor(new Color(156,174,89));
                setShift(2);
                break;
            case 2:
                setColor(new Color(174,156,94));
                setShift(1);
                break;
            case 3:
                setColor(new Color(128,126,60));
                setShift(0);
                break;
            case 4:
                setColor(new Color(218,184,171));
                setShift(2);
                break;
            case 5:
                setColor(new Color(107,150,100));
                setShift(0);
                break;
            case 6:
                setColor(new Color(156,201,219));
                setShift(0);
                break;
            case 7:
                setColor(new Color(Color.gray));
                setShift(3);
                break;
            case 8:
                setColor(new Color(181,112,87));
                setShift(0);
                break;
            default:
                //clear
                setColor(new Color(183,209,163));
                setShift(0);
                break;
                
                
        }
        type = t;
    }
    

    
    public void setCounter(Counter c){
        counter = c;
    }
    
    public Counter getCounter(){
        return counter;
    }
    
    public ArrayList<Counter> getCounters(){
        return counters;
    }
    
    public int getMoveCost(){
        switch (type){
            case 0:
                return 1;
            case 1:
                return 2;
            case 2:
                return 2;
            case 3:
                return 3;
            case 4:
                return 1;
            case 5:
                return 3;
            case 6:
                return 99;
            case 7: 
                return 1;
            case 8: 
                return 3;
            default:
                return 2;
        } 
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name = n;
    }
    
    public boolean isLeftClicked(GameContainer gc){
        if(this.getPolygon().contains(Mouse.getX(), gc.getHeight() - Mouse.getY())
                    && gc.getInput().isMousePressed(0)){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean isRightClicked(GameContainer gc){
        if(this.getPolygon().contains(Mouse.getX(), gc.getHeight() - Mouse.getY())
                    && gc.getInput().isMousePressed(1)){
            return true;
        }else{
            return false;
        }
    }
    
    public int getShift(){
        return shift;
    }
    
    public void setShift(int s){
        shift = s;
    }
    
    public Point getP(){
        return p;
    }
}