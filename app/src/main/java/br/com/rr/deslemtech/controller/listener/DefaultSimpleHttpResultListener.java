package br.com.rr.deslemtech.controller.listener;

import android.content.Context;
import android.os.Handler;

import br.com.rr.deslemtech.model.domain.HttpResultDTO;
import br.com.rr.deslemtech.utils.Utils;

public class DefaultSimpleHttpResultListener<T> implements ISimpleHttpResultListener<T> {

	protected Context context;
	protected Handler handler;

	public DefaultSimpleHttpResultListener(Context context) {
		this.context = context;
	}

	public DefaultSimpleHttpResultListener(Context context, final Handler handler) {
		this.context = context;
		this.handler = handler;
	}

	@Override
	public void result(final HttpResultDTO<T> resultDTO) {

		if(handler != null) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					resultHandler(resultDTO);
				}
			});
		}
		else {
			resultHandler(resultDTO);
		}

	}
	public void resultHandler(HttpResultDTO<T> resultDTO) {

		if(resultDTO.result) {
			success(resultDTO.data, resultDTO.type);
		}
		else {
			failed(resultDTO);

			// show error msg
			Utils.showWsRequestError(context, resultDTO);
		}
	}

	protected void success(T data) {
	}
	protected void success(T data, int type) {
		success(data);
	}

	protected void failed() {
	}
	protected void failed(HttpResultDTO<T> resultDTO) {
		failed();
	}

}
