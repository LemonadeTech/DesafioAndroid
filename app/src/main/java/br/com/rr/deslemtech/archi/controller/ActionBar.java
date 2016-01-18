package br.com.rr.deslemtech.archi.controller;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.controller.activity.AbstractFragmentActivity;
import br.com.rr.deslemtech.archi.view.fragment.AbstractFragment;
import br.com.rr.deslemtech.model.domain.enumeration.ActionBarStyle;
import br.com.rr.deslemtech.utils.Fonts;


public class ActionBar implements View.OnClickListener {

    protected AbstractFragmentActivity activity;
    protected AbstractFragment fragment;

	protected Menu menu;

    protected ActionBarStyle currentStyle = null;
    protected View actionBarLayout;
    protected TextView textActionBar;
    protected Button buttonMenu;

	protected View.OnTouchListener barTouchListener;

    public ActionBar(AbstractFragmentActivity activity) {

		this.activity = activity;

        initActionBar();
    }

    public ActionBar(AbstractFragment fragment) {

        this.fragment = fragment;

		initActionBar();
    }

    protected View findViewById(final int resId) {
        View retView = null;

        if (activity != null) {
            retView = activity.findViewById(resId);
        }
		else if (fragment != null) {
            retView = fragment.findViewById(resId);
        }

        return retView;
    }

    private void initActionBar() {

        actionBarLayout = findViewById(R.id.action_bar);

        buttonMenu = (Button) findViewById(R.id.actbar_button_menu);

        textActionBar = (TextView) findViewById(R.id.actbar_textview_title);

		// set typeface
		textActionBar.setTypeface(Fonts.robotoBold);

        // set click listeners
        setClickListener(buttonMenu);

    }

    protected void setClickListener(View view) {
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(final View view) {

        if (barTouchListener == null) {

            int id = view.getId();

            switch (id) {

			case R.id.actbar_button_menu:
				menuDefaultAction();
				break;

			default:
				break;
            }

        }

    }

    protected void menuDefaultAction() {
        changeMenuApp();
    }

    protected void changeMenuApp() {

        if (menu != null) {

            if (menu.drawerLayout.isDrawerOpen(menu.drawerMenu)) {
                menu.drawerLayout.closeDrawer(menu.drawerMenu);
            }
			else {
                menu.drawerLayout.openDrawer(menu.drawerMenu);
            }
        }

    }

    public void setDrawerMenu(final Menu menu) {
        this.menu = menu;
    }


    public void setStyle(final ActionBarStyle actionBarStyle) {
        currentStyle = actionBarStyle;

        // set all invisible
        setAllInvisible();

        switch (actionBarStyle) {

            case MENU:

                setMenuLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

				actionBarLayout.setVisibility(View.VISIBLE);

				buttonMenu.setVisibility(View.VISIBLE);
                break;


			case BACK:
				actionBarLayout.setVisibility(View.VISIBLE);
				break;


			default:
                break;
        }
    }

    protected void setAllInvisible() {

		setMenuLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

		buttonMenu.setVisibility(View.GONE);

    }

    private void setMenuLockMode(final int DrawerLockMode) {

        if (menu != null && menu.drawerLayout != null) {
            menu.drawerLayout.setDrawerLockMode(DrawerLockMode);
        }

    }

    public void setText(final String text) {

        if (textActionBar != null) {
            textActionBar.setText(text);
        }
    }

    public void setText(final int resId) {

        if (textActionBar != null) {
            textActionBar.setText(resId);
        }
        if (textActionBar != null) {
            textActionBar.setText(resId);
        }
    }
	
}
