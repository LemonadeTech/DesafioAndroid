package br.com.rr.deslemtech.view.fragment.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.model.domain.City;
import br.com.rr.deslemtech.utils.DisplayUtil;
import br.com.rr.deslemtech.view.OverviewItemView;

public class OverviewPageAdapter extends PagerAdapter {

    protected List<String> list;

	protected Context context;
	protected LayoutInflater inflater;

    public OverviewPageAdapter(Context context, List<String> list) {

		this.context = context;

		inflater = LayoutInflater.from(context);

        this.list = list;

    }

    @Override
	public Object instantiateItem(ViewGroup container, int position) {

		View view = inflater.inflate(R.layout.overview_page_item, container, false);

		DisplayUtil.setLayoutParams((ViewGroup) view);

        // class to control overview item
        new OverviewItemView(view, context, list.get(position));

		// remove parent if it has anyone, it is use when user back from other fragment
		if(view.getParent() != null) {
			((ViewGroup) view.getParent()).removeView(view);
		}

		((ViewGroup) container).addView(view);

        return view;
    }

	@Override
	public CharSequence getPageTitle(int position) {

        String ret = "";

        if(list != null) {
            ret = list.get(position);

//            String country = list.get(position).country;
//            if(!country.isEmpty()) {
//                ret += ", " + country;
//            }
        }

		return ret;
	}

    @Override
    public int getCount() {
        return list.size();
    }

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
