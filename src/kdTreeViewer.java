import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class kdTreeViewer extends JFrame {
    DrawArea drawArea;
    static int HORIZONTAL_WINDOW_SIZE = 400;
    static int VERTICAL_WINDOW_SIZE = 400;

    public kdTreeViewer(Model m, KDTree t){
        drawArea = new DrawArea(m);
        add(drawArea);
        setSize(HORIZONTAL_WINDOW_SIZE,VERTICAL_WINDOW_SIZE);
        setResizable(false);
        setTitle("KD Tree Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Point newPoint = e.getPoint();
                KDNode justAdded = t.add(newPoint.x, newPoint.y);
                Line2D.Double line;
                if(justAdded.orientation) {
                	 line = new Line2D.Double(justAdded.pt.x, justAdded.pt.x, justAdded.r.min.y, justAdded.r.max.y);
                }
                else {
                	 line = new Line2D.Double(justAdded.r.min.x, justAdded.r.max.x, justAdded.pt.y, justAdded.pt.y);
                }
                drawArea.pointClicked(newPoint);
                drawArea.lineAdded(line);
                repaint();
            }
        });
    }

    public static void main(String[] args) {
    	KDTree tree = new KDTree();
        Model m = new Model();
        kdTreeViewer k = new kdTreeViewer(m, tree);
        k.setVisible(true);
    }
}
