package SociologyInspiredSmartMeter.SmartMeterClient;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class CalendarPageExchangeInformation extends JFrame{

    private JPanel contentPane;

	private SociologyInspiredSmartMeter.Controller controller;

	private Config config;

	private Settings settings;

     public CalendarPageExchangeInformation(SociologyInspiredSmartMeter.Controller passedController, Config passedConfig, Settings passedSettings) {

		this.controller = passedController;

		this.config = passedConfig;

		this.settings = passedSettings;

		buildFrame();

		staticElements();

		//Passes the timeline builder the y position of the upper timeline that displays
		//the preference timeline.
		timelineBuilder(197, controller.timeslotAssignments);

		exchangeMessageBuilder();

		setContentPane(contentPane);

		this.setVisible(true);
     
    }

	public void buildFrame() {

		setTitle("Smart Meter");

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, 1380, 800);

		contentPane = new JPanel();

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		contentPane.setBackground(Color.WHITE);

		contentPane.setLayout(null);

		//Announcement Text
		JLabel announcementText = new JLabel(controller.announcementHandler(), SwingConstants.CENTER);
		announcementText.setBounds(80,45, 1100, 32);
		announcementText.setFont(new Font("Serif", Font.PLAIN, 32));
		
		// Clock
		JLabel onScreenClock = new JLabel();
		onScreenClock.setBounds(1260, 45, 200, 32);
		onScreenClock.setFont(new Font("Serif", Font.PLAIN, 32));
		
		Timer timer = new Timer(1000, (ActionEvent e) -> {
			DateTimeFormatter myTime = DateTimeFormatter.ofPattern("HH:mm");
			LocalDateTime now = LocalDateTime.now();
			onScreenClock.setText(String.valueOf(myTime.format(now)));
			announcementText.setText(controller.announcementHandler());
		});

		timer.setRepeats(true);
		timer.start();
		contentPane.add(onScreenClock);
		contentPane.add(announcementText);
		
		// Static Images
		// Background Image
        JLabel backgroundImage;

		try {
			BufferedImage backgroundImageImage = ImageIO.read(getClass().getResourceAsStream("/resources/Background1Timeline.png"));
			backgroundImage = new JLabel(new ImageIcon(backgroundImageImage));
			backgroundImage.setBounds(0, 0, 1400, 800);
			contentPane.add(backgroundImage);
			contentPane.setComponentZOrder(backgroundImage, 2);
		} catch (IOException backgroundImageErr) {
			System.out.println("Background Image unable to load");
			System.exit(2);
		}

		// Icon Images

		// Battery Icon
		JLabel batteryIcon;
		try {
			BufferedImage batteryIconImage = ImageIO.read(getClass().getResourceAsStream("/resources/battery.png"));
			batteryIcon = new JLabel(new ImageIcon(batteryIconImage));
			batteryIcon.setBounds(1190, 45, 64, 32);
			contentPane.add(batteryIcon);
			contentPane.setComponentZOrder(batteryIcon, 1);
		} catch (IOException batteryIconErr) {
			System.out.println("Battery Icon unable to load");
			System.exit(2);
		}

		// Signal Icon
		JLabel signalIcon;
		try {
			BufferedImage signalIconImage = ImageIO.read(getClass().getResourceAsStream("/resources/signal.png"));
			signalIcon = new JLabel(new ImageIcon(signalIconImage));
			signalIcon.setBounds(40, 40, 32, 32);
			contentPane.add(signalIcon);
			contentPane.setComponentZOrder(signalIcon, 1);
		} catch (IOException signalIconErr) {
			System.out.println("Signal Icon unable to load");
			System.exit(2);
		}

		// Navigation
		// Home Navigation Button
		JButton btnHome;
		try {
			BufferedImage buttonIconHome = ImageIO.read(getClass().getResourceAsStream("/resources/home.png"));
			btnHome = new JButton(new ImageIcon(buttonIconHome));
			btnHome.setBorder(BorderFactory.createEmptyBorder());
			btnHome.setContentAreaFilled(false);
			btnHome.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {

					dispose();
					controller.displayHomePage();

				}
			});
			btnHome.setBounds(648, 687, 64, 64);
			contentPane.add(btnHome);
			contentPane.setComponentZOrder(btnHome, 1);
		} catch (IOException btnHomeErr) {
			System.out.println("Home Icon unable to load");
			System.exit(2);
		}

		// Settings Navigation Button
		JButton btnSettings;
		try {
			BufferedImage buttonIconSettings = ImageIO.read(getClass().getResourceAsStream("/resources/settings.png"));
			btnSettings = new JButton(new ImageIcon(buttonIconSettings));
			btnSettings.setBorder(BorderFactory.createEmptyBorder());
			btnSettings.setContentAreaFilled(false);
			btnSettings.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {

					dispose();
					controller.displaySettingsPage();

				}
			});
			btnSettings.setBounds(808, 687, 64, 64);
			contentPane.add(btnSettings);
			contentPane.setComponentZOrder(btnSettings, 1);
		} catch (IOException btnSettingsErr) {
			System.out.println("Settings Icon unable to load");
			System.exit(2);
		}

		// Refresh Navigation Button
		JButton btnRefresh;
		try {
			BufferedImage buttonIconRefresh = ImageIO.read(getClass().getResourceAsStream("/resources/refresh.png"));
			btnRefresh = new JButton(new ImageIcon(buttonIconRefresh));
			btnRefresh.setBorder(BorderFactory.createEmptyBorder());
			btnRefresh.setContentAreaFilled(false);
			btnRefresh.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {

					revalidate();
					repaint();

				}
			});
			btnRefresh.setBounds(728, 687, 64, 64);
			contentPane.add(btnRefresh);
			contentPane.setComponentZOrder(btnRefresh, 1);
		} catch (IOException btnRefreshErr) {
			System.out.println("Refresh Icon unable to load");
			System.exit(2);
		}

		// Calendar Navigation Button
		JButton btnCalendar;
		try {
			BufferedImage buttonIconCalendar = ImageIO.read(getClass().getResourceAsStream("/resources/calendar.png"));
			btnCalendar = new JButton(new ImageIcon(buttonIconCalendar));
			btnCalendar.setBorder(BorderFactory.createEmptyBorder());
			btnCalendar.setContentAreaFilled(false);
			btnCalendar.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {

					dispose();
					controller.displayCalendarPage();

				}
			});
			btnCalendar.setBounds(568, 687, 64, 64);
			contentPane.add(btnCalendar);
			contentPane.setComponentZOrder(btnCalendar, 1);
		} catch (IOException btnCalendarErr) {
			System.out.println("Calendar Icon unable to load");
			System.exit(2);
		}

		// Statistics Navigation Button
		JButton btnStatistics;
		try {
			BufferedImage buttonIconStatistics = ImageIO.read(getClass().getResourceAsStream("/resources/bar-chart.png"));
			btnStatistics = new JButton(new ImageIcon(buttonIconStatistics));
			btnStatistics.setBorder(BorderFactory.createEmptyBorder());
			btnStatistics.setContentAreaFilled(false);
			btnStatistics.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {

					dispose();
					controller.displayStatisticsPage();

				}
			});
			btnStatistics.setBounds(488, 687, 64, 64);
			contentPane.add(btnStatistics);
			contentPane.setComponentZOrder(btnStatistics, 1);
		} catch (IOException btnStatisticsErr) {
			System.out.println("Statistics Icon unable to load");
			System.exit(2);
		}
	}

	public void staticElements()
	{

        //Static Label for the preference timeslot list.
		JLabel preferenceListLabel = new JLabel("Assigned Timeslot Timeline");
		preferenceListLabel.setBounds(500, 100, 500, 50);
		preferenceListLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		contentPane.add(preferenceListLabel);
		contentPane.setComponentZOrder(preferenceListLabel, 1);

        //JButton to navigate to the feedback page.
        JButton btnFeedback = new JButton("Provide Feedback");
        btnFeedback.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            public void actionPerformed(ActionEvent e) {

				controller.status = "Feedback";
                dispose();
                controller.displayFeedbackPage();

            }
        });
        btnFeedback.setBounds(500, 600, 400, 50);
        btnFeedback.setFont(new Font("Tahoma", Font.PLAIN, 30));
        contentPane.add(btnFeedback);
        contentPane.setComponentZOrder(btnFeedback, 1);

    }

	public void timelineBuilder(int position, HashMap<Integer, String> timeslotPreferences)
	{

		ImageIcon washingMachineImage = new ImageIcon(getClass().getResource("/resources/washingMachineSmall.png"));
		ImageIcon tumbleDryerImage = new ImageIcon(getClass().getResource("/resources/tumbleDryerSmall.png"));
		ImageIcon dishwasherImage = new ImageIcon(getClass().getResource("/resources/dishwasherSmall.png"));
		ImageIcon heaterImage = new ImageIcon(getClass().getResource("/resources/heaterSmall.png"));
		ImageIcon pointerImage = new ImageIcon(getClass().getResource("/resources/pointerSmall.png"));

		//For each timeslot in the preference list, create an icon and display it on the timeline.

		timeslotPreferences.forEach((key, value) -> {

			JLabel label;

			switch(value)
			{
				case "Washing Machine":
					label = new JLabel(washingMachineImage);
					break;
				case "Tumble Dryer":
				 	label = new JLabel(tumbleDryerImage);
					break;
				case "Dishwasher":
					label = new JLabel(dishwasherImage);
					break;
				case "Heater":
					label = new JLabel(heaterImage);
					break;
				case "Pointer":
					label = new JLabel(pointerImage);
					break;
				default:
                    label = new JLabel(pointerImage);
					break;
			}

			switch(key) {
				case 0:
					label.setBounds(62, position, 32, 32);
					break;
					//Same as 0
				case 24:
					label.setBounds(62, position, 32, 32);
					break;
				case 1:
					label.setBounds(112, position, 32, 32);
					break;
				case 2:
					label.setBounds(168, position, 32, 32);
					break;
				case 3:
					label.setBounds(222, position, 32, 32);
					break;
				case 4:
					label.setBounds(278, position, 32, 32);
					break;
				case 5:
					label.setBounds(330, position, 32, 32);
					break;
				case 6:
					label.setBounds(384, position, 32, 32);
					break;
				case 7:
					label.setBounds(440, position, 32, 32);
					break;
				case 8:
					label.setBounds(494, position, 32, 32);
					break;
				case 9:
					label.setBounds(548, position, 32, 32);
					break;
				case 10:
					label.setBounds(604, position, 32, 32);
					break;
				case 11:
					label.setBounds(655, position, 32, 32);
					break;
				case 12:
					label.setBounds(708, position, 32, 32);
					break;
				case 13:
					label.setBounds(762, position, 32, 32);
					break;
				case 14:
					label.setBounds(814, position, 32, 32);
					break;
				case 15:
					label.setBounds(866, position, 32, 32);
					break;
				case 16:
					label.setBounds(918, position, 32, 32);
					break;
				case 17:
					label.setBounds(972, position, 32, 32);
					break;
				case 18:
					label.setBounds(1025, position, 32, 32);
					break;
				case 19:
					label.setBounds(1078, position, 32, 32);
					break;
				case 20:
					label.setBounds(1130, position, 32, 32);
					break;
				case 21:
					label.setBounds(1184, position, 32, 32);
					break;
				case 22:
					label.setBounds(1240, position, 32, 32);
					break;
				case 23:
					label.setBounds(1290, position, 32, 32);
					break;
				default:
					// code block
			}

		contentPane.add(label);
		contentPane.setComponentZOrder(label, 1);

	});	
	}

	public void exchangeMessageBuilder()
	{

		int txtY = 300;

		int picY = 285;

		for (ExchangeMessage message : controller.exchangeMessages) {

			JLabel label = new JLabel(message.message);
			label.setBounds(120, txtY, 800, 32);
			label.setFont(new Font("Tahoma", Font.PLAIN, 20));
			contentPane.add(label);
			contentPane.setComponentZOrder(label, 1);

			//Picture
			ImageIcon image = null;

			if(message.getConnotation().equals("Positive"))
			{

			image = new ImageIcon(getClass().getResource("/resources/thumbUp.png"));
				
			} else if(message.getConnotation().equals("Neutral"))
			{

			image = new ImageIcon(getClass().getResource("/resources/thumbSide.png"));
				
			} else if(message.getConnotation().equals("Negative"))
			{

			image = new ImageIcon(getClass().getResource("/resources/thumbDown.png"));
				
			}

			JLabel picLabel = new JLabel(image);
			picLabel.setBounds(50, picY, 64, 64);
			contentPane.add(picLabel);
			contentPane.setComponentZOrder(picLabel, 1);

			txtY += 80;
			picY += 80;

		}
			
	}
	
}
