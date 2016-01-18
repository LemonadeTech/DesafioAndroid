package br.com.rr.deslemtech.archi.exception;

public class MissingLayoutException extends RuntimeException {

	public MissingLayoutException() {
		super("Layout resource id is missing. Make 'this.layoutResId = R.layout.your_frag_layout;' in fragment constructor.");
	}

}
