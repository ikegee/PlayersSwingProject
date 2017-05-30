/**
 * Project: A00101515Lab10 File: PlayerTableItem.java
 * Package: a00101515.player.gui
 * Date: Mar 6, 2017 Time: 5:51:03 PM 
 * Author: G.E. Eidsness, A00101515
 */
package a00101515.player.gui;

import a00101515.data.Player;

public class PlayerTableItem {
	
	private Player player;
	
	/**
	 * @param player
	 */
	public PlayerTableItem(Player player) {
		this.player = player;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (player == null) {
			return null;
		}

		String birthdate = "";
		if (player.getBirthDate() != null) {
			birthdate = PlayerListModel.birthDateFormat.format(player.getBirthDate().getTime());
		}
	
		return player.getId() + " " + player.getFirstName() + " " + player.getLastName() + " " 
		+ player.getEmailAddress() + " " + player.getGamerTag() + " " +  birthdate;
	}

}
