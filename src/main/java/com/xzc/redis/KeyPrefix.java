package com.xzc.redis;

public abstract class KeyPrefix {

	private int expire;
	private String prefix;
	
	public KeyPrefix(String prefix) {//0代表永不过期
		this(0, prefix);
	}
	
	public KeyPrefix(int expire, String prefix) {
		this.expire = expire;
		this.prefix = prefix;
	}
	
	public int expire() {//默认0代表永不过期
		return expire;
	}

	public String getPrefix() {
		String className = getClass().getSimpleName();
		return className+":" + prefix;
	}

}
