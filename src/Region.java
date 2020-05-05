import java.awt.Point;

public class Region{
        public Point min, max;
        public Region(Point min, Point max){
            this.min = min;
            this.max = max;
        }
        public Region(int xmin, int ymin, int xmax, int ymax){
            this(new Point(xmin, ymin), new Point(xmax, ymax));
        }
        
        /**
         * This constructor is used to create the first region, its size is the whole drawing area
         * Not sure if this is exactly what you want to be using when not using a drawing area, 
         * since then we can use a much wider variety of values (something to think about)
         */
        public Region() {
            this(new Point(0, 0), new Point(kdTreeViewer.HORIZONTAL_WINDOW_SIZE, kdTreeViewer.VERTICAL_WINDOW_SIZE));
        }
        
    }