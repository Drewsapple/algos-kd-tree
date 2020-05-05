import java.awt.Point;

public class KDNode{

        Point pt;
        boolean orientation;				//true for vertical, false for horizontal
        Region r;
        
        KDNode smaller;
        KDNode bigger;

        public KDNode(int x, int y, boolean orient, Region region){
        	pt = new Point(x,y);
        	orientation = orient;
        	r = region;
        	smaller = null;
        	bigger = null;
        }
}
