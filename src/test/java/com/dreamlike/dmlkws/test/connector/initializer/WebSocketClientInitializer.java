/**
 * 
 */
package com.dreamlike.dmlkws.test.connector.initializer;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.dreamlike.dmlkws.test.connector.codec.WebSocketClientCodec;
import com.dreamlike.dmlkws.test.connector.handler.WebSocketClientHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;

/**
 * @author Broly
 *
 */
@Component("webSocketClientInitializer")
public class WebSocketClientInitializer extends ChannelInitializer<SocketChannel> {

	@Autowired
	private WebSocketClientCodec webSocketClientCodec;
	@Autowired
	private WebSocketClientHandler webSocketClientHandler;

	@Value("${server.websocket.scheme}")
	private String websocketScheme;

	@Value("${server.websocket.host}")
	private String websocketHost;

	@Value("${server.websocket.port}")
	private int websocketPort;

	@Value("${server.websocket.path}")
	private String websocketPath;

	private URI getWebSocketURL() {
		String uri = websocketScheme + "://" + websocketHost + ":" + String.valueOf(websocketPort) + websocketPath;
		return URI.create(uri);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ChannelPipeline pipeline = ch.pipeline();

		// 编解码 http 请求
		pipeline.addLast(new HttpClientCodec());
		// 聚合解码 HttpRequest/HttpContent/LastHttpContent 到 FullHttpRequest
		// 保证接收的 Http 请求的完整性
		pipeline.addLast(new HttpObjectAggregator(64 * 1024));
		// 处理其他的 WebSocketFrame
		pipeline.addLast(new WebSocketClientProtocolHandler(getWebSocketURL(), WebSocketVersion.V13, null, false,
				new DefaultHttpHeaders(), 65536));
		// 处理 TextWebSocketFrame
		pipeline.addLast(webSocketClientCodec);
		pipeline.addLast(webSocketClientHandler);
	}

}
