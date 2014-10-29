package com.g1436218.Object;

public class Connection {

	private String macAddress;
	private int strength;
	
	public Connection(String macAddress, int strength){
		this.macAddress = macAddress;
		this.strength = strength;
	}
	
	public String getMacAddress(){
		return macAddress;
	}
	
	@Override
	public boolean equals(Object obj){
		return macAddress.equals(((Connection) obj).getMacAddress());
	}
	
	@Override
	public int hashCode(){
		return this.macAddress.hashCode();
	}
	
	@Override
	public String toString(){
		return macAddress + " : " + strength + "dBm";
	}
}
