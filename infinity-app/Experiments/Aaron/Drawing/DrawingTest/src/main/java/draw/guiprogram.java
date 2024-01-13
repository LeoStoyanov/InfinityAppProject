package draw;

import java.awt.*; // Using AWT container and various component classes
import java.awt.event.*; // provides a interface and classes for different types of events
import javax.swing.*; // provides a set of "lightweight" components to the maximum degree possible

public class guiprogram {

	public guiprogram() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] a) {
		JFrame f = new JFrame();
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		f.setContentPane(new Container());
		f.setSize(250, 250);
		f.setVisible(true);
	}

}
