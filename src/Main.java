import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class Main
{
    static long startTime = 0;
    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static float tock(){
        return (System.currentTimeMillis() - startTime) / 1000.0f;
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(new BufferedReader(new FileReader("data/med_in.txt")));
        while(sc.hasNextDouble()) {
            int columns = sc.nextInt();
            int rows =sc.nextInt();
            float [][] myArray = new float[rows][columns];
            for (int i=0; i<rows; i++) {
                for (int j=0; j<columns; j++) {
                    myArray[i][j] = sc.nextFloat();
                }
            }
            tick();
            ArrayList<String> f = basinTerrain(myArray);
            System.out.println("Basins No. :"+f.size());
            float time = tock();

            File file = new File("data/med_out_text.txt");
            FileWriter fr = new FileWriter(file, true);
            for(String line: f){
                String l = line+"\n";
                fr.write(l);
            }
            fr.write("Run took "+ time +" seconds");
            fr.close();
            System.out.println(f);


        }

    }
    static  ArrayList<String>  basinTerrain(float[][] array)
    {
        return ForkJoinPool.commonPool().invoke(new FindBasin(array,0,array.length));

    }


}