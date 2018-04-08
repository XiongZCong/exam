package com.xzc.redis;

public class OrderKey extends KeyPrefix {

	public OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

}
