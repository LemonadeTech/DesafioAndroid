package br.com.rr.deslemtech.model.domain.enumeration;

public enum ErrorType {

	CONNECTION, // connection error, not have a server contact
	UNREAD_RESPONSE, // error in server response, can not read the response
	SERVER // some error from server

}
