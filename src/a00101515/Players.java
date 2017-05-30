/**
* Project: A00101515Lab10
* File: Players.java
* Date: 2015-03-23
* Time: `6:58:02 PM
 */

package a00101515;

import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import a00101515.dao.PlayerDao;
import a00101515.data.Database;
import a00101515.data.Player;
import a00101515.player.gui.MainFrame;
import a00101515.player.io.PlayerReader;

/**
 * @author G.E. Eidsness, A00101515
 */
public class Players {
	private static final String PLAYERS_DATA_FILENAME = "players.txt";
	private static final String LOG_PROPERTIES_FILENAME = "log.properties";
	private static final String DB_PROPERTIES_FILENAME = "db.properties";

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static Logger LOG = Logger.getLogger(Players.class);
	private final File playerDataFile;

	/**
	 * @param args
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Properties logProperties = new Properties();
		logProperties.load(new FileInputStream(LOG_PROPERTIES_FILENAME));
		PropertyConfigurator.configure(logProperties);
		LOG.info(DATE_FORMAT.format(new Date()));
		File file = new File(PLAYERS_DATA_FILENAME);
		if (!file.exists()) {
			System.out.format("Required '%s' is missing.", PLAYERS_DATA_FILENAME);
			System.exit(-1);
		}
		new Players(file).run();
		LOG.info(DATE_FORMAT.format(new Date()));
	}

	/**
	 * Lab10 constructor. Initialized the players collection.
	 */
	public Players(File playerDataFile) {
		this.playerDataFile = playerDataFile;
	}

	public void run() {
		try {
			loadData();
			createUI();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Run() Error: " + e.getMessage(), " " + e.getCause(),
					JOptionPane.ERROR_MESSAGE);
			LOG.error(e.getStackTrace());
		}
	}

	private void loadData() throws IOException, SQLException, ApplicationException {
		LOG.info("loadData() open");
		Properties dbProperties = new Properties();
		dbProperties.load(new FileInputStream(DB_PROPERTIES_FILENAME));
		Database.init(dbProperties);
		Database.newInstance();
		PlayerDao playerDao = new PlayerDao();
		List<Player> players = new ArrayList<Player>();
		int playerCount = 0;
		try {
			if (!Database.tableExists(PlayerDao.TABLE_NAME)) {
				playerDao.create();
				players = PlayerReader.read(playerDataFile);
				for (Player player : players) {
					playerDao.add(player);
					playerCount++;
				}
				LOG.debug("Inserted " + playerCount + " players");
			} else {
				playerDao.drop();
				playerDao.create();
				players = PlayerReader.read(playerDataFile);
				for (Player player : players) {
					playerDao.add(player);
					playerCount++;
				}
				LOG.debug("Updated " + playerCount + " players");
			}		
		} catch (Exception ex) {
			LOG.error(ex.getClass() + ": " + ex.getMessage() + ": " + ex.getCause());
			ex.printStackTrace();
		} 
	}

	private void setLookAndFeel() {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, use the default.
		}
	}

	public void createUI() {
		setLookAndFeel();
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame mainFrame = new MainFrame();
					mainFrame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "MainFrame Error: " + e.getMessage(), " " + e.getCause(),
							JOptionPane.ERROR_MESSAGE);
					LOG.error(e.getStackTrace());
				}
			}
		});
	}
}
