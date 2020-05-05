import javax.swing.*;
import java.awt.*;

public class DrawArea extends JPanel {
    Model m;

    public DrawArea(Model m) {
        this.m = m;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPoints(g);
    }

    private void drawPoints(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        for(Point p : m.points) {
            Dimension d = this.getSize();
            g2d.fillRect(p.x-1, p.y-1, 2,2);
        }
    }

    public void pointClicked(Point p) {
        Dimension d = this.getSize();
        m.addPoint(p);
    }
}
