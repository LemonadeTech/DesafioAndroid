package br.com.rr.deslemtech.archi.controller;

import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.TextView;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.controller.listener.IFragmentListener;
import br.com.rr.deslemtech.model.domain.enumeration.MenuItems;
import br.com.rr.deslemtech.utils.Fonts;

public class Menu implements View.OnClickListener {

    protected IFragmentListener activityListener;
    protected ActionBar actionBar;
    protected MenuItems menuAction;

    protected DrawerLayout drawerLayout;
    protected View drawerMenu;

	protected View menuOverview;
	protected View menuConfigs;


    public Menu(IFragmentListener activityListener, ActionBar actionBar) {

        this.activityListener = activityListener;
        this.actionBar = actionBar;

        actionBar.setDrawerMenu(this);

        initDrawerMenu();

    }

    protected View findViewById(final int resId) {

        View retView = null;

        if (activityListener != null) {
            retView = activityListener.findViewById(resId);
        }

        return retView;
    }

    public void initDrawerMenu() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerMenu = findViewById(R.id.left_drawer);

        menuOverview = findViewById(R.id.menu_item_overview);
        menuConfigs = findViewById(R.id.menu_item_configs);

		TextView textViewItemOverview = (TextView) findViewById(R.id.menu_textview_item_overview);
		TextView textViewItemConfigs = (TextView) findViewById(R.id.menu_textview_item_configs);

		// set typeface
		textViewItemOverview.setTypeface(Fonts.robotoMedium);
		textViewItemConfigs.setTypeface(Fonts.robotoMedium);

		// set touch
		menuOverview.setOnClickListener(this);
        menuConfigs.setOnClickListener(this);

		// set drawer listener
        drawerLayout.setDrawerListener(new MyDrawerListener());
    }

    @Override
    public void onClick(View view) {

		int id = view.getId();

		switch(id) {
			case R.id.menu_item_overview:
				menuAction = MenuItems.OVERVIEW;
				break;

			case R.id.menu_item_configs:
				menuAction = MenuItems.CONFIGS;
				break;

			default:
				break;
		}

		drawerLayout.closeDrawers();

    }

	private class MyDrawerListener implements DrawerLayout.DrawerListener {

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(View drawerView) {
        }

        @Override
        public void onDrawerClosed(View drawerView) {

            if (menuAction != null) {

                switch (menuAction) {
					case OVERVIEW:
//                        activityListener.showOverviewScreen();
                        activityListener.popBackStack();
						break;

					case CONFIGS:
                        activityListener.showConfigsScreen();
						break;

                    default:
                        break;
                }
            }

            menuAction = null;
        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    }


}