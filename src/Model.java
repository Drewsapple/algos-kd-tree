import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Model {
	
    public KDNode root;
    public ArrayList<Point> points;
    public ArrayList<Line2D.Double> lines;

    public Model(){
        points = new ArrayList<Point>();
        lines = new ArrayList<Line2D.Double> ();
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
    
    public boolean addLine(Line2D.Double l) {
    	if(lines.contains(l)) {
            return false;
        }
        else {
            lines.add(l);
            return true;
        }
    }
}

