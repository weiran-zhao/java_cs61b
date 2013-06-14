// test code for lab5 
// Written by Ryan (Weiran) Zhao 
// Thu,Jun 13th 2013 02:45:10 PM EDT
import java.io.*;

public class test {
    public static void main(String[] argv) {
        // for part I
        X[] xa= new X[10];
        Y[] ya= new Y[10];
        for(int i=0;i<10;i++)
        {
            xa[i]=new X();
            ya[i]=new Y();
        }
        xa[0] = ya[0];
        System.out.println(xa[0].x);
        //System.out.println(ya[0].y);
    }
}
