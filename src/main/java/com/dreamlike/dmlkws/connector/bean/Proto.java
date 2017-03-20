/**
 * 
 */
package com.dreamlike.dmlkws.connector.bean;

/**
 * @author Broly
 *
 */
public class Proto {

	public static final int HEADER_LENGTH = 20;
	public static final int VERSION = 1;

	private int length;
	private int version;
	private int serviceType;
	private int crc;
	private int reserved;
	private Object body;

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getServiceType() {
		return serviceType;
	}

	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}

	public int getCrc() {
		return crc;
	}

	public void setCrc(int crc) {
		this.crc = crc;
	}

	public int getReserved() {
		return reserved;
	}

	public void setReserved(int reserved) {
		this.reserved = reserved;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Proto [length=" + length + ", version=" + version + ", serviceType=" + serviceType + ", crc=" + crc
				+ ", reserved=" + reserved + ", body=" + body + "]";
	}

}
