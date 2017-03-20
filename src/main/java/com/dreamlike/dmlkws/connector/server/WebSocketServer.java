/**
 * 
 */
package com.dreamlike.dmlkws.connector.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dreamlike.dmlkws.connector.initializer.WebSocketServerInitializer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author Broly
 *
 */
@Component("webSocketServer")
public class WebSocketServer implements BaseServer {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

	@Autowired
	private WebSocketServerInitializer webSocketServerInitializer;

	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	private ChannelFuture channelFuture;

	@Value("${server.websocket.port}")
	private int websocketPort;

	@Override
	public void start() throws Exception {
		try {
			ServerBootstrap bootstrap = new ServerBootstrap();// (3)
			bootstrap.group(bossGroup, workerGroup); // (4)
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(webSocketServerInitializer);
			bootstrap.option(ChannelOption.SO_BACKLOG, 128);
			bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

			// 绑定端口 同步等待成功
			channelFuture = bootstrap.bind(websocketPort).sync();
			// 等待服务端监听端口关闭
			// channelFuture.channel().closeFuture().sync();

			logger.debug("WebSocketServer 启动了");

		} finally {
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					shutdown();
				}
			});
		}
	}

	@Override
	public void restart() throws Exception {
		shutdown();
		start();
	}

	@Override
	public void shutdown() {

		if (channelFuture != null) {
			channelFuture.channel().close().syncUninterruptibly();
		}
		if (bossGroup != null) {
			bossGroup.shutdownGracefully();
		}
		if (workerGroup != null) {
			workerGroup.shutdownGracefully();
		}
	}

}
