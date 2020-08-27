import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

class FindBasin extends  RecursiveTask<ArrayList<String>> {
    static final int SEQUENTIAL_THRESHOLD = 100000;

    public int lo;
    public int hi;
    public float[][] arr;

    FindBasin(float[][] a, int l, int h) {
        lo=l;
        hi=h;
        arr = a;
    }

    protected ArrayList<String> compute() {
        if(hi - lo <= SEQUENTIAL_THRESHOLD) {

            ArrayList<String> list = new ArrayList<>();
            for(int i=lo; i < hi; ++i){
                for(int j=lo; j < hi; ++j){
                    if (i != 0 &&j != 0 && j != arr.length-1 &&i != arr.length-1){
                        if ((arr[i][j] +0.01)<= arr[i - 1][j - 1] && (arr[i][j] +0.01) <= arr[i - 1][j ] && (arr[i][j] +0.01) <= arr[i-1][j + 1] && (arr[i][j] +0.01) <= arr[i][j - 1] && (arr[i][j] +0.01) <= arr[i][j+1] && (arr[i][j] +0.01) <= arr[i + 1][j - 1] && (arr[i][j] +0.01) <= arr[i+1][j] && (arr[i][j] +0.01) <= arr[i + 1][j + 1]) {
                            String indexes = i + " and " + j;
                            System.out.println(indexes);
                            list.add(indexes);
                        }
                    }
                }
            }
            return list;
        }
        else {
            int mid = lo + (hi - lo) / 2;
            FindBasin left = new FindBasin(arr,lo,mid);
            FindBasin right = new FindBasin(arr,mid,hi);
            left.fork();
            ArrayList <String> rightAns = right.compute();
            System.out.println(rightAns.size());
            ArrayList <String> leftAns = left.join();
            rightAns.addAll(leftAns);
            return rightAns;
        }
    }
}
