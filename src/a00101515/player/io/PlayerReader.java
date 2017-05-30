/**
* Project: A00101515Lab10
* File: PlayerReader.java
* Date: 2015-03-23
* Time: `7:22:45 PM
 */
package a00101515.player.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

import a00101515.ApplicationException;
import a00101515.data.Player;
import a00101515.util.Validator;

/**
 * @author G.E. Eidsness, A00101515
 *
 */

public class PlayerReader {

	public static final String RECORD_DELIMITER = ":";
	public static final String FIELD_DELIMITER = "\\|";
	private static Logger LOG = Logger.getLogger(PlayerReader.class);

	/**
	 * private constructor to prevent instantiation
	 */
	private PlayerReader() {
	}

	public static List<Player> read(File file) throws ApplicationException {
		LOG.debug("Reading" + file.getAbsolutePath());
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), " "+e.getCause(), JOptionPane.ERROR_MESSAGE);
		}
		List<Player> personas = new ArrayList<Player>();
		try {
			while (scanner.hasNext()) {
				String row = scanner.nextLine();
				String[] elements = row.split(FIELD_DELIMITER);
				if (elements.length != Player.ATTRIBUTE_COUNT) {
					JOptionPane.showMessageDialog(null, String.format("Expected %d but got %d: %s", 
							Player.ATTRIBUTE_COUNT, elements.length, Arrays.toString(elements)));
				}

				Player persona = new Player();
				int index = 0;
				persona.setId(Integer.parseInt(elements[index++]));
				persona.setFirstName(elements[index++]);
				persona.setLastName(elements[index++]);
				String email = elements[index++];
				if (!Validator.validateEmail(email)) {
					JOptionPane.showMessageDialog(null, "Error: ", 
							String.format("Invalid email: %s", email), JOptionPane.ERROR_MESSAGE);

				}
				persona.setEmailAddress(email);
				persona.setGamerTag(elements[index++]);

				String yyyymmdd = elements[index];
				try {
					persona.setBirthDate(Integer.parseInt(yyyymmdd.substring(0, 4)),
							Integer.parseInt(yyyymmdd.substring(4, 6)) - 1, Integer.parseInt(yyyymmdd.substring(6, 8)));
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), 
							"Invalid date:" + yyyymmdd, JOptionPane.ERROR_MESSAGE);
				}

				personas.add(persona);
			}
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		LOG.debug("End PlayerReader");
		return personas;
	}	
}

