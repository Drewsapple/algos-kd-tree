import java.awt.*;
import java.util.ArrayList;

public class Model {
    public KDTree.KDNode root;
    public ArrayList<Point> points;

    public Model(){
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
