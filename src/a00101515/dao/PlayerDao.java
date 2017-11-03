/**
* File: PlayerDao.java
* Date: 2015-03-23
* Time: `7:12:12 PM
 */
package a00101515.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import a00101515.ApplicationException;
import a00101515.data.Database;
import a00101515.data.Player;

/**
 * @author G.E. Eidsness
 *
 */
public class PlayerDao extends Dao {

	private static Logger LOG = Logger.getLogger(PlayerDao.class.getName());
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

	public static final String TABLE_NAME = "Players";
	public static final String ID_COLUMN_NAME = "id";
	public static final String FIRSTNAME_COLUMN_NAME = "firstName";
	public static final String LASTNAME_COLUMN_NAME = "lastName";
	public static final String EMAIL_ADDRESS_COLUMN_NAME = "emailAddress";
	public static final String GAMERTAG_COLUMN_NAME = "gamerTag";
	public static final String BIRTHDATE_COLUMN_NAME = "birthDate";
	public static final int MAX_FIRSTNAME_LENGTH = 40;
	public static final int MAX_LASTNAME_LENGTH = 40;
	public static final int MAX_EMAIL_ADDRESS_LENGTH = 40;
	public static final int MAX_GAMERTAG_LENGTH = 20;

	public PlayerDao() {
		super(TABLE_NAME);
	}


	@Override
	public void create() throws SQLException {
		LOG.info(dateFormat.format(new Date()));
		LOG.debug("Creating database table " + TABLE_NAME);
		String sqlString = String
				.format("CREATE TABLE %s(%s INTEGER, %s VARCHAR(%d), %s VARCHAR(%d), %s VARCHAR(%d), %s VARCHAR(%d), %s DATE, PRIMARY KEY (%s))",
						TABLE_NAME, ID_COLUMN_NAME, FIRSTNAME_COLUMN_NAME, MAX_FIRSTNAME_LENGTH, LASTNAME_COLUMN_NAME,
						MAX_LASTNAME_LENGTH, EMAIL_ADDRESS_COLUMN_NAME, MAX_EMAIL_ADDRESS_LENGTH, GAMERTAG_COLUMN_NAME,
						MAX_GAMERTAG_LENGTH, BIRTHDATE_COLUMN_NAME, ID_COLUMN_NAME);

		try {
			super.create(sqlString);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
		} finally {
			LOG.debug("Created database table " + TABLE_NAME);
		}
	}
	
	public void add(Player player) throws SQLException {
		String sqlAdd = String.format("INSERT INTO %s VALUES(%d, '%s', '%s', '%s', '%s', '%s')", TABLE_NAME,
				player.getId(), player.getFirstName(), player.getLastName(), player.getEmailAddress(),
				player.getGamerTag(), new java.sql.Date(player.getBirthDate().getTime().getTime()));
		//LOG.debug(sqlAdd);
		try {
			super.execute(sqlAdd);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
		} finally {
			LOG.debug(sqlAdd);
			//LOG.debug("Added to " + TABLE_NAME);
		}
	}

	/**
	 * Update the player.
	 * 
	 * @param player
	 * @throws SQLException
	 */
	public void update(Player player) throws SQLException {
		String sqlUpdate = String.format("UPDATE %s SET %s='%s', %s='%s', %s='%s', %s='%s' WHERE %s=%d",
				TABLE_NAME, FIRSTNAME_COLUMN_NAME, player.getFirstName(), LASTNAME_COLUMN_NAME, player.getLastName(),
				EMAIL_ADDRESS_COLUMN_NAME, player.getEmailAddress(), GAMERTAG_COLUMN_NAME, player.getGamerTag(),
				ID_COLUMN_NAME, player.getId());
		try {
			super.execute(sqlUpdate);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
		} finally {
			LOG.debug(sqlUpdate);
		}
	}

	/**
	 * Delete the player from the database.
	 * 
	 * @param player
	 * @throws SQLException
	 */
	public void delete(Player player) throws SQLException {
		String sqlDelete = String.format("DELETE FROM %s WHERE %s=%d", TABLE_NAME, ID_COLUMN_NAME, player.getId());
		//LOG.debug(sqlDelete);
		try {
			super.execute(sqlDelete);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), " " + e.getCause(),	JOptionPane.ERROR_MESSAGE);
		} finally {
			LOG.debug(sqlDelete);
			//LOG.debug("Deleted from " + TABLE_NAME);
		}
	}
	
	/**
	 * @param actionCommand
	 * @return
	 * @throws ApplicationException
	 */
	public Player getPlayerById(Player player) throws Exception {
		String selectString = String.format("SELECT * FROM %s WHERE %s=%d", TABLE_NAME, ID_COLUMN_NAME, player.getId());
		LOG.debug(selectString);
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(selectString);
			int count = 0;
			while (resultSet.next()) {
				count++;
				if (count > 1) {
					JOptionPane.showInternalMessageDialog(null, "Error: " + String.format("Expected one result, got %d", count), " ", JOptionPane.ERROR_MESSAGE);
					}
				Player person= new Player();
				person.setId(resultSet.getInt(ID_COLUMN_NAME));
				person.setFirstName(resultSet.getString(FIRSTNAME_COLUMN_NAME));
				person.setLastName(resultSet.getString(LASTNAME_COLUMN_NAME));
				person.setEmailAddress(resultSet.getString(EMAIL_ADDRESS_COLUMN_NAME));
				person.setGamerTag(resultSet.getString(GAMERTAG_COLUMN_NAME));
				java.sql.Date date = resultSet.getDate(BIRTHDATE_COLUMN_NAME);
				person.setBirthDate(date);
				return person;
				}
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(null, "SQL Error: " + se.getMessage(), " "+se.getCause(), JOptionPane.ERROR_MESSAGE);

		} finally {
			close(statement);
		}
		return null;
	}
	
	

	public List<Player> getAllPlayers() throws SQLException, Exception {
		LOG.debug("Open getAllPlayers()");
		List<Player> players = new ArrayList<Player>();
		String sql = String.format("SELECT * FROM %s ", TABLE_NAME);
		LOG.debug(sql);
		Statement stmnt = null;
		ResultSet resultSet = null;
		try {
			Connection connection = Database.getConnection();
			stmnt = connection.createStatement();
			resultSet = stmnt.executeQuery(sql);
			while (resultSet.next()) {
				Player player = new Player();
				player.setId(resultSet.getInt(ID_COLUMN_NAME));
				player.setFirstName(resultSet.getString(FIRSTNAME_COLUMN_NAME));
				player.setLastName(resultSet.getString(LASTNAME_COLUMN_NAME));
				player.setEmailAddress(resultSet.getString(EMAIL_ADDRESS_COLUMN_NAME));
				player.setGamerTag(resultSet.getString(GAMERTAG_COLUMN_NAME));
				java.sql.Date date = resultSet.getDate(BIRTHDATE_COLUMN_NAME);
				player.setBirthDate(date);
				players.add(player);
			}
		} catch (SQLException se) {
			JOptionPane.showMessageDialog(null, "SQL Error: " + se.getMessage(), " " + se.getCause(),
					JOptionPane.ERROR_MESSAGE);
		} finally {
			close(stmnt);
			LOG.debug("Close getAllPlayers()");
		}
		return players;
	}
}
