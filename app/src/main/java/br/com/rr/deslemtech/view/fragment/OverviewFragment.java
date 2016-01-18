package br.com.rr.deslemtech.view.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.view.fragment.AbstractFragment;
import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.model.domain.enumeration.ActionBarStyle;
import br.com.rr.deslemtech.utils.DisplayUtil;
import br.com.rr.deslemtech.utils.Fonts;
import br.com.rr.deslemtech.utils.Utils;
import br.com.rr.deslemtech.view.fragment.adapter.OverviewPageAdapter;

public class OverviewFragment extends AbstractFragment {

    protected OverviewPageAdapter overviewPageAdapter;

	public OverviewFragment() {
		layoutResId = R.layout.overview;
	}

	@Override
    protected void initView(Bundle savedInstanceState) {

        fragmentListener.setActionBarStyle(ActionBarStyle.MENU);
		fragmentListener.setActionBarTitle(R.string.menu_overview);

        View noCitySelectedLayout = findViewById(R.id.overview_no_city_selected_layout);

        if(LemTechBO.getInstance().selectedCities.isEmpty()) {

            TextView noCitySelectedText = (TextView) findViewById(R.id.overview_no_city_selected_text);

            // fonts
            noCitySelectedText.setTypeface(Fonts.robotoBold);

            noCitySelectedLayout.setVisibility(View.VISIBLE);

//            fragmentListener.showConfigsScreen();
        }
        else {

            noCitySelectedLayout.setVisibility(View.GONE);

            ViewPager viewPager = (ViewPager) findViewById(R.id.overview_pager);
            PagerTabStrip viewPagerTitle = (PagerTabStrip) findViewById(R.id.overview_title_view_pager);

            if(overviewPageAdapter == null) { // this is used to not destroy the adapter when user go to another fragment
                overviewPageAdapter = new OverviewPageAdapter(getContext(), LemTechBO.getInstance().selectedCities);
            }
            viewPager.setAdapter(overviewPageAdapter);
            viewPager.setOffscreenPageLimit(5);
            viewPagerTitle.setTabIndicatorColorResource(R.color.overview_page_title_indicator_color);
            viewPagerTitle.setTextColor(getResources().getColor(R.color.overview_page_title_textcolor));

			if(LemTechBO.getInstance().isTablet()) {
				viewPagerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.calcByHeight(24));
			}
			else {
				viewPagerTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, DisplayUtil.calcByHeight(28));
			}

            // try set font color and face
            Utils.setPageStripFontColorFace(viewPagerTitle, getResources().getColor(R.color.overview_page_title_textcolor), Fonts.robotoBold);

        }

    }

}
