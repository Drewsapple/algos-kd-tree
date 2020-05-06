import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;

public class kdTreeViewer extends JFrame {
    DrawArea drawArea;
    static int HORIZONTAL_WINDOW_SIZE = 400;
    static int VERTICAL_WINDOW_SIZE = 400;

    public kdTreeViewer(Model m){
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
                m.tree.add(newPoint.x, newPoint.y);
                drawArea.pointClicked(newPoint);
                repaint();
            }
        });
    }

    public static void main(String[] args) {
        Model m = new Model();
        kdTreeViewer k = new kdTreeViewer(m);
        k.setVisible(true);
    }
}
