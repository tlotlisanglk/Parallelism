
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
    static float [][] DATA_ARRAY_READ = null;;
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
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("data/"+inFile)));
        while(scanner.hasNextLine()) {
            int rows = scanner.nextInt();
            int cols = scanner.nextInt();
            DATA_ARRAY_READ = new float[rows][cols];
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    DATA_ARRAY_READ[i][j] = scanner.nextFloat();
                }
            }
            scanner.close();
            break;
        }
        ArrayList<Float> timeArray = new ArrayList<>();
        ArrayList<String> f = new ArrayList<>();
        //Running the program 20 times.
        for(int r = 0 ; r<20 ;r++)
        {
            if(r!=19) {
                tick();
                ArrayList<String> ff = basinTerrain(DATA_ARRAY_READ);
                float time = tock();
                System.out.println("Runtime" + r + " takes: " + time + " seconds");
                timeArray.add(time);
            }
            else{
                tick();
                f = basinTerrain(DATA_ARRAY_READ);
                float time = tock();
                System.out.println("Runtime" + r + " takes: " + time + " seconds");
                timeArray.add(time);
            }

        }
        float total_time = 0;
        total_time = timeArray.stream().reduce(total_time, Float::sum);
        System.out.println("Average of all runtimes is: " +total_time/20);
        File file = new File("data/"+outFile);
        FileWriter fr = new FileWriter(file, false);
        fr.write(f.size()+"\n");
        for(String line: f){
            String l = line+"\n";
            fr.write(l);
        }
        fr.write("Average run time: "+ total_time/20 +" seconds");
        fr.close();

    }
    static  ArrayList<String>  basinTerrain(float[][] array)
    {
        return FJPOOL01.invoke(new FindBasin(array,0,array.length,array[0].length));
    }
}
