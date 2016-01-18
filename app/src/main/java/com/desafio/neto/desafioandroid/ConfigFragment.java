package com.desafio.neto.desafioandroid;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.desafio.neto.desafioandroid.models.TimeDay;
import com.desafio.neto.desafioandroid.openWater.OpenWaterMap;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.Normalizer;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@EFragment(R.layout.fragment_config)
public class ConfigFragment extends Fragment {

    String[] cities;

    @ViewById
    AutoCompleteTextView city;

    @AfterViews
    void init() {
        cities = getResources().getStringArray(R.array.city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, cities);
        city.setAdapter(adapter);
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    @Click(R.id.save)
    void saveCity() {
        boolean isValid = false;
        for(String c : cities) {
            if (c.equals(city.getText().toString()))
                isValid = true;
        }
        if (isValid) {
            DataCity.setCity(removerAcentos(city.getText().toString()));
            Toast.makeText(getContext(), R.string.sucesso, Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getContext(), R.string.erro, Toast.LENGTH_LONG).show();

        if(getResources().getBoolean(R.bool.dual_pane)) {
            Intent intent = new Intent(getContext(), MainActivity_.class);
            startActivity(intent);
        }
    }
}
