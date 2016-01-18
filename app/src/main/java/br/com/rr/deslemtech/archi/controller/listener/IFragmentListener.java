package br.com.rr.deslemtech.archi.controller.listener;

import android.view.View;

import br.com.rr.deslemtech.model.domain.enumeration.ActionBarStyle;
import br.com.rr.deslemtech.archi.view.fragment.Frags;

public interface IFragmentListener {

    void popBackStack(Frags frags);
    void popBackStack();
    void registerBackPress(final IBackPressListener iBackPressListener);

    View findViewById(int resId);

    /*Action Bar*/
    void setActionBarTitle(int stringResId);
    void setActionBarTitle(String string);
    void setActionBarStyle(ActionBarStyle style);

    /*Fragments*/
    void showOverviewScreen();
	void showDetailsScreen();
    void showConfigsScreen();

	void setLoadingVisibility(final boolean visible);

}
