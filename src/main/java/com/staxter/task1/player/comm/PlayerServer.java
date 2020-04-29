package com.staxter.task1.player.comm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.staxter.task1.player.config.ServerConfig;

public class PlayerServer {

	final static Logger log = Logger.getLogger(PlayerServer.class);

	private Selector selector;
	private ServerSocketChannel serverSocket;

	int COUNTER = 0;

	public static final String POISON_PILL = "Go to sleep, game over";

	private boolean gameContinues = true;

	/**
	 * Start the server of the player
	 * 
	 * @throws IOException
	 */
	public void startServer(ServerConfig serverConfig) throws IOException {

		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		log.info("Player server is starting on " + serverConfig.getHostToListen() + ":"
				+ serverConfig.getPortToListen());
		serverSocket.bind(new InetSocketAddress(serverConfig.getHostToListen(), serverConfig.getPortToListen()));
		serverSocket.configureBlocking(false);
		serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		ByteBuffer buffer = ByteBuffer.allocate(10000);

		while (gameContinues) {

			selector.select();

			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();

			while (iter.hasNext()) {

				SelectionKey key = iter.next();

				if (key.isAcceptable()) {
					register(selector, serverSocket);
				}

				if (key.isReadable()) {
					answerWithEcho(buffer, key);
				}

				iter.remove();
			}
		}

		return;
	}

	private void answerWithEcho(ByteBuffer buffer, SelectionKey key) throws IOException {

		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);

		String received = new String(buffer.array(), "UTF-8").trim();

		log.debug("Server received -> " + received);

		// game over, shut down server
		if (received.equals(POISON_PILL)) {

			String response = "We are closed! You sent us the pill!";

			client.write(ByteBuffer.wrap(response.getBytes("UTF-8")));
			buffer.clear();
			setGameContinues(false);
			shutDown();
		} else {
			COUNTER++;
			String response = received + COUNTER;

			client.write(ByteBuffer.wrap(response.getBytes("UTF-8")));
			buffer.clear();
		}

	}

	public boolean isGameContinues() {
		return gameContinues;
	}

	public void setGameContinues(boolean gameContinues) {
		this.gameContinues = gameContinues;
	}

	private void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {

		SocketChannel client = serverSocket.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
	}

	public void shutDown() {
		try {
			log.info("Shutting down server");
			serverSocket.close();
			log.info("Shut down server");
		} catch (IOException e) {
			log.error("Got an error during server shutdown", e);
		}
	}

}
