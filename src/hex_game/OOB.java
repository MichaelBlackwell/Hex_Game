/*
Battalion Level Counters
1,5,7,11 Marine Rgt.
1-1
1 
 */
package hex_game;

import java.util.ArrayList;

/**
 *
 * @author mike
 */
public class OOB {
    
    Map map;
    
    private ArrayList<Counter> OOB;
    private Counter counter;
    
    public OOB(Map m){
        map = m;
        counter = null;
        OOB = new ArrayList<Counter>();

        counter = new Counter("2 ", 1, 0, 0, 5, 4, map.getHex(0,5));
        map.getHex(0,5).setCounter(counter);
        OOB.add(counter);
    
        counter = new Counter("10", 1, 0, 0, 5, 4, map.getHex(0,8));
        OOB.add(counter);
        map.getHex(0,8).setCounter(counter);

        counter = new Counter("11", 1, 0, 0, 5, 4, map.getHex(0,10));
        OOB.add(counter);
        map.getHex(0,10).setCounter(counter);

        counter = new Counter("CCA", 1, 1, 1, 7, 10, map.getHex(0,6));
        OOB.add(counter);
        map.getHex(0,6).setCounter(counter);

        counter = new Counter("CCB", 1, 1, 1, 7, 10, map.getHex(0,7));
        OOB.add(counter);
        map.getHex(0,7).setCounter(counter);

        counter = new Counter("357", 1, 0, 2, 4, 4, map.getHex(0,2));
        OOB.add(counter);
        map.getHex(0,2).setCounter(counter);

        counter = new Counter("358", 1, 0, 2, 4, 4, map.getHex(0,3));
        OOB.add(counter);
        map.getHex(0,3).setCounter(counter);

        counter = new Counter("CCR", 1, 1, 1, 5, 10, map.getHex(0,9));
        OOB.add(counter);
        map.getHex(0,9).setCounter(counter);
  
        //pTwo OOB
        //pTwoOOB.add(new Counter("106", 1, 3, 1, 8, map.get(10)));
        
        counter = new Counter("Gar", 2, 0, 4, 1, 1, map.getHex(7,3));
        OOB.add(counter);
        map.getHex(7,3).setCounter(counter);

        counter = new Counter("Fhnj", 2, 0, 5, 3, 4, map.getHex(4,6));
        OOB.add(counter);
        map.getHex(4,6).setCounter(counter);

        counter = new Counter("1125", 2, 0, 6, 1, 4, map.getHex(3,-1));
        OOB.add(counter);
        map.getHex(3,-1).setCounter(counter);

        counter = new Counter("1126", 2, 0, 6, 1, 4, map.getHex(3,1));
        OOB.add(counter);
        map.getHex(3,1).setCounter(counter);

        counter = new Counter("1010", 2, 0, 7, 1, 4, map.getHex(4,4));
        OOB.add(counter);
        map.getHex(4,4).setCounter(counter);

        counter = new Counter("Utrf", 2, 0, 7, 2, 4, map.getHex(4,2));
        OOB.add(counter);
        map.getHex(4,2).setCounter(counter);

        counter = new Counter("8", 2, 1, 8, 2, 8, map.getHex(5,6));
        OOB.add(counter);
        map.getHex(5,6).setCounter(counter);
        
        counter = new Counter("29", 2, 1, 8, 2, 8, map.getHex(5,8));
        OOB.add(counter);
        map.getHex(5,8).setCounter(counter);
        
        counter = new Counter("37", 2, 1, 9, 3, 8, map.getHex(7,4));
        OOB.add(counter);
        map.getHex(7,4).setCounter(counter);
        
        counter = new Counter("38", 2, 1, 9, 2, 8, map.getHex(6,5));
        OOB.add(counter);
        map.getHex(6,5).setCounter(counter);
        
    }
    
    public ArrayList<Counter> getOOB(){
        return OOB;
    }
}
