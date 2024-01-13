package paint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * component for drawing
 * 
 * @author ascase
 *
 */

public class DrawArea extends JComponent {

	// Image in which we're going to draw
	private Image image;
	// Graphic2D object - used to draw on
	private Graphics2D g2;
	// Mouse coordinates
	private int currentX, currentY, oldX, oldY;

	public DrawArea() {

		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				// save cords x,y when mouse is pressed
				oldX = e.getX();
				oldY = e.getY();

			}

		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				// cords x,y when dragging mouse
				currentX = e.getX();
				currentY = e.getY();

				if (g2 != null) {
					// draw line if g2 context is not null
					g2.drawLine(oldX, oldY, currentX, currentY);
					// refresh draw area to repaint
					repaint();
					// store current cords x,y as old x,y
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
	}

	protected void paintComponent(Graphics g) {
		if (image == null) {
			// image to draw null - we create
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
			// enable antialiasing
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// clear draw area
			clear();
		}

		g.drawImage(image, 0, 0, null);
	}

	// creating exposed methods
	public void clear() {
		Paint paintColor = g2.getPaint();
		g2.setPaint(Color.white);
		// draw white on the entire draw area to clear
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setPaint(paintColor);
		repaint();
	}

	// applying color on g2 content
	public void red() {
		g2.setPaint(Color.red);
	}

	public void black() {
		g2.setPaint(Color.black);
	}

	public void magenta() {
		g2.setPaint(Color.magenta);
	}

	public void green() {
		g2.setPaint(Color.green);
	}

	public void blue() {
		g2.setPaint(Color.blue);
	}

}
