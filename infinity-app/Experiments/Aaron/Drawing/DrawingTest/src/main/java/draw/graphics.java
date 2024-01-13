package draw;

import java.awt.BorderLayout; // lays out a container to fit regions "North, South, East, West, Center" 
import java.awt.Graphics; // abstract base class for all the graphics, allows applications to draw onto components "basic rendering in java"
import java.awt.Point; // represents the location "X and Y" on the coordinate plane space. This is specified in integer
import java.awt.event.MouseAdapter; // allows to see for mouse events "Dragging mouse on canvas" this is for convenience
import java.awt.event.MouseEvent; // indicates that a mouse action occurs in a particular component. Only works if mouse cursor component is in a unobscured bounds when the action happens
import java.awt.event.MouseMotionAdapter; // receives mouse motion events

import javax.swing.JFrame; // extended version of java.awt. adds support to the swing components 
import javax.swing.JPanel; // general "lightweight" container

@SuppressWarnings("serial")
public class graphics extends JPanel {

	private Point lastPoint;

	public graphics() {
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				lastPoint = new Point(e.getX(), e.getY());
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				Graphics g = getGraphics();
				g.drawLine(lastPoint.x, lastPoint.y, e.getX(), e.getY());
				g.dispose();
			}
		});
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame("Drawing Canvas");
		frame.getContentPane().add(new graphics(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 250);
		frame.setVisible(true);

	}

}
