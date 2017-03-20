/**
 * 
 */
package com.dreamlike.dmlkws.test.connector.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dreamlike.dmlkws.test.connector.initializer.WebSocketClientInitializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author Broly
 *
 */
@Component("webSocketClient")
public class WebSocketClient {

	@Autowired
	private WebSocketClientInitializer webSocketClientInitializer;

	@Value("${server.websocket.host}")
	private String websocketHost;

	@Value("${server.websocket.port}")
	private int websocketPort;

	private EventLoopGroup group;

	public Channel start() throws Exception {

		Channel channel = null;

		group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap().group(group).channel(NioSocketChannel.class)
					.handler(webSocketClientInitializer);
			channel = bootstrap.connect(websocketHost, websocketPort).sync().channel();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return channel;
	}

	public void shutdown() {
		if (group != null)
			group.shutdownGracefully();
	}

}
