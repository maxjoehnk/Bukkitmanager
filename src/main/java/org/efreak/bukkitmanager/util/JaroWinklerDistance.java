package org.efreak.bukkitmanager.util;

import org.efreak.bukkitmanager.Bukkitmanager;
import org.efreak.bukkitmanager.IOManager;

public class JaroWinklerDistance {

	private static String compOne;
	private static String compTwo;

	private static String theMatchA = "";
	private static String theMatchB = "";
	private static int mRange = -1;

	private static IOManager io;
	
	static {
		io = Bukkitmanager.getIOManager();
	}
	
	public static double getSimilarity(String s1, String s2) {
		io.debug("Calculating JaroWinklerDistance between " + s1 + " and " + s2);
		if (s1.length() > s2.length()) {
			compOne = s2;
			compTwo = s1;
		}else {
			compOne = s1;
			compTwo = s2;
		}
		
		mRange = Math.max(compOne.length(), compTwo.length()) / 2 - 1;
		double res = -1;
		int m = getMatch();
		int t = 0;
		if (getMissMatch(compTwo, compOne) > 0)
			t = (getMissMatch(compOne, compTwo) / getMissMatch(compTwo, compOne));
		int l1 = compOne.length();
		int l2 = compTwo.length();
		double f = 0.3333;
		double mt = (double) (m - t) / m;
		double jw = f * ((double) m / l1 + (double) m / l2 + (double) mt);
		res = jw + getCommonPrefix(compOne, compTwo) * (0.1 * (1.0 - jw));
		return res;
	}

	private static int getMatch() {
		theMatchA = "";
		theMatchB = "";
		int matches = 0;
		for (int i = 0; i < compOne.length(); i++) {
			int counter = 0;
			while (counter <= mRange && i >= 0 && counter <= i) {
				if (compOne.charAt(i) == compTwo.charAt(i - counter)) {
					matches++;
					theMatchA = theMatchA + compOne.charAt(i);
					theMatchB = theMatchB + compTwo.charAt(i);
				}
				counter++;
			}
			counter = 1;
			while (counter <= mRange && i < compTwo.length() && counter + i < compTwo.length()) {
				if (compOne.charAt(i) == compTwo.charAt(i + counter)) {
					matches++;
					theMatchA = theMatchA + compOne.charAt(i);
					theMatchB = theMatchB + compTwo.charAt(i);
				}
				counter++;
			}
		}
		return matches;
	}

	private static int getMissMatch(String s1, String s2) {
		int transPositions = 0;
		for (int i = 0; i < theMatchA.length(); i++) {
			int counter = 0;
			while (counter <= mRange && i >= 0 && counter <= i) {
				if (theMatchA.charAt(i) == theMatchB.charAt(i - counter) && counter > 0) transPositions++;
				counter++;
			}
			counter = 1;
			while (counter <= mRange && i < theMatchB.length() && (counter + i) < theMatchB.length()) {
				if (theMatchA.charAt(i) == theMatchB.charAt(i + counter) && counter > 0) transPositions++;
				counter++;
			}
		}
		return transPositions;
	}

	private static int getCommonPrefix(String compOne, String compTwo) {
		int cp = 0;
		for (int i = 0; i < 4 && i < compOne.length() && i < compTwo.length(); i++) {
			if (compOne.charAt(i) == compTwo.charAt(i)) cp++;
		}
		return cp;
	}
}
