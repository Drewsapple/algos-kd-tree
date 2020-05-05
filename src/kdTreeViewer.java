import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class kdTreeViewer extends JFrame {
    DrawArea drawArea;

    public kdTreeViewer(Model m){
        drawArea = new DrawArea(m);
        add(drawArea);
        setSize(400,400);
        setResizable(false);
        setTitle("KD Tree Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        drawArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                drawArea.pointClicked(e.getPoint());
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
