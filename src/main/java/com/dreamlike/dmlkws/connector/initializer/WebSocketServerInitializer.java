/**
 * 
 */
package com.dreamlike.dmlkws.connector.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dreamlike.dmlkws.connector.codec.WebSocketServerCodec;
import com.dreamlike.dmlkws.connector.handler.WebSocketServerHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @author Broly
 *
 */
@Component("webSocketServerInitializer")
public class WebSocketServerInitializer extends ChannelInitializer<NioSocketChannel> {

	@Autowired
	private WebSocketServerHandler webSocketServerHandler;
	@Autowired
	private WebSocketServerCodec webSocketServerCodec;

	@Value("${server.websocket.path}")
	private String websocketPath;

	@Override
	protected void initChannel(NioSocketChannel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();

		// 编解码 http 请求
		pipeline.addLast(new HttpServerCodec());
		// 写文件内容
		pipeline.addLast(new ChunkedWriteHandler());
		// 聚合解码 HttpRequest/HttpContent/LastHttpContent 到 FullHttpRequest
		// 保证接收的 Http 请求的完整性
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		// 处理其他的 WebSocketFrame
		pipeline.addLast(new WebSocketServerProtocolHandler(websocketPath));
		// 处理 TextWebSocketFrame
		pipeline.addLast(webSocketServerCodec);
		pipeline.addLast(webSocketServerHandler);
	}

}
