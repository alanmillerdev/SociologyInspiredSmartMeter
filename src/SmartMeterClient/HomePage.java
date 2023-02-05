package SmartMeterClient;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HomePage extends JFrame {

	private JPanel contentPane;

	public HomePage(Controller controller, Config config) {

		buildFrame(config);

	}

	public void buildFrame(Config config) {

		setTitle(config.getFrameTitle());

		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(config.getViewXPos(), config.getViewYPos(), config.getViewWidth(), config.getViewHeight());

		contentPane = new JPanel();

		contentPane.setBorder(config.getBorder());

		contentPane.setBackground(config.getBackgroundColour());

		contentPane.setLayout(null);

		// Navigation

		// Home Navigation Button
		JButton btnHome;
		try {
			BufferedImage buttonIconHome = ImageIO.read(new File("src/SmartMeterClient/Icons/home.png"));
			btnHome = new JButton(new ImageIcon(buttonIconHome));
			btnHome.setBorder(BorderFactory.createEmptyBorder());
			btnHome.setContentAreaFilled(false);
			btnHome.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					System.out.println("Ben");
				}
			});
			btnHome.setBounds(648, 683, 64, 64);
			contentPane.add(btnHome);
		} catch (IOException btnHomeErr) {
			System.out.println("Home Icon unable to load");
			System.exit(2);
		}

		// Settings Navigation Button
		JButton btnSettings;
		try {
			BufferedImage buttonIconSettings = ImageIO.read(new File("src/SmartMeterClient/Icons/settings.png"));
			btnSettings = new JButton(new ImageIcon(buttonIconSettings));
			btnSettings.setBorder(BorderFactory.createEmptyBorder());
			btnSettings.setContentAreaFilled(false);
			btnSettings.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					System.out.println("Ben");
				}
			});
			btnSettings.setBounds(808, 683, 64, 64);
			contentPane.add(btnSettings);
		} catch (IOException btnSettingsErr) {
			System.out.println("Settings Icon unable to load");
			System.exit(2);
		}

		// Refresh Navigation Button
		JButton btnRefresh;
		try {
			BufferedImage buttonIconRefresh = ImageIO.read(new File("src/SmartMeterClient/Icons/refresh.png"));
			btnRefresh = new JButton(new ImageIcon(buttonIconRefresh));
			btnRefresh.setBorder(BorderFactory.createEmptyBorder());
			btnRefresh.setContentAreaFilled(false);
			btnRefresh.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					System.out.println("Ben");
				}
			});
			btnRefresh.setBounds(728, 683, 64, 64);
			contentPane.add(btnRefresh);
		} catch (IOException btnRefreshErr) {
			System.out.println("Refresh Icon unable to load");
			System.exit(2);
		}

		// Calendar Navigation Button
		JButton btnCalendar;
		try {
			BufferedImage buttonIconCalendar = ImageIO.read(new File("src/SmartMeterClient/Icons/calendar.png"));
			btnCalendar = new JButton(new ImageIcon(buttonIconCalendar));
			btnCalendar.setBorder(BorderFactory.createEmptyBorder());
			btnCalendar.setContentAreaFilled(false);
			btnCalendar.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					System.out.println("Ben");
				}
			});
			btnCalendar.setBounds(568, 683, 64, 64);
			contentPane.add(btnCalendar);
		} catch (IOException btnCalendarErr) {
			System.out.println("Calendar Icon unable to load");
			System.exit(2);
		}

		// Statistics Navigation Button
		JButton btnStatistics;
		try {
			BufferedImage buttonIconStatistics = ImageIO.read(new File("src/SmartMeterClient/Icons/bar-chart.png"));
			btnStatistics = new JButton(new ImageIcon(buttonIconStatistics));
			btnStatistics.setBorder(BorderFactory.createEmptyBorder());
			btnStatistics.setContentAreaFilled(false);
			btnStatistics.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					System.out.println("Ben");
				}
			});
			btnStatistics.setBounds(488, 683, 64, 64);
			contentPane.add(btnStatistics);
		} catch (IOException btnCalendarErr) {
			System.out.println("Calendar Icon unable to load");
			System.exit(2);
		}

		// Display Content Pane Once it has loaded
		repaint();
		setContentPane(contentPane);
		this.setVisible(true);
	}

	// Error caused here when trying to draw the border
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3));
		g2d.drawRoundRect(20, 40, 1360, 740, 50, 30);
		g2d.drawRoundRect(40, 60, 1320, 650, 50, 30);
	}
}