package com.staxter.test;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.staxter.task1.StaxterDifferentPid_Initiator;
import com.staxter.task1.player.Player;

public class StaxterTest {

	final static Logger log = Logger.getLogger(StaxterDifferentPid_Initiator.class);

	static Player player1;
	static Player player2;

	static int TEST_PORT_1 = 9096;
	static int TEST_PORT_2 = 9097;

	public static final String GAME_MESSAGE = "Hey this is a test";
	public static final int GAME_ROUNDS = 5;

	static {

		try {

			createPlayerServers(TEST_PORT_1, TEST_PORT_2);

		} catch (Exception e) {
			log.error("Could not start players", e);
		}

	}

	@Test
	// Let's test if the player2's server will respond with a good counter at the
	// end as concatenated
	// Hey this is a test -> should receive -> Hey this is a test1
	// and Hey this is a test2 for the 2nd round etc..
	public void serverRespondsWithCounter() throws IOException, InterruptedException {

		for (int i = 0; i < GAME_ROUNDS; i++) {
			Assert.assertEquals(GAME_MESSAGE + (i + 1), player1.sendMessage(GAME_MESSAGE));
		}

	}

	public static void createPlayerServers(int portPlayer1, int portPlayer2) throws IOException, InterruptedException {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// playerResponder listens to port1 and calls the player on port2
				player1 = new Player("Initiator", "localhost", portPlayer1, "localhost", portPlayer2);
				try {
					player1.initializeServer();

				} catch (IOException | InterruptedException e) {
					log.error("Could not start player 1", e);
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {

				// player2 listens to port2 and calls the player on port1
				// for this game, it will not call playerResponder, just respond to
				// playerResponder's messages
				player2 = new Player("Responder", "localhost", portPlayer2, "localhost", portPlayer1);
				try {
					player2.initializeServer();
				} catch (IOException | InterruptedException e) {
					log.error("Could not start player 2", e);
				}

			}
		});

		t1.start();
		t2.start();

		Thread.sleep(5000);
	}

}
