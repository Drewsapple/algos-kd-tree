import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Model {
	
    public KDTree tree;
    public ArrayList<Point> points;

    public Model(){
        tree = new KDTree();
        points = new ArrayList<Point>();
    }

    public boolean addPoint(Point p){
        if(points.contains(p)) {
            return false;
        }
        else {
            points.add(p);
            return true;
        }
    }
}

