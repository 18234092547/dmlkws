/**
 * 
 */
package com.dreamlike.dmlkws.connector.global;

/**
 * @author Broly
 *
 */
public enum ProtoServiceType {

	/**
	 * Broly: 默认
	 */
	DEFAULT(1000),

	/**
	 * Broly: 广播（即系统消息）
	 */
	BROADCAST(1001),
	
	/**
	 * Broly: 消息
	 */
	MESSAGE(1002);

	private int value;

	private ProtoServiceType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
