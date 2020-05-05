import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

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
        for(Point p : m.points) {
            Dimension d = this.getSize();
            g2d.fillRect(p.x-1, p.y-1, 2,2);
        }
    }
    
    private void drawLines(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        for(Line2D.Double l : m.lines) {
            Dimension d = this.getSize();
            g2d.drawLine((int)l.x1,(int) l.x2, (int)l.y1,(int) l.y2);
        }
    }

    public void pointClicked(Point p) {
        Dimension d = this.getSize();
        m.addPoint(p);
    }
    
    public void lineAdded(Line2D.Double l) {
    	m.addLine(l);
    }
}
