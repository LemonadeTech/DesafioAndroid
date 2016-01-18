package br.com.rr.deslemtech.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.controller.listener.DefaultSimpleHttpResultListener;
import br.com.rr.deslemtech.model.ForecastComparator;
import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.model.domain.City;
import br.com.rr.deslemtech.model.domain.Forecast;
import br.com.rr.deslemtech.model.domain.HttpResultDTO;
import br.com.rr.deslemtech.model.domain.Weather;
import br.com.rr.deslemtech.utils.DisplayUtil;
import br.com.rr.deslemtech.utils.Fonts;
import br.com.rr.deslemtech.utils.Utils;
import br.com.rr.deslemtech.utils.WrapperLog;

public class OverviewItemView implements View.OnClickListener {

    protected View viewRoot;
    protected Context context;
    protected String cityName;

    protected LayoutInflater inflater;

    protected View loading;
    protected View previewUnavailableLayout;
    protected View unpreviewed;

    public OverviewItemView(View viewRoot, Context context, String cityName) {

        this.viewRoot = viewRoot;
        this.context = context;
        this.cityName = cityName;

        inflater = LayoutInflater.from(context);

        initView();
    }

    protected void initView() {

        loading = viewRoot.findViewById(R.id.loading_layout);
        previewUnavailableLayout = viewRoot.findViewById(R.id.preview_unavailable_layout);
        TextView unavailableText = (TextView) viewRoot.findViewById(R.id.preview_unavailable_text);

        unpreviewed = viewRoot.findViewById(R.id.overview_item_unpreviewed_layout);
        TextView unpreviewedText = (TextView) viewRoot.findViewById(R.id.overview_item_unpreviewed_text);

        // fonts
        unavailableText.setTypeface(Fonts.robotoBold);
        unpreviewedText.setTypeface(Fonts.robotoBold);

        // city infos
        City cityInfos = LemTechBO.getInstance().cityInfos.get(cityName.toLowerCase());

        if(cityInfos == null) {

            loading.setVisibility(View.VISIBLE);

            LemTechBO.getInstance().forecast16Request(cityName, new Forecast16HttpResultListener(context));

        }
        else {

            setInfos(null);

        }

    }

    protected void setInfos(final Forecast inForecast) {

        WrapperLog.error("setInfos::cityName: " + cityName );

        // get the city infos from bo
        City cityBO = LemTechBO.getInstance().cityInfos.get(cityName.toLowerCase());

        if(cityBO == null || cityBO.mapForecast == null || cityBO.mapForecast.isEmpty()) {
            previewUnavailableLayout.setVisibility(View.VISIBLE);
        }
        else {

            // get list forecast preview
            List<Forecast> listForecast = new ArrayList<>(cityBO.mapForecast.values());

            // sort forecast list
            Collections.sort(listForecast, new ForecastComparator());

            // set forescast to show
            Forecast forecast = inForecast;
            if(forecast == null) {
                // get first forecast
                forecast = listForecast.get(0);
            }

            // simple date format current info
            SimpleDateFormat dateFormatCurr = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Calendar calTemp = Calendar.getInstance();

            TextView forecastDate = (TextView) viewRoot.findViewById(R.id.overview_item_forecast_date);
            TextView currentTemperature = (TextView) viewRoot.findViewById(R.id.overview_item_current_temperature);
            TextView maxMinTemperature = (TextView) viewRoot.findViewById(R.id.overview_item_max_min_temperature);
            TextView weatherMain = (TextView) viewRoot.findViewById(R.id.overview_item_weather_main);
            TextView weatherDesc = (TextView) viewRoot.findViewById(R.id.overview_item_weather_desc);
            TextView windLabel = (TextView) viewRoot.findViewById(R.id.overview_item_wind_label);
            TextView wind = (TextView) viewRoot.findViewById(R.id.overview_item_wind);
            TextView humidityLabel = (TextView) viewRoot.findViewById(R.id.overview_item_humidity_label);
            TextView humidity = (TextView) viewRoot.findViewById(R.id.overview_item_humidity);
            TextView cloudsLabel = (TextView) viewRoot.findViewById(R.id.overview_item_clouds_label);
            TextView clouds = (TextView) viewRoot.findViewById(R.id.overview_item_clouds);

            // TODO image
            ImageView weatherImage = (ImageView) viewRoot.findViewById(R.id.overview_item_weather_image);

            // fonts
            forecastDate.setTypeface(Fonts.robotoMedium);
            currentTemperature.setTypeface(Fonts.robotoBold);
            maxMinTemperature.setTypeface(Fonts.robotoMedium);
            weatherMain.setTypeface(Fonts.robotoMedium);
            weatherDesc.setTypeface(Fonts.robotoRegular);
            windLabel.setTypeface(Fonts.robotoRegular);
            wind.setTypeface(Fonts.robotoRegular);
            humidityLabel.setTypeface(Fonts.robotoRegular);
            humidity.setTypeface(Fonts.robotoRegular);
            cloudsLabel.setTypeface(Fonts.robotoRegular);
            clouds.setTypeface(Fonts.robotoRegular);

            // texts
            calTemp.setTimeInMillis(Long.parseLong(forecast.date) * 1000);
            forecastDate.setText(dateFormatCurr.format(calTemp.getTime()));
            currentTemperature.setText(Utils.roundString(forecast.temperature.day) + "°");
            maxMinTemperature.setText(Utils.roundString(forecast.temperature.max) + "° / " + Utils.roundString(forecast.temperature.min) + "° C");
            wind.setText(forecast.windSpeed + " m/s - " + forecast.windDegrees + "°");
            humidity.setText(forecast.humidity + "%");
            clouds.setText(forecast.clouds + "%");

            // put weather data
            if(forecast.listWeather != null && !forecast.listWeather.isEmpty()) {

                Weather weather;

                StringBuilder main = new StringBuilder();
                StringBuilder desc = new StringBuilder();

                String imageName = "";

                int size = forecast.listWeather.size();
                for (int count = 0; count < size; count++) {

                    weather = forecast.listWeather.get(count);

                    if(count > 0) {
                        main.append(", ");
                        desc.append(", ");
                    }
                    else {
                        imageName = weather.icon;
                    }

                    main.append(weather.main);
                    desc.append(weather.desc);

                }

                weatherMain.setText(main);
                weatherDesc.setText(desc);

                // load image
                LemTechBO.getInstance().loadImage(imageName, weatherImage);

            }
            else {
                weatherImage.setVisibility(View.INVISIBLE);
            }


            generateWeatherPreviews(listForecast);

        }

    }

    protected void generateWeatherPreviews(List<Forecast> listForecast) {

        ViewGroup parentPreview = (ViewGroup) viewRoot.findViewById(R.id.overview_item_linear_parent_preview);

        // clean layout parent
        parentPreview.removeAllViews();

        int size = listForecast.size();
        if(size > 1) {

            // gone text of no preview
            unpreviewed.setVisibility(View.GONE);

            // simple date format preview day
            SimpleDateFormat dateFormatDay = new SimpleDateFormat("dd");
            Calendar calTemp = Calendar.getInstance();

            Forecast forecast;
            View view;
            for(int count = 0; count < size; count++) {

                forecast = listForecast.get(count);

                view = inflater.inflate(R.layout.overview_item_preview, parentPreview, false);
                DisplayUtil.setLayoutParams((ViewGroup) view);

                TextView previewDate = (TextView) view.findViewById(R.id.overview_item_preview_date);
                TextView previewTemperature = (TextView) view.findViewById(R.id.overview_item_preview_temperature);
                TextView previewMaxMin = (TextView) view.findViewById(R.id.overview_item_max_min_temperature);
                ImageView previewImage = (ImageView) view.findViewById(R.id.overview_item_preview_weather_image);

                // fonts
                previewDate.setTypeface(Fonts.robotoRegular);
                previewTemperature.setTypeface(Fonts.robotoBold);
                previewMaxMin.setTypeface(Fonts.robotoRegular);

                // texts
                calTemp.setTimeInMillis(Long.parseLong(forecast.date) * 1000);
                previewDate.setText(dateFormatDay.format(calTemp.getTime()));


                if(forecast.listWeather != null && !forecast.listWeather.isEmpty()) {

                    // get first weather info
                    Weather weather = forecast.listWeather.get(0);

                    // load image
                    LemTechBO.getInstance().loadImage(weather.icon, previewImage);
                }


                // TODO convertion and kmdsdfsf
                previewTemperature.setText(Utils.roundString(forecast.temperature.day) + "°");
                previewMaxMin.setText(Utils.roundString(forecast.temperature.max) + "° / " + Utils.roundString(forecast.temperature.min) + "°");

                // touch
                view.setTag(forecast);
                view.setOnClickListener(this);

                parentPreview.addView(view);
            }

        }
        else {
            unpreviewed.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.overview_item_preview:
                setInfos((Forecast) view.getTag());
                break;

            default:
                break;
        }

    }

    protected class Forecast16HttpResultListener extends DefaultSimpleHttpResultListener<City> {

        public Forecast16HttpResultListener(Context context) {
            super(context);
        }

        @Override
        protected void success(City data) {

            City cityBO = LemTechBO.getInstance().cityInfos.get(Utils.removeAccents(data.name.toLowerCase()));

            if(cityBO == null) {
                LemTechBO.getInstance().cityInfos.put(Utils.removeAccents(data.name.toLowerCase()), data);
            }
            else {
                cityBO.mapForecast.putAll(data.mapForecast);
            }

            // save city info
            LemTechBO.getInstance().saveCityInfos();

            WrapperLog.error("cityLoadedInfos: "+data.name);
            setInfos(null);

            // set loading
            loading.setVisibility(View.GONE);

        }

        @Override
        protected void failed(HttpResultDTO<City> resultDTO) {

            // set loading
            loading.setVisibility(View.GONE);
        }
    };

}
