/**
 * 
 */
package com.dreamlike.dmlkws.connector.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.dreamlike.dmlkws.connector.bean.Proto;
import com.dreamlike.dmlkws.connector.global.ProtoServiceType;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author Broly
 *
 */
@Component("webSocketServerHandler")
@Scope("prototype")
@Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Proto> {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

	public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception { // (2)
		Channel incoming = ctx.channel();

		// Broadcast a message to multiple Channels
		channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "加入\n");
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception { // (3)
		Channel incoming = ctx.channel();

		// Broadcast a message to multiple Channels
		channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + "离开\n");

		// A closed Channel is automatically removed from ChannelGroup,
		// so there is no need to do "channels.remove(ctx.channel());"
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Proto proto) throws Exception {
		Channel channel = ctx.channel();

		int serviceType = proto.getServiceType();
		if (serviceType == ProtoServiceType.DEFAULT.getValue()) {
			doDefaultAction(channel, proto);
		} else if (serviceType == ProtoServiceType.MESSAGE.getValue()) {
			doMessageAction(channel, proto);
		}

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
		Channel incoming = ctx.channel();

		logger.debug("[SERVER] - " + incoming.remoteAddress() + "在线\n");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
		Channel incoming = ctx.channel();

		logger.debug("[SERVER] - " + incoming.remoteAddress() + "掉线\n");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (7)
		Channel incoming = ctx.channel();

		logger.debug("[SERVER] - " + incoming.remoteAddress() + "异常\n");

		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}

	private void doDefaultAction(Channel channel, Proto proto) {
		// do nothing
	}

	private void doMessageAction(Channel channel, Proto proto) {
		logger.debug(proto.toString());
	}

}
