import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class SortTester {

    public static void main(String[] args) {
        runSortTester();
    }

    public static void runSortTester() {
        int LENGTH, // length of array to sort
        	RUNS = 16,
        	THREADS = Runtime.getRuntime().availableProcessors();


        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer d1, Integer d2) {
                return d1.compareTo(d2);
            }
        };

        for(int i = 1; i <= THREADS; i*=2){
        	LENGTH = 1000;
            System.out.printf("Threads: %d\n", i);
            
        	for(int j = 0; j < RUNS; j++){
                int[] a = createRandomArray(LENGTH);
                long startTime = System.currentTimeMillis();
                parallelMergeSort.parallelmergeSort(a, i);
                long endTime = System.currentTimeMillis();

                if (!isSorted(a, comp)) {
                    throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
                }
                
                System.out.printf("%10d elements  =>  %6d ms \n", LENGTH, endTime - startTime);
            	LENGTH *= 2;
        	}
        }
    }

    /**
     * Returns true if the given array is in sorted ascending order.
     *
     * @param a the array to examine
     * @param comp the comparator to compare array elements
     * @return true if the given array is sorted, false otherwise
     */
    public static <E> boolean isSorted(int[] a, Comparator<Integer> comp) {
        for (int i = 0; i < a.length - 1; i++) {
            if (comp.compare(a[i], a[i + 1]) > 0) {
                System.out.println(a[i] + " > " + a[i + 1]);
                return false;
            }
        }
        return true;
    }

    // Randomly rearranges the elements of the given array.
    public static <E> void shuffle(E[] a) {
        for (int i = 0; i < a.length; i++) {
            // move element i to a random index in [i .. length-1]
            int randomIndex = (int) (Math.random() * a.length - i);
            swap(a, i, i + randomIndex);
        }
    }

    // Swaps the values at the two given indexes in the given array.
    public static final <E> void swap(E[] a, int i, int j) {
        if (i != j) {
            E temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    // Creates an array of the given length, fills it with random
    // non-negative integers, and returns it.
    public static int[] createRandomArray(int length) {
        int[] a = new int[length];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(1000000);
        }
        return a;
    }
}