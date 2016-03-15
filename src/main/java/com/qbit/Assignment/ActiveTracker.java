package com.qbit.Assignment;

import com.qbit.Objects.General;
import com.qbit.Objects.Project;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class ActiveTracker extends JDialog implements NativeKeyListener,NativeMouseInputListener,ActionListener{
	
	public ActiveTracker(){
		
	}
	int selectedProject = 0;
	JLabel lbl_status;
	Timer timer;
//	List<Integer> wordcounts = Collections.synchronizedList(new ArrayList());
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
//			timer = new Timer();
//			timer.schedule(new RemindTask(), 5* 1000, 10 * 1000);
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
            GlobalScreen.addNativeMouseListener(this);
            GlobalScreen.addNativeMouseMotionListener(this);
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
	
//	class RemindTask extends TimerTask {
//	    public void run() {
//	    	updateProjectCount(0);
//	   }
//	}
	static JLabel lbl_display ;
	static JDialog Displaydialog;
	static JFrame displayFrame;//test tettett etet 
	public static void disposeWordCount(){
		try{
				displayFrame.dispose();
				Displaydialog.dispose();
		}catch(Exception c){}
	}
	public void DisplayWordCount(){
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

		updateProjectCount(0);
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

    public void updateProjectCount(List<Integer> countList) {
        addToProjectWordCountList(countList);
        if (countList.get(0) > 0) {
            updateProjectCount(countList.size());
        }
    }

    private void addToProjectWordCountList(List<Integer> countList) {
        switch (selectedProject) {
            case 1:
                project1.getWordCountList().addAll(countList);
                break;
            case 2:
                project2.getWordCountList().addAll(countList);
                break;
            case 3:
                project3.getWordCountList().addAll(countList);
                break;
            case 4:
                project4.getWordCountList().addAll(countList);
                break;
            case 5:
                project5.getWordCountList().addAll(countList);
                break;
        }
        saveProject();
    }

    public void updateProjectCount(int size){  //the size matters when came from clipboard
		if (configPath == null) {
			setConfigPath();
		}

        if (size > 100) {
            // Special entry for double spaces, enters and tabs
            List<Integer> countList = new ArrayList<>();
            countList.add(size);
            addToProjectWordCountList(countList);
            return;
        }

//		System.out.println("Update Word Count Start"+wordcounts.size());
		int wordsRemain = 0; // the word remaining before the updates
		switch (selectedProject) {
		case 1:
			if (size != -99) {
				project1.setCurrentWords(project1.getCurrentWords() + size);
				wordsRemain = project1.getWordsTillDate();
				project1.setWordsTillDate(project1.getWordsTillDate() + size);
				checkWordCountReward(project1,wordsRemain);
			} else {
				project1.setCurrentWords(0);
				project1.setWordsTillDate(0);
			}
			showWordCount(project1);
			 break;
		case 2:
			if (size != -99) {
				project2.setCurrentWords(project2.getCurrentWords() + size);
				wordsRemain = project2.getWordsTillDate();
				project2.setWordsTillDate(project2.getWordsTillDate() + size);
				checkWordCountReward(project2, wordsRemain);
			} else {
				project2.setCurrentWords(0);
				project2.setWordsTillDate(0);
			}
			showWordCount(project2);
			 break;	
		
		case 3:
			if (size != -99) {
				project3.setCurrentWords(project3.getCurrentWords() + size);
				wordsRemain = project3.getWordsTillDate();
				project3.setWordsTillDate(project3.getWordsTillDate() + size);
				checkWordCountReward(project3, wordsRemain);
			} else {
				project3.setCurrentWords(0);
				project3.setWordsTillDate(0);
			}
			showWordCount(project3);
		 	break;
		//for project 4 and 5
		case 4:
			if (size != -99) {
				project4.setCurrentWords(project4.getCurrentWords() + size);
				wordsRemain = project4.getWordsTillDate();
				project4.setWordsTillDate(project4.getWordsTillDate() + size);
				checkWordCountReward(project4, wordsRemain);
			} else {
				project4.setCurrentWords(0);
				project4.setWordsTillDate(0);
			}
			showWordCount(project4);
			 break;	
		
		case 5:
			if (size != -99) {
				project5.setCurrentWords(project5.getCurrentWords() + size);
				wordsRemain = project5.getWordsTillDate();
				project5.setWordsTillDate(project5.getWordsTillDate() + size);
				checkWordCountReward(project5, wordsRemain);
			} else {
				project5.setCurrentWords(0);
				project5.setWordsTillDate(0);
			}
			showWordCount(project5);
			 break;
		default:
			break;
		}
        saveProject();
//		wordcounts.clear();
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
//			Properties prop = new Properties();
//			String propFileName = "media.properties";
//			configPath = System.getenv("WRITRACK_HOME");
//			if(configPath==null)
//				configPath="C:\\Config";
//			prop.load(new FileReader(configPath+"\\"+propFileName));
			
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
//    static volatile int wordCount = 0;
//    int prevPressedKey = 99;
	int currentWordCount = 0;
//	List<Integer> wordCountList = new ArrayList<>();
    Date keyPressed = null;
    boolean highlightedText;
     
	/* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
		int keyCode = e.getKeyCode();

		if (keyCode == NativeKeyEvent.VC_BACKSPACE) {
            if (currentWordCount == 0 && getPreviousWordCount() > 100) {
                // Special entry for double spaces, enters and tabs
                takeOffProjectsLastWordCount();
                prevKey = keyCode;
                return;
            }
            if (getPreviousWordCount() >= 0) {
				System.out.println("currentWordCount before backspace: " + currentWordCount);
				if (currentWordCount > 0) {
					currentWordCount--;
				} else {
					currentWordCount = getPreviousWordCount();
					takeOffProjectsLastWordCount();
					if (prevKey != NativeKeyEvent.VC_ENTER && prevKey != NativeKeyEvent.VC_SPACE && prevKey != NativeKeyEvent.VC_TAB) {
						updateProjectCount(-1);
					}
				}
				System.out.println("currentWordCount on backspace: " + currentWordCount);
				prevKey = keyCode;
				return;
			} else {
				if (currentWordCount == 0) {
					updateProjectCount(-99);
					prevKey = keyCode;
					return;
				}
				currentWordCount--;
				return;
			}
		}

        if (keyCode == NativeKeyEvent.VC_DELETE && highlightedText) {
//            try {
            // TODO: get ctrl-c to work
//                NativeInputEvent nativeInputEvent = new NativeInputEvent(GlobalScreen.class, NativeKeyEvent.VC_C, new Date().getTime(), NativeInputEvent.CTRL_MASK);
//                GlobalScreen.postNativeEvent(nativeInputEvent);
//                Robot robot = new Robot();
//                robot.keyPress(KeyEvent.VK_CONTROL);
//                robot.keyPress(KeyEvent.VK_C);
//                Thread.sleep(250);
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//                robot.keyRelease(KeyEvent.VK_CONTROL);
//            } catch (AWTException | InterruptedException e1) {
//                e1.printStackTrace();
//            }
//            updateProjectCount(-countWords(getClipboardContents()));
//            currentWordCount = 0;
        }
        highlightedText = false;

    	if((prevKey == NativeKeyEvent.VC_CONTROL_L || prevKey == NativeKeyEvent.VC_CONTROL_R)&& keyCode == NativeKeyEvent.VC_V){
    		//capture clipboard data
    		//System.out.println("Clipboard data:"+getClipboardContents());
    		try {
				Thread.sleep(250);
			} catch (Exception e2) {
				// TODO: handle exception
			}
            System.out.println("List: " + project1.getWordCountList());
            updateProjectCount(countWords(getClipboardContents()));
            System.out.println("List: " + project1.getWordCountList());
            currentWordCount = 0;
			prevKey = keyCode;
            return;
    	}

        if (prevKey != 99) {
            if (prevKey == NativeKeyEvent.VC_SPACE && keyCode == NativeKeyEvent.VC_SPACE) {
                updateProjectCount(101);
            }

            if (prevKey == NativeKeyEvent.VC_ENTER && keyCode == NativeKeyEvent.VC_ENTER) {
                updateProjectCount(101);
            }

            if (prevKey == NativeKeyEvent.VC_TAB && keyCode == NativeKeyEvent.VC_TAB) {
                updateProjectCount(101);
            }

            if ((prevKey != NativeKeyEvent.VC_SPACE && prevKey != NativeKeyEvent.VC_ENTER && prevKey != NativeKeyEvent.VC_TAB) && (keyCode == NativeKeyEvent.VC_SPACE || keyCode == NativeKeyEvent.VC_ENTER || keyCode == NativeKeyEvent.VC_TAB)) {
                ArrayList<Integer> countList = new ArrayList<>();
                countList.add(currentWordCount);
				System.out.println("currentWordCount added to history: " + currentWordCount);
				updateProjectCount(countList);
                System.out.println("List: " + project1.getWordCountList());
            }

            currentWordCount = 0;
            prevKey = keyCode;
            return;
        }

    	prevKey = keyCode;
        if (!e.isActionKey() &&  keyCode != NativeKeyEvent.VC_ENTER && keyCode != NativeKeyEvent.VC_SPACE && keyCode != NativeKeyEvent.VC_TAB) {
            currentWordCount++;
        }
//        System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(keyCode));
        System.out.println("currentWordCount at end of method: " + currentWordCount);
    }

    private int getPreviousWordCount() {
        switch (selectedProject) {
            case 1:
				if (project1.getWordCountList().isEmpty()) {
					return -1;
				}
				return project1.getWordCountList().get(project1.getWordCountList().size() - 1);
            case 2:
				if (project2.getWordCountList().isEmpty()) {
					return -1;
				}
                return project2.getWordCountList().get(project2.getWordCountList().size() - 1);
            case 3:
				if (project3.getWordCountList().isEmpty()) {
					return -1;
				}
                return project3.getWordCountList().get(project3.getWordCountList().size() - 1);
            case 4:
				if (project4.getWordCountList().isEmpty()) {
					return -1;
				}
                return project4.getWordCountList().get(project4.getWordCountList().size() - 1);
            case 5:
				if (project5.getWordCountList().isEmpty()) {
					return -1;
				}
                return project5.getWordCountList().get(project5.getWordCountList().size() - 1);
            default:
                return 0;
        }
    }

    private void saveProject() {
        switch (selectedProject) {
            case 1:
                try {
                    FileOutputStream fout = new FileOutputStream(configPath+"\\project1.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(project1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    FileOutputStream fout = new FileOutputStream(configPath+"\\project2.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(project2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    FileOutputStream fout = new FileOutputStream(configPath+"\\project3.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(project3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    FileOutputStream fout = new FileOutputStream(configPath+"\\project4.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(project4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 5:
                try {
                    FileOutputStream fout = new FileOutputStream(configPath+"\\project5.ser");
                    ObjectOutputStream oos = new ObjectOutputStream(fout);
                    oos.writeObject(project5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void takeOffProjectsLastWordCount() {
        switch (selectedProject) {
            case 1:
                if (!project1.getWordCountList().isEmpty()) {
                    project1.getWordCountList().remove(project1.getWordCountList().size() - 1);
                }
                break;
            case 2:
                if (!project2.getWordCountList().isEmpty()) {
                    project2.getWordCountList().remove(project2.getWordCountList().size() - 1);
                }
                break;
            case 3:
                if (!project3.getWordCountList().isEmpty()) {
                    project3.getWordCountList().remove(project3.getWordCountList().size() - 1);
                }
                break;
            case 4:
                if (!project4.getWordCountList().isEmpty()) {
                    project4.getWordCountList().remove(project4.getWordCountList().size() - 1);
                }
                break;
            case 5:
                if (!project5.getWordCountList().isEmpty()) {
                    project5.getWordCountList().remove(project5.getWordCountList().size() - 1);
                }
                break;
        }
        saveProject();
    }

    public List<Integer> countWords(String str) {
    	str = str.trim();
        int previousPosition = 0;
        List<Integer> countList = new ArrayList<>();
        for (int i = 0; i <= str.length()-1; i++) {
            if (str.charAt(i) == ' ' && str.charAt(i+1) != ' ') {
                countList.add(i - previousPosition);
                previousPosition = i + 1;
            }
        }



        if (previousPosition < str.length()) {
            countList.add(str.length() - previousPosition);
        }

        return countList;
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
//        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));


//        prevKey = e.getKeyCode();
//    	if(prevKey == 99)
//    		prevKey = e.getKeyCode();
//    	else{
//    		if(prevKey != NativeKeyEvent.VC_SPACE && (e.getKeyCode()== NativeKeyEvent.VC_SPACE || e.getKeyCode()== NativeKeyEvent.VC_ENTER))
//    		wordcounts.add(wordCount);
//    	}



//            if (currentWordCount == 0) {
//                currentWordCount = wordCountList.size() > 0 ? wordCountList.get(wordCountList.size() - 1) : 5;
//            }
//
//            if (currentWordCount == 1) {
//                wordcounts.clear();
//                updateProjectCount(-1);
//                currentWordCount = wordCountList.size() > 0 ? wordCountList.get(wordCountList.size() - 1) + 1 : 5;
//            } else if (currentWordCount > 1) {
//                currentWordCount--;
//            }
//        } else {
//            currentWordCount++;

//        if (prevKey == NativeKeyEvent.VC_SPACE && (e.getKeyCode() == NativeKeyEvent.VC_SPACE || e.getKeyCode() == NativeKeyEvent.VC_TAB)) {
//            return;
//        }
        //    	if(prevKey == 99)
        //    		prevKey = e.getVirtualKeyCode();
        //    	else{
        //System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode())+"WordCOunt="+wordCount);
        
    }

    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        int i = 0;
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent nativeMouseEvent) {
        int i = 0;
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
        int i = 0;
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        int i = 0;
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        highlightedText = true;
    }
}



