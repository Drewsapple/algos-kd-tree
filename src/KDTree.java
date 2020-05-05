import java.awt.*;

public class KDTree {
    public enum Orientation{
        HORIZONTAL,
        VERTICAL
    }

    public class KDNode{
        public class Region{
            public Point min, max;
            public Region(Point min, Point max){
                this.min = min;
                this.max = max;
            }
            public Region(int xmin, int ymin, int xmax, int ymax){
                this(new Point(xmin, ymin), new Point(xmax, ymax));
            }
        }


        Point pt;
        Orientation orient;
        Region r;

        public KDNode(){

        }

    }

    KDNode root;
    public KDTree(){

    }

    public boolean add(double x, double y) {
        if(this.root == null) {
            root = new KDNode();
        }
        return false;
    }

}
