
public class PerformanceTrial {
	
	static final long numRuns = 100;
	static final long numQueries = 1000;
	
	public static KDTree randomKDTree(int n) {
		KDTree tree = new KDTree();
		for (int i = 0; i < n; i++) {
			double x = Math.random() * kdTreeViewer.HORIZONTAL_WINDOW_SIZE;
			double y = Math.random() * kdTreeViewer.VERTICAL_WINDOW_SIZE;
			tree.add(x, y);
		}
		return tree;
	}
	
	public static long timeRandomKDQuery(KDTree tree) {
		long start = System.nanoTime();
		double x = Math.random() * kdTreeViewer.HORIZONTAL_WINDOW_SIZE;
		double y = Math.random() * kdTreeViewer.VERTICAL_WINDOW_SIZE;
		tree.nearestNode(x, y);
		long end = System.nanoTime();
		return end - start;
	}
	
	public static void kdTreeTrial() {
		System.out.println("KD Tree Trial -- average query time");
		System.out.printf("%6s\t%12s\n", "N", "time (ms)");
		
		for (int n = 32; n <= 262144; n *= 2) {
			long totalTimeNano = 0;
			for (int run = 0; run < numRuns; run++) {
				KDTree tree = randomKDTree(n);
				for (int query = 0; query < numQueries; query++) {
					totalTimeNano += timeRandomKDQuery(tree);
				}
			}
			double averageTimeMillis = (double) totalTimeNano / (numRuns * numQueries * 1000000);
			System.out.printf("%6d\t%12f\n", n, averageTimeMillis);
		}
		
		System.out.println();
	}
	
	private static double distSquared(double x1, double y1, double x2, double y2) {
		return (x1 - x2)*(x1 - x2) + (y1 - y2)*(y1 - y2);
	}
	
	public static double[][] randomPoints(int n){
		double[][] points = new double[n][2];
		for (int i = 0; i < n; i++) {
			points[i][0] = Math.random() * kdTreeViewer.HORIZONTAL_WINDOW_SIZE;
			points[i][1] = Math.random() * kdTreeViewer.VERTICAL_WINDOW_SIZE;
		}
		return points;
	}
	
	public static double[] bruteForceQuery(double[][] points, double x, double y) {
		double[] nearest = new double[2];
		double minDist = Double.MAX_VALUE;
		for (int i = 0; i < points.length; i++) {
			double dist = distSquared(points[i][0], points[i][1], x, y);
			if (dist < minDist) {
				minDist = dist;
				nearest[0] = points[i][0];
				nearest[1] = points[i][1];
			}
		}
		return nearest;
	}
	
	public static long timeRandomBruteQuery(double[][] points) {
		long start = System.nanoTime();
		double x = Math.random() * kdTreeViewer.HORIZONTAL_WINDOW_SIZE;
		double y = Math.random() * kdTreeViewer.VERTICAL_WINDOW_SIZE;
		bruteForceQuery(points, x, y);
		long end = System.nanoTime();
		return end - start;
	}
	
	public static void bruteForceTrial() {
		System.out.println("Brute Force Trial -- average query time");
		System.out.printf("%6s\t%12s\n", "N", "time (ms)");
		
		for (int n = 32; n <= 262144; n *= 2) {
			long totalTimeNano = 0;
			for (int run = 0; run < numRuns; run++) {
				double[][] points = randomPoints(n);
				for (int query = 0; query < numQueries; query++) {
					totalTimeNano += timeRandomBruteQuery(points);
				}
			}
			double averageTimeMillis = (double) totalTimeNano / (numRuns * numQueries * 1000000);
			System.out.printf("%6d\t%12f\n", n, averageTimeMillis);
		}
		
		System.out.println();
	}

	public static void main(String[] args) {
		kdTreeTrial();
		bruteForceTrial();
	}

}
