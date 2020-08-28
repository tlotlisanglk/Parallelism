
/*
 * Find Basin Class
 *
 *
 */
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

class FindBasin extends RecursiveTask<ArrayList<String>> {
    static final int SEQUENTIAL_THRESHOLD = 700;

    public int ROW_LOW;
    public int ROW_HIGH;
    public int COL_HIGH;
    public int COL_LOW;
    public float[][] DATA_ARRAY;

    FindBasin(float[][] a, int ROW_LOW, int ROW_HIGH, int COL_LOW, int COL_HIGH) {
        this.ROW_LOW = ROW_LOW;
        this.ROW_HIGH = ROW_HIGH;
        this.COL_LOW = COL_LOW;
        this.COL_HIGH = COL_HIGH;
        DATA_ARRAY = a;
    }

    protected ArrayList<String> compute() {
        if ((COL_HIGH - COL_LOW <= SEQUENTIAL_THRESHOLD) && (ROW_HIGH - ROW_LOW <= SEQUENTIAL_THRESHOLD)) {
            int ans = 0;
            ArrayList<String> list_of_indexes = new ArrayList<>();
            for (int i = ROW_LOW; i < ROW_HIGH; ++i) {
                for (int j = COL_LOW; j < COL_HIGH; ++j) {
                    if (i != 0 && j != 0 && j != DATA_ARRAY.length - 1 && i != DATA_ARRAY.length - 1) {
                        if ((DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i - 1][j - 1]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i - 1][j]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i - 1][j + 1]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i][j - 1]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i][j + 1]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i + 1][j - 1]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i + 1][j]
                                && (DATA_ARRAY[i][j] + 0.01) <= DATA_ARRAY[i + 1][j + 1]) {
                            String indexes = i + " " + j;
                            list_of_indexes.add(indexes);
                        }
                    }
                }
            }
            return list_of_indexes;
        } else {

            FindBasin left_one = new FindBasin(DATA_ARRAY, ROW_LOW, (ROW_LOW + ROW_HIGH) / 2, COL_LOW,
                    (COL_LOW + COL_HIGH) / 2);
            FindBasin right_one = new FindBasin(DATA_ARRAY, ROW_LOW, (ROW_LOW + ROW_HIGH) / 2, (COL_LOW + COL_HIGH) / 2,
                    COL_HIGH);
            FindBasin left_two = new FindBasin(DATA_ARRAY, (ROW_LOW + ROW_HIGH) / 2, ROW_HIGH, COL_LOW,
                    (COL_LOW + COL_HIGH) / 2);
            FindBasin right_two = new FindBasin(DATA_ARRAY, (ROW_LOW + ROW_HIGH) / 2, ROW_HIGH,
                    (COL_LOW + COL_HIGH) / 2, COL_HIGH);

            left_one.fork();
            right_one.fork();
            left_two.fork();
            ArrayList<String> basins_list = right_two.compute();

            basins_list.addAll(left_one.join());
            basins_list.addAll(right_one.join());
            basins_list.addAll(left_two.join());

            return basins_list;
        }
    }
}
