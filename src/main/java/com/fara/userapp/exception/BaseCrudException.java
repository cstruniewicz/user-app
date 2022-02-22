package com.fara.userapp.exception;

public abstract class BaseCrudException extends RuntimeException{

	public BaseCrudException(String message) {
		super(message);
	}
}
