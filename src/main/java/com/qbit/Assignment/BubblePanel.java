package com.qbit.Assignment;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class BubblePanel extends JPanel{
	public BubblePanel()
    {
        setVisible(true);
    }

	@Override
    public void paintComponent(Graphics g)
    {
		Image bgimage = Toolkit.getDefaultToolkit().getImage("./images/Parchment.jpg");
        int imwidth = bgimage.getWidth(null);
        int imheight = bgimage.getHeight(null);
        g.drawImage(bgimage, 1, 1,1000,1000, null);
        repaint();
		//Graphics2D g2d = (Graphics2D)g;
        //g2d.setColor(Color.BLUE);
       // g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
       // g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
    }

}
