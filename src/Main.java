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

        // pass the path to the file as a parameter
        tick();
        File file = new File("data/large_in.txt");
        Scanner sc = new Scanner(file);
        String[] size = sc.nextLine().split(" ");
        String[] data =  sc.nextLine().split(" ");
        int rows = Integer.parseInt(size[0]);
        int cols = Integer.parseInt(size[1]);
        float[][] array = new float[rows][cols];
        File filee = new File("data/med_check.txt");
        FileWriter fr = new FileWriter(filee, true);

        for(int i=0; i< rows ; i++) {
            for(int j=0 ; j<cols;j++) {
                array[i][j] = Float.parseFloat(data[i+j]);
            }
        }
        fr.close();

        ArrayList<String> f = findBasin(array);
        System.out.println("Basins No. :"+data.length);
        float time = tock();

        File fileex = new File("data/med_out_text.txt");
        FileWriter frr = new FileWriter(fileex, true);
        for(String line: f){
            String l = line+"\n";
            frr.write(l);
        }
        frr.write("Run took "+ time +" seconds");
        frr.close();
        System.out.println(f);
    }
    static  ArrayList<String>  findBasin(float[][] array)
    {
        return ForkJoinPool.commonPool().invoke(new FindBasin(array,0,array.length));

    }


}