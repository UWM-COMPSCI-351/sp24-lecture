import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Consumer;

import edu.uwm.cs351.Bubblesort;
import edu.uwm.cs351.Quicksort;

public class CompareSort {
	private static final int TIMES = 100;
	
	public long run(Random r, int power, Consumer<Integer[]> action) {
		int n = 1 << power;
		Integer[][] arrays = new Integer[TIMES][n];
		for (int i = 0; i < TIMES; ++i) {
			for (int j=0; j < n; ++j) {
				arrays[i][j] = r.nextInt();
			}
		}
		long start = System.nanoTime();
		for (int i=0; i < TIMES; ++i) {
			action.accept(arrays[i]);
		}
		long stop = System.nanoTime();
		System.gc();
		return (stop-start)/TIMES;
	}
	
	public void compare(Random r, int power) {
		Quicksort<Integer> qs = new Quicksort<Integer>(Comparator.naturalOrder());
		Bubblesort<Integer> bs = new Bubblesort<Integer>(Comparator.naturalOrder());
		long l1 = run(r, power, (a) -> bs.sort(a));
		long l2 = run(r, power, (a) -> qs.sort(a));
		long l3 = run(r, power, (a) -> Arrays.sort(a));
		System.out.printf("%2d: %10.2f %10.2f %10.2f\n", power, l1/1000.0, l2/1000.0, l3/1000.0);
	}
	
	public static void main(String[] args) {
		Random r = new Random();
		CompareSort cs = new CompareSort();
		System.out.println("2^N BubbleSort  QuickSort LibrarySort");
		for (int p = 10; p < 20; ++p) {
			cs.compare(r,  p);;
		}
	}
}
