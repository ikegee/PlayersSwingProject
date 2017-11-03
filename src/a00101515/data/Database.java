/**
* File: Database.java
* Date: 2015-03-23
* Time: `7:03:37 PM
 */
package a00101515.data;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

/**
 * @author G.E. Eidsness
 *
 */
public class Database implements Serializable{

	private static final long serialVersionUID = 1L;
	public static final String DB_DRIVER_KEY = "db.driver";
	public static final String DB_URL_KEY = "db.url";
	public static final String DB_USER_KEY = "db.user";
	public static final String DB_PASSWORD_KEY = "db.password";

	private static Logger LOG = Logger.getLogger(Database.class.getName());

	private static Database theInstance = new Database();
	private static Connection connection;
	private static Properties properties;

	private Database() {
	}
	
	public static void init(Properties properties) {
		if (Database.properties == null) {
			LOG.debug("Loading database properties from db.properties");
			Database.properties = properties;
		}
	}

	public static Connection getConnection() throws SQLException {
		if (connection != null) {
			return connection;
		}
		try {
			connect();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Conn Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
		}

		return connection;
	}

	private static void connect() throws ClassNotFoundException, SQLException {
		String dbDriver = properties.getProperty(DB_DRIVER_KEY);
		Class.forName(dbDriver);
		LOG.debug("Loading " + dbDriver);
		try {
			connection = DriverManager.getConnection(properties.getProperty(DB_URL_KEY),
					properties.getProperty(DB_USER_KEY), properties.getProperty(DB_PASSWORD_KEY));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DB Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
		}
		
		LOG.debug("Database connected");
	}

	/**
	 * Close the connections to the database
	 */
	public void shutdown() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Determine if the database table exists.
	 * 
	 * @param tableName
	 * @return true is the table exists, false otherwise
	 * @throws SQLException
	 */
	public static boolean tableExists(String tableName) throws SQLException {
		DatabaseMetaData databaseMetaData = getConnection().getMetaData();
		ResultSet resultSet = null;
		String rsTableName = null;
		try {
			resultSet = databaseMetaData.getTables(connection.getCatalog(), "%", "%", null);
			while (resultSet.next()) {
				rsTableName = resultSet.getString("TABLE_NAME");
				if (rsTableName.equalsIgnoreCase(tableName)) {
					return true;
				}
			}
		} finally {
			resultSet.close();
		}
		return false;
	}

	/**
	 * @return the theinstance
	 * Thread safe
	 */
 	public static synchronized Database newInstance() {
        if(theInstance == null){
            theInstance = new Database();
        }
		return theInstance;
	}
}

