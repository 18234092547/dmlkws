/**
 * 
 */
package com.dreamlike.dmlkws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.dreamlike.dmlkws.connector.server.BaseServer;

/**
 * @author Broly
 *
 */
@Configuration
@ComponentScan("com.dreamlike.dmlkws")
@EnableAutoConfiguration
@ImportResource({ "applicationContext.xml" })
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private BaseServer webSocketServer;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		logger.debug("--- Server start ---");

		try {
			webSocketServer.start();

			// 让线程一直执行
			Thread.currentThread().join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
