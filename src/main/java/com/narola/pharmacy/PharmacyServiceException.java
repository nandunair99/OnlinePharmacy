package com.narola.pharmacy;

public class PharmacyServiceException extends Exception{
	public PharmacyServiceException(String message) {
		super(message);
	}

	public PharmacyServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public PharmacyServiceException(Throwable cause) {
		super(cause);
	}
}
