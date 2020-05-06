import java.awt.Point;
import java.awt.geom.Point2D;

public class KDNode{

    Point2D.Double pt;
    Orientation orientation;				//true for vertical, false for horizontal
    Region r;

    KDNode smaller;
    KDNode bigger;

    public KDNode(double x, double y, Orientation orient, Region region){
        pt = new Point2D.Double(x, y);
        orientation = orient;
        r = region;
        smaller = null;
        bigger = null;
    }

    public Region getSubRegion(boolean bigger) {
        Region newRegion;
        switch (orientation) {
            case HORIZONTAL:
                if (bigger)
                    newRegion = new Region(r.min.x, pt.y, r.max.x, r.max.y);
                else
                    newRegion = new Region(r.min.x, r.min.y, r.max.x, pt.y);
                break;
            case VERTICAL:
                if(bigger)
                    newRegion = new Region(pt.x, r.min.y, r.max.x, r.max.y);
                else
                    newRegion = new Region(r.min.x, r.min.y, pt.x, r.max.y);
                break;
            default:
                // this should never happen unless new values are added to the orientation enum. However, this is
                // required to stop a compile error that protects newRegion from being uninitialized in that case
                newRegion = new Region(r.min.x, r.min.y, r.max.x, r.max.y); // Just returns the same region
        }
        return newRegion;
    }
}
