import java.awt.geom.Point2D;

public class Region{
        public Point2D.Double min, max;
        public Region(Point2D.Double min, Point2D.Double max){
            this.min = min;
            this.max = max;
        }

        public Region(double xmin, double ymin, double xmax, double ymax){
            this(new Point2D.Double(xmin, ymin), new Point2D.Double(xmax, ymax));
        }
        
        /**
         * This constructor is used to create the first region, its size is the whole drawing area
         * Not sure if this is exactly what you want to be using when not using a drawing area, 
         * since then we can use a much wider variety of values (something to think about)
         */
        public Region() {
            this(new Point2D.Double(0, 0), new Point2D.Double(kdTreeViewer.HORIZONTAL_WINDOW_SIZE, kdTreeViewer.VERTICAL_WINDOW_SIZE));
        }
        
    }