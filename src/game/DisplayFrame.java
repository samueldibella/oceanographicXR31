package game;

import java.awt.Color;

public class DisplayFrame extends javax.swing.JFrame {
	public DisplayFrame(){
        this.setSize(1400, 650); //The window Dimensions
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setBounds(5, 5, 1400, 650);
        setBackground(Color.black);
        processing.core.PApplet sketch = new Game();
        panel.add(sketch);
        this.add(panel);
        sketch.init(); //this is the function used to start the execution of the sketch
        this.setVisible(true);
    }
}
