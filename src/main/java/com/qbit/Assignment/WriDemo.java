package com.qbit.Assignment;

import com.qbit.Dialogs.ActivateDialog;
import com.qbit.Objects.General;
import com.qbit.Objects.Project;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.auth.NullAuthorization;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import twitter4j.TwitterFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WriDemo extends JFrame implements NativeKeyListener, ActionListener, FocusListener, ItemListener {

    private JTabbedPane tabbedPane;
    private String names[];
    private boolean showActivate;

    //general Tab
    JTextField txt_Name;
    JTextField txt_email;
    JComboBox txt_tz;
    JTextField txt_fb;
    JTextField txt_twt;
    JCheckBox isLaunched;
    JCheckBox isMinimized;
    JCheckBox isShowCount;

    StandardButton btnGeneralSave = new StandardButton("    Save    ");
//    StandardButton btnProjectEdit;
    StandardButton btnProjectSave;

    JLabel lblStatus;
    JRadioButton radio_total;
    JRadioButton radio_session;

    //Color bgColor = new Color(176,204,210);
    static Font font = new Font("Times New Roman", Font.PLAIN, 13);
    static Font fontBold = new Font("Times New Roman", Font.BOLD, 12);
    int easternTimeZone = 0;

    public WriDemo() {

        MyTimeZone tz = new MyTimeZone();
        String ids[] = tz.getAvailableIDs();  //loading all the timezone so that we can display that on the preference page.
        names = new String[ids.length];
        for (int i = 0; i < ids.length; i++) {
            tz.setID(ids[i]);
            names[i] = tz.toString();

        }

        for (int i = 0; i < ids.length; i++) {
            //System.out.println(ids[i]);
            if (ids[i].equalsIgnoreCase("US/Eastern")) {
                easternTimeZone = i;
                System.out.println("Time zone id" + easternTimeZone);
            }
        }
    }

    public void showActivate(boolean showActivate) {
        this.showActivate = showActivate;
    }

    public void focusGained(FocusEvent e) {
       /* System.out.println("Gained "+((JTextField)e.getSource()).getName());
        String Name = ((JTextField)e.getSource()).getName();
        if(Name.equalsIgnoreCase("GEN_NAME")) {
        	lblStatus.setText("");
        	lblStatus.setText("Please enter String values only");
        }
        if(Name.equalsIgnoreCase("GEN_EMAIl")) {
        	lblStatus.setText("");
        	lblStatus.setText("Please enter Proper Email Only");
        }
        if(Name.equalsIgnoreCase("GEN_NAME")) {
        	lblStatus.setText("");
        	lblStatus.setText("Please enter String values only");
        }
        if(Name.equalsIgnoreCase("Gen_TWT")) {
        	lblStatus.setText("");
        	lblStatus.setText("Please enter String values only");
        }
        if(Name.equalsIgnoreCase("Gen_FB")) {
        	lblStatus.setText("");
        	lblStatus.setText("Please enter String values only");
        }
        
        
        //For Project Tabs
        if(Name.equalsIgnoreCase("Proj_Title") ||
           Name.equalsIgnoreCase("Proj_Deadline") ||	
           Name.equalsIgnoreCase("Proj_1000") ||
           Name.equalsIgnoreCase("Proj_2000") ||
           Name.equalsIgnoreCase("Proj_5000") ||
           Name.equalsIgnoreCase("Proj_10000") ||
           Name.equalsIgnoreCase("Proj_Mile_rew") ||
           Name.equalsIgnoreCase("Proj_Mil_Pen") ||
           Name.equalsIgnoreCase("Proj_Comp_rew")) {
        	lblStatus.setText("");
        	lblStatus.setText("Please enter String values only");
        }*/


    }

    public void focusLost(FocusEvent e) {
        //displayMessage("Focus lost", e);
        System.out.println("Lost " + ((JTextField) e.getSource()).getName());
        String Name = ((JTextField) e.getSource()).getName();
        if (Name.equalsIgnoreCase("GEN_NAME")) {
            lblStatus.setText("");

        }
        if (Name.startsWith("Proj_Title")) {
            String NameT = ((JTextField) e.getSource()).getName();
            String titleT = ((JTextField) e.getSource()).getText();
            if (titleT.trim().equalsIgnoreCase(""))
                return;
            System.out.println("Name is " + ((JTextField) e.getSource()).getName());
            int i = Integer.parseInt(NameT.substring(10));
            System.out.println("Title" + ((JTextField) e.getSource()).getText() + "Tab:" + i);
            tabbedPane.setTitleAt(i, ((JTextField) e.getSource()).getText());
            //tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(),((JTextField)e.getSource()).getText());
        }
        if (Name.equalsIgnoreCase("GEN_EMAIl")) {
            lblStatus.setText("");

        }
        if (Name.equalsIgnoreCase("GEN_NAME")) {
            lblStatus.setText("");

        }
        if (Name.equalsIgnoreCase("Gen_TWT")) {
            lblStatus.setText("");

        }
        if (Name.equalsIgnoreCase("Gen_FB")) {
            lblStatus.setText("");

        }


        //For Project Tabs
        if (Name.equalsIgnoreCase("Proj_Title") ||
                Name.equalsIgnoreCase("Proj_Deadline") ||
                Name.equalsIgnoreCase("Proj_1000") ||
                Name.equalsIgnoreCase("Proj_2000") ||
                Name.equalsIgnoreCase("Proj_5000") ||
                Name.equalsIgnoreCase("Proj_10000") ||
                Name.equalsIgnoreCase("Proj_Mile_rew") ||
                Name.equalsIgnoreCase("Proj_Mil_Pen") ||
                Name.equalsIgnoreCase("Proj_Comp_rew")) {
            lblStatus.setText("");

        }
    }


    public String validatePrefernece() {
//		if(txt_Name.getText().trim().equals(""))
//			return "Please Enter Name";
//		if(txt_email.getText().trim().equals(""))
//			return "Please Enter Email";
//		String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
//		if(!txt_email.getText().trim().matches(EMAIL_REGEX))
//			return "Please Enter Valid EMail";
//		if(txt_fb.getText().trim().equals(""))
//			return "Please Enter FaceBook ID";
//		if(!txt_fb.getText().trim().matches(EMAIL_REGEX))
//			return "Please Enter Valid Facebook ID";
//		if(txt_twt.getText().trim().equals(""))
//			return "Please Enter Twitter ID";
        //if(!txt_twt.getText().trim().matches(EMAIL_REGEX))
        //return "Please Enter Valid Twitter ID";
        return "SUCCESS";
    }

    /*
     * The main function which is called when the user clicks on any item on the system tray.
     * It creates the Pereference Page and then depending on the number of projects created by the user, it loads them.
     * Does all the calculation .
     */
    private void initUI(General general) {

        setTitle("Writer Demo");
        try {
            setIconImage(ImageIO.read(getClass().getResource("/images/WW.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        //

        //


        tabbedPane = new JTabbedPane();
        tabbedPane.setUI(new MyTabbedPaneUI(tabbedPane));
        tabbedPane.setFont(fontBold);

        //tabbedPane.setBorder(new EmptyBorder(1,1,1,1));

        RoundedPanel panel1 = new RoundedPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();


        JLabel lbl1 = new JLabel("<html><b>Full Name:</html>");
        lbl1.setFont(font);
        //lbl1.setFont(new Font("Arial", Font.BOLD, 15));
        c.insets = new Insets(10, 10, 10, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel1.add(lbl1, c);


        txt_Name = new JTextField();
        txt_Name.setFont(font);
        txt_Name.setPreferredSize(new Dimension(150, 25));
        if (general != null)
            txt_Name.setText(general.getFullName());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 0;
        panel1.add(txt_Name, c);
        txt_Name.setName("Gen_Name");
        //txt_Name.)
        txt_Name.addFocusListener(this);
        addTxtFieldListener(txt_Name);

        JLabel lbl_bl = new JLabel("");
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.REMAINDER;
        c.gridx = 2;
        c.gridy = 0;
        panel1.add(lbl_bl, c);


        JLabel lbl2 = new JLabel("<html><b>Email Address:<html/>");
        lbl2.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel1.add(lbl2, c);


        txt_email = new JTextField();
        txt_email.setFont(font);
        txt_email.setPreferredSize(new Dimension(150, 25));
        if (general != null)
            txt_email.setText(general.getEmailAddress());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 1;
        panel1.add(txt_email, c);
        txt_email.setName("Gen_Email");
        //txt_Name.)
        txt_email.addFocusListener(this);
        addTxtFieldListener(txt_email);


        JLabel lbl3 = new JLabel("<html><b>Time Zone:<html/>");
        lbl3.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel1.add(lbl3, c);


        txt_tz = new JComboBox(names);
        txt_tz.setFont(font);
        //txt_tz.setBackground(bgColor);
        txt_tz.setPreferredSize(new Dimension(150, 25));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 2;

        if (general != null) {
            txt_tz.setSelectedIndex(general.getTimeZone());
        } else {
            if (easternTimeZone != 0)
                txt_tz.setSelectedIndex(easternTimeZone);
            else
                txt_tz.setSelectedIndex(1);
        }
        panel1.add(txt_tz, c);
        addComboBoxListener(txt_tz);

        JLabel lbl4 = new JLabel("<html><b>Facebook UserName:<html/>");
        lbl4.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 3;
        panel1.add(lbl4, c);


        txt_fb = new JTextField();
        txt_fb.setFont(font);
        txt_fb.setPreferredSize(new Dimension(150, 25));
        if (general != null)
            txt_fb.setText(general.getFbUserName());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        panel1.add(txt_fb, c);
        txt_fb.setName("Gen_FB");
        //txt_Name.)
        txt_fb.addFocusListener(this);
        addTxtFieldListener(txt_fb);


        JLabel lbl5 = new JLabel("<html><b>Twitter UserName:<html/>");
        lbl5.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 4;
        panel1.add(lbl5, c);


        txt_twt = new JTextField();
        txt_twt.setFont(font);
        txt_twt.setPreferredSize(new Dimension(150, 25));
        if (general != null)
            txt_twt.setText(general.getTwitUserName());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        panel1.add(txt_twt, c);
        txt_twt.setName("Gen_TWT");
        //txt_Name.)
        txt_twt.addFocusListener(this);
        addTxtFieldListener(txt_twt);

        JLabel lbl6 = new JLabel("<html><b>WriTracker Launches<br>At Startup:<html/>");
        lbl6.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 5;
        panel1.add(lbl6, c);

        isLaunched = new JCheckBox("");
        isLaunched.setOpaque(false);

        if (general != null)
            isLaunched.setSelected(general.isLaunchStartUp());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 5;
        panel1.add(isLaunched, c);
        addCheckBoxListener(isLaunched);


        JLabel lbl7 = new JLabel("<html><b>Keep Minimized<br>While Running:<html/>");
        lbl7.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 6;
        panel1.add(lbl7, c);


        isMinimized = new JCheckBox("");
        isMinimized.setOpaque(false);
        //isMinimized.setBackground(bgColor);
        if (general != null)
            isMinimized.setSelected(general.isMinimized());
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 6;
        panel1.add(isMinimized, c);
        addCheckBoxListener(isMinimized);

        //new requirement
        JLabel lbl8 = new JLabel("<html><b>Show Word Count<br>When Active:<html/>");
        lbl8.setFont(font);
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 7;
        panel1.add(lbl8, c);

        RoundedPanel panWord = new RoundedPanel();
        //panWord.setBackground(bgColor);
        FlowLayout lay = new FlowLayout();
        lay.setAlignment(FlowLayout.LEFT);
        panWord.setLayout(lay);

        isShowCount = new JCheckBox("");
        isShowCount.setName("ShowWordCheckbox");
        isShowCount.setOpaque(false);
        isShowCount.addItemListener(this);
        //isShowCount.setBackground(bgColor);

        panWord.add(isShowCount);
        isShowCount.setActionCommand("ShowCount");
        isShowCount.addActionListener(this);
        addCheckBoxListener(isShowCount);
        boolean isShow = false;
        if (general != null && general.getShowCount() != 0)
            isShow = true;

        radio_total = new JRadioButton("Show Total Words");
        radio_total.setFont(font);
        radio_total.setOpaque(false);
        //radio_total.setBackground(bgColor);
        panWord.add(radio_total);
        radio_total.setVisible(isShow);
        addRadioButtonListener(radio_total);
        //if(isShowCount.isSelected())
        //radio_total.setSelected(true);

        radio_session = new JRadioButton("Show Session Totals");
        radio_session.setFont(font);
        radio_session.setOpaque(false);
        //radio_session.setBackground(bgColor);
        panWord.add(radio_session);
        radio_session.setVisible(isShow);
        addRadioButtonListener(radio_session);

        ButtonGroup wordCountGroup = new ButtonGroup();
        wordCountGroup.add(radio_total);
        wordCountGroup.add(radio_session);

        if (general != null && general.getShowCount() != 0) // check showcount values
            isShowCount.setSelected(true);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.gridx = 1;
        c.gridy = 7;
        panel1.add(panWord, c);
        // End of new REqu.


        lblStatus = new JLabel("");
        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 8;
        panel1.add(lblStatus, c);


        JPanel pnlBottom = new JPanel();
        pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnGeneralSave.setFont(font);
        btnGeneralSave.setBackground(Color.gray);
        btnGeneralSave.setForeground(Color.black);
        btnGeneralSave.setActionCommand("GS");  //i.e. General Save
        btnGeneralSave.addActionListener(this);
        btnGeneralSave.setVisible(false);
        pnlBottom.add(btnGeneralSave);

        if (showActivate) {
            StandardButton btn_Activate = new StandardButton("    Activate    ");
            btn_Activate.setActionCommand("AC");
            btn_Activate.setFont(font);
            btn_Activate.addActionListener(this);
            pnlBottom.add(btn_Activate);
        }

        //pnlBottom.setBackground(bgColor);

        //c.fill = GridBagConstraints.HORIZONTAL;
        //c.ipady = 0;       //reset to default
        //c.weighty = 1.0;   //request any extra vertical space
        //c.anchor = GridBagConstraints.PAGE_START; //bottom of space
        //c.insets = new Insets(10,0,0,0);  //top padding
        //c.gridx = 1;       //aligned with button 2
        //c.gridwidth = 2;   //2 columns wide
        //c.gridy = 8;       //third row

        c.weightx = 0.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 9;
        pnlBottom.setOpaque(false);
        panel1.add(pnlBottom, c);

        //.setBackground(bgColor);
        tabbedPane.addTab("General", panel1);
        //pnl_Proj1 = getProject(1);
        if (project1 != null)
            tabbedPane.addTab(project1.getProjectTitle(), getProject(1));
        else
            tabbedPane.addTab("Project 1", getProject(1));
        //pnl_Proj2 = getProject(2);
        if (project2 != null)
            tabbedPane.addTab(project2.getProjectTitle(), getProject(2));
        else
            tabbedPane.addTab("Project 2", getProject(2));
        //pnl_Proj3 = getProject(3);
        if (project3 != null)
            tabbedPane.addTab(project3.getProjectTitle(), getProject(3));
        else
            tabbedPane.addTab("Project 3", getProject(3));
        //for porject 4 and 5
        //we will add a condition here for license version
        if (general != null && general.isActivated()) {
            if (project4 != null) {
                tabbedPane.addTab(project4.getProjectTitle(), getProject(4));
            } else {
                tabbedPane.addTab("Project 4", getProject(4));
            }

            if (project5 != null) {
                tabbedPane.addTab(project5.getProjectTitle(), getProject(5));
            } else {
                tabbedPane.addTab("Project 5", getProject(5));
            }
        }

        topPanel.add(tabbedPane, BorderLayout.CENTER);
        setSize(1300, 600);
        setLocationRelativeTo(null);
        //
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        ImageIcon image = new ImageIcon(getClass().getResource("/images/Logo.png"));
        JLabel labelLeft = new JLabel("", image, JLabel.CENTER);
        labelLeft.setName("Logo");
        leftPanel.add(labelLeft);
        goWebsite(labelLeft);
        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));

        JLabel label = new JLabel("About WriteTracker");
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label.setToolTipText("www.writetracker.com");
        label.setFont(fontBold);
        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));
        leftPanel.add(label);
        label.setName("About");
        goWebsite(label);

        JLabel label1 = new JLabel("Support");
        label1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label1.setToolTipText("admin@evicuna.com");
        label1.setFont(fontBold);
        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));
        leftPanel.add(label1);
        label1.setName("Support");
        goWebsite(label1);

        JLabel label2 = new JLabel("WriteTracker.com");
        label2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label2.setToolTipText("www.writetracker.com");
        label2.setFont(fontBold);
        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));
        leftPanel.add(label2);
        label2.setName("WriteTracker");
        goWebsite(label2);

        JLabel label3 = new JLabel("Contact Us");
        label3.setCursor(new Cursor(Cursor.HAND_CURSOR));
        label3.setToolTipText("admin@evicuna.com");
        label3.setFont(fontBold);
        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));
        leftPanel.add(label3);
        label3.setName("Support");
        goWebsite(label3);


        StandardButton btnFacebook = new StandardButton("Connect To Facebook");
        btnFacebook.setBackground(Color.gray);
        btnFacebook.setFont(font);
        btnFacebook.setForeground(Color.black);
        btnFacebook.setActionCommand("FB");
        btnFacebook.setName("btnFacebook");
        btnFacebook.addActionListener(this);
        btnFacebook.setVisible(true);

        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));
        leftPanel.add(btnFacebook);


        StandardButton btnTwitter = new StandardButton("  Connect To Twitter   ");
        btnTwitter.setBackground(Color.gray);
        btnTwitter.setFont(font);
        btnTwitter.setForeground(Color.black);
        btnTwitter.setActionCommand("TW");
        btnTwitter.setName("btnTwitter");
        btnTwitter.addActionListener(this);
        btnTwitter.setVisible(true);

        leftPanel.add(Box.createRigidArea(new Dimension(5, 25)));
        leftPanel.add(btnTwitter);

        //design the left panel

        //
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftPanel, topPanel);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(228);

        //Provide minimum sizes for the two components in the split pane
        Dimension minimumSize = new Dimension(228, 50);
        leftPanel.setMinimumSize(minimumSize);
        topPanel.setMinimumSize(minimumSize);
        //
        //setContentPane(topPanel);
        setContentPane(splitPane);
        //  setDefaultCloseOperation(DISPOSE_ON_CLOSE);


    }


    private void goWebsite(JLabel website) {
        website.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    if (e.getComponent().getName().equalsIgnoreCase("Support")) {
                        String mailTo = "admin@evicuna.com";
                        URI uriMailTo = null;
                        try {
                            if (mailTo.length() > 0) {
                                uriMailTo = new URI("mailto", mailTo, null);
                                desktop.mail(uriMailTo);
                            } else {
                                desktop.mail();
                            }
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        } catch (URISyntaxException use) {
                            use.printStackTrace();
                        }
                    } else {
                        desktop.browse(new URI("http://www.writracker.com"));
                    }
                } catch (Exception ex) {
                    //It looks like there's a problem
                }
            }
        });
    }

    //JTextField txt_ProjName;
    JTextField txt_deadline;
    JComboBox cmb_ProjType;
    JTextField txt_projGoal;
    JComboBox cmb_intervals;
    JCheckBox isPost;
    JTextField txt_1000[] = new JTextField[5];
    JTextField txt_Milestone_reward;
    JTextField txt_2000[] = new JTextField[5];
    JTextField txt_Milestone_penalty;
    JTextField txt_5000[] = new JTextField[5];
    JTextField txt_10000[] = new JTextField[5];
    JTextField txt_completion;
    JDatePickerImpl datePicker;


    ArrayList<Object> proj1Lists = new ArrayList<>();
    ArrayList<Object> proj2Lists = new ArrayList<>();
    ArrayList<Object> proj3Lists = new ArrayList<>();
    ArrayList<Object> proj4Lists = new ArrayList<>();
    ArrayList<Object> proj5Lists = new ArrayList<>();

    JLabel lbl_1000[] = new JLabel[5];
    JLabel lbl_2000[] = new JLabel[5];
    JLabel lbl_5000[] = new JLabel[5];
    JLabel lbl_10000[] = new JLabel[5];

    JLabel lbl_wordstocomplete[] = new JLabel[5];
    JLabel lbl_totalwords[] = new JLabel[5];
    JLabel lbl_wordgoal[] = new JLabel[5];
    JLabel lbl_milestonecount[] = new JLabel[5];
    JLabel lbl_daysLeft[] = new JLabel[5];

    JTextField txt_ProjName[] = new JTextField[5];

    public JPanel getProject(int projID) {
        //Get the name of the writer from general
        String Name = "[NAME]";
        ObjectInputStream ois;
        try {
            FileInputStream fin = new FileInputStream(configPath + "\\general.ser");
            ois = new ObjectInputStream(fin);
            general = (General) ois.readObject();
            Name = general.getFullName();
        } catch (Exception e) {
            //e.printStackTrace();
            Name = "[NAME]";
        }
        //
        Project proj = null;
        if (projID == 1 && project1 != null)
            proj = project1;
        if (projID == 2 && project2 != null)
            proj = project2;
        if (projID == 3 && project3 != null)
            proj = project3;
        if (projID == 4 && project4 != null)
            proj = project4;
        if (projID == 5 && project5 != null)
            proj = project5;

        //JPanel panel = new JPanel();
        RoundedPanel panel = new RoundedPanel();
        panel.setLayout(new GridBagLayout());
        //panel.setBackground(bgColor);
        GridBagConstraints c = new GridBagConstraints();

        //Row 1

        JLabel lbl1 = new JLabel("<html><b>Project " + projID + " Title:</html>");
        lbl1.setFont(font);
        c.insets = new Insets(5, 5, 5, 2);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(lbl1, c);


        txt_ProjName[projID - 1] = new JTextField();
        txt_ProjName[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 2;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(txt_ProjName[projID - 1], c);
        txt_ProjName[projID - 1].setName("Proj_Title" + (projID));
        txt_ProjName[projID - 1].addFocusListener(this);
        if (proj != null) {
            txt_ProjName[projID - 1].setText(proj.getProjectTitle());
        } else {
            txt_ProjName[projID - 1].setText("Project " + projID);
        }
        addTxtFieldListener(txt_ProjName[projID - 1]);

        switch (projID) {
            case 1:
                proj1Lists.add(txt_ProjName[projID - 1]);
                break;
            case 2:
                proj2Lists.add(txt_ProjName[projID - 1]);
                break;
            case 3:
                proj3Lists.add(txt_ProjName[projID - 1]);
                break;
            case 4:
                proj4Lists.add(txt_ProjName[projID - 1]);
                break;
            case 5:
                proj5Lists.add(txt_ProjName[projID - 1]);
                break;

            default:
                break;
        }


        JLabel lbl2 = new JLabel("<html><b>Deadline:</html>");
        lbl2.setFont(font);
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(lbl2, c);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        // Don't know about the formatter, but there it is...
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setName("Proj_Deadline");


        //txt_deadline = new JTextField();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 2;
        c.gridx = 3;
        c.gridy = 0;
        panel.add(datePicker, c);
        addDatePickerListener(datePicker);
        //datePicker.setBackground(bgColor);
        if (proj != null) {
            System.out.println("Date tt" + proj.getProjectDeadline());
            Calendar cal = Calendar.getInstance();
            cal.setTime(proj.getProjectDeadline());
            model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            model.setSelected(true);
        }
        //txt_deadline.setText(proj.getProjectDeadline().toString());
        switch (projID) {
            case 1:
                proj1Lists.add(datePicker);
                break;
            case 2:
                proj2Lists.add(datePicker);
                break;
            case 3:
                proj3Lists.add(datePicker);
                break;
            case 4:
                proj4Lists.add(datePicker);
                break;
            case 5:
                proj5Lists.add(datePicker);
                break;

            default:
                break;
        }


        JLabel lbl_bl = new JLabel("");
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.REMAINDER;
        c.gridx = 5;
        c.gridy = 0;
        panel.add(lbl_bl, c);

        //Row 2
        JLabel lbl3 = new JLabel("<html><b>Project Type:</html>");
        lbl3.setFont(font);
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        panel.add(lbl3, c);


        String[] proj_Type = {"Novel", "Blog Post", "Essay", "Journal", "Novella", "Novellette", "Script", "Short Story", "Text Book", "Misc"};
        cmb_ProjType = new JComboBox(proj_Type);
        cmb_ProjType.setFont(font);
        //cmb_ProjType.setBackground(bgColor);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 1;
        panel.add(cmb_ProjType, c);
        if (proj != null) {
            cmb_ProjType.setSelectedIndex(proj.getProjectType());
        }
        addComboBoxListener(cmb_ProjType);
        switch (projID) {
            case 1:
                proj1Lists.add(cmb_ProjType);
                break;
            case 2:
                proj2Lists.add(cmb_ProjType);
                break;
            case 3:
                proj3Lists.add(cmb_ProjType);
                break;
            case 4:
                proj4Lists.add(cmb_ProjType);
                break;
            case 5:
                proj5Lists.add(cmb_ProjType);
                break;

            default:
                break;
        }

        cmb_ProjType.addItemListener(this);


        JLabel lbl4 = new JLabel("<html><b>Countdown:</html>");
        lbl4.setFont(font);
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 1;
        panel.add(lbl4, c);


        lbl_daysLeft[projID - 1] = new JLabel("<html><b> Days left:</html>");
        lbl_daysLeft[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 3;
        c.gridy = 1;
        panel.add(lbl_daysLeft[projID - 1], c);
        if (proj != null)
            lbl_daysLeft[projID - 1].setText("<html><b>" + TimeUnit.DAYS.convert((proj.getProjectDeadline().getTime() - System.currentTimeMillis()), TimeUnit.MILLISECONDS) + " Days left:</html>");


        //Row 3
        lbl_wordgoal[projID - 1] = new JLabel("<html><b>Word Goal:</html>");
        lbl_wordgoal[projID - 1].setFont(font);
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(lbl_wordgoal[projID - 1], c);

        //NumberFormat format = NumberFormat.getNumberInstance();
        txt_projGoal = new JTextField();
        txt_projGoal.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 2;
        panel.add(txt_projGoal, c);
        txt_projGoal.setName("Proj_wordGoal");
        if (proj != null) {
            txt_projGoal.setText(proj.getWordGoal() + "");
        }
        addTxtFieldListener(txt_projGoal);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_projGoal);
                break;
            case 2:
                proj2Lists.add(txt_projGoal);
                break;
            case 3:
                proj3Lists.add(txt_projGoal);
                break;
            case 4:
                proj4Lists.add(txt_projGoal);
                break;
            case 5:
                proj5Lists.add(txt_projGoal);
                break;

            default:
                break;
        }
        //


        lbl_totalwords[projID - 1] = new JLabel("<html><b>Current<br>Total Words:</html>");
        lbl_totalwords[projID - 1].setFont(font);
        //c.insets = new Insets(5,5,5,5);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 2;
        panel.add(lbl_totalwords[projID - 1], c);
        if (proj != null)
            lbl_totalwords[projID - 1].setText("<html><b>Current<br>Total Words:" + proj.getCurrentWords() + "</html>");


        lbl_wordstocomplete[projID - 1] = new JLabel("<html><b>Words <br>Left to Complete:</html>");
        lbl_wordstocomplete[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 3;
        c.gridy = 2;
        panel.add(lbl_wordstocomplete[projID - 1], c);
        if (proj != null)
            lbl_wordstocomplete[projID - 1].setText("<html><b>Words <br>Left to Complete: " + (proj.getWordGoal() - proj.getWordsTillDate()) + "</html>");
        //lbl33.setText("<html><b>Words <br>Left to Complete: "+(proj.getWordGoal() - proj.getCurrentWords()) +"</html>");


        JLabel lbl41 = new JLabel("<html><b>Intervals:</html>");
        lbl41.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 3;
        panel.add(lbl41, c);


        String[] proj_Intervals = {"Daily", "Weekly", "Monthly"};
        cmb_intervals = new JComboBox(proj_Intervals);
        //cmb_intervals.setBackground(bgColor);
        cmb_intervals.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 3;
        panel.add(cmb_intervals, c);
        if (proj != null) {
            cmb_intervals.setSelectedIndex(proj.getInterval());
        }
        addComboBoxListener(cmb_intervals);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(cmb_intervals);
                break;
            case 2:
                proj2Lists.add(cmb_intervals);
                break;
            case 3:
                proj3Lists.add(cmb_intervals);
                break;
            case 4:
                proj4Lists.add(cmb_intervals);
                break;
            case 5:
                proj5Lists.add(cmb_intervals);
                break;

            default:
                break;
        }


        int MileStoneWordCount = 0;
        if (proj != null)
            MileStoneWordCount = Util.checkMilestoneReward(proj);
        lbl_milestonecount[projID - 1] = new JLabel("<html><b>Milestone<br>words count: " + MileStoneWordCount + "</html>");
        lbl_milestonecount[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 2;
        c.gridy = 3;
        panel.add(lbl_milestonecount[projID - 1], c);


        JLabel lbl51 = new JLabel("<html><b>Post on<br>Social Networks</html>");
        lbl51.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 4;
        panel.add(lbl51, c);


        isPost = new JCheckBox();
        isPost.setOpaque(false);
        //isPost.setBackground(bgColor);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 4;
        panel.add(isPost, c);
        if (proj != null) {
            isPost.setSelected(proj.isPostSocMedia());
        }
        addCheckBoxListener(isPost);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(isPost);
                break;
            case 2:
                proj2Lists.add(isPost);
                break;
            case 3:
                proj3Lists.add(isPost);
                break;
            case 4:
                proj4Lists.add(isPost);
                break;
            case 5:
                proj5Lists.add(isPost);
                break;

            default:
                break;
        }


        lbl_1000[projID - 1] = new JLabel("<html><b>1,000 Words <br>Reward:</html>");
        lbl_1000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 5;
        panel.add(lbl_1000[projID - 1], c);

        txt_1000[projID - 1] = new JTextField();
        txt_1000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 5;
        panel.add(txt_1000[projID - 1], c);
        txt_1000[projID - 1].setName("Proj_1000");
        if (proj != null) {
            txt_1000[projID - 1].setText(proj.getReward1000());
        }
        else {
            txt_1000[projID - 1].setText("Congratulations! " + Name + " hit 1,000 words!");
        }
        addTxtFieldListener(txt_1000[projID - 1]);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_1000[projID - 1]);
                break;
            case 2:
                proj2Lists.add(txt_1000[projID - 1]);
                break;
            case 3:
                proj3Lists.add(txt_1000[projID - 1]);
                break;
            case 4:
                proj4Lists.add(txt_1000[projID - 1]);
                break;
            case 5:
                proj5Lists.add(txt_1000[projID - 1]);
                break;

            default:
                break;
        }


        JLabel lbl62 = new JLabel("<html><b>Milestone <br>Reward:</html>");
        lbl62.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 2;
        c.gridy = 5;
        panel.add(lbl62, c);

        txt_Milestone_reward = new JTextField();
        txt_Milestone_reward.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 3;
        c.gridy = 5;
        panel.add(txt_Milestone_reward, c);
        txt_Milestone_reward.setName("Proj_Mile_rew");
        if (proj != null) {
            txt_Milestone_reward.setText(proj.getRewardMilestone());
        }
        else {
            txt_Milestone_reward.setText(Name + " made a milestone writing goal! Kudos are appreciated!");
        }
        addTxtFieldListener(txt_Milestone_reward);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_Milestone_reward);
                break;
            case 2:
                proj2Lists.add(txt_Milestone_reward);
                break;
            case 3:
                proj3Lists.add(txt_Milestone_reward);
                break;
            case 4:
                proj4Lists.add(txt_Milestone_reward);
                break;
            case 5:
                proj5Lists.add(txt_Milestone_reward);
                break;

            default:
                break;
        }

        //
        lbl_2000[projID - 1] = new JLabel("<html><b>2,000 Words <br>Reward:</html>");
        lbl_2000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 6;
        panel.add(lbl_2000[projID - 1], c);

        txt_2000[projID - 1] = new JTextField();
        txt_2000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 6;
        panel.add(txt_2000[projID - 1], c);
        txt_2000[projID - 1].setName("Proj_2000");
        if (proj != null) {
            txt_2000[projID - 1].setText(proj.getReward2000());
        }
        else {
            txt_2000[projID - 1].setText("Hooray! " + Name + " wrote 2,000 words today!");
        }
        addTxtFieldListener(txt_2000[projID - 1]);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_2000[projID - 1]);
                break;
            case 2:
                proj2Lists.add(txt_2000[projID - 1]);
                break;
            case 3:
                proj3Lists.add(txt_2000[projID - 1]);
                break;
            case 4:
                proj4Lists.add(txt_2000[projID - 1]);
                break;
            case 5:
                proj5Lists.add(txt_2000[projID - 1]);
                break;

            default:
                break;
        }


        JLabel lbl72 = new JLabel("<html><b>Milestone <br>Penalty:</html>");
        lbl72.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 2;
        c.gridy = 6;
        panel.add(lbl72, c);

        txt_Milestone_penalty = new JTextField();
        txt_Milestone_penalty.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 3;
        c.gridy = 6;
        panel.add(txt_Milestone_penalty, c);
        txt_Milestone_penalty.setName("Proj_Mil_Pen");
        if (proj != null) {
            txt_Milestone_penalty.setText(proj.getPenalty());
        }
        else {
            txt_Milestone_penalty.setText(Name + " missed a milestone writing goal! Encouragement is needed!");
        }
        addTxtFieldListener(txt_Milestone_penalty);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_Milestone_penalty);
                break;
            case 2:
                proj2Lists.add(txt_Milestone_penalty);
                break;
            case 3:
                proj3Lists.add(txt_Milestone_penalty);
                break;
            case 4:
                proj4Lists.add(txt_Milestone_penalty);
                break;
            case 5:
                proj5Lists.add(txt_Milestone_penalty);
                break;

            default:
                break;
        }

        lbl_5000[projID - 1] = new JLabel("<html><b>5,000 Words <br>Reward:</html>");
        lbl_5000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 7;
        panel.add(lbl_5000[projID - 1], c);

        txt_5000[projID - 1] = new JTextField();
        txt_5000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 7;
        panel.add(txt_5000[projID - 1], c);
        txt_5000[projID - 1].setName("Proj_5000");
        if (proj != null) {
            txt_5000[projID - 1].setText(proj.getReward5000());
        }
        else {
            txt_5000[projID - 1].setText(Name + " is soaring! 5,000 words written!");
        }
        addTxtFieldListener(txt_5000[projID - 1]);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_5000[projID - 1]);
                break;
            case 2:
                proj2Lists.add(txt_5000[projID - 1]);
                break;
            case 3:
                proj3Lists.add(txt_5000[projID - 1]);
                break;
            case 4:
                proj4Lists.add(txt_5000[projID - 1]);
                break;
            case 5:
                proj5Lists.add(txt_5000[projID - 1]);
                break;

            default:
                break;
        }

        lbl_10000[projID - 1] = new JLabel("<html><b>10,000 Words <br>Reward:</html>");
        lbl_10000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 8;
        panel.add(lbl_10000[projID - 1], c);

        txt_10000[projID - 1] = new JTextField();
        txt_10000[projID - 1].setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 8;
        panel.add(txt_10000[projID - 1], c);
        txt_10000[projID - 1].setName("Proj_10000");
        if (proj != null) {
            txt_10000[projID - 1].setText(proj.getReward10000());
        }
        else {
            txt_10000[projID - 1].setText(Name + " is burning through the word count. 10,000 words written today!");
        }
        addTxtFieldListener(txt_10000[projID - 1]);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_1000[projID - 1]);
                break;
            case 2:
                proj2Lists.add(txt_1000[projID - 1]);
                break;
            case 3:
                proj3Lists.add(txt_1000[projID - 1]);
                break;
            case 4:
                proj4Lists.add(txt_1000[projID - 1]);
                break;
            case 5:
                proj5Lists.add(txt_1000[projID - 1]);
                break;

            default:
                break;
        }
        //


        JLabel lbl101 = new JLabel("<html><b>Completion <br>Reward:</html>");
        lbl101.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 9;
        panel.add(lbl101, c);

        txt_completion = new JTextField();
        txt_completion.setFont(font);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 2;
        c.gridx = 1;
        c.gridy = 9;
        panel.add(txt_completion, c);
        txt_completion.setName("Proj_Comp_rew");
        if (proj != null) {
            txt_completion.setText(proj.getRewardCompletion());
        }
        else {
            txt_completion.setText("Take [PROJECT] and start editing! Well done!");
        }
        addTxtFieldListener(txt_completion);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(txt_completion);
                break;
            case 2:
                proj2Lists.add(txt_completion);
                break;
            case 3:
                proj3Lists.add(txt_completion);
                break;
            case 4:
                proj4Lists.add(txt_completion);
                break;
            case 5:
                proj5Lists.add(txt_completion);
                break;

            default:
                break;
        }


        JPanel pnlBottom = new JPanel();
        btnProjectSave = new StandardButton("    Save    ");
        btnProjectSave.setBackground(Color.gray);
        btnProjectSave.setFont(font);
        btnProjectSave.setForeground(Color.black);
        btnProjectSave.setActionCommand(projID + "S");
        btnProjectSave.setName("btnProjectSave" + projID);
        btnProjectSave.addActionListener(this);
        btnProjectSave.setVisible(false);
        pnlBottom.add(btnProjectSave);
        //pnlBottom.setBackground(bgColor);

//        StandardButton btnProjectEdit = new StandardButton("    Edit    ");
//        btnProjectEdit.setBackground(Color.gray);
//        btnProjectEdit.setForeground(Color.black);
//        btnProjectEdit.setFont(font);
//        pnlBottom.add(btnProjectEdit);
//        btnProjectEdit.setActionCommand(projID + "E");
//        btnProjectEdit.setName("btnProjectEdit" + projID);
//        btnProjectEdit.addActionListener(this);
        pnlBottom.setOpaque(true);


        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;       //reset to default
        c.weighty = 1.0;   //request any extra vertical space
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        //c.insets = new Insets(10,0,10,0);  //top padding
        c.gridx = 1;       //aligned with button 2
        c.gridwidth = 2;   //2 columns wide
        c.gridy = 10;       //third row
        pnlBottom.setOpaque(false);
        panel.add(pnlBottom, c);
        //
        switch (projID) {
            case 1:
                proj1Lists.add(btnProjectSave);
//                proj1Lists.add(btnProjectEdit);
//                EnableProjectFields(proj1Lists, false);
                break;
            case 2:
                proj2Lists.add(btnProjectSave);
//                proj2Lists.add(btnProjectEdit);
//                EnableProjectFields(proj2Lists, false);
                break;
            case 3:
                proj3Lists.add(btnProjectSave);
//                proj3Lists.add(btnProjectEdit);
//                EnableProjectFields(proj3Lists, false);
                break;
            case 4:
                proj4Lists.add(btnProjectSave);
//                proj4Lists.add(btnProjectEdit);
//                EnableProjectFields(proj4Lists, false);
                break;
            case 5:
                proj5Lists.add(btnProjectSave);
//                proj5Lists.add(btnProjectEdit);
//                EnableProjectFields(proj5Lists, false);
                break;

            default:
                break;
        }
        //
        //EnableProjectFields(projID==1?proj1Lists:projID==2?proj2Lists:proj3Lists, false);
        return panel;
    }

    private void addTxtFieldListener(JTextField txtField) {
        txtField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                fieldChanged();
            }


            @Override
            public void removeUpdate(DocumentEvent e) {
                fieldChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                fieldChanged();
            }
        });
    }

    private void addComboBoxListener(JComboBox comboBox) {
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldChanged();
            }
        });
    }

    private void addCheckBoxListener(JCheckBox checkBox) {
        checkBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldChanged();
            }
        });
    }

    private void addRadioButtonListener(JRadioButton radioButton) {
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldChanged();
            }
        });
    }

    private void addDatePickerListener(JDatePickerImpl datePicker) {
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldChanged();
            }
        });
    }

    private void fieldChanged() {
        if (!btnGeneralSave.isVisible()) {
            btnGeneralSave.setVisible(true);
        }

        EditProject(1);
        EditProject(2);
        EditProject(3);
        EditProject(4);
        EditProject(5);
    }

    static boolean proj1SessionStart = true;
    static boolean proj2SessionStart = true;
    static boolean proj3SessionStart = true;
    static boolean proj4SessionStart = true;
    static boolean proj5SessionStart = true;


    public void ShowUI() {
        //
        setConfigPath();
        System.out.println("ConfigPath" + configPath);
        //get the pref file
        ObjectInputStream ois;
        try {
            FileInputStream fin = new FileInputStream(configPath + "\\general.ser");
            ois = new ObjectInputStream(fin);
            general = (General) ois.readObject();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project1.ser");
            ois = new ObjectInputStream(fin);
            project1 = (Project) ois.readObject();
            if (proj1SessionStart)
                project1.setCurrentWords(0);
        } catch (Exception e) {
            //e.printStackTrace();
        }


        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project2.ser");
            ois = new ObjectInputStream(fin);
            project2 = (Project) ois.readObject();
            if (proj2SessionStart)
                project2.setCurrentWords(0);
        } catch (Exception e) {
            //e.printStackTrace();
        }


        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project3.ser");
            ois = new ObjectInputStream(fin);
            project3 = (Project) ois.readObject();
            if (proj3SessionStart)
                project3.setCurrentWords(0);
        } catch (Exception e) {
            //e.printStackTrace();
        }

        if (general != null && general.isActivated()) {
            try {
                FileInputStream fin = new FileInputStream(configPath + "\\project4.ser");
                ois = new ObjectInputStream(fin);
                project4 = (Project) ois.readObject();
                if (proj4SessionStart)
                    project4.setCurrentWords(0);
            } catch (Exception e) {
                //e.printStackTrace();
            }


            try {
                FileInputStream fin = new FileInputStream(configPath + "\\project5.ser");
                ois = new ObjectInputStream(fin);
                project5 = (Project) ois.readObject();
                if (proj5SessionStart)
                    project5.setCurrentWords(0);
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

        if (FacebookController.FACEBOOK_INSTANCE.getAuthorization() instanceof NullAuthorization && general != null) {
            FacebookController.FACEBOOK_INSTANCE = new FacebookFactory().getInstance();
            FacebookController.FACEBOOK_INSTANCE.setOAuthAppId("1509940699313820", "5c9700b49ea2cd74432d6b101074196f");
            FacebookController.FACEBOOK_INSTANCE.setOAuthPermissions("publish_actions");
            FacebookController.FACEBOOK_INSTANCE.setOAuthAccessToken(new AccessToken(general.getFbAccessToken(), 100000L));
        }

        if (TwitterController.TWITTER_INSTANCE.getAuthorization() instanceof twitter4j.auth.NullAuthorization && general != null && general.getTwAccessToken() != null) {
            TwitterController.TWITTER_INSTANCE = new TwitterFactory().getInstance();
            TwitterController.TWITTER_INSTANCE.setOAuthConsumer("gfLcjIGfRenvVWNyxyQnooShC", "bHoR3N8Wa8DjPm44oz21RuK44Se4lnFcu4a6pxZfecRdtg5u1m");
            TwitterController.TWITTER_INSTANCE.setOAuthAccessToken(new twitter4j.auth.AccessToken(general.getTwAccessToken(), general.getTwAccessSecret()));
        }

        initUI(general);
        setVisible(true);
    }

    private static void setConfigPath() {
        String write_track_home = System.getenv("WRITE_TRACK_HOME");
        if (write_track_home == null) {
            configPath = "C:\\Config";
        } else {
            configPath = write_track_home + "\\Config";
        }

        File configFile = new File(configPath);
        if (!configFile.exists()) {
            configFile.mkdir();
        }
    }

    public static String configPath;
    public static General general;
    public static Project project1;
    public static Project project2;
    public static Project project3;
    public static Project project4;
    public static Project project5;

    public static void main(String[] args) {
        //Get the WriteTracker Home
        setConfigPath();
        System.out.println("ConfigPath" + configPath);
        //get the pref file
        ObjectInputStream ois;
        try {
            FileInputStream fin = new FileInputStream(configPath + "\\general.ser");
            ois = new ObjectInputStream(fin);
            general = (General) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project1.ser");
            ois = new ObjectInputStream(fin);
            project1 = (Project) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project2.ser");
            ois = new ObjectInputStream(fin);
            project2 = (Project) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project3.ser");
            ois = new ObjectInputStream(fin);
            project3 = (Project) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //for project 4 and 5
        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project4.ser");
            ois = new ObjectInputStream(fin);
            project4 = (Project) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fin = new FileInputStream(configPath + "\\project4.ser");
            ois = new ObjectInputStream(fin);
            project5 = (Project) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //
        try {
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
            props.store(fos, "Properties file generated from Java program");

            fos.close();
        } catch (Exception c) {
            c.printStackTrace();
        }
        //

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    // Set System L&F
                    UIManager.setLookAndFeel(
                            UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    // handle exception
                }
                WriDemo ex = new WriDemo();
                ex.initUI(general);
                /*try {
	                //
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
	                //
	                System.err.println("There was a problem registering the native hook.");
	                System.err.println(exc.getMessage());
	                System.exit(1);
	            }*/
                ex.setVisible(true);
            }
        });
    }

    //button clicked
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("GS"))
            SaveGeneralTab();
        if (ae.getActionCommand().equals("1S"))
            SaveProject(1);
        if (ae.getActionCommand().equals("2S"))
            SaveProject(2);
        if (ae.getActionCommand().equals("3S"))
            SaveProject(3);
        //for proj 4 and 5
        if (ae.getActionCommand().equals("4S"))
            SaveProject(4);
        if (ae.getActionCommand().equals("5S"))
            SaveProject(5);
        if (ae.getActionCommand().equals("1E"))
            EditProject(1);
        if (ae.getActionCommand().equals("2E"))
            EditProject(2);
        if (ae.getActionCommand().equals("3E"))
            EditProject(3);
        //for proj 4 and 5
        if (ae.getActionCommand().equals("4E"))
            EditProject(4);
        if (ae.getActionCommand().equals("5E"))
            EditProject(5);
        if (ae.getActionCommand().equals("AC")) {
            ActivateDialog dlgActivate = new ActivateDialog(this);
            dlgActivate.setLocationRelativeTo(this);
            dlgActivate.setVisible(true);
        }
        if (ae.getActionCommand().equals("FB")) {
            FacebookController.connectToFacebook();
        }
        if (ae.getActionCommand().equals("TW")) {
            TwitterController.connectToTwitter();
        }
        //this.dispose();
        if (ae.getActionCommand().equalsIgnoreCase("ShowCount")) {
            radio_session.setVisible(isShowCount.isSelected());
            radio_total.setVisible(isShowCount.isSelected());
        }

    }

    public static void saveAccessToken(String token, String secret, String type) {
        General general = null;
        ObjectInputStream ois;
        try {
            FileInputStream fin = new FileInputStream(configPath+"\\general.ser");
            ois = new ObjectInputStream(fin);
            general = (General) ois.readObject();
        } catch (Exception e) {
            //e.printStackTrace();
        }

        if (general != null) {
            if (type.equals("Facebook")) {
                general.setFbAccessToken(token);
            }

            if (type.equals("Twitter")) {
                general.setTwAccessToken(token);
                general.setTwAccessSecret(secret);
            }
        }

        saveGeneral(configPath, general);
    }

    public void EditProject(int id) {
        ArrayList<Object> lists;
        switch (id) {
            case 1:
                lists = proj1Lists;
                break;
            case 2:
                lists = proj2Lists;
                break;
            case 3:
                lists = proj3Lists;
                break;
            case 4:
                lists = proj4Lists;
                break;
            case 5:
                lists = proj5Lists;
                break;
            default:
                lists = new ArrayList<>();
        }

        for (Object obj : lists) {
            if (!(obj instanceof StandardButton)) {
                continue;
            }
            StandardButton btn = (StandardButton) obj;
//            if (btn.getName().equals("btnProjectEdit" + id)) {
//                btn.setVisible(false);
//            }
            if (btn.getName().equals("btnProjectSave" + id)) {
                btn.setVisible(true);
            }
        }

//        EnableProjectFields(lists, true);
    }


    public void SaveProject(int id) {
        ArrayList<Object> lists;
        //
        switch (id) {
            case 1:
                lists = proj1Lists;
                break;
            case 2:
                lists = proj2Lists;
                break;
            case 3:
                lists = proj3Lists;
                break;
            case 4:
                lists = proj4Lists;
                break;
            case 5:
                lists = proj5Lists;
                break;
            default:
                lists = new ArrayList<>();
        }

        Project proj = new Project();
        String projTitle = ((JTextField) lists.get(0)).getText().trim();
        if (projTitle == null || projTitle.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter Project Title", "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        proj.setProjectTitle(projTitle);
        proj.setProjectType(((JComboBox) lists.get(2)).getSelectedIndex());
        JDatePickerImpl mydatePicker = (JDatePickerImpl) lists.get(1);
        Date deadDate = (Date) mydatePicker.getModel().getValue();
        if (deadDate == null || deadDate.before(new Date())) {
            JOptionPane.showMessageDialog(this, "Project Deadline is not a valid date", "Project deadline Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //System.out.println("Date "+(Date) mydatePicker.getModel().getValue());
        proj.setProjectDeadline(deadDate);
        int wordGoal = 0;
        try {
            wordGoal = Integer.parseInt((((JTextField) lists.get(3)).getText().trim()));
        } catch (Exception c) {
            JOptionPane.showMessageDialog(this, "Please enter numeric value only(only numbers without any commas)", "Project WordGoal Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        proj.setWordGoal(wordGoal);
        proj.setInterval(((JComboBox) lists.get(4)).getSelectedIndex());
        proj.setPostSocMedia(((JCheckBox) lists.get(5)).isSelected());
        proj.setReward1000(((JTextField) lists.get(6)).getText().trim());
        proj.setRewardMilestone(((JTextField) lists.get(7)).getText().trim());
        proj.setReward2000(((JTextField) lists.get(8)).getText().trim());
        proj.setPenalty(((JTextField) lists.get(9)).getText().trim());
        proj.setReward5000(((JTextField) lists.get(10)).getText().trim());
        proj.setReward10000(((JTextField) lists.get(11)).getText().trim());
        proj.setRewardCompletion(((JTextField) lists.get(12)).getText().trim());

        try {
            FileOutputStream fout = new FileOutputStream(configPath + "\\project" + id + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(proj);
//            EnableProjectFields(lists, false);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Path Not found", "Date Save Error", JOptionPane.ERROR_MESSAGE);
        }
        JOptionPane.showMessageDialog(this, projTitle + " saved successfully.", "Save Preferences", JOptionPane.INFORMATION_MESSAGE);
        //getProject(id);
//        EnableProjectFields(lists, false);

        for (Object obj : lists) {
            if (!(obj instanceof StandardButton)) {
                continue;
            }
            StandardButton btn = (StandardButton) obj;
//            if (btn.getName().equals("btnProjectEdit" + id)) {
//                btn.setVisible(true);
//            }
            if (btn.getName().equals("btnProjectSave" + id)) {
                btn.setVisible(false);
            }
        }
    }

//    public void EnableProjectFields(ArrayList<Object> lists, boolean isEnabled) {
//        ((JTextField) lists.get(0)).setEditable(isEnabled);
//        ((JComboBox) lists.get(2)).setEditable(isEnabled);
//        ((JTextField) lists.get(3)).setEditable(isEnabled);
//        ((JComboBox) lists.get(4)).setEditable(isEnabled);
//        ((JCheckBox) lists.get(5)).setEnabled(isEnabled);
//        ((JTextField) lists.get(6)).setEnabled(isEnabled);
//        ((JTextField) lists.get(7)).setEnabled(isEnabled);
//        ((JTextField) lists.get(8)).setEnabled(isEnabled);
//        ((JTextField) lists.get(9)).setEnabled(isEnabled);
//        ((JTextField) lists.get(10)).setEnabled(isEnabled);
//        ((JTextField) lists.get(11)).setEnabled(isEnabled);
//        ((JTextField) lists.get(12)).setEnabled(isEnabled);
//    }


    public void SaveGeneralTab() {
        String validate = validatePrefernece();
        if (!validate.equals("SUCCESS")) {
            JOptionPane.showMessageDialog(this, validate, "Input Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        btnGeneralSave.setVisible(false);

        //adding dialog box to asking for save

        General general = new General();
        general.setFullName(txt_Name.getText().trim());
        general.setEmailAddress(txt_email.getText().trim());
        general.setFbUserName(txt_fb.getText().trim());
        general.setTwitUserName(txt_twt.getText().trim());
        general.setTimeZone(txt_tz.getSelectedIndex());
        general.setLaunchStartUp(isLaunched.isSelected());
        general.setMinimized(isMinimized.isSelected());
        if (Start.general == null || Start.general.getFirstSaveDate() == null) {
            general.setFirstSaveDate(new Date());
        } else {
            general.setFirstSaveDate(Start.general.getFirstSaveDate());
        }

        int showCount = 0;
        if (isShowCount.isSelected()) {
            if (radio_total.isSelected())
                showCount = 1;
            else
                showCount = 2;
        }
        general.setShowCount(showCount);

        saveGeneral(configPath, general);
        JOptionPane.showMessageDialog(this, "Preferences successfully saved.", "Save Preferences", JOptionPane.INFORMATION_MESSAGE);

//        txt_Name.setEnabled(false);
//        txt_email.setEnabled(false);
//        txt_fb.setEnabled(false);
//        txt_twt.setEnabled(false);
//        txt_tz.setEnabled(false);
//        isLaunched.setEnabled(false);
//        isMinimized.setEnabled(false);
//        isShowCount.setEnabled(false);
//        radio_total.setEnabled(false);
//        radio_session.setEnabled(false);
    }

    @Override
    public void dispose() {
        for (ActionListener al : btnGeneralSave.getActionListeners()) {
            btnGeneralSave.removeActionListener(al);
        }

        super.dispose();
    }

    public static void saveGeneral(String path, General general) {
        try {
            FileOutputStream fout = new FileOutputStream(path + "\\general.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(general);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Path Not found", "Date Save Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    int prevKey = 99;
    static int wordCount = 0;

    /* Key Pressed */
    public void nativeKeyPressed(NativeKeyEvent e) {
        //System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

        /* Terminate program when one press ESCAPE */
        //if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
        // try{
        //	 GlobalScreen.unregisterNativeHook();
        // }catch(Exception ig){}
        // }
    }

    /* Key Released */
    public void nativeKeyReleased(NativeKeyEvent e) {
        if (prevKey == 99)
            prevKey = e.getKeyCode();
        if (prevKey != NativeKeyEvent.VC_SPACE && e.getKeyCode() == NativeKeyEvent.VC_SPACE)
            ++wordCount;
        prevKey = e.getKeyCode();
        System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()) + "WordCOunt=" + wordCount);
    }

    /* I can't find any output from this call */
    public void nativeKeyTyped(NativeKeyEvent e) {
        //System.out.println("Key Typed: " + e.getKeyText(e.getKeyCode()));
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        if (event.getItem() == isShowCount) {
            radio_total.setSelected(isShowCount.isSelected());
            return;
        }

        if (event.getStateChange() == ItemEvent.SELECTED) {
            //
            String Name = "[NAME]";
            ObjectInputStream ois;
            try {
                FileInputStream fin = new FileInputStream(configPath + "\\general.ser");
                ois = new ObjectInputStream(fin);
                general = (General) ois.readObject();
                Name = general.getFullName();
            } catch (Exception e) {
                //e.printStackTrace();
                Name = "[NAME]";
            }
            //
            int index = tabbedPane.getSelectedIndex();
            //System.out.println("Selected Index:"+index);
            Object item = event.getItem();

            // do something with object
            // System.out.println("Selected "+item.toString());
            Project proj = null;
            switch (index) {
                case 1:
                    proj = project1;
                    break;
                case 2:
                    proj = project2;
                    break;
                case 3:
                    proj = project3;
                    break;
                case 4:
                    proj = project4;
                    break;
                case 5:
                    proj = project5;
                    break;
                default:
                    break;
            }
            --index;
            System.out.println("proj" + proj);
            // if(proj==null){
            if (item.toString().equalsIgnoreCase("SCRIPT")) {
                lbl_1000[index].setText("<html><b>5 Pages <br>Reward:</html>");
                lbl_2000[index].setText("<html><b>10 Pages <br>Reward:</html>");
                lbl_5000[index].setText("<html><b>25 Pages <br>Reward:</html>");
                lbl_10000[index].setText("<html><b>50 Pages <br>Reward:</html>");
                lbl_wordstocomplete[index].setText("<html><b>Pages <br>Left to Complete:</html>");
                lbl_totalwords[index].setText("<html><b>Current<br>Total Pages:</html>");
                lbl_wordgoal[index].setText("<html><b>Page Goal:</html>");
                lbl_milestonecount[index].setText("<html><b>Milestone<br>Pages count: ");
                txt_1000[index].setText("Congratulations! " + Name + " hit 5 pages !");
                txt_2000[index].setText("Hooray! " + Name + " wrote 10 Pages");
                txt_5000[index].setText("" + Name + " is soaring! 25 Pages written!");
                txt_10000[index].setText("" + Name + " is burning through the page count. 50 Pages written today!");

            } else {
                lbl_1000[index].setText("<html><b>1,000 Words <br>Reward:</html>");
                lbl_2000[index].setText("<html><b>2,000 Words <br>Reward:</html>");
                lbl_5000[index].setText("<html><b>5,000 Words <br>Reward:</html>");
                lbl_10000[index].setText("<html><b>10,000 Words <br>Reward:</html>");
                lbl_wordstocomplete[index].setText("<html><b>Words <br>Left to Complete:</html>");
                lbl_totalwords[index].setText("<html><b>Current<br>Total Words:</html>");
                lbl_wordgoal[index].setText("<html><b>Words Goal:</html>");
                lbl_milestonecount[index].setText("<html><b>Milestone<br>words count: ");
                txt_1000[index].setText("Congratulations! " + Name + " hit 1,000 words !");
                txt_2000[index].setText("Hooray! " + Name + " wrote 2,000 words today");
                txt_5000[index].setText("" + Name + " is soaring! 5,000 words written!");
                txt_10000[index].setText("" + Name + " is burning through the word count. 10,000 words written today!");

            }
            // }
        }
    }


}
