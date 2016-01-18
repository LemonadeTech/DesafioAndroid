package br.com.rr.deslemtech.model.domain;

import br.com.rr.deslemtech.model.domain.enumeration.ErrorType;

public class HttpResultDTO<T> {

	public boolean result;
	public String msg;
	public int msgResId;
	public String errorCode;
	public int type;
	public T data;
	public ErrorType errorType;

	public boolean showToastError = true;
}
