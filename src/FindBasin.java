/*
 * Find Basin Class
 *
 *
 */
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

class FindBasin extends  RecursiveTask<ArrayList<String>> {
    static final int SEQUENTIAL_THRESHOLD = 1250;

    public int lo;
    public int hi;
    public int cols;
    public float[][] DATA_ARRAY;

    FindBasin(float[][] a, int lo, int hi,int cols) {
        this.lo = lo;
        this.hi=hi;
        this.cols = cols;
        DATA_ARRAY = a;
    }

    protected ArrayList<String> compute() {
        if((hi - lo) <= SEQUENTIAL_THRESHOLD){
            ArrayList<String> LIST_INDEXES = new ArrayList<>();
            for(int i=lo; i < hi; ++i){
                for(int j=1; j < cols; ++j){
                    if (i != 0 && j != DATA_ARRAY.length - 1 && i != DATA_ARRAY.length - 1){
                        if ((DATA_ARRAY[i][j] +0.01)<= DATA_ARRAY[i - 1][j - 1] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i - 1][j ] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i-1][j + 1] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i][j - 1] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i][j+1] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i + 1][j - 1] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i+1][j] && (DATA_ARRAY[i][j] +0.01) <= DATA_ARRAY[i + 1][j + 1]) {
                            String indexes = i + " " + j;
                            LIST_INDEXES.add(indexes);
                        }
                    }
                }
            }
            return LIST_INDEXES;
        }
        else {

            FindBasin left = new FindBasin(DATA_ARRAY,lo, (lo+hi)/2,cols);
            FindBasin right = new FindBasin(DATA_ARRAY,(hi + lo) / 2,  hi,cols);
            left.fork();
            ArrayList <String> basins_list = right.compute();
            basins_list.addAll(left.join());
            return basins_list;
        }
    }
}

