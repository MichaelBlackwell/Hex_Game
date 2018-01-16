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
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author mike
 */
public class Map {
    
    int width;
    int height;
    int widthPixel;
    int heightPixel;
    Layout layout;
    Orientation o; // orientation
    Point s; //size
    Point g; //origin
    ArrayList<Hex> map; 
    
    ArrayList<Hex> line;
    Polygon polygon;
    
    int[][] mapTypes;
    int[][][] mapEdges;
    int[][] mapTypes0;
    int[][][] mapEdges0;
    int[][] mapTypes1;
    int[][][] mapEdges1;
    OOB oob;
    
    Hex selectedHex;
    Hex prevSelectedHex;
    Hex targetedHex;
    Hex prevTargetedHex;
    Rules rules;
    
    
    public Map(){
        o = new Orientation(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0, 0.0);
        s = new Point(30, 30);
        g = new Point(150, 50);
        width = 9;
        height = 11;
        widthPixel = (width * (int) s.x) * 2 -100;
        heightPixel = (width * (int) s.x) * 2;
        layout = new Layout(o, s, g);
        map = new ArrayList<>();
        line = new ArrayList<>();

        mapTypes0 = new int[][]{
            {0,0,1,1,2,0,4,0,0},
            {0,1,1,1,1,1,0,0,0},
            {0,0,1,1,1,1,0,0,0},
            {0,0,4,1,1,1,0,0,0},
            {1,0,1,0,1,1,0,0,0},
            {0,0,2,0,0,7,7,7,0},
            {0,4,2,0,7,0,0,4,0},
            {0,0,2,1,7,1,0,0,0},
            {0,4,2,1,7,7,7,0,1},
            {0,4,1,1,0,2,0,2,1},
            {0,0,1,1,0,2,2,2,2}
        };
        
        mapEdges0 = new int[][][]{ //N, NW, SW edges  :  1 = river 2 = road 3 = both
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,2},{0,0,2},{0,1,1},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,2},{0,2,2},{0,0,0},{2,2,0},{2,1,1},{0,2,0}},
            {{0,0,0},{0,2,2},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{2,1,1},{0,0,0}},
            {{0,0,0},{0,0,0},{2,2,2},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{1,2,2},{0,2,1}},
            {{0,0,0},{2,0,0},{2,0,0},{0,2,0},{0,0,0},{2,0,2},{0,2,0},{2,0,0},{2,1,1}},
            {{0,0,0},{2,2,0},{0,0,0},{0,0,0},{0,2,0},{0,2,0},{0,0,0},{2,0,0},{2,1,1}},
            {{0,0,0},{2,0,0},{0,2,0},{0,2,2},{0,0,2},{2,2,0},{0,0,2},{3,3,2},{2,1,3}},
            {{0,0,0},{2,2,0},{0,0,2},{0,0,0},{2,0,0},{2,2,2},{1,1,1},{3,1,1},{0,3,0}},
            {{0,0,0},{2,0,2},{0,0,2},{0,2,0},{0,0,0},{1,1,1},{2,1,2},{1,0,0},{0,2,1}},
            {{0,0,0},{2,0,0},{0,0,0},{0,0,2},{2,2,2},{2,1,1},{0,0,0},{1,1,1},{2,1,2}},
            {{0,0,0},{0,0,0},{0,2,2},{0,0,0},{2,0,0},{2,1,0},{0,0,0},{2,1,0},{0,0,0}}
        };
        
        mapTypes1 = new int[][]{
            {8,8,8,3,4,4,5,5,6,6,6,6,6,6,6},
            {3,8,8,3,1,4,4,4,5,6,6,6,6,6,6},
            {8,8,8,8,2,0,4,4,5,5,6,6,6,6,6},
            {3,1,3,3,3,3,4,0,4,4,5,5,6,6,6},
            {3,3,1,3,8,8,3,3,0,0,4,4,5,5,6},
            {3,3,1,3,3,8,8,8,3,1,0,7,4,4,5},
            {1,1,1,8,8,8,3,8,3,3,1,1,4,4,4},
            {1,1,8,8,3,8,8,3,8,8,3,3,1,1,0},
            {1,1,8,8,8,8,8,3,1,3,8,3,3,3,1},
            {1,8,8,3,8,3,8,8,3,8,3,8,8,8,3},
            {8,8,8,8,8,8,3,8,8,8,8,8,3,8,8}
        };
        
        mapEdges1 = new int[][][]{ //N, NW, SW edges  :  1 = river 2 = road 3 = both
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{1,1,0},{0,1,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{0,2,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,2,0},{0,2,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{0,0,0},{0,0,0},{0,1,1},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,2,0},{0,2,0},{0,1,3},{0,2,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,1,1},{0,0,3},{0,2,2}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,2},{2,2,0},{1,2,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},
            {{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{2,2,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0},{0,0,0}},

        };
        
        mapTypes = mapTypes0;
        mapEdges = mapEdges0;
        
        selectedHex = null;
        prevSelectedHex = null;
        
        targetedHex = null;
        prevTargetedHex = null;
        
        rules = new Rules(this);
        
    }
    
    public void init(GameContainer gc, StateBasedGame sbc) throws SlickException
    {
        
        //map shape
        for (int q = 0; q < width; q++) {
            int r_offset = (int)Math.floor(q/2); // or r>>1
            for (int r = -r_offset; r < height - r_offset; r++) {
                map.add(new Hex(q, r, -q-r));
            }
        }
        getHex(2,0).setName("Trieux");
        
        
        //hex shape/color
        for (Hex checkHex : map){
            polygon = new Polygon();
            checkHex.setType(mapTypes[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col]);
            
            for (Point corner : layout.polygonCorners(layout, checkHex)){
                polygon.addPoint((float)corner.x, (float)corner.y);
            }
            checkHex.setPolygon(polygon);
        }
        
        rules.init();
        oob = new OOB(this);
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g) throws SlickException
    {
        //draw map
        //drawMap(gc, sbc, g);
        
        //draw map as an image
        g.drawImage(new Image("res/MAP.PNG"), (float)(layout.origin.x - layout.size.x), (float)(layout.origin.y - layout.size.y));
        
        //draw p one OOB
        
        
        rules.render(gc, sbc, g);
        
        for(Counter checkCounter : oob.getOOB()){
            checkCounter.render(gc, sbc, g);
        }
    }
    
    public void update(GameContainer gc, StateBasedGame sbc, int delta) throws SlickException
    {
        
        rules.update(gc, sbc, delta);
        
        for(Counter checkCounter : oob.getOOB()){
            checkCounter.update(gc, sbc, delta);
        }
        
    }
    
    public boolean checkRoad(Hex sHex, Hex cHex){
        if((cHex.isNeighbor(sHex,cHex) >= 2
                && cHex.isNeighbor(sHex, cHex) <= 4
                && (mapEdges[OffsetCoord.qoffsetFromCube(0, sHex).row][OffsetCoord.qoffsetFromCube(0, sHex).col][sHex.isNeighbor(sHex,cHex) - 2] == 2
                || mapEdges[OffsetCoord.qoffsetFromCube(0, sHex).row][OffsetCoord.qoffsetFromCube(0, sHex).col][sHex.isNeighbor(sHex,cHex) - 2] == 3))){
            return true;
        }
        else if((cHex.isNeighbor(sHex,cHex) >= 0
                && cHex.isNeighbor(sHex, cHex) <= 1)
                && (mapEdges[OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).row]
                            [OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).col]
                            [sHex.isNeighbor(cHex,sHex) - 2] == 2
                ||  mapEdges[OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).row]
                            [OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).col]
                            [sHex.isNeighbor(cHex,sHex) - 2] == 3)){
            return true;
        }
        else if(cHex.isNeighbor(sHex,cHex) == 5
                && (mapEdges[OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).row]
                            [OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).col]
                            [sHex.isNeighbor(cHex,sHex) - 2] == 2
                ||  mapEdges[OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).row]
                            [OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).col]
                            [sHex.isNeighbor(cHex,sHex) - 2] == 3)){
            return true;
        }
        return false; 
    }
    
    public boolean checkRiver(Hex sHex, Hex cHex){
        if((cHex.isNeighbor(sHex,cHex) >= 2
                && cHex.isNeighbor(sHex, cHex) <= 4
                && (mapEdges[OffsetCoord.qoffsetFromCube(0, sHex).row][OffsetCoord.qoffsetFromCube(0, sHex).col][sHex.isNeighbor(sHex,cHex) - 2] == 1))){
            return true;
        }
        else if((cHex.isNeighbor(sHex,cHex) >= 0
                && cHex.isNeighbor(sHex, cHex) <= 1)
                && (mapEdges[OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).row]
                            [OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).col]
                            [sHex.isNeighbor(cHex,sHex) - 2] == 1)){
            return true;
        }
        else if(cHex.isNeighbor(sHex,cHex) == 5
                && (mapEdges[OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).row]
                            [OffsetCoord.qoffsetFromCube(0, sHex.neighbor(sHex, cHex.isNeighbor(sHex,cHex))).col]
                            [sHex.isNeighbor(cHex,sHex) - 2] == 1)){
            return true;
        }
        return false;
    }
    
    public boolean checkEnemy(Hex sHex, Hex cHex){
        for(int i = 0; i < 6; i++){
            if(getHex(cHex.neighbor(cHex,i).getQ(),cHex.neighbor(cHex,i).getR()) != null
                    && getHex(cHex.neighbor(cHex,i).getQ(),cHex.neighbor(cHex,i).getR()).getCounter() != null
                    && ((sHex.getCounter().getFormation() <= 2 
                    && getHex(cHex.neighbor(cHex,i).getQ(),cHex.neighbor(cHex,i).getR()).getCounter().getFormation() >= 3)
                    || (sHex.getCounter().getFormation() >= 3 
                    && getHex(cHex.neighbor(cHex,i).getQ(),cHex.neighbor(cHex,i).getR()).getCounter().getFormation() <= 2))){
                return true;
            }
        }
        return false;
    }
    
    public boolean checkNeighbor(Hex sHex, Hex cHex){
        for(int i = 0; i < 6; i++){
            if(getHex(cHex.neighbor(cHex,i).getQ(),cHex.neighbor(cHex,i).getR()) != null
                    && getHex(cHex.neighbor(cHex,i).getQ(),cHex.neighbor(cHex,i).getR()) == sHex){
                return true;
            }
        }
        return false;
    }
    
    public Hex getHex(int q, int r){
        for(Hex checkHex : map){
            if (checkHex.getQ() == q && checkHex.getR() == r){
                return checkHex;
            }
        }
        return null;
    }
    
    public void drawMap(GameContainer gc, StateBasedGame sbc, Graphics g){
        //draw map
        for (Hex checkHex : map){
            g.setLineWidth(3);
            g.setColor(Color.black);
            
            g.draw(checkHex.getPolygon());
            
            g.setColor(checkHex.getColor());
            
            g.fill(checkHex.getPolygon());
            
            for(int i = 0; i < 3; i++){
                g.setColor(new Color(22,147,165));
                g.setLineWidth(6);
                if(mapEdges[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col][2 - i] == 1 
                        || mapEdges[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col][2 - i] == 3){
                    g.drawLine(checkHex.getPolygon().getPoint(i+2)[0], checkHex.getPolygon().getPoint(i+2)[1], checkHex.getPolygon().getPoint(i+3)[0], checkHex.getPolygon().getPoint(i+3)[1]);
                }
                g.setColor(new Color(new Color(185,120,62)));
                g.setLineWidth(6);
                if(mapEdges[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col][2 - i] == 2
                        || mapEdges[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col][2 - i] == 3){
                    g.drawLine(checkHex.getPolygon().getCenterX(),
                            checkHex.getPolygon().getCenterY(),
                            checkHex.getPolygon().getCenterX() + (float)layout.hexToPixel(layout, checkHex.direction(-i + 4)).x - (float)layout.origin.x ,
                            checkHex.getPolygon().getCenterY() + (float)layout.hexToPixel(layout, checkHex.direction(-i + 4)).y - (float)layout.origin.y);
                }//checkHex.direction(i).getX()
            }
            
            g.setColor(Color.black);
        }
    }
    
    public void drawSelectedHex(GameContainer gc, StateBasedGame sbc, Graphics g){
        if(selectedHex != null){
            g.setColor(new Color(Color.yellow));
            g.drawString("Selected Hex:"
                    + "\n  (Q,R): " + selectedHex.getQ() + "," + selectedHex.getR() 
                    + "\n  Type: " + selectedHex.getTypeString(), (widthPixel + (int) this.g.x), 0);
            if(selectedHex.getCounter() != null){
                g.drawString("Counter:"
                        + "\n  Name: " + selectedHex.getCounter().getName() 
                        + "\n  FP:" + selectedHex.getCounter().getFP()
                        + "\n  Move:" + selectedHex.getCounter().getMove(), (widthPixel + (int) this.g.x), 85);
            }
            g.setLineWidth(4);
            g.draw(selectedHex.getPolygon());

            //draw neighbor hex
            if(selectedHex.getCounter() != null){
                for(int i = 0; i < 6; i++){

                    g.drawOval((float)layout.hexToPixel(layout,selectedHex.neighbor(selectedHex,i)).x - 10,
                            (float)layout.hexToPixel(layout,selectedHex.neighbor(selectedHex,i)).y - 10,
                            20,20);

                }
            }
        }
    }
    public void drawTargetedHex(GameContainer gc, StateBasedGame sbc, Graphics g){
        if(targetedHex != null){
            g.setColor(new Color(Color.red));
            g.drawString("Targeted Hex:"
                    + "\n  (Q,R): " + targetedHex.getQ() + "," + targetedHex.getR() 
                    + "\n  Type: " + targetedHex.getTypeString(), (widthPixel + (int) this.g.x), 200);
            if(targetedHex.getCounter() != null){
                g.drawString("Counter:" 
                        + "\n  Name: " + targetedHex.getCounter().getName() 
                        + "\n  FP:" + targetedHex.getCounter().getFP()
                        + "\n  Move:" + targetedHex.getCounter().getMove(), (widthPixel + (int) this.g.x), 285);
            }
            g.setLineWidth(4);
            g.draw(targetedHex.getPolygon());
        }
    }
}
