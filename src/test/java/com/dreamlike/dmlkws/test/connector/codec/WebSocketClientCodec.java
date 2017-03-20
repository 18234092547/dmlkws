/**
 * 
 */
package com.dreamlike.dmlkws.test.connector.codec;

import java.util.List;

import org.springframework.stereotype.Component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author Broly
 *
 */
@Component("webSocketClientCodec")
public class WebSocketClientCodec extends MessageToMessageCodec<TextWebSocketFrame, String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
		out.add(new TextWebSocketFrame(msg));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame, List<Object> out)
			throws Exception {
		out.add(textWebSocketFrame.text());
	}

}
