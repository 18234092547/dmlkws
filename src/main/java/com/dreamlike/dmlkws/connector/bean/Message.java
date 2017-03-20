/**
 * 
 */
package com.dreamlike.dmlkws.connector.bean;

import java.io.Serializable;

/**
 * @author Broly
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer userId;
	private Integer createTime;
	private Integer msgType;
	private String msgData;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getMsgData() {
		return msgData;
	}

	public void setMsgData(String msgData) {
		this.msgData = msgData;
	}

	@Override
	public String toString() {
		return "Message [userId=" + userId + ", createTime=" + createTime + ", msgType=" + msgType + ", msgData="
				+ msgData + "]";
	}

}
