package org.efreak.bukkitmanager.external;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BukkitmanagerTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testMain() {
		Bukkitmanager.main(new String[]{"--help"});
		Bukkitmanager.main(new String[]{"--external", "--start-remote"});
		Bukkitmanager.main(new String[]{"--external", "--stop-remote"});
	}

}
