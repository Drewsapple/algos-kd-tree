import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class KDTree {
	//In which direction the Node divides the plot (in which direction we draw the line)

    KDNode root;
    public KDTree(){

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
    	if (this.root == null) {
    		return null;
		} else {
    		return nearestNode(this.root,x,y);
		}
	}

	private KDNode nearestNode(KDNode node, double x,double y) {
		double distance = getDistance(node,x,y);
		KDNode biggerNode = null;
		double biggerDistance = Double.MAX_VALUE;
		KDNode smallerNode = null;
		double smallerDistance = Double.MAX_VALUE;
		if (node.bigger != null) {
			biggerNode = nearestNode(node.bigger,x,y);
			biggerDistance = getDistance(biggerNode,x,y);
			//System.out.println("bigger: " + getDistance(node.bigger,x,y));
		}
		if (node.smaller != null) {
			smallerNode = nearestNode(node.smaller,x,y);
			smallerDistance = getDistance(smallerNode,x,y);
		}
		if (smallerDistance < biggerDistance && smallerDistance < distance) {
			return smallerNode;
		} else if (biggerDistance < distance && biggerDistance < smallerDistance) {
			return biggerNode;
		} else {
			return node;
		}
	}

	private double getDistance(KDNode node,double x,double y) {
    	System.out.println((node.pt.x - x));
		System.out.println((node.pt.y - y));
    	return Math.sqrt((node.pt.x - x) * (node.pt.x - x) + (node.pt.y - y) * (node.pt.y - y));
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
