/**
 * 
 */
package com.dreamlike.dmlkws.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dreamlike.dmlkws.connector.bean.Message;
import com.dreamlike.dmlkws.connector.bean.Proto;
import com.dreamlike.dmlkws.connector.global.ProtoServiceType;
import com.dreamlike.dmlkws.test.connector.client.WebSocketClient;
import com.dreamlike.dmlkws.util.JsonUtil;

import io.netty.channel.Channel;

/**
 * @author Broly
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext.xml", "classpath:spring-test.xml" })
public class WebSocketTests {

	private static final Logger logger = LoggerFactory.getLogger(WebSocketTests.class);

	@Autowired
	private WebSocketClient webSocketClient;

	@Test
	public void socketTest() {

		logger.debug("--- Client start ---");

		Channel channel = null;
		try {
			channel = webSocketClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (channel != null) {
			int max = 99999;
			int min = 10000;
			Random random = new Random();

			int userId = random.nextInt(max) % (max - min + 1) + min;

			int sendCount = 10;
			for (int i = 0; i < sendCount; i++) {
				channel.writeAndFlush(getMockProto(userId));
			}
		}

		webSocketClient.shutdown();
	}

	private String getMockProto(int userId) {

		int max = 1;
		int min = 50;
		Random random = new Random();
		int length = random.nextInt(max) % (max - min + 1) + min;
		String randomString = getRandomString(length);

		Message message = new Message();
		message.setUserId(userId);
		// message.setCreateTime((int) (System.currentTimeMillis() / 1000L));
		message.setCreateTime((int) System.currentTimeMillis());
		message.setMsgType(0);
		message.setMsgData(randomString);

		Proto proto = new Proto();
		proto.setLength(Proto.HEADER_LENGTH + sizeofObject(message));
		proto.setVersion(Proto.VERSION);
		proto.setServiceType(ProtoServiceType.MESSAGE.getValue());
		proto.setCrc(0);
		proto.setReserved(0);
		proto.setBody(message);

		return JsonUtil.object2String(proto);
	}

	private int sizeofObject(Object object) {

		int size = 0;

		try {
			ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
			ObjectOutputStream objectOutputStream;

			objectOutputStream = new ObjectOutputStream(byteOutputStream);

			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
			objectOutputStream.close();

			size = byteOutputStream.toByteArray().length;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return size;
	}

	private String getRandomString(int length) {
		StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(random.nextInt(range)));
		}
		return sb.toString();
	}
}
