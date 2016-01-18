package br.com.rr.deslemtech.view.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.view.fragment.AbstractFragment;

public class DetailsFragment extends AbstractFragment {

	protected InputMethodManager inputMethodManager;

	public DetailsFragment() {
		layoutResId = R.layout.overview;
	}

	@Override
    protected void initView(Bundle savedInstanceState) {


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {

			default:
				break;
		}
    }

}
