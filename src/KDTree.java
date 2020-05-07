import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class KDTree {
	//In which direction the Node divides the plot (in which direction we draw the line)
	int countRecursive;
	int countGetDist;				//for both getDistanceSqr and getDistanceSqrToPartition
	int countComparison;
	
	
    KDNode root;
    public KDTree(){
    	countGetDist = 0;
    	countComparison = 0;
    	countRecursive = 0;
    }

    /**
     * Adds a point with coords x and y to our KDTree
     * @param x
     * @param y
     * @return return the node that was just added
     */
    public KDNode add(double x, double y) {
        if(this.root == null) {
            root = new KDNode(x,y, Orientation.VERTICAL, new Region());
            return root;
        }
        else {
        	return add(this.root, x, y);
        }
    }
    public KDNode nearestNode(double x,double y) {
    	countRecursive = 0;
    	countComparison = 0;
    	countGetDist = 0;
    	if (this.root == null) {
    		return null;
		} else {
    		return nearestNode(this.root,x,y);
		}
	}

	private KDNode nearestNode(KDNode root, double x, double y) {
		++countRecursive;
		if (root == null) {
			return null;
		}
		
		double rootDistance = getDistanceSqr(root, x, y);
		KDNode closerNode = root.smaller;
		KDNode fartherNode = root.bigger;
		
		if (biggerThanNode(root, x, y)) {
			closerNode = root.bigger;
			fartherNode = root.smaller;
		}
		
		closerNode = nearestNode(closerNode, x, y);
		double closerDistance = getDistanceSqr(closerNode, x, y);
		if (rootDistance <= closerDistance) {
			closerDistance = rootDistance;
			closerNode = root;
		}
		
		if (closerDistance <= getDistanceSqrToPartition(root, x, y)) {
			return closerNode;
		}
		
		fartherNode = nearestNode(fartherNode, x, y);
		if (closerDistance <= getDistanceSqr(fartherNode, x, y)) {
			return closerNode;
		}
		return fartherNode;
	}
	//
	private double getDistanceSqrToPartition(KDNode root, double x, double y) {
		++countGetDist;
		if (root == null) {
			return Double.MAX_VALUE;
		}
		if (root.orientation == Orientation.VERTICAL) {
			return (root.pt.x - x)*(root.pt.x - x);
		}
		return (root.pt.y - y)*(root.pt.y - y);
	}

	private double getDistanceSqr(KDNode node,double x,double y) {
		++countGetDist;
		if (node == null) {
			return Double.MAX_VALUE;
		}
    	//System.out.println((node.pt.x - x));
		//System.out.println((node.pt.y - y));
    	return (node.pt.x - x) * (node.pt.x - x) + (node.pt.y - y) * (node.pt.y - y);
	}



	/**
	 * Adds a point with coords x and y to our KDTree as a child of a specific node
	 * @param parentNode
	 * @param x
	 * @param y
	 * @return return the node that was just added
	 */
    public KDNode add(KDNode parentNode, double x, double y) {
    	boolean bigger = biggerThanNode(parentNode,x,y);
		Region newRegion = parentNode.getSubRegion(bigger);
		KDNode nodeAdded;
    	//base case checks if a new node is to be added
    	//it checks whether the bigger and smaller subTrees are null and checks if the corresponding
    	//coordinate justifies adding the new point as the bigger or smaller subtree

    	if (bigger && parentNode.bigger == null) {
    		nodeAdded = new KDNode(x,y, flipOrientation(parentNode.orientation), newRegion);
    		parentNode.bigger = nodeAdded;
    		return nodeAdded;
    	}
    	else if (!bigger && parentNode.smaller == null) {
    		nodeAdded = new KDNode(x,y, flipOrientation(parentNode.orientation), newRegion);
    		parentNode.smaller = nodeAdded;
    		return nodeAdded;
		}
    	
    	// now to the recursive case:
    	// we just have to see to which on which subtree to call the recursion on 
    	if (bigger) {			
    		return add(parentNode.bigger, x, y);
    	}
    	else {
    		return add(parentNode.smaller,x, y);
    	}
    }
    
    /**
     * Given a Node in a particular orientation, and a points coordinates, determine
     * whether the point should be considered "bigger" (true) or smaller (false) with 
     * respect to the orientation of the node
     */
    public boolean biggerThanNode(KDNode n, double x, double y) {
    	++countComparison;
		double newValue, existingValue;
    	if (n.orientation == Orientation.HORIZONTAL) {
			newValue = y;
			existingValue = n.pt.y;
    	}
    	else {
			newValue = x;
			existingValue = n.pt.x;
    	}

		if (newValue > existingValue) {
			return true;
		}
		else {
			return false;
		}
    }

    public Orientation flipOrientation(Orientation o) {
    	if(o == Orientation.HORIZONTAL){
    		return Orientation.VERTICAL;
		}
    	else {
    		return Orientation.HORIZONTAL;
		}
	}

	public ArrayList<Point2D.Double> points() {
		ArrayList<Point2D.Double> points = new ArrayList<>();
		points = points(root, points);
		return points;
	}

	public ArrayList<Point2D.Double> points(KDNode n, ArrayList<Point2D.Double> points ) {
    	if(n != null) {
			points = points(n.smaller, points);
			points.add(n.pt);
			points = points(n.bigger, points);
		}
		return points;
	}

	public ArrayList<Line2D.Double> lines() {
		ArrayList<Line2D.Double> lines = new ArrayList<Line2D.Double>();
		lines = lines(root, lines);
		return lines;
	}

	public ArrayList<Line2D.Double> lines(KDNode n, ArrayList<Line2D.Double> lines ) {
		if(n != null) {
			lines = lines(n.smaller, lines);
			if(n.orientation == Orientation.VERTICAL)
				lines.add(new Line2D.Double(n.pt.x, n.r.min.y, n.pt.x, n.r.max.y));
			else // if horizontal
				lines.add(new Line2D.Double(n.r.min.x, n.pt.y, n.r.max.x, n.pt.y));
			lines = lines(n.bigger, lines);
		}
		return lines;
	}
}
