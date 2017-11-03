/**
 * File: PlayerSorters.java
 * Package: a00101515.player
 * Date: Mar 3, 2017 Time: 12:36:18 PM 
 * Author: G.E. Eidsness
 */
package a00101515.player;

import java.util.Comparator;
import org.apache.log4j.Logger;
import a00101515.data.Player;

public class PlayerSorters {
	
	private static Logger LOG  = Logger.getLogger(PlayerSorters.class); 

	public PlayerSorters(){
		super();
		LOG.debug("PlayerSorters constructor.");
	}
	
	public static class CompareToByBirthdate implements Comparator<Player> {
		@Override
		public int compare(Player p1, Player p2) {
			return p1.getBirthDate().compareTo(p2.getBirthDate());
		}
	}
	
	public static class CompareToById implements Comparator<Player> {
		@Override
		public int compare(Player id1, Player id2) {
			return Integer.valueOf(id1.getId()).compareTo(Integer.valueOf(id2.getId()));
		}
	}
	
	public static class CompareByLastName implements Comparator<Player> {
		@Override
		public int compare(Player n1, Player n2) {
			return n1.getLastName().compareToIgnoreCase(n2.getLastName());
		}
	}

}
