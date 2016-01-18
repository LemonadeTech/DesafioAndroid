package br.com.rr.deslemtech.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.controller.ActionBar;
import br.com.rr.deslemtech.archi.controller.Menu;
import br.com.rr.deslemtech.archi.controller.activity.AbstractFragmentActivity;
import br.com.rr.deslemtech.archi.controller.listener.IFragmentListener;
import br.com.rr.deslemtech.archi.view.fragment.Frags;
import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.model.domain.enumeration.ActionBarStyle;
import br.com.rr.deslemtech.utils.DisplayUtil;
import br.com.rr.deslemtech.utils.ImageLoader;
import br.com.rr.deslemtech.utils.WrapperLog;
import br.com.rr.deslemtech.view.fragment.ConfigsFragment;
import br.com.rr.deslemtech.view.fragment.DetailsFragment;
import br.com.rr.deslemtech.view.fragment.OverviewFragment;


public class MainActivity extends AbstractFragmentActivity implements IFragmentListener {

	protected static final String SAVE_INST_USER_LOGGED = "SAVE_INST_USER_LOGGED";
	protected static final String SAVE_INST_LIST_AREA = "SAVE_INST_LIST_AREA";

    protected ActionBar actionBar;
    protected Menu menu;
	protected View loadingScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

		DisplayUtil.setLayoutParams((ViewGroup) findViewById(R.id.main_layout));

        LemTechBO.getInstance().imgLoader = new ImageLoader(this);

//        // load city data
//        LemTechBO.getInstance().loadCitiesData();
//
//        // verify city list
//        if(LemTechBO.getInstance().mapCities == null || LemTechBO.getInstance().mapCities.isEmpty()) {
//            try {
//                LemTechBO.getInstance().parseCityList(getAssets().open("city.list.json"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        // load cities selected
        LemTechBO.getInstance().loadSelectedCities();

        // TODO: load cities infos
        LemTechBO.getInstance().loadCitiesInfo();

        if (savedInstanceState == null) {
            showOverviewScreen();
        }
		else { // get saved instance data

		}

        initMainScreen();

    }

	@Override
	protected void onSaveInstanceState(Bundle outState) {

		// TODO: onSaveInstanceState

		super.onSaveInstanceState(outState);
	}

	public void initMainScreen() {

		// init actionbar and menu
        actionBar = new ActionBar(this);
        menu = new Menu(this, actionBar);

		// loading screen
		loadingScreen = findViewById(R.id.loading_screen);

    }


    @Override
    public void showOverviewScreen() {

        // to not enter in the same fragment
        if(Frags.OVERVIEW.getName().equals(lastFragmentName())) {
            return;
        }

        OverviewFragment fragment = (OverviewFragment) registerFragment(Frags.OVERVIEW);

		if (fragment != null) {
            replaceFragment(Frags.OVERVIEW, false);
        }
		else {
            WrapperLog.error("ERROR FRAGMENT" + Frags.OVERVIEW, getClass(), "showOverviewScreen");
        }
    }

    @Override
    public void showDetailsScreen() {

		DetailsFragment fragment = (DetailsFragment) registerFragment(Frags.DETAILS);

        if (fragment != null) {
            replaceFragment(Frags.DETAILS, true);
        }
		else {
            WrapperLog.error("ERROR FRAGMENT" + Frags.DETAILS, getClass(), "showDetailsScreen");
        }
    }

    @Override
    public void showConfigsScreen() {

        // to not enter in the same fragment
        if(Frags.CONFIGS.getName().equals(lastFragmentName())) {
            return;
        }

        ConfigsFragment fragment = (ConfigsFragment) registerFragment(Frags.CONFIGS);

        if (fragment != null) {
            replaceFragment(Frags.CONFIGS, true);
        }
		else {
            WrapperLog.error("ERROR FRAGMENT" + Frags.CONFIGS, getClass(), "showConfigsScreen");
        }

    }

	@Override
    public void setActionBarTitle(int stringResId) {
        if (actionBar != null) {
            actionBar.setText(stringResId);
        }
    }

    @Override
    public void setActionBarTitle(String title) {
        if (actionBar != null) {
            actionBar.setText(title);
        }
    }

    @Override
    public void setActionBarStyle(ActionBarStyle style) {
        if (actionBar != null) {
            actionBar.setStyle(style);
        }
    }

    @Override
    public void onClick(View view) {
    }

	@Override
	public void setLoadingVisibility(final boolean visible) {

		if(visible) {
			loadingScreen.setVisibility(View.VISIBLE);
		}
		else {
			loadingScreen.setVisibility(View.GONE);
		}

	}

}
