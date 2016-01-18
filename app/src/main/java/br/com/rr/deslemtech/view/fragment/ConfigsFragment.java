package br.com.rr.deslemtech.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.view.fragment.AbstractFragment;
import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.model.domain.enumeration.ActionBarStyle;
import br.com.rr.deslemtech.utils.Fonts;
import br.com.rr.deslemtech.utils.Utils;

public class ConfigsFragment extends AbstractFragment {

	protected EditText edittextCity;
    protected ArrayAdapter<String> arrayAdapter;

    protected InputMethodManager inputMethodManager;

	public ConfigsFragment() {
		layoutResId = R.layout.configs;
	}

    @Override
    public void onDestroy() {

        closeKeyBoard();

        super.onDestroy();
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        fragmentListener.setActionBarStyle(ActionBarStyle.MENU);
        fragmentListener.setActionBarTitle(R.string.menu_configs);

        TextView cityLabel = (TextView) findViewById(R.id.configs_city_label);
        edittextCity = (EditText) findViewById(R.id.configs_city);
        Button button = (Button) findViewById(R.id.configs_button);
        TextView citiesAddedLabel = (TextView) findViewById(R.id.configs_cities_added_label);
        ListView listView = (ListView) findViewById(R.id.configs_list_selected_cities);

        // fonts
        cityLabel.setTypeface(Fonts.robotoMedium);
        edittextCity.setTypeface(Fonts.robotoRegular);
        button.setTypeface(Fonts.robotoBold);
        citiesAddedLabel.setTypeface(Fonts.robotoMedium);

        // touches
        button.setOnClickListener(this);

        // get added cities
        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, LemTechBO.getInstance().selectedCities);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemLongClickListener(new RemoveItemLongClickListener());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
			case R.id.configs_button:
				addCity();
				break;

			default:
				break;
		}
    }

    protected void addCity() {

        String cityName = edittextCity.getText().toString().trim().toLowerCase();

        if(!cityName.isEmpty()) {

            // removing accents
            cityName = Utils.removeAccents(cityName);

            // add city
            LemTechBO.getInstance().selectedCities.add(cityName);

            // clean edittext
            edittextCity.setText("");

            // notify list
            arrayAdapter.notifyDataSetChanged();

            // save
            LemTechBO.getInstance().saveSelectedCities();
        }

    }

    private void closeKeyBoard() {

        if(inputMethodManager == null) {
            inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        if (inputMethodManager != null && activityFragment.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(activityFragment.getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected class RemoveItemLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            // remove city
            LemTechBO.getInstance().selectedCities.remove(position);

            // notify list
            arrayAdapter.notifyDataSetChanged();

            // save
            LemTechBO.getInstance().saveSelectedCities();

            return true;
        }
    }


}
