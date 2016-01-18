package br.com.rr.deslemtech.controller.listener;

import br.com.rr.deslemtech.model.domain.HttpResultDTO;

public interface ISimpleHttpResultListener<T> {

	void result(HttpResultDTO<T> result);

}
