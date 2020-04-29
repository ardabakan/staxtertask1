package com.staxter.task1;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.staxter.task1.player.Player;

public class StaxterDifferentPid_Initiator {

	final static Logger log = Logger.getLogger(StaxterDifferentPid_Initiator.class);

	static Player player1;

	public static final String GAME_MESSAGE = "Hey how are you?";
	public static final int GAME_ROUNDS = 10;

	public static void main(String[] args) {

		if (args == null || args.length != 2) {

			System.out.println(
					"Please provide 2 ports to start 2 players, ex : \"java StaxterDifferentPid_Initiator 9091 9092\"");

			System.exit(0);

		}

		try {

			int portPlayer1 = Integer.parseInt(args[0]);
			int portPlayer2 = Integer.parseInt(args[1]);

			createPlayerServers(portPlayer1, portPlayer2);

		} catch (Exception e) {
			log.error("Could not start Initiator", e);
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

		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {

				try {

					Thread.sleep(3000);

					player1.playTheGame(GAME_MESSAGE, GAME_ROUNDS);
				} catch (IOException | InterruptedException e) {
					log.error("Player 1 could not play the game", e);
				}

			}
		});

		t1.start();

		t3.start();
	}

}
