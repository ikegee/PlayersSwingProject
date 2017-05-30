/**
 * Project: A00101515Lab10 File: Validator.java
 * Package: a00101515.util
 * Date: Mar 3, 2017 Time: 11:44:23 PM 
 * Author: G.E. Eidsness, A00101515
 */
package a00101515.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static Pattern pattern;
	private static Matcher matcher;

	private Validator() {
	}

	public static boolean validateEmail(final String email) {
		if (pattern == null) {
			pattern = Pattern.compile(EMAIL_PATTERN);
		}

		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
