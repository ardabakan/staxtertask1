package com.staxter.task1;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.staxter.task1.player.Player;

public class StaxterDifferentPid_Responder {

	final static Logger log = Logger.getLogger(StaxterDifferentPid_Responder.class);

	static Player playerResponder;

	public static void main(String[] args) {

		if (args == null || args.length != 2) {

			System.out.println(
					"Please provide 1 ports to start the responder, ex : \"java StaxterDifferentPid_Responder 9092 9091\"");

			System.exit(0);

		}

		try {

			int portPlayer1 = Integer.parseInt(args[0]);
			int portPlayer2 = Integer.parseInt(args[1]);

			createPlayerServers(portPlayer1, portPlayer2);

		} catch (Exception e) {
			log.error("Could not start responder", e);
		}

	}

	public static void createPlayerServers(int portPlayer1, int portPlayer2) throws IOException, InterruptedException {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {

				// playerResponder listens to port1 and calls the player on port2
				playerResponder = new Player("Initiator", "localhost", portPlayer1, "localhost", portPlayer2);
				try {
					playerResponder.initializeServer();

				} catch (IOException | InterruptedException e) {
					log.error("Could not start player 1", e);
				}
			}
		});

		t1.start();
	}

}
