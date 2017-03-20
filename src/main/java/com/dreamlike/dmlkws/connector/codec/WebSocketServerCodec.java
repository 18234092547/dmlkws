/**
 * 
 */
package com.dreamlike.dmlkws.connector.codec;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dreamlike.dmlkws.connector.bean.Proto;
import com.dreamlike.dmlkws.util.JsonUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @author Broly
 *
 */
@Component("webSocketServerCodec")
@Sharable
public class WebSocketServerCodec extends MessageToMessageCodec<TextWebSocketFrame, Proto> {

	@Override
	protected void encode(ChannelHandlerContext ctx, Proto proto, List<Object> out) throws Exception {
		String json = JsonUtil.object2String(proto);
		out.add(new TextWebSocketFrame(json));
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame, List<Object> out)
			throws Exception {
		String json = textWebSocketFrame.text();
		Proto proto = JsonUtil.string2Object(json, Proto.class);
		out.add(proto);
	}

}
