/**
* Project: A00101515Lab10
* File: Dao.java
* Date: 2015-03-23
* Time: `7:02:29 PM
 */
package a00101515.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import a00101515.data.Database;

/**
 * @author G.E. Eidsness, A00101515
 *
 */
public abstract class Dao {
	
	private static Logger LOG = Logger.getLogger(Dao.class);
	protected final Database database;
	protected final String tableName;

	protected Dao(String tableName) {
		database = Database.newInstance();
		this.tableName = tableName;
	}

	public abstract void create() throws SQLException;
	
	protected void create(String createStatement) throws SQLException {
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			statement.executeUpdate(createStatement);
		} finally {
			close(statement);
		}
	}
	
	protected int execute(String sqlStatement) throws SQLException {
		int row = -1;
		Statement statement = null;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			row = statement.executeUpdate(sqlStatement);
		} finally {
			close(statement);
		}
		return row;
	}
	
	/**
	 * Delete the database table
	 * 
	 * @throws SQLException
	 */
	public void drop() throws SQLException {
		Statement statement = null;
		String sql = "DROP TABLE " + tableName;
		try {
			Connection connection = Database.getConnection();
			statement = connection.createStatement();
			if (Database.tableExists(tableName)) {
				statement.executeUpdate(sql);
			}
		} finally {
			close(statement);
			LOG.debug(sql);
		}
	}
	
	protected static void close(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			LOG.error("Failed to close statment" + e);
		}
	}
}

