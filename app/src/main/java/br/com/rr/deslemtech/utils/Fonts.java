package br.com.rr.deslemtech.utils;

import android.content.Context;
import android.graphics.Typeface;

public final class Fonts {

    private Fonts() {
        // do nothing
    }

	public static Typeface robotoLight;
	public static Typeface robotoBold;
	public static Typeface robotoMedium;
	public static Typeface robotoRegular;

    /**
     * Method to initiate system's font
     *
     * @param context - Application context
     */
    public static void initFonts(final Context context) {
        robotoLight = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
		robotoBold = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Bold.ttf");
		robotoMedium = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
		robotoRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf");
    }

}
