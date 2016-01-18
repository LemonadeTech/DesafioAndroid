package br.com.rr.deslemtech.model.http;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.controller.listener.ISimpleHttpResultListener;
import br.com.rr.deslemtech.model.domain.HttpResultDTO;
import br.com.rr.deslemtech.model.domain.enumeration.ErrorType;
import br.com.rr.deslemtech.utils.WrapperLog;

public class DefaultResultAsyncHttpRespHandler<T> extends AsyncHttpResponseHandler {

	protected ISimpleHttpResultListener<T> listener;

	protected HttpResultDTO<T> resultDTO;

	public DefaultResultAsyncHttpRespHandler(final ISimpleHttpResultListener<T> listener) {

		this.listener = listener;

		resultDTO = new HttpResultDTO<>();
	}

	@Override
	public void onSuccess(int code, Header[] header, byte[] bc) {

		try {
			// get json from result
			String json = new String(bc, "utf-8");
			WrapperLog.error(json);

			// get data
			getDataFromJson(json); // this will set resultDTO

			if(listener != null) {
				listener.result(resultDTO);
			}

		} catch (UnsupportedEncodingException e) {

			resultDTO.result = false;
			resultDTO.msgResId = R.string.error_on_server_response;
			resultDTO.errorType = ErrorType.UNREAD_RESPONSE;

			if(listener != null) {
				listener.result(resultDTO);
			}

			WrapperLog.error("UnsupportedEncodingException: " + e.getLocalizedMessage(), e);

		} catch(JSONException e) {

			resultDTO.result = false;
			resultDTO.msgResId = R.string.error_on_server_response;
			resultDTO.errorType = ErrorType.UNREAD_RESPONSE;

			if(listener != null) {
				listener.result(resultDTO);
			}

			WrapperLog.error("JSONException: " + e.getLocalizedMessage(), e);

		}

	}

	@Override
	public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

		resultDTO.result = false;
		resultDTO.msgResId = R.string.connection_error;
		resultDTO.errorType = ErrorType.CONNECTION;

		if(listener != null) {
			listener.result(resultDTO);
		}
		WrapperLog.error("HTTP: onFailure: "+error.getLocalizedMessage(), error);
	}

	protected void getDataFromJson(String json) throws JSONException {

		// parse json
		JSONObject jsonObjRoot = new JSONObject(json);

        resultDTO.data = parseData(jsonObjRoot);
        resultDTO.result = true;

	}

	protected T parseData(JSONObject jsonObjRoot) throws JSONException {
		return null;
	}

}
