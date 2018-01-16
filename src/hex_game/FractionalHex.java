/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hex_game;

import java.util.ArrayList;

/**
 *
 * @author mike
 */
class FractionalHex
{
    public FractionalHex(double q, double r, double s)
    {
        this.q = q;
        this.r = r;
        this.s = s;
    }
    public final double q;
    public final double r;
    public final double s;

    static public Hex hexRound(FractionalHex h)
    {
        int q = (int)(Math.round(h.q));
        int r = (int)(Math.round(h.r));
        int s = (int)(Math.round(h.s));
        double q_diff = Math.abs(q - h.q);
        double r_diff = Math.abs(r - h.r);
        double s_diff = Math.abs(s - h.s);
        if (q_diff > r_diff && q_diff > s_diff)
        {
            q = -r - s;
        }
        else
            if (r_diff > s_diff)
            {
                r = -q - s;
            }
            else
            {
                s = -q - r;
            }
        return new Hex(q, r, s);
    }


    static public FractionalHex hexLerp(Hex a, Hex b, double t)
    {
        return new FractionalHex(a.getQ() + (b.getQ() - a.getQ()) * t, a.getR() + (b.getR() - a.getR()) * t, a.getS() + (b.getS() - a.getS()) * t);
    }


    static public ArrayList<Hex> hexLinedraw(Hex a, Hex b)
    {
        int N = Hex.distance(a, b);
        ArrayList<Hex> results = new ArrayList<Hex>(){{}};
        double step = 1.0 / Math.max(N, 1);
        for (int i = 0; i <= N; i++)
        {
            results.add(FractionalHex.hexRound(FractionalHex.hexLerp(a, b, step * i)));
        }
        return results;
    }

}