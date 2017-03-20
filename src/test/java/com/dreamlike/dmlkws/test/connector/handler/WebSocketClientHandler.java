/**
 * 
 */
package com.dreamlike.dmlkws.test.connector.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Broly
 *
 */
@Component("webSocketClientHandler")
public class WebSocketClientHandler extends SimpleChannelInboundHandler<String> {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketClientHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//Channel channel = ctx.channel();

		//channel.writeAndFlush("hello");
		logger.debug("client channel active");
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Channel channel = ctx.channel();

		channel.writeAndFlush("Server returned: " + msg);
	}

}
