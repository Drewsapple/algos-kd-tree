import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class DrawArea extends JPanel {
    Model m;

    public DrawArea(Model m) {
        this.m = m;
    }

   @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPoints(g);
        drawLines(g);
    }

    private void drawPoints(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        for(Point2D.Double p : m.tree.points()) {
            Dimension d = this.getSize();
            g2d.fillRect((int)p.x-1, (int)p.y-1, 3,3);
        }
    }
    
    private void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        ArrayList<Line2D.Double> lines = m.tree.lines();
        for(Line2D.Double l :lines) {
            g2d.drawLine((int)l.x1, (int)l.y1, (int)l.x2, (int)l.y2);
        }
    }

    public void pointClicked(Point p) {
        Dimension d = this.getSize();
        m.addPoint(p);
    }
}
