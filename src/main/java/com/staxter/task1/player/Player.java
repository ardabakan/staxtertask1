package com.staxter.task1.player;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.staxter.task1.player.comm.PlayerClient;
import com.staxter.task1.player.comm.PlayerServer;
import com.staxter.task1.player.config.ClientConfig;
import com.staxter.task1.player.config.ServerConfig;

public class Player {

	final static Logger log = Logger.getLogger(Player.class);

	String name;

	PlayerClient playerClient;
	PlayerServer playerServer;

	ClientConfig clientConfig;
	ServerConfig serverConfig;

	public Player(String playerName, String hostToListen, int portToListen, String hostToCall, int portToCall) {

		clientConfig = new ClientConfig(hostToCall, portToCall);
		serverConfig = new ServerConfig(hostToListen, portToListen);
		name = playerName;

	}

	public void initializeServer() throws IOException, InterruptedException {

		log.info("Starting server " + name + " on " + serverConfig.getHostToListen() + ":"
				+ serverConfig.getPortToListen());

		playerServer = new PlayerServer();

		playerServer.startServer(serverConfig);

	}

	public void playTheGame(String gameMessage, int timesToSend) throws IOException, InterruptedException {

		for (int i = 0; i < timesToSend; i++) {

			log.debug("Game Call " + (i + 1) + ", sending to Player 2 the message -> " + gameMessage);

			String response = sendMessage(gameMessage);

			log.debug("Game Call " + (i + 1) + " ended and Player 2 responded -> " + response);

		}
		log.info("I am finished playing, i am sending Player 2 the poison pill");

		sendMessage(PlayerServer.POISON_PILL);

		log.info("I am finished playing, shutting down my own server");

		playerServer.shutDown();

		System.exit(0);

	}

	public String sendMessage(String message) throws IOException, InterruptedException {

		playerClient = new PlayerClient(clientConfig);

		return playerClient.sendMessage(message);

	}

}
