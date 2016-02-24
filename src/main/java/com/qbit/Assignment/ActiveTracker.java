package com.qbit.Assignment;

import com.qbit.Objects.General;
import com.qbit.Objects.Project;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ActiveTracker extends JDialog implements NativeKeyListener,ActionListener{
	
	public ActiveTracker(){
		
	}
	int selectedProject = 0;
	JLabel lbl_status;
	Timer timer;
	List<Integer> wordcounts = Collections.synchronizedList(new ArrayList());
	StandardButton start;
	static Color bgColor = new Color(176,204,210);
	public void init(){
		try {
	        setIconImage(ImageIO.read(getClass().getResource("/images/WW.png")));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    setTitle("Active Tracker");
		setSize(250,170);
		setLayout(null);
		RoundedPanel panel = new RoundedPanel();
		panel.setBackground(bgColor);
		panel.setLayout(null);
		panel.setSize(250,170);
		start = new StandardButton(Color.GREEN,Color.GREEN,Color.GREEN,Color.GREEN);
		start.setText("<HTML><b>START</HTML>");
		start.setActionCommand("S");
		start.setBackground(Color.GREEN);
		start.setBounds(10, 10, 100,50);
		start.addActionListener(this);
		panel.add(start);
		//getContentPane().add(start);
		StandardButton stop = new StandardButton(Color.RED,Color.RED,Color.RED,Color.RED);
		stop.setText("<HTML><b>STOP</HTML>");
		stop.setBounds(130, 10, 100,50);
		stop.setBackground(Color.RED);
		stop.setActionCommand("F");
		stop.addActionListener(this);
		//getContentPane().add(stop);
		panel.add(stop);
		lbl_status = new JLabel();
		lbl_status.setBounds(15,70,150,30);
		lbl_status.setFont(new Font("Arial",Font.BOLD,12));
		panel.add(lbl_status);
		setBackground(bgColor);
		setContentPane(panel);
		//getContentPane().add(panel);
		setLocationRelativeTo(null);
		//setUndecorated(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	private void showProjectList(){
	      final JPopupMenu editMenu = new JPopupMenu("Select Project"); 
	      
	      if(project1 != null){
		      JMenuItem proj1 = new JMenuItem(project1.getProjectTitle());
		      proj1.setActionCommand("1");
		      proj1.setFont(new Font("Arial",Font.BOLD,13));
		      proj1.addActionListener(this);
		      editMenu.add(proj1);
		      editMenu.add(new JSeparator());
	      }
	      if(project2 != null){
		      JMenuItem proj2 = new JMenuItem(project2.getProjectTitle());
		      proj2.setActionCommand("2");
		      proj2.setFont(new Font("Arial",Font.BOLD,13));
		      proj2.addActionListener(this);
		      editMenu.add(proj2);
		      editMenu.add(new JSeparator());
	      }
	      if(project3 != null){
		      JMenuItem proj3 = new JMenuItem(project3.getProjectTitle());
		      proj3.setActionCommand("3");
		      proj3.setFont(new Font("Arial",Font.BOLD,13));
		      proj3.addActionListener(this);
		      editMenu.add(proj3);
	      }
	      editMenu.show(this, 20, 20);
	   }
	
	public static boolean appStatus = false;
	
	
	public void actionPerformed(ActionEvent ae){
		System.out.println(ae.getActionCommand());
		if(ae.getActionCommand().equals("S")){ 		//start pressed
			lbl_status.setText("START Clicked     ");
			showProjectList();
			startListening();
			timer = new Timer();
			timer.schedule(new RemindTask(), 5* 1000, 10 * 1000);
		}else if(ae.getActionCommand().equals("1")){ //start pressed
			lbl_status.setText(project1.getProjectTitle()+ " Selected");
			selectedProject = 1;
			//start.setBackground(Color.GRAY);
			start.setEnabled(false);
			project1.setCurrentWords(0);
			WriDemo.proj1SessionStart = false;
			appStatus = true;
			//showProjectList();
		}else if(ae.getActionCommand().equals("2")){ //start pressed
			lbl_status.setText(project2.getProjectTitle()+ " Selected");
			selectedProject = 2;
			//start.setBackground(Color.GRAY);
			start.setEnabled(false);
			project2.setCurrentWords(0);
			WriDemo.proj2SessionStart = false;
			appStatus = true;
			//showProjectList();
		}else if(ae.getActionCommand().equals("3")){ //start pressed
			lbl_status.setText(project3.getProjectTitle()+ " Selected");
			selectedProject = 3;
			//start.setBackground(Color.GRAY);
			start.setEnabled(false);
			project3.setCurrentWords(0);
			WriDemo.proj3SessionStart = false;
			appStatus = true;
			//showProjectList();
		}else{ //Stop pressed
			if(timer!=null)
				timer.cancel();
			selectedProject = 0;	
			stopListening();
			lbl_status.setText("STOP Clicked        ");
			start.setEnabled(true);
			appStatus = false;
			WriDemo.proj1SessionStart = false;
			WriDemo.proj2SessionStart = false;
			WriDemo.proj3SessionStart = false;
			
		}
	}
	
	
	public void Start(){
		configPath = System.getenv("WRITRACK_HOME");
		if(configPath==null)
			configPath="C:\\Config";
		ObjectInputStream ois;

		try {
			FileInputStream fin = new FileInputStream(configPath+"\\general.ser");
			ois = new ObjectInputStream(fin);
			general = (General) ois.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
		}

		try {
			FileInputStream fin = new FileInputStream(configPath+"\\project1.ser");
			ois = new ObjectInputStream(fin);
			project1 = (Project) ois.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(configPath+"\\project2.ser");
			ois = new ObjectInputStream(fin);
			project2 = (Project) ois.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(configPath+"\\project3.ser");
			ois = new ObjectInputStream(fin);
			project3 = (Project) ois.readObject();
		} catch (Exception e) {
			//e.printStackTrace();
		}

		if (general.isActivated()) {
			try {
				FileInputStream fin = new FileInputStream(configPath+"\\project4.ser");
				ois = new ObjectInputStream(fin);
				project4 = (Project) ois.readObject();
			} catch (Exception e) {
				//e.printStackTrace();
			}

			try {
				FileInputStream fin = new FileInputStream(configPath+"\\project5.ser");
				ois = new ObjectInputStream(fin);
				project5 = (Project) ois.readObject();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

		if(project1 == null && project2 == null && project3 == null && project4 == null && project5 == null){
			JOptionPane.showMessageDialog(this,"Please enter Project Info first","Input Validation Error",JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		init();
		//showProjectList();
		setVisible(true);
	}
	
	
	public String getClipboardContents() {
	    String result = "";
	    Clipboard clipboard = this.getToolkit().getSystemClipboard();
	    Transferable contents = clipboard.getContents(null);
	    boolean hasTransferableText =
	      (contents != null) &&
	      contents.isDataFlavorSupported(DataFlavor.stringFlavor)
	    ;
	    if (hasTransferableText) {
	      try {
	        result = (String)contents.getTransferData(DataFlavor.stringFlavor);
	      }
	      catch (Exception ex){
	        System.out.println(ex);
	        ex.printStackTrace();
	      }
	    }
	    return result;
	  }
		
	
	static String configPath;
	static Project project1;
	static Project project2;
	static Project project3;
	static Project project4;
	static Project project5;
	static General general;
	
	
	public void startListening(){ //start listening to word count
		try {
            /* Register jNativeHook */
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

            // This is to stop libriaries own logs
            logger.setFilter(new Filter() {
				
				public boolean isLoggable(LogRecord record) {
                  if (record.getLoggerName().equals("org.jnativehook")){
                	  return false;
                   }
                  return true;
				}});
        
        } catch (NativeHookException exc) {
            /* Its error */
            System.err.println("There was a problem registering the native hook.");
            System.err.println(exc.getMessage());
        }
		
	}
	
	public void stopListening(){ //stop listening to word count
		try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
	}
	
	class RemindTask extends TimerTask {
	    public void run() {
	    	updateProjectCount(0);    
	   }
	}
	static JLabel lbl_display ;
	static JDialog Displaydialog;
	static JFrame displayFrame;//test tettett etet 
	public static void disposeWordCount(){
		try{
				displayFrame.dispose();
				Displaydialog.dispose();
		}catch(Exception c){}
	}
	public static void DisplayWordCount(){
		displayFrame = new JFrame();
		Displaydialog = new JDialog(displayFrame,false);
		Displaydialog.setModalityType(ModalityType.MODELESS);
        Displaydialog.setAlwaysOnTop(true);
        Displaydialog.setBackground(bgColor);
		//dialog.setSize(100,100);
		int ScreenX = Toolkit.getDefaultToolkit().getScreenSize().height - 120;
		int ScreenY = Toolkit.getDefaultToolkit().getScreenSize().width - 120;
		System.out.println("ScreenX "+ScreenX+"ScreenY "+ScreenY);
		Displaydialog.setLayout(null);
		Displaydialog.setUndecorated(true);
		Displaydialog.setLocationRelativeTo(null);
		Displaydialog.setBounds(10, ScreenX, 100,25);
		Displaydialog.validate();
		RoundedPanel panel = new RoundedPanel();
		panel.setBackground(bgColor);
		 lbl_display = new JLabel();
		//if(value == 1)
			//lbl.setText(""+tillDate+"/"+total);
		//else
			//lbl.setText("Session :"+sessionTotal);
		panel.add(lbl_display);
		Displaydialog.setContentPane(panel);
		Displaydialog.setVisible(true);
	}
	
	public void showWordCount(int value){
		int total = 0;
		int tillDate = 0;
		int sessionTotal = 0;
		System.out.println("inside showWordCount"+selectedProject);
		switch (selectedProject) {
		case 1:
			total = project1.getWordGoal();
			tillDate = project1.getWordsTillDate();
			sessionTotal = project1.getCurrentWords();
			if(project1.getProjectType() == 6){
				tillDate = tillDate / 500;
				sessionTotal = sessionTotal / 500;
			}
			break;
		case 2:
			total = project2.getWordGoal();
			tillDate = project2.getWordsTillDate();
			sessionTotal = project2.getCurrentWords();
			if(project2.getProjectType() == 6){
				tillDate = tillDate / 500;
				sessionTotal = sessionTotal / 500;
			}
			break;
		case 3:
			total = project3.getWordGoal();
			tillDate = project3.getWordsTillDate();
			sessionTotal = project3.getCurrentWords();
			if(project3.getProjectType() == 6){
				tillDate = tillDate / 500;
				sessionTotal = sessionTotal / 500;
			}
			break;
		//for project 4 and 5
		case 4:
			total = project4.getWordGoal();
			tillDate = project4.getWordsTillDate();
			sessionTotal = project4.getCurrentWords();
			if(project4.getProjectType() == 6){
				tillDate = tillDate / 500;
				sessionTotal = sessionTotal / 500;
			}
			break;
		case 5:
			total = project5.getWordGoal();
			tillDate = project5.getWordsTillDate();
			sessionTotal = project5.getCurrentWords();
			if(project5.getProjectType() == 6){
				tillDate = tillDate / 500;
				sessionTotal = sessionTotal / 500;
			}
			break;
		default:
			break;
		}
		
		
		if(value == 1)
			lbl_display.setText(""+tillDate+"/"+total);
		else
			lbl_display.setText("Session :"+sessionTotal);
		
		
		//try {
		//	Thread.sleep(2000);
		//} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		//dialog.dispose();
	}
	
	
	
	
	
	
	public void showWordCount(Project project){
		switch (general.getShowCount()) {
		case 0:
			//Nothing to do;
			break;
			
		case 1:   /// to display the total words
			showWordCount(1);			
			break;
		
		case 2:  // to display the session total words
			showWordCount(2);	
			break;
			
		default:
			break;
		}
	}

	private static void setConfigPath() {
		String wriTrackHome = System.getenv("WRITRACK_HOME");
		if (wriTrackHome == null) {
			configPath = "C:\\Config";
		} else {
			configPath = wriTrackHome + "\\Config";
		}

		File configFile = new File(configPath);
		if (!configFile.exists()) {
			configFile.mkdir();
		}
	}
	
	public void updateProjectCount(int size){  //the size matters when came from clipboard
		if (configPath == null) {
			setConfigPath();
		}

		System.out.println("Update Word Count Start"+wordcounts.size());
		int wordsRemain = 0; // the word remaining before the updates
		switch (selectedProject) {
		case 1:
			 project1.setCurrentWords(project1.getCurrentWords() +wordcounts.size());
			 //wordcounts.clear();
			 project1.setCurrentWords(project1.getCurrentWords() +size);
			 wordsRemain = project1.getWordsTillDate();
			 project1.setWordsTillDate(project1.getWordsTillDate() + wordcounts.size() + size) ;
			 try {
					FileOutputStream fout = new FileOutputStream(configPath+"\\project1.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(project1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//checkMilestoneReward(project1,wordsRemain);	
				checkWordCountReward(project1,wordsRemain);
				showWordCount(project1);
			 break;
		case 2:
			 project2.setCurrentWords(project2.getCurrentWords() +wordcounts.size());
			 //wordcounts.clear();
			 project2.setCurrentWords(project2.getCurrentWords() +size);
			 wordsRemain = project2.getWordsTillDate();
			 project2.setWordsTillDate(project2.getWordsTillDate() + wordcounts.size() + size) ;
			 try {
					FileOutputStream fout = new FileOutputStream(configPath+"\\project2.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(project2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//checkMilestoneReward(project2,wordsRemain);	
				checkWordCountReward(project2,wordsRemain);
				showWordCount(project2);
			 break;	
		
		case 3:
			 project3.setCurrentWords(project3.getCurrentWords() +wordcounts.size());
			 //wordcounts.clear();
			 project3.setCurrentWords(project3.getCurrentWords() +size);
			 wordsRemain = project3.getWordsTillDate();
			 project3.setWordsTillDate(project3.getWordsTillDate() + wordcounts.size() + size) ;
			 try {
					FileOutputStream fout = new FileOutputStream(configPath+"\\project3.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(project3);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//checkMilestoneReward(project3,wordsRemain);
				checkWordCountReward(project3,wordsRemain);
				showWordCount(project3);
			 break;	
		//for project 4 and 5
		case 4:
			 project4.setCurrentWords(project4.getCurrentWords() +wordcounts.size());
			 //wordcounts.clear();
			 project4.setCurrentWords(project4.getCurrentWords() +size);
			 wordsRemain = project4.getWordsTillDate();
			 project4.setWordsTillDate(project4.getWordsTillDate() + wordcounts.size() + size) ;
			 try {
					FileOutputStream fout = new FileOutputStream(configPath+"\\project4.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(project4);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//checkMilestoneReward(project2,wordsRemain);	
				checkWordCountReward(project4,wordsRemain);
				showWordCount(project4);
			 break;	
		
		case 5:
			 project5.setCurrentWords(project5.getCurrentWords() +wordcounts.size());
			 //wordcounts.clear();
			 project5.setCurrentWords(project5.getCurrentWords() +size);
			 wordsRemain = project5.getWordsTillDate();
			 project5.setWordsTillDate(project5.getWordsTillDate() + wordcounts.size() + size) ;
			 try {
					FileOutputStream fout = new FileOutputStream(configPath+"\\project5.ser");
					ObjectOutputStream oos = new ObjectOutputStream(fout);
					oos.writeObject(project5);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//checkMilestoneReward(project3,wordsRemain);
				checkWordCountReward(project5,wordsRemain);
				showWordCount(project5);
			 break;
		default:
			break;
		}
		wordcounts.clear();
	}
	
	

	
	public void checkWordCountReward(Project project, int previousCount){
		
		int wordsTillDate = project.getWordsTillDate();
		
		//Check 1000 Word Count
		if(previousCount < 1000 && wordsTillDate >= 1000){
			//show Label
			displayReward(project.getReward1000(),project.isPostSocMedia());
			
		}
		//Check 2000 Word Count
		if(previousCount < 2000 && wordsTillDate >= 2000){
			//show Label
			displayReward(project.getReward2000(),project.isPostSocMedia());
		}
		//Check 5000 Word Count
		if(previousCount < 5000 && wordsTillDate >= 5000){
			//show Label
			displayReward(project.getReward5000(),project.isPostSocMedia());
		}
		//Check 10000 Word Count
		if(previousCount < 10000 && wordsTillDate >= 10000){
			//show Label
			displayReward(project.getReward10000(),project.isPostSocMedia());
		}
		
		if(wordsTillDate >= project.getWordGoal()){
			//show completion
			displayReward(project.getRewardCompletion(),project.isPostSocMedia());
		}
	}
	
	public void displayReward(String str,boolean isSocMedia){
		System.out.println("inside Reward");
		ImageIcon icon = new ImageIcon(getClass().getResource("/images/Reward.png"));
		JFrame dialog = new JFrame();
		
		RewardLabel rewLbl = new RewardLabel(str, icon);
		dialog.getContentPane().add(rewLbl);
		int scrX = Toolkit.getDefaultToolkit().getScreenSize().height;
		int scrY = Toolkit.getDefaultToolkit().getScreenSize().width;
		System.out.println("X="+scrX+"Y:"+scrY);
		System.out.println("Img H"+(scrX - icon.getIconHeight())+"Img W"+(scrY - icon.getIconWidth()));
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
		if(isSocMedia)
			postonMedia(str);
	}
	
	public void postonMedia(String str){
		//read config file first

		try {
			Properties prop = new Properties();
			String propFileName = "media.properties";
			configPath = System.getenv("WRITRACK_HOME");
			if(configPath==null)
				configPath="C:\\Config";
			prop.load(new FileReader(configPath+"\\"+propFileName));
			
			//Facebook Post
			FacebookController.postToFacebook(str);
			
			//Twitter Post
			TwitterController.postToTwitter(str);
			
		}catch(Exception c){
			System.out.println(" Facebook Twitter posting not working :"+c.getMessage());
		}
	}
	
	public static void main(String[] args) {
		//
		configPath = System.getenv("WRITRACK_HOME");
		if(configPath==null)
			configPath="C:\\Config";
		ObjectInputStream ois;
    	//
		try {
			FileInputStream fin = new FileInputStream(configPath+"\\project1.ser");
			ois = new ObjectInputStream(fin);
			project1 = (Project) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileInputStream fin = new FileInputStream(configPath+"\\project2.ser");
			ois = new ObjectInputStream(fin);
			project2 = (Project) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    EventQueue.invokeLater(new Runnable() {
	    
	        public void run() {
	        	try {
	                // Set System L&F
		            UIManager.setLookAndFeel(
		                UIManager.getSystemLookAndFeelClassName());
		        } 
		        catch (Exception e) {
		           // handle exception
		        }
	        	ActiveTracker ex = new ActiveTracker();
	        	try {
	                /* Register jNativeHook */
	                GlobalScreen.registerNativeHook();
	                GlobalScreen.addNativeKeyListener(ex);
	                Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());

	                // This is to stop libriaries own logs
	                logger.setFilter(new Filter() {
						
						public boolean isLoggable(LogRecord record) {
	                      if (record.getLoggerName().equals("org.jnativehook")){
	                    	  return false;
	                       }
	                      return true;
						}});
	            
	            } catch (NativeHookException exc) {
	                /* Its error */
	                System.err.println("There was a problem registering the native hook.");
	                System.err.println(exc.getMessage());
	                System.exit(1);
	            }
	            ex.setVisible(true);
	        }
	    });
	}
	
	
	
	 int prevKey = 99;
     static volatile int wordCount = 0;
     int prevPressedKey = 99;
     
	/* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

    	//System.out.println("Key Pressed: "+e.getKeyCode());
    	if((prevPressedKey == NativeKeyEvent.VC_CONTROL_L || prevPressedKey == NativeKeyEvent.VC_CONTROL_R)&& e.getKeyCode()== NativeKeyEvent.VC_V){
    		//capture clipboard data
    		//System.out.println("Clipboard data:"+getClipboardContents());
    		try {
				Thread.sleep(250);
			} catch (Exception e2) {
				// TODO: handle exception
			}
    		updateProjectCount(countWords(getClipboardContents()));
    	}
    	prevPressedKey = e.getKeyCode();
    }
    
    public static int countWords(String str)
    {
    	//System.out.println("String is"+str+";");
    	str = str.trim();
        int count = 1;
        for (int i=0;i<=str.length()-1;i++)
        {
            if (str.charAt(i) == ' ' && str.charAt(i+1)!=' ')
            {
                count++;
            }
        }
        return count;
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
    	if(prevKey == 99)
    		prevKey = e.getKeyCode();
    	else{
    		if(prevKey != NativeKeyEvent.VC_SPACE && (e.getKeyCode()== NativeKeyEvent.VC_SPACE || e.getKeyCode()== NativeKeyEvent.VC_ENTER))
    		wordcounts.add(wordCount);
    	}
    	prevKey = e.getKeyCode();
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode())+"WordCOunt="+wordCount);
        
    }

    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

}



