package br.com.rr.deslemtech.archi.view.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

public class NoClickLinearLayout extends LinearLayout {

	public NoClickLinearLayout(final Context context) {
		super(context);
	}

	public NoClickLinearLayout(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean dispatchTouchEvent(final MotionEvent event) {
		
		super.dispatchTouchEvent(event);
		
		return true;
	}
	
}
