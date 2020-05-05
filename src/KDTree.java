import java.awt.*;

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
    public KDNode add(int x, int y) {
        if(this.root == null) {
            root = new KDNode(x,y,true , new Region());
            return root;}
        else {
        	return add(this.root, x, y);
        }
    }
    
    public KDNode add(KDNode parentNode, int x, int y) {
    	boolean bigger = biggerThanNode(parentNode,x,y);
    	
    	//base case checks if a new node is to be added
    	//it checks whether the bigger and smaller subTrees are null and checks if the corresponding
    	//coordinate justifies adding the new point as the bigger or smaller subtree
    	if (bigger && parentNode.bigger == null) {
    		Region newRegion;
    		if (parentNode.orientation) {			//if orentation of parent was vertical
    			//only xmin changes
    			newRegion = new Region(parentNode.pt.x,parentNode.r.min.y,
    									parentNode.r.max.x,parentNode.r.max.y);
    		}
    		else {									//else if orientation of parent is horizontal
    			//only ymin changes
    			newRegion = new Region(parentNode.r.min.x,parentNode.pt.y,
						parentNode.r.max.x,parentNode.r.max.y);
    		}
    		KDNode nodeAdded = new KDNode(x,y, !parentNode.orientation, newRegion);
    		parentNode.bigger = nodeAdded;
    		return nodeAdded;
    	}
    	else if (!bigger && parentNode.smaller == null) {
    		Region newRegion;
    		if (parentNode.orientation) {			//if orentation of parent was vertical
    			//only xmax changes
    			newRegion = new Region(parentNode.r.min.x,parentNode.r.min.y,
    									parentNode.pt.x,parentNode.r.max.y);
    		}
    		else {									//else if orientation of parent is horizontal
    			//only ymax changes
    			newRegion = new Region(parentNode.r.min.x,parentNode.r.min.y,
    									parentNode.r.max.x,parentNode.pt.y);
    		}
    		KDNode nodeAdded = new KDNode(x,y, !parentNode.orientation, newRegion);
    		parentNode.smaller = nodeAdded;
    		return nodeAdded;
    		}		// that was the base case in which we actually add a new node
    	
    	// now to the recursive case:
    	// we just have to see to which on which subtree to call the recursion on 
    	if (bigger) {			
    		return(add(parentNode.bigger, x, y));

    	}
    	else {
    		return(add(parentNode.smaller,x, y));
    	}
    }
    
    /**
     * Given a Node in a particular orientation, and a points coordinates, determine
     * whether the point should be considered "bigger" (true) or smaller (false) with 
     * respect to the orientation of the node
     */
    public boolean biggerThanNode(KDNode n, int x, int y) {
    	if (!n.orientation) {							//if the orientation is horizontal
    		if (y > n.pt.y) {return true;}				//return bigger aka true
    	}
    	else {											//otherwise the orientation is vertical
    		if (x > n.pt.x) {return true;}				//return bigger aka false
    	}					
    	return false; 									//return smaller aka false
    }

}
