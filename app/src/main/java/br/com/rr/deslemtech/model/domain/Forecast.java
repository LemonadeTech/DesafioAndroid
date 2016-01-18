package br.com.rr.deslemtech.model.domain;

import java.io.Serializable;

public class Forecast implements Serializable {

	private static final long serialVersionUID = 8286800894539431408L;

	public String date;
	public Temperature temperature;
	public ListWeather listWeather;
	public String humidity;
	public String windSpeed;
	public String windDegrees;
	public String clouds;

}
