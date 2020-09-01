import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;

public class SequentialFindBasin {


    public static float[][] DATA_ARRAY_READ;
    public static int ROWS, COLS;
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
            ROWS = scanner.nextInt();
            COLS = scanner.nextInt();
            DATA_ARRAY_READ = new float[ROWS][COLS];
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    DATA_ARRAY_READ[i][j] = scanner.nextFloat();
                }
            }
 	    scanner.close();
            break;
        }
        ArrayList<String> LIST_INDEXES = new ArrayList<>();
        tick();
        for(int i=0; i < ROWS; ++i){
            for(int j=0; j < COLS; ++j){
                if (i != 0 &&j != 0 && j != DATA_ARRAY_READ.length-1 &&i != DATA_ARRAY_READ.length-1){
                    if ((DATA_ARRAY_READ[i][j] +0.01)<= DATA_ARRAY_READ[i - 1][j - 1] && (DATA_ARRAY_READ[i][j] +0.01)
                            <= DATA_ARRAY_READ[i - 1][j ] && (DATA_ARRAY_READ[i][j] +0.01) <= DATA_ARRAY_READ[i-1][j + 1]
                            && (DATA_ARRAY_READ[i][j] +0.01) <= DATA_ARRAY_READ[i][j - 1] && (DATA_ARRAY_READ[i][j] +0.01)
                            <= DATA_ARRAY_READ[i][j+1] && (DATA_ARRAY_READ[i][j] +0.01) <= DATA_ARRAY_READ[i + 1][j - 1]
                            && (DATA_ARRAY_READ[i][j] +0.01) <= DATA_ARRAY_READ[i+1][j] && (DATA_ARRAY_READ[i][j] +0.01)
                            <= DATA_ARRAY_READ[i + 1][j + 1]) {
                        String indexes = i + " " + j;
                        LIST_INDEXES.add(indexes);
                    }
                }
            }
        }
        float time = tock();
        File file = new File("data/"+outFile);
        FileWriter fr = new FileWriter(file, false);
        fr.write(LIST_INDEXES.size()+"\n");
        for(String line:LIST_INDEXES){
            String l = line+"\n";
            fr.write(l);
        }
        fr.write("Run time: "+ time +" seconds");
        System.out.println(inFile+" run time: "+ time +" seconds");
        fr.close();
    }
}

