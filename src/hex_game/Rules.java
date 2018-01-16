/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex_game;

import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author mike
 */
public class Rules {
    private Map map;
    private int playerTurn;
    private int turnNum;
    private int[][] mapVP;
    private int[][] combatTable;
    private enum Phase {SETUP,
                        MOVE,
                        ATTACK,
                        RETREAT,
                        END_TURN,
                        END_GAME};
    private int xpos;
    private int ypos;
    private Input input;
    private Button nextPhase;
    private Button attackButton;
    private Button deathButton;
    
    private Random rand;
    private Phase phase;
    private Boolean endTurn;
    private int pOneVP = 0;
    private int pTwoVP = 0;
    public String log = " ";
    private boolean riverCrossed;
    private boolean metzCap;
    private boolean thionvilleCap;
    
    private Hex copyHex;
    private Counter copyCounter;
    private Polygon polygon;
    
    private ArrayList<Hex> hexSelection;
    private int attackingFP;
    private int combatDifferential;
    
    private int retreatAmount;
    private int retreatPlayer;
    
    private int test;
    
    public Rules(Map map){
        this.map = map;
        this.phase = Phase.MOVE;
        this.playerTurn = 1;
        this.turnNum = 0;
        this.rand = new Random();
        
        mapVP = new int[][]{
            {0,0,0,0,0,0,3,1,1},
            {0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,1,1},
            {0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,2,1},
            {0,0,0,0,0,0,1,1,1},
            {0,0,0,0,0,1,1,1,1},
            {0,0,0,0,0,1,1,1,1},
            {0,0,0,0,0,1,1,1,1}
        };
        
        combatTable = new int[][]{
          //[roll][differential] 
            {1,2,2,2,3,3,3,3}, //roll 1
            {1,1,2,2,2,3,3,3}, //roll 2
            {0,1,1,2,2,2,3,3}, //etc...
            {0,0,0,1,2,2,2,3},
            {0,0,0,0,1,2,2,2},
            {0,0,0,0,0,1,2,2}
        };
        
        this.riverCrossed = false;
        this.metzCap = false;
        this.thionvilleCap = false;
        copyHex = null;
        copyCounter = null;
        attackingFP = 0;
        combatDifferential = 0;
        
        hexSelection = new ArrayList<Hex>();
    }
    
    //deal all personnel cards (and later equipment and vehicle cards) to players
    //and have them place their cards (unseen by the opponent) into teams on the table
    
    public void init(){
        
        
        
        try{
            nextPhase = new Button(new Image("res/nextButton.png"), map.widthPixel + 400, 300);
            attackButton = new Button(new Image("res/Target.png"), map.widthPixel + 400, 150);
            deathButton = new Button(new Image("res/Skull.png"), map.widthPixel + 400, 150);
        }catch(SlickException e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void render(GameContainer gc, StateBasedGame sbc, Graphics g){
        try{
        //display log
        if( test == 1){
            g.drawString("HEX PRESSED", 0,0);
        }    
        
            
        g.setColor(Color.black);
        g.drawString(log, 0, 10);
        
        g.drawString("Player One VPs: " + pOneVP, 10 , gc.getHeight()- 80);
        
        g.drawString("Player Two VPs: " + pTwoVP, 10 , gc.getHeight()- 60);
        
        g.drawString("Turn: " + (turnNum + 1), 10 , gc.getHeight()- 40);
        
        nextPhase.render(gc,sbc,g);
        map.drawSelectedHex(gc,sbc,g);
        
        map.drawTargetedHex(gc,sbc,g);
        
        //SETUP phase
        if(phase == Phase.SETUP){
            g.drawString("Player " + playerTurn + ", place a counter", 10 , gc.getHeight()- 20);
        }
        
        //MOVE phase
        else if (phase == Phase.MOVE){
            g.drawString("Player " + playerTurn + ", move a counter", 10 , gc.getHeight()- 20);
            
        }
        
        //ATTACK phase
        else if(phase == Phase.ATTACK){
            g.drawString("Player " + playerTurn + ", attack a counter", 10 , gc.getHeight()- 20);

            attackButton.render(gc,sbc,g);
            
            if(map.targetedHex != null){
                
                //draw copy of targeted hex
                g.setColor(copyHex.getColor());
                g.fill(copyHex.getPolygon());
                g.setColor(Color.red);
                g.draw(copyHex.getPolygon());
                
                for(int i = 0; i < 6; i++){
                    
                    if(map.getHex(map.targetedHex.neighbor(map.targetedHex,i).getQ(),map.targetedHex.neighbor(map.targetedHex,i).getR()) != null
                            && map.getHex(map.targetedHex.neighbor(map.targetedHex,i).getQ(),map.targetedHex.neighbor(map.targetedHex,i).getR()).getCounter() != null){
                    }
                }
                if(!hexSelection.isEmpty()){
                    for(Hex checkHex : hexSelection){
                        g.setColor(Color.blue);
                        g.setLineWidth(6);
                        g.draw(checkHex.getPolygon());
                    }
                }
            }
            
            g.setColor(Color.blue);
            g.drawString("Attackers: ", map.widthPixel + (int) map.g.x, 400);
            int i = 0;
            attackingFP = 0;
            for(Hex checkHex : hexSelection){
                attackingFP += checkHex.getCounter().getFP();
                g.drawString("  Name: " + checkHex.getCounter().getName()
                            + "\n  FP: " + checkHex.getCounter().getFP(), map.widthPixel + (int) map.g.x, 425 + (40 * i));
                i++;
            }
            if(map.targetedHex != null){
                g.drawString("Attackers Total FP: " + attackingFP
                        + "\nDefenders Total FP: " + map.targetedHex.getCounter().getFP()
                        + "\nCombat Differential: " + combatDifferential, map.widthPixel + (int) map.g.x + 200, 400);
            }
        }
        //RETREAT PHASE
        if(phase == Phase.RETREAT){
            
            deathButton.render(gc, sbc, g);
            
            if(!hexSelection.isEmpty()){
                for(Hex checkHex : hexSelection){
                    g.setColor(Color.blue);
                    g.setLineWidth(6);
                    g.draw(checkHex.getPolygon());
                }
            }
        }
        
        //end turn
        else{
            
        }
        }catch(SlickException e) {
            e.printStackTrace();
        }
        
    }
    
    //Players alternate: choosing action or terrain cards, picking a team to play it on, 
    //and (if neccisary) choose a an opponets team.
    public void update(GameContainer gc, StateBasedGame sbc, int delta){
        xpos = Mouse.getX();
        ypos = Mouse.getY();
        input = gc.getInput();
        
        for (Hex checkHex : map.map){
            try{
                checkHex.update(gc,sbc,delta);
            }catch(SlickException e){
                
            }
            
            
            //MOVE PHASE
            if(phase == Phase.MOVE){
                
                
                if(checkHex.isLeftClicked(gc)){
                    //Moved selected Counter
                    if(checkNormalMove(checkHex))
                    {
                        normalMove(checkHex);
                    }
                    
                    //move along river
                    else if(checkRiverMove(checkHex))
                    {
                        riverMove(checkHex);
                    }
                    
                    //move next to enemy
                    else if(checkZOCMove(checkHex))
                    {
                        ZOCMove(checkHex);
                    }

                    //move along road
                    else if(checkRoadMove(checkHex))
                    {
                        roadMove(checkHex);
                    }

                    

                    

                    //Change selected Hex
                    else if (checkHex.getCounter() != null
                            && checkHex.getCounter().getPlayer() == map.rules.getPlayerTurn()){
                        changeSelectedHex(checkHex);
                    }
                    else{
                    }
                }
                else{

                }
            }
            
            //ATTACK PHASE
            if(phase == Phase.ATTACK){
                if(checkHex.isLeftClicked(gc)){
                    if (checkHex.getCounter() != null
                            && checkHex.getCounter().getPlayer() == map.rules.getPlayerTurn()
                            && map.checkNeighbor(map.targetedHex, checkHex)){
                        if(hexSelection.contains(checkHex)){
                            attackingFP -= checkHex.getCounter().getFP();
                            hexSelection.remove(checkHex);
                            resetCombat();
                        }else{
                            
                            attackingFP += checkHex.getCounter().getFP();
                            hexSelection.add(checkHex);
                            resetCombat();
                        }
                        
                    }
                    
                }else if(checkHex.isRightClicked(gc)){
                    if (checkHex.getCounter() != null
                            && checkHex.getCounter().getPlayer() != map.rules.getPlayerTurn()){
                        changeTargetedHex(checkHex);
                        attackingFP = 0;
                        hexSelection.clear();
                        resetCombat();
                    }
                }
            }
            
            if(phase == Phase.RETREAT){
                if(checkHex.isLeftClicked(gc) ){
                    if(map.selectedHex != null){
                        if(checkRetreatMove(checkHex)){

                            retreatMove(checkHex);
                        }
                    }else{
                        pickAttackRetreat(checkHex);
                    }
                    
                }
                
            }
        }
        
        //Turn sequence
        if(phase == Phase.SETUP){
            
            phase = Phase.MOVE;
        }
        
        else if(phase == Phase.MOVE){
            
            //move P one card
            if(nextPhase.isLeftClicked(gc, xpos, ypos)){ // right click
                
                phase = Phase.ATTACK; 
                map.selectedHex = null;
            }
        }
        
        //attack enemy
        else if(phase == Phase.ATTACK){
            
            for(Hex checkHex : map.map){
                if(checkHex.getCounter() != null
                        && checkHex.getCounter().getPlayer() == playerTurn){
                    checkHex.getCounter().endMove();
                }
            }
            
            
            if(map.targetedHex != null){
                copyHex = new Hex(0,0,0);
                copyHex.setType(map.targetedHex.getType());
                
                polygon = new Polygon();
                for (Point corner : map.layout.polygonCorners(map.layout, map.targetedHex)){
                    polygon.addPoint((float)corner.x, (float)corner.y);
                }
                copyHex.setPolygon(polygon);
                copyHex.getPolygon().setCenterX(map.widthPixel + (int) map.g.x + 160);
                copyHex.getPolygon().setCenterY(280);
            }
            
            //Attack after clicking attack button
            if(attackButton.isLeftClicked(gc, xpos, ypos)){ // right click
                
                combatRoll();
                attackingFP = 0;
                
                resetCombat();
                
                if(retreatPlayer == playerTurn){
                    
                }else{
                    map.selectedHex = map.targetedHex;
                    map.targetedHex = null;
                }
                
                
                phase = Phase.RETREAT;
            }
            
            if(nextPhase.isLeftClicked(gc, xpos, ypos)){ // right click
                attackingFP = 0;
                hexSelection.clear();
                resetCombat();
                phase = Phase.END_TURN; 
                map.targetedHex = null;
            }
            
            
            
        }
        else if(phase == Phase.RETREAT){
            
            if(deathButton.isLeftClicked(gc, xpos, ypos)){ // right click
                
                //VPs for killing an american unit
                if(map.selectedHex != null){
                    if(map.selectedHex.getCounter().getFormation() <= 2){
                        pTwoVP += map.selectedHex.getCounter().getFP();
                    }
                    map.oob.getOOB().remove(map.selectedHex.getCounter());
                    map.selectedHex.setCounter(null);
                    retreatAmount = 0;
                    
                }
                
                
            }
            if(retreatAmount <= 0){
                if(retreatPlayer == playerTurn
                        && !hexSelection.isEmpty()){
                    retreatAmount = 0;
                    map.selectedHex = null;
                }else{
                    retreatPlayer = 0;
                    retreatAmount = 0;
                    hexSelection.clear();
                    map.selectedHex = null;
                    map.targetedHex = null;
                    phase = Phase.ATTACK;
                    //phase = Phase.AAC
                }
                
            }
        }
        
        //end turn
        else if (phase == Phase.END_TURN){
            
            endTurn = true;

            if(playerTurn == 1){
                phase = Phase.MOVE; 
                playerTurn = 2;
            }else{
                
                for(Hex checkHex : map.map){
                    if((mapVP[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col] == 1
                            || mapVP[OffsetCoord.qoffsetFromCube(0, checkHex).row][OffsetCoord.qoffsetFromCube(0, checkHex).col] == 2)
                            && checkHex.getCounter() != null
                            && checkHex.getCounter().getPlayer() == 1){
                        pOneVP++;
                        riverCrossed = true;
                    }
                }
                
                //metz check
                if(map.getHex(7,3).getCounter() != null
                        && map.getHex(7,3).getCounter().getPlayer() == 1
                        && !metzCap){
                    pOneVP += 5;
                    metzCap = true;
                }
                
                //thionville check 
                if(map.getHex(6,-3).getCounter() != null
                        && map.getHex(6,-3).getCounter().getPlayer() == 1
                        && !thionvilleCap){
                    pOneVP += 5;
                    thionvilleCap = true;
                }
                if(!riverCrossed){
                    pTwoVP++;
                }
                
                if(turnNum >= 6){
                    phase = Phase.END_GAME;
                }else{
                    phase = Phase.MOVE; 
                }
                turnNum++;
                
                //reset turn variables
                playerTurn = 1;
                riverCrossed = false;
            }
            
            
            log = "";
        }
        
        else{
            sbc.enterState(0);
        }
    }
    
    public int getPlayerTurn(){
        return playerTurn;
    }
    
    public int getPhase(){
        switch(phase){
            case SETUP:
                return 0;
            case MOVE:
                return 1;
            case ATTACK:
                return 2;
            case RETREAT:
                return 3;
            case END_TURN:
                return 4;
            case END_GAME:
                return 5;
            default:
                return -99;
        }
    }
    
    public void normalMove(Hex cHex){
        map.selectedHex.getCounter().setHex(cHex);
        cHex.setCounter(map.selectedHex.getCounter());

        cHex.getCounter().move();

        map.selectedHex.setCounter(null);
        map.selectedHex = null;
    }
    
    public void roadMove(Hex cHex){
        map.selectedHex.getCounter().setHex(cHex);
        cHex.setCounter(map.selectedHex.getCounter());

        cHex.getCounter().moveOne();

        map.selectedHex.setCounter(null);
        map.selectedHex = null;
    }
    
    public void riverMove(Hex cHex){
        map.selectedHex.getCounter().setHex(cHex);
        cHex.setCounter(map.selectedHex.getCounter());

        cHex.getCounter().moveEmpty();

        map.selectedHex.setCounter(null);
        map.selectedHex = null;
    }
    
    public void ZOCMove(Hex cHex){
        map.selectedHex.getCounter().setHex(cHex);
        cHex.setCounter(map.selectedHex.getCounter());

        cHex.getCounter().moveEmpty();

        map.selectedHex.setCounter(null);
        map.selectedHex = null;
    }
    
    
    
    public boolean checkNormalMove(Hex cHex){
        if(map.selectedHex != null
                && map.selectedHex.getCounter() != null
                && !map.checkRoad(map.selectedHex, cHex)
                && !map.checkRiver(map.selectedHex, cHex)
                && !map.checkEnemy(map.selectedHex, cHex)
                && map.selectedHex.getCounter().getMove() - cHex.getMoveCost() >= 0
                && cHex.getCounter() == null
                && cHex.isNeighbor(map.selectedHex,cHex) >= 0
                && map.selectedHex.getCounter().getMove() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean checkRoadMove(Hex cHex){
        if(map.selectedHex != null
                && map.selectedHex.getCounter() != null
                && map.checkRoad(map.selectedHex, cHex)
                && !map.checkEnemy(map.selectedHex, cHex)
                && map.selectedHex.getCounter().getMove() >= 1
                && cHex.getCounter() == null
                && cHex.isNeighbor(map.selectedHex,cHex) >= 0
                && map.selectedHex.getCounter().getMove() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean checkRiverMove(Hex cHex){
        if(map.selectedHex != null
                && map.selectedHex.getCounter() != null
                && map.checkRiver(map.selectedHex, cHex)
                && !map.checkRoad(map.selectedHex, cHex)
                && map.selectedHex.getCounter().getMove() - cHex.getMoveCost() >= 0
                && cHex.getCounter() == null
                && cHex.isNeighbor(map.selectedHex,cHex) >= 0
                && map.selectedHex.getCounter().getMove() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public boolean checkZOCMove(Hex cHex){
        if(map.selectedHex != null
                && map.selectedHex.getCounter() != null
                && map.checkEnemy(map.selectedHex, cHex)
                && (map.checkRoad(map.selectedHex, cHex)
                || map.selectedHex.getCounter().getMove() - cHex.getMoveCost() >= 0)
                && cHex.getCounter() == null
                && cHex.isNeighbor(map.selectedHex,cHex) >= 0
                && map.selectedHex.getCounter().getMove() > 0){
            return true;
        }else{
            return false;
        }
    }
    
    public void changeSelectedHex(Hex cHex){
        map.prevSelectedHex = map.selectedHex;
        map.selectedHex = cHex;
        if(map.selectedHex == map.prevSelectedHex && map.prevSelectedHex != null){
            map.selectedHex = null;
        }
    }
    
    public void changeTargetedHex(Hex cHex){
        map.prevTargetedHex = map.targetedHex;
        map.targetedHex = cHex;
        if(map.targetedHex == map.prevTargetedHex && map.targetedHex != null){
            map.targetedHex = null;
        }
    }
    
    public void resetCombat(){
        if(hexSelection.isEmpty()){
            combatDifferential = 0;
        }
        else{
            for(Hex checkHex : hexSelection){
                combatDifferential = attackingFP - map.targetedHex.getCounter().getFP();
            }
        }
    }
    
    public void retreatMove(Hex cHex){
        map.selectedHex.getCounter().setHex(cHex);
        cHex.setCounter(map.selectedHex.getCounter());

        map.selectedHex.setCounter(null);
        map.selectedHex = cHex;
        
        retreatAmount--;
    }
    
    public boolean checkRetreatMove(Hex cHex){
        if(map.selectedHex != null
                && map.selectedHex.getCounter() != null
                && !map.checkRiver(map.selectedHex, cHex)
                && map.selectedHex.getCounter().getMove() - cHex.getMoveCost() >= 0
                && cHex.getCounter() == null
                && cHex.isNeighbor(map.selectedHex,cHex) >= 0){
            return true;
        }
        return false;
    }
    
    public void combatRoll(){
        
        int result;
        int diffCol = 0;
        
        diffCol = combatDifferential + 1;
        
        if(combatDifferential < -1){
            diffCol = 0;
        }else{
        
            if(combatDifferential > 2){
                diffCol--;
            }
            if(combatDifferential > 4){
                diffCol--;
            }
            if(combatDifferential > 6){
                diffCol--;
            }
            if(combatDifferential > 8){
                diffCol--;
            }
            if(combatDifferential > 10){
                diffCol = 7;
            }
        }
        
        result = combatTable[rollDie() - 1][diffCol];
                
        switch(result){
            case 0:
                if(playerTurn == 1){
                    retreatPlayer = 1;
                }else{
                    retreatPlayer = 2;
                }
                retreatAmount = 1;
                break;
            
            case 1:
                retreatAmount = 0;
                break;
                
            case 2:
                if(playerTurn == 1){
                    retreatPlayer = 2;
                }else{
                    retreatPlayer = 1;
                }
                retreatAmount = 1;
                break;
                
            case 3:
                if(playerTurn == 1){
                    retreatPlayer = 2;
                }else{
                    retreatPlayer = 1;
                }
                retreatAmount = 2;
                break;
        }
        
        log += "Combat result: " + retreatPlayer + " " + retreatAmount;
    }
    
    public int rollDie(){
        int randomNumOne = rand.nextInt((6 - 1) + 1) + 1;
        
        return randomNumOne;
    }
    
    public void pickAttackRetreat(Hex cHex){
        if(hexSelection.contains(cHex)){
            map.selectedHex = cHex;
            hexSelection.remove(map.selectedHex);
        }
    }
    
    public int noEasing(int time ,int begin, int change, int duration) {
	return change * time / duration + begin;
    }
}
