/**
 * 
 */
package com.dreamlike.dmlkws.connector.server;

/**
 * @author Broly
 *
 */
public interface BaseServer {

	void start() throws Exception;

	void restart() throws Exception;
	
	void shutdown();

}
