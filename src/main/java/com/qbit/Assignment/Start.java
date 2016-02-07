package com.qbit.Assignment;

import com.qbit.Objects.General;
import com.qbit.Objects.Project;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

/*
 * This is the first class which is called.
 * The main() function is executed at the start.
 * Funtionalities of the main function are
 * a. Load the LookofFeel of System. We can use others (see commented)
 * b. Get the WRITE_TRACK_HOME system varibale and if not set, set it to C:\Config
 * c. Read the media.properties file from the above location. Its stores all the FB and twitter stuffs.
 * d. init function is called.
 */
public class Start implements ActionListener, MenuListener {
    static boolean isWindowShowing = false;
    ActiveTracker mytracker = new ActiveTracker();
    WriDemo demo = new WriDemo();

    public static void main(String[] args) {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            //String lookAndFeel = "javax.swing.plaf.metal.MetalLookAndFeel";
            //logger.info(System.getProperty("---Setup LookANDFeel---"));
            //  UIManager.setLookAndFeel(lookAndFeel);
            // MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
            // handle exception
        }
        //
        String configPath = System.getenv("WRITE_TRACK_HOME");
        if (configPath == null)
            configPath = "C:\\Config";
        System.out.println("ConfigPath" + configPath);
        try {
            File f = new File(configPath + "\\media.properties");
            if (!f.exists() && !f.isDirectory()) {

                Properties props = new Properties();
                FileOutputStream fos = new FileOutputStream(configPath + "\\media.properties");
                props.setProperty("FBAppID", "");
                props.setProperty("FBAppSecret", "");
                props.setProperty("FBAccessToken", "");
                props.setProperty("TWT_consumerKey", "");
                props.setProperty("TWT_consumerSecret", "");
                props.setProperty("TWT_accessToken", "");
                props.setProperty("TWT_accessTokenSecret", "");
                //writing properites into properties file from Java
                props.store(fos, "Please enter the Facebook and twitter tokens in the below fields");

                fos.close();
            }
        } catch (Exception c) {
            c.printStackTrace();
        }
        //
        Start start = new Start();
        start.init();
    }

    JPopupMenu popup;
    TrayIcon trayIcon;
    final JMenu TrackProj = new JMenu("Active Tracker");


    /*
     *  This function is responsible to display the icon on the system tray and its associated event handling.
     *
     */
    public void init() {
        loadFiles();
        checkActivation();
        popup = new JPopupMenu();
        trayIcon = new TrayIcon(createImageIcon("/images/Logo.png", "Active Tracker").getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        final SystemTray tray = SystemTray.getSystemTray();
        final JMenuItem PrefItem = new JMenuItem("Preference");
        PrefItem.setActionCommand("P");
        PrefItem.addActionListener(this);
        ImageIcon icon = null;
        if (ActiveTracker.appStatus)
            icon = new ImageIcon(getClass().getResource("/images/start.PNG"));
        else
            icon = new ImageIcon(getClass().getResource("/images/stop.PNG"));

        TrackProj.setActionCommand("T");
        //TrackProj.addActionListener(this);
        TrackProj.addMenuListener(this);
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.setActionCommand("E");
        exitItem.addActionListener(this);
        popup.add(PrefItem);
        popup.add(new JSeparator());
        popup.add(TrackProj);
        popup.add(new JSeparator());
        popup.add(exitItem);
        final JMenuItem stopTrack = new JMenuItem("STOP Tracker");
        stopTrack.setActionCommand("STOP");
        stopTrack.addActionListener(this);
        //trayIcon.setPopupMenu(popup);
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
            return;
        }

        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Is Window SHowing " + isWindowShowing);
                if (e.getClickCount() == 2 && !isWindowShowing) {
                    // your code here

                    demo.ShowUI();
                    demo.toFront();
                    isWindowShowing = true;
                    demo.addWindowListener(new WindowAdapter() {
                        public void windowClosing(WindowEvent e) {
                            isWindowShowing = false;
                            demo.dispose();
                        }
                    });
                }
            }

            public void mousePressed(MouseEvent e) {
                maybeShowPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                maybeShowPopup(e);
            }

            private void maybeShowPopup(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.repaint();
                    if (ActiveTracker.appStatus) {
                        popup.removeAll();
                        popup.add(PrefItem);
                        popup.add(stopTrack);
                        popup.add(exitItem);
                        stopTrack.setIcon(new ImageIcon(getClass().getResource("/images/stop.PNG")));
                    } else {
                        popup.removeAll();
                        popup.add(PrefItem);
                        popup.add(TrackProj);
                        popup.add(exitItem);
                        TrackProj.setIcon(new ImageIcon(getClass().getResource("/images/start.PNG")));
                        TrackProj.setText("Active Tracker");
                    }
                    popup.repaint();
                    System.out.println("init called" + ActiveTracker.appStatus);
                    popup.setLocation(e.getX() - 50, e.getY() - 50);
                    popup.setInvoker(popup);
                    popup.setVisible(true);
                }
            }
        });
        trayIcon.addActionListener(this);
    }

    private void checkActivation() {
        if (general != null && !general.isActivated()) {
            demo.showActivate(true);
        }
    }

    @Override
    public void menuSelected(MenuEvent e) {
        //  System.out.println("menuSelected"+e.getSource());
        if (!ActiveTracker.appStatus)
            addProjectToMenu();


    }

    @Override
    public void menuDeselected(MenuEvent e) {
        // System.out.println("menuDeselected");
        ((JMenu) e.getSource()).removeAll();


    }

    @Override
    public void menuCanceled(MenuEvent e) {
        System.out.println("menuCanceled");

    }

    /*
     * This function handles the action to be performed when the user clicks one of the item in the icon of teh system tray.
     */
    //ActiveTracker tracker;
    Timer timer;

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command == null) return;
        System.out.println("COmmand" + command);
        if (command.equals("E"))
            System.exit(0);

        if (command.equals("T")) {//start Tracker

        }
        if (command.equals("1")) {
            mytracker.selectedProject = 1;
            mytracker.stopListening();
            mytracker.startListening();
            ActiveTracker.disposeWordCount();
            ActiveTracker.DisplayWordCount();
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            timer.schedule(mytracker.new RemindTask(), 5 * 1000, 10 * 1000);
            project1.setCurrentWords(0);
            System.out.println("Date" + project1.getProjectDeadline());
            if (project1.getProjectDeadline().before(new Date())) {
                JOptionPane.showMessageDialog(null, " " + project1.getProjectTitle() + " deadline already passed. Please change the deadline", "Deadline Past Today", JOptionPane.INFORMATION_MESSAGE);
            }
            WriDemo.proj1SessionStart = false;
            ActiveTracker.appStatus = true;
        }
        if (command.equals("2")) {
            mytracker.selectedProject = 2;
            ActiveTracker.disposeWordCount();
            ActiveTracker.DisplayWordCount();
            mytracker.stopListening();
            mytracker.startListening();
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            timer.schedule(mytracker.new RemindTask(), 5 * 1000, 10 * 1000);
            project2.setCurrentWords(0);
            System.out.println("Date" + project2.getProjectDeadline());
            if (project2.getProjectDeadline().before(new Date())) {
                JOptionPane.showMessageDialog(null, " " + project2.getProjectTitle() + " deadline already passed. Please change the deadline", "Deadline Past Today", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Date" + project2.getProjectDeadline());
            WriDemo.proj2SessionStart = false;
            ActiveTracker.appStatus = true;
        }
        if (command.equals("3")) {
            mytracker.selectedProject = 3;
            mytracker.stopListening();
            mytracker.startListening();
            ActiveTracker.disposeWordCount();
            ActiveTracker.DisplayWordCount();
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            timer.schedule(mytracker.new RemindTask(), 5 * 1000, 10 * 1000);
            project3.setCurrentWords(0);
            System.out.println("Date" + project3.getProjectDeadline());
            if (project3.getProjectDeadline().before(new Date())) {
                JOptionPane.showMessageDialog(null, " " + project3.getProjectTitle() + " deadline already passed. Please change the deadline", "Deadline Past Today", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Date" + project3.getProjectDeadline());
            WriDemo.proj3SessionStart = false;
            ActiveTracker.appStatus = true;
        }
        //for project 4 and 5
        if (command.equals("4") && general.isActivated()) {
            mytracker.selectedProject = 4;
            ActiveTracker.disposeWordCount();
            ActiveTracker.DisplayWordCount();
            mytracker.stopListening();
            mytracker.startListening();
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            timer.schedule(mytracker.new RemindTask(), 5 * 1000, 10 * 1000);
            project4.setCurrentWords(0);
            System.out.println("Date" + project4.getProjectDeadline());
            if (project4.getProjectDeadline().before(new Date())) {
                JOptionPane.showMessageDialog(null, " " + project4.getProjectTitle() + " deadline already passed. Please change the deadline", "Deadline Past Today", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Date" + project4.getProjectDeadline());
            WriDemo.proj4SessionStart = false;
            ActiveTracker.appStatus = true;
        }
        if (command.equals("5") && general.isActivated()) {
            mytracker.selectedProject = 5;
            mytracker.stopListening();
            mytracker.startListening();
            ActiveTracker.disposeWordCount();
            ActiveTracker.DisplayWordCount();
            if (timer != null)
                timer.cancel();
            timer = new Timer();
            timer.schedule(mytracker.new RemindTask(), 5 * 1000, 10 * 1000);
            project5.setCurrentWords(0);
            System.out.println("Date" + project5.getProjectDeadline());
            if (project5.getProjectDeadline().before(new Date())) {
                JOptionPane.showMessageDialog(null, " " + project5.getProjectTitle() + " deadline already passed. Please change the deadline", "Deadline Past Today", JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Date" + project5.getProjectDeadline());
            WriDemo.proj5SessionStart = false;
            ActiveTracker.appStatus = true;
        }
        //
        if (command.equals("P") && !isWindowShowing) {//start Preference
            //WriDemo demo = new WriDemo();
            demo.ShowUI();
            //check the deadlines
            Date today = new Date();
            StringBuilder str = new StringBuilder("");
            if (project1 != null && project1.getProjectDeadline().before(today))
                str.append(project1.getProjectTitle() + " deadline already passed. Please change the deadline\n");

            if (project2 != null && project2.getProjectDeadline().before(today))
                str.append(project2.getProjectTitle() + " deadline already passed. Please change the deadline\n");

            if (project3 != null && project3.getProjectDeadline().before(today))
                str.append(project3.getProjectTitle() + " deadline already passed. Please change the deadline");

            if (general.isActivated()) {
                if (project4 != null && project4.getProjectDeadline().before(today))
                    str.append(project4.getProjectTitle() + " deadline already passed. Please change the deadline\n");

                if (project5 != null && project5.getProjectDeadline().before(today))
                    str.append(project5.getProjectTitle() + " deadline already passed. Please change the deadline");
            }

            if (!str.toString().equals(""))
                JOptionPane.showMessageDialog(null, str.toString(), "Deadline Past Today", JOptionPane.INFORMATION_MESSAGE);
        }
        if (command.equals("STOP")) {
            ActiveTracker.disposeWordCount();
            mytracker.selectedProject = 0;
            ActiveTracker.appStatus = false;
            mytracker.stopListening();
            if (timer != null)
                timer.cancel();
        }

    }

    //
    /*
	 * It checks whether the project is already created by the user or not.
	 * And if yes, then it loads the corresponding files and creates a menu item to the original system tray menu.  
	 */
    private void addProjectToMenu() {
        loadFiles();

        if (project1 != null) {
            JMenuItem proj1 = new JMenuItem(project1.getProjectTitle());
            proj1.setActionCommand("1");
            proj1.setFont(new Font("Arial", Font.BOLD, 13));
            proj1.addActionListener(this);
            TrackProj.add(proj1);

        }
        if (project2 != null) {
            JMenuItem proj2 = new JMenuItem(project2.getProjectTitle());
            proj2.setActionCommand("2");
            proj2.setFont(new Font("Arial", Font.BOLD, 13));
            proj2.addActionListener(this);
            TrackProj.add(proj2);
        }
        if (project3 != null) {
            JMenuItem proj3 = new JMenuItem(project3.getProjectTitle());
            proj3.setActionCommand("3");
            proj3.setFont(new Font("Arial", Font.BOLD, 13));
            proj3.addActionListener(this);
            TrackProj.add(proj3);
        }

        if (general.isActivated()) {
            if (project4 != null) {
                JMenuItem proj4 = new JMenuItem(project4.getProjectTitle());
                proj4.setActionCommand("4");
                proj4.setFont(new Font("Arial", Font.BOLD, 13));
                proj4.addActionListener(this);
                TrackProj.add(proj4);
            }
            if (project5 != null) {
                JMenuItem proj5 = new JMenuItem(project5.getProjectTitle());
                proj5.setActionCommand("5");
                proj5.setFont(new Font("Arial", Font.BOLD, 13));
                proj5.addActionListener(this);
                TrackProj.add(proj5);
            }
        }

        // editMenu.show(this, 20, 20);
    }

    //


    static String configPath;
    static Project project1;
    static Project project2;
    static Project project3;
    static Project project4;
    static Project project5;
    static General general;

    //Load files into memmory
	/*
	 * This function loads all the project files(if present) into the meory.
	 * We used to store the project attributes into files with extension .ser
	 * The project object is directly saved so as to ensure that the user cant change in background.
	 */
    public void loadFiles() {
        configPath = System.getenv("WRITE_TRACK_HOME");
        if (configPath == null)
            configPath = "C:\\Config";
        mytracker.configPath = configPath;
        ObjectInputStream ois;

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\general.ser");
            ois = new ObjectInputStream(fin);
            general = (General) ois.readObject();
            mytracker.general = general;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project1.ser");
            ois = new ObjectInputStream(fin);
            project1 = (Project) ois.readObject();
            mytracker.project1 = project1;
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project2.ser");
            ois = new ObjectInputStream(fin);
            project2 = (Project) ois.readObject();
            mytracker.project2 = project2;
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project3.ser");
            ois = new ObjectInputStream(fin);
            project3 = (Project) ois.readObject();
            mytracker.project3 = project3;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        //for project 4 and 5
        if (general.isActivated()) {
            try {
                FileInputStream fin = new FileInputStream(configPath + "\\project4.ser");
                ois = new ObjectInputStream(fin);
                project4 = (Project) ois.readObject();
                mytracker.project4 = project4;
            } catch (Exception e) {
                //e.printStackTrace();
            }

            try {
                FileInputStream fin = new FileInputStream(configPath + "\\project5.ser");
                ois = new ObjectInputStream(fin);
                project5 = (Project) ois.readObject();
                mytracker.project5 = project5;
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
    }
    //


    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}
