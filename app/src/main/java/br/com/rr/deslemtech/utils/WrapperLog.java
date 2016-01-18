package br.com.rr.deslemtech.utils;

import android.util.Log;

/**
 * Class Represents a Wrapper DesignPattern in Log, we should use instead to
 * Android Native Log.
 */
public class WrapperLog {

	public static final boolean LOG_ENABLE = true;

	public static final String LOG_TAG = "DESLEMTECH";

	public static void debug(String msg) {
		if (LOG_ENABLE) {
			Log.d(LOG_TAG, msg);
		}
	}

	public static void info(String msg) {
		if (LOG_ENABLE) {
			Log.i(LOG_TAG, msg);
		}
	}

	public static void error(String msg, Throwable e) {
		if (LOG_ENABLE) {
			Log.e(LOG_TAG, msg, e);
		}
	}
	
	public static void error(String log, String msg) {
		if (LOG_ENABLE) {
			Log.e(log, msg);
		}
	}

	public static void error(String msg) {
		if (LOG_ENABLE) {
			Log.e(LOG_TAG, msg);
		}
	}

	public static void warning(String msg) {
		if (LOG_ENABLE) {
			Log.w(LOG_TAG, msg);
		}
	}

	public static void error(String msg, Class<?> className, String classMethod) {
		if(LOG_ENABLE) {
			Log.e(LOG_TAG, "[" + className.getSimpleName() + "." + classMethod + "] " + msg);
		}

	}

}
