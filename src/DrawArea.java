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
        for(Point p : m.points) {
            Dimension d = this.getSize();

            int x = (int) (d.width * p.x);
            int y = (int) (d.height * p.y);
            g2d.drawLine(x, y, x, y);
        }
    }

    public void pointClicked(Point p) {
        Dimension d = this.getSize();
        m.addPoint(p);
    }
}
