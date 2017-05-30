/**
* Project: A00101515Lab10
* File: MainFrame.java
* Date: 2015-03-23
* Time: `10:40:49 PM
 */
package a00101515.player.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import a00101515.dao.PlayerDao;
import a00101515.data.Player;

/**
 * @author G.E. Eidsness, A00101515
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static Logger LOG = Logger.getLogger(MainFrame.class);

	private PlayerDao playerDao;
	private JList<PlayerListItem> playersList;
	private PlayerListModel playerModel;

	public MainFrame() throws SQLException {
		super("Lab 10");

		playerDao = new PlayerDao();
		playerModel = new PlayerListModel();
		
		setBounds(100, 100, 500, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(-1);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(MainFrame.this, "Lab 10\nBy G.E. Eidsness, A00101515", "Lab 10",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});
		mnHelp.add(mntmAbout);

		playersList = new JList<PlayerListItem>(playerModel);
		playersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		add(new JScrollPane(playersList));
		
		playersList.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				if (event.getValueIsAdjusting()) {
					return;
				}
				PlayerListItem player = playersList.getSelectedValue();
				if (player != null) {
					showPlayerDetails(player, playersList.getSelectedIndex());
				}
			}
		});
		
		// set the data
		try {
			playerModel.setPlayers(playerDao.getAllPlayers());
		} catch (Exception e) {
			LOG.equals(e.getMessage());
		}
	}

	protected void showPlayerDetails(PlayerListItem item, int index) {
		try {
			PlayerDialog dialog = new PlayerDialog(item.getPlayer());
			dialog.setVisible(true);
			Player player = dialog.getPlayer();
			if (player != null) {
				playerDao.update(player);
				playerModel.setElementAt(player, index);
			}
			playersList.clearSelection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
