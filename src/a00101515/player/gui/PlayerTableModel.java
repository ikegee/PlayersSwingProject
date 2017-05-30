/**
 * Project: A00101515Lab10 File: PlayerTableModel.java
 * Package: a00101515.player.gui
 * Date: Mar 6, 2017 Time: 5:50:04 PM 
 * Author: G.E. Eidsness, A00101515
 */
package a00101515.player.gui;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.table.AbstractTableModel;
import a00101515.data.Player;

public class PlayerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final int ID_COL = 0;
	private static final int LAST_NAME_COL = 1;
	private static final int FIRST_NAME_COL = 2;
	private static final int EMAIL_COL = 3;
	private static final int GAMERTAG_COL = 4;
	private static final int BIRTHDATE_COL = 5;

	private final String[] columnNames = { "id", "Last Name", "First Name", "Email", "Gamer Tag", "Birthdate" };
	private final List<Player> players;
	
	public PlayerTableModel(List<Player> players) {
		this.players = players;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return players.size();
	}

	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int row, int column) {
		Player tempPlayer = players.get(row);
		switch (column) {
		case ID_COL:
			return tempPlayer.getId();
		case LAST_NAME_COL:
			return tempPlayer.getLastName();
		case FIRST_NAME_COL:
			return tempPlayer.getFirstName();
		case EMAIL_COL:
			return tempPlayer.getEmailAddress();
		case BIRTHDATE_COL:
			GregorianCalendar birthDate = tempPlayer.getBirthDate();
			return new SimpleDateFormat("dd-MM-yyyy").format(birthDate.getTime());
		case GAMERTAG_COL:
			return tempPlayer.getGamerTag();
		default:
			return tempPlayer.getGamerTag();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}

}
