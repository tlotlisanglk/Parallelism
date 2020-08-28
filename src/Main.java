
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main
{

    static final ForkJoinPool FJPOOL01 = new ForkJoinPool();
    static long startTime = 0;
    /**
     * start time method: when the program starts running
     */
    private static void tick(){

        startTime = System.currentTimeMillis();
    }

    /**
     * @ RETURN the running time of the program
     */
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }

    /**
     * Reads the args[0] input file and sends the data to the FindBasin class which
     * returns an arrayList of the basins found which are then written to
     * args[1] file
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String inFile = args[0], outFile = args[1];
        Scanner sc = new Scanner(new BufferedReader(new FileReader("data/"+inFile)));
        while(sc.hasNextDouble()) {
            int cols = sc.nextInt();
            int rows =sc.nextInt();
            float [][] myArray = new float[rows][cols];
            for (int i=0; i<rows; i++) {
                for (int j=0; j<cols; j++) {
                    myArray[i][j] = sc.nextFloat();
                }
            }
            tick();
            ArrayList<String> f = basinTerrain(myArray);
            float time = tock();
            File file = new File("data/"+outFile);
            FileWriter fr = new FileWriter(file, false);
            fr.write(f.size()+"\n");
            for(String line: f){
                String l = line+"\n";
                fr.write(l);
            }
            fr.write("Run time: "+ time +" seconds");
            fr.close();
        }
    }
    static  ArrayList<String>  basinTerrain(float[][] array)
    {
        return FJPOOL01.invoke(new FindBasin(array,0,array.length,0,array[0].length));
    }
}