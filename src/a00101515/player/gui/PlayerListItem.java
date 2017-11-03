/**
 * File: PlayerListItem.java
 * Date: Mar 16, 2015
 * Time: 10:00:39 PM
 */

package a00101515.player.gui;

import a00101515.data.Player;

/**
 * @author G.E. Eidsness
 *
 */
public class PlayerListItem {
	
	private Player player;
	
	/**
	 * @param player
	 */
	public PlayerListItem(Player player) {
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
