package com.example.tiposdementes;

public class Singleton {
	private static Singleton instance = null;
	private boolean response;

	private Singleton() {
	}

	public static Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		return instance;
	}

	public void setResponse(boolean response) {
		this.response = response;
	}

	public boolean getResponse() { return response; }
}
