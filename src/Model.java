import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Contains information about our KDTree model
 * @author Ivan
 *
 */
public class Model {
	
    public KDTree tree;
    public Point2D.Double mousePoint;
    public ArrayList<Point2D.Double> points;

    public Model(){
        tree = new KDTree();
        points = new ArrayList<Point2D.Double>();
    }
}

