package org.thecoreme.rodrigo.desafioandroid;

import java.util.List;

import com.survivingwithandroid.weather.lib.model.City;

import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;

public class CityAdapter extends ArrayAdapter<City>{
	private Context m_context;
	private List<City> m_cityList;
	
	public CityAdapter(Context context, List<City> cityList) {
		super(context, R.layout.row_city_layout, cityList);
		m_cityList = cityList;
		m_context = context;
	}
	
	@Override
	public int getCount() {
		return m_cityList.size();
	}

	@Override
	public City getItem(int position) {
		return m_cityList.get(position);
	}


	@Override
	public long getItemId(int position) {
		City city = m_cityList.get(position);

		return city.getId().hashCode();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater =
					(LayoutInflater) m_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.row_city_layout, parent, false);
		}
		
		City city = m_cityList.get(position);
		TextView cityText = (TextView) convertView.findViewById(R.id.cityName);

		cityText.setText(city.getName() + "," + city.getCountry());
		
		return convertView;
	}

    public void setCityList(List<City> cityList) {
        m_cityList = cityList;
    }
}
