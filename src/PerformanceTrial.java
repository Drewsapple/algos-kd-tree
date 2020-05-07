
public class PerformanceTrial {
	
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
		
		final long numRuns = 100;
		final long numQueries = 1000;
		
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
	
	private static KDNode bruteForceHelper(KDNode root, double x, double y) {
		if (root == null) {
			return null;
		}
		KDNode closest = root;
		double distance = distSquared(root.pt.x, root.pt.y, x, y);
		
		KDNode big = bruteForceHelper(root.bigger, x, y);
		if (big != null) {
			double bigDistance = distSquared(big.pt.x, big.pt.y, x, y);
			if (bigDistance < distance) {
				closest = big;
				distance = bigDistance;
			}
		}
		
		KDNode small = bruteForceHelper(root.smaller, x, y);
		if (small != null) {
			double smallDistance = distSquared(small.pt.x, small.pt.y, x, y);
			if (smallDistance < distance) {
				closest = small;
			}
		}
		
		return closest;
	}
	
	public static KDNode bruteForceQuery(KDTree tree, double x, double y) {
		return bruteForceHelper(tree.root, x, y);
	}
	
	public static long timeRandomBruteQuery(KDTree tree) {
		long start = System.nanoTime();
		double x = Math.random() * kdTreeViewer.HORIZONTAL_WINDOW_SIZE;
		double y = Math.random() * kdTreeViewer.VERTICAL_WINDOW_SIZE;
		bruteForceQuery(tree, x, y);
		long end = System.nanoTime();
		return end - start;
	}
	
	public static void bruteForceTrial() {
		System.out.println("Brute Force Trial -- average query time");
		System.out.printf("%6s\t%12s\n", "N", "time (ms)");
		
		final long numRuns = 10;
		final long numQueries = 100;
		
		for (int n = 32; n <= 262144; n *= 2) {
			long totalTimeNano = 0;
			for (int run = 0; run < numRuns; run++) {
				KDTree tree = randomKDTree(n);
				for (int query = 0; query < numQueries; query++) {
					totalTimeNano += timeRandomBruteQuery(tree);
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
