package br.com.rr.deslemtech.model;

import android.content.Context;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.controller.listener.ISimpleHttpResultListener;
import br.com.rr.deslemtech.model.domain.CitiesInfo;
import br.com.rr.deslemtech.model.domain.City;
import br.com.rr.deslemtech.model.domain.SelectedCities;
import br.com.rr.deslemtech.model.http.Forecast16AsyncHttpRespHandler;
import br.com.rr.deslemtech.model.persistence.SerializationDAO;
import br.com.rr.deslemtech.utils.ImageLoader;
import br.com.rr.deslemtech.utils.WrapperLog;


public class LemTechBO {

    private static LemTechBO instance;

	public static Context context;

	// time out http conection
	private final static int CONNECTION_TIMEOUT = 10000;

    // forecast map key date format
    public final static String FORECAST_MAP_KEY_DATE_FORMAT = "yyyyMMdd";

    // cities selected to show weather
    public SelectedCities selectedCities;
    // cities informations
    public CitiesInfo cityInfos;

    // forecast map key simple date format
    public SimpleDateFormat forecastKeyDateFormat;

    // ImageLoader class instance
    public ImageLoader imgLoader;

	// Device which game is running
	private DeviceType deviceType = DeviceType.PHONE;

	/**
	 * Enum for device types.
	 */
	public enum DeviceType {
		TABLET,
		PHONE
	}

	private LemTechBO() {

        selectedCities = new SelectedCities();
        cityInfos = new CitiesInfo();

        forecastKeyDateFormat = new SimpleDateFormat(FORECAST_MAP_KEY_DATE_FORMAT);

    }

    public static LemTechBO getInstance() {

		if (instance == null) {
			instance = new LemTechBO();
		}

		return instance;
    }

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(final DeviceType type) {
		deviceType = type;
	}

	public boolean isTablet() {
		return(deviceType == DeviceType.TABLET);
	}

    public void saveCityInfos() {
        persistData(cityInfos);
    }
    public void loadCitiesInfo() {
        cityInfos = (CitiesInfo) loadData(CitiesInfo.class);

        if(cityInfos == null) {
            cityInfos = new CitiesInfo();
        }
    }

    public void saveSelectedCities() {
        persistData(selectedCities);
    }
    public void loadSelectedCities() {
        selectedCities = (SelectedCities) loadData(SelectedCities.class);

        if(selectedCities == null) {
            selectedCities = new SelectedCities();
        }
    }

	public boolean persistData(final Serializable data) {
		return persistData(data, "");
	}
	public boolean persistData(final Serializable data, final String filenameAppend) {

		boolean ret = false;

		try {
			SerializationDAO.persistData(data, filenameAppend);

			ret = true;
		} catch (IOException e) {
			// TODO: make a better error call, to inform the user about failed opeartion
			WrapperLog.error("Cannot persist data.");
		}

		return ret;
	}

	public Serializable loadData(final Class<? extends Serializable> clazz) {
		return this.loadData(clazz, "");
	}
	public Serializable loadData(final Class<? extends Serializable> clazz, final String filenameAppend) {

		Serializable ret = null;
		Serializable dataIn = null;

		try {

			dataIn = clazz.newInstance();

			ret = (Serializable) SerializationDAO.loadData(dataIn, filenameAppend);

		} catch (OptionalDataException e) {
			WrapperLog.error("Cannot load data, OptionalDataException.");
		} catch (ClassNotFoundException e) {
			WrapperLog.error("Cannot load data, ClassNotFoundException.");
		} catch (IOException e) {
			WrapperLog.error("Cannot load data, IOException.");
		} catch (InstantiationException e) {
			WrapperLog.error("Cannot load data, InstantiationException.");
		} catch (IllegalAccessException e) {
			WrapperLog.error("Cannot load data, IllegalAccessException.");
		}

		return ret;
	}

	protected AsyncHttpClient httpClient;
	protected AsyncHttpClient getConnClient() {

		if(httpClient == null) {
			httpClient = new AsyncHttpClient();
			httpClient.setTimeout(CONNECTION_TIMEOUT);
			httpClient.setMaxRetriesAndTimeout(1, CONNECTION_TIMEOUT);
		}

		return httpClient;
	}

    public void forecast16Request(final String cityName, final ISimpleHttpResultListener<City> listener) {

        // response handler
        final AsyncHttpResponseHandler respHandler = new Forecast16AsyncHttpRespHandler(listener);

        // put params
        StringBuilder params = new StringBuilder();
        params.append("q=");

        try {
            params.append(URLEncoder.encode(cityName, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        params.append("&appid=");
        params.append(context.getString(R.string.apikey));
        params.append("&units=metric");

        WrapperLog.debug(params.toString());

        // make connection
        String address = context.getString(R.string.ws_forecast_daily_addr);
        String url = address + params.toString();
        getConnClient().get(url, respHandler);
        WrapperLog.error("URL: "+url);

    }

    public void loadImage(String imageName, ImageView weatherImage) {

        String imageUrl = context.getString(R.string.ws_image_addr) + imageName + ".png";

        LemTechBO.getInstance().imgLoader.DisplayImage(imageUrl, R.drawable.placeholder, weatherImage);

    }

//    public void parseCityList(InputStream isCities) {
//
//        City city;
//        mapCities = new CitiesData();
//
//        BufferedReader bufReader = null;
//        try {
//            bufReader = new BufferedReader(new InputStreamReader(isCities));
//
//            String line;
//            while ((line = bufReader.readLine()) != null) {
//
//                // parse json
//                try {
//                    JSONObject jsonObjRoot = new JSONObject(line);
//
//                    city = new City();
//
//                    city.id = jsonObjRoot.getString("_id");
//                    city.name = jsonObjRoot.getString("name");
//                    city.country = jsonObjRoot.getString("country");
//
//                    putCityMap(city);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            // save object
//            persistData(mapCities);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (bufReader != null) {
//                try {
//                    bufReader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }
//
//    private void putCityMap(City city) {
//
//        List<City> listCity = mapCities.get(city.country);
//
//        if(listCity == null) {
//            listCity = new ArrayList<>();
//
//            mapCities.put(city.country, listCity);
//        }
//
//        listCity.add(city);
//    }

}
