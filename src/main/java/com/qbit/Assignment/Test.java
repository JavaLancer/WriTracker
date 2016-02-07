package com.qbit.Assignment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class Test {
	
	public static void main(String args[]){
		displayReward("Hello");
	}
	
	public static void displayReward(String str){
		System.out.println("inside Reward");
		ImageIcon icon = new ImageIcon("./images/Reward.png");
		JFrame dialog = new JFrame();
		
		RewardLabel rewLbl = new RewardLabel(str, icon);
		dialog.getContentPane().add(rewLbl);
		int scrX = Toolkit.getDefaultToolkit().getScreenSize().height;
		int scrY = Toolkit.getDefaultToolkit().getScreenSize().width;
		//System.out.println("X="+scrX+"Y:"+scrY);
		//System.out.println("Img H"+(scrX - icon.getIconHeight())+"Img W"+(scrY - icon.getIconWidth()));
		dialog.setLocation(new Point(scrY - (icon.getIconWidth() + 50), scrX - (icon.getIconHeight()+ 50)));
		dialog.setUndecorated(true);
		dialog.setSize(200,200);
		dialog.repaint();
		dialog.setVisible(true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		try{
			Thread.sleep(5000);
		}catch(Exception ig){}
		dialog.dispose();

	}

}

class RewardLabel extends JLabel{
	private String rewardStr;
	private ImageIcon icon;
	
	public RewardLabel(String rewardStr,ImageIcon icon){
		this.rewardStr = rewardStr;
		this.icon = icon;
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(icon.getImage(), 0, 0, icon.getIconWidth(), icon.getIconHeight(),this);
		g.setFont(WriDemo.fontBold);
		g.drawString(rewardStr, 10,icon.getIconHeight() / 2);
	}
	
}

class MyDialog extends JDialog{
	
	public MyDialog(){
		super();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		super.paint(g2);
		g2.dispose();
	}
		
}

class MyLabel extends JLabel{
	
	public MyLabel(ImageIcon icon){
		super(icon);
	}
	
	public void paint(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g.create();
		//g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		//super.paint(g2);
		//g2.dispose();
		BufferedImage img = new BufferedImage(100,100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();

		int ovalX = 50;
		int ovalY = 70;
		int ovalRadius = 20;

		/* Draw the grey rectangle */
		g2.setColor(Color.GRAY);
		g2.fillRect(0, 0, 100,100);

		/* Enable Anti-Alias */
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Clear the circle away */
		Composite comp = AlphaComposite.Clear;
		g2.setComposite(comp);
		g2.fillOval(ovalX - ovalRadius, ovalY - ovalRadius, 2 * ovalRadius, 2 * ovalRadius);

		g2.dispose();
	}
		
}
