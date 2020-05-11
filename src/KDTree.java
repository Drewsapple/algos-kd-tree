import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class KDTree {
	//In which direction the Node divides the plot (in which direction we draw the line)
	int countRecursive;
	int countGetDist;				//for both getDistanceSqr and getDistanceSqrToPartition
	int countComparison;
	int countAdd;
	int countBiggerThanNode;
	
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
		countAdd = 0;
		countBiggerThanNode = 0;
        if(this.root == null) {
            root = new KDNode(x,y, Orientation.VERTICAL, new Region());
            return root;
        }
        else {
        	countAdd++;
        	return add(this.root, x, y);
        }
    }
    
	/**
	 * Adds a point with coords x and y to our KDTree and returns the newly created node
	 * @param parentNode
	 * @param x
	 * @param y
	 * @return return the node that was just added
	 */
    public KDNode add(KDNode parentNode, double x, double y) {
		countBiggerThanNode++;
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
			countAdd++;
    		return add(parentNode.bigger, x, y);
    	}
    	else {
			countAdd++;
    		return add(parentNode.smaller,x, y);
    	}
    }
    
    /**
     * Returns the node in our KDTree whose coordinates are closest to coordinates x and y
     * @param x
     * @param y
     * @return
     */
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
	
	/**
	 * Returns the distance squared from the region bounded by root to coordinates x and y
	 * with respect to the roots partitioning orientation
	 * @param root
	 * @param x
	 * @param y
	 * @return
	 */
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

	/**
	 * Returns the distance squared from node to coordinates x and y
	 * @param node
	 * @param x
	 * @param y
	 * @return
	 */
	private double getDistanceSqr(KDNode node,double x,double y) {
		++countGetDist;
		if (node == null) {
			return Double.MAX_VALUE;
		}
    	return (node.pt.x - x) * (node.pt.x - x) + (node.pt.y - y) * (node.pt.y - y);
	}




    
    /**
     * Given a Node in a particular partitioning orientation, and a point's coordinates, determine
     * whether the point should be considered a "bigger" (true) or smaller (false) child with 
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
		if (newValue > existingValue) {return true;}
		else {return false;	}
    }
    
    /**
     * Given an orientation Horizontal or Vertical, flip it so it is the opposite
     * @param o
     * @return
     */
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
