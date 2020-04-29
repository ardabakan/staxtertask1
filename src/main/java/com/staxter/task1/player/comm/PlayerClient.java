package com.staxter.task1.player.comm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.apache.log4j.Logger;

import com.staxter.task1.player.config.ClientConfig;

public class PlayerClient {
	
	final static Logger log = Logger.getLogger(PlayerClient.class);

	ClientConfig clientConfig;

	public PlayerClient(ClientConfig clientConfig) {
		super();
		this.clientConfig = clientConfig;
	}

	public String sendMessage(String messageToSend) throws IOException, InterruptedException {

		log.debug("Client will send : " + messageToSend + " to " + clientConfig.getHostToCall() + ":"
				+ clientConfig.getPortToCall());

		SocketChannel client = SocketChannel
				.open(new InetSocketAddress(clientConfig.getHostToCall(), clientConfig.getPortToCall()));

		ByteBuffer buffer = ByteBuffer.wrap(messageToSend.getBytes());
		ByteBuffer readBuffer = ByteBuffer.allocate(10000);

		String response = null;
		try {
			client.write(buffer);
			buffer.clear();
			client.read(readBuffer);
			response = new String(readBuffer.array()).trim();
			log.debug("Player received from server -> " + response);
			readBuffer.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;

	}

}
