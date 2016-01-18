package br.com.rr.deslemtech.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.view.PagerTabStrip;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.text.Normalizer;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.model.domain.HttpResultDTO;

public class Utils {

	/**
	 * change the visibility of a view
	 * @param view the view to change visibility
	 * @return true if the view if visible now, false otherwise
	 */
	public static boolean changeVisibility(final View view) {

		boolean ret;

		if(view.getVisibility() == View.VISIBLE) {
			view.setVisibility(View.GONE);
			ret = false;
		}
		else {
			view.setVisibility(View.VISIBLE);
			ret = true;
		}

		return ret;
	}

	public static void showWsRequestError(Context context, HttpResultDTO resultDTO) {

		if(resultDTO.msg != null && !resultDTO.msg.isEmpty()) { // try get message error from server
			if(resultDTO.showToastError) {
				Toast.makeText(context, resultDTO.msg, Toast.LENGTH_SHORT).show();
			}
			WrapperLog.error(resultDTO.msg);
		}
		else { // if msg error from server do not come

			int msgResId = resultDTO.msgResId;

			if(msgResId == 0) { // if do not have a error at all, show the default one
				msgResId = R.string.server_error;
			}

			if(resultDTO.showToastError) {
				Toast.makeText(context, msgResId, Toast.LENGTH_SHORT).show();
			}
			WrapperLog.error(context.getString(msgResId));
		}

	}

    public static void setPageStripFontColorFace(final PagerTabStrip viewPagerTitle, final int color, final Typeface fontTypeFace) {

        for (int i = 0; i < viewPagerTitle.getChildCount(); ++i) {

            View nextChild = viewPagerTitle.getChildAt(i);

            if (nextChild instanceof TextView) {

                TextView textViewToConvert = (TextView) nextChild;

                textViewToConvert.setTextColor(color);

                if(fontTypeFace != null) {
                    textViewToConvert.setTypeface(fontTypeFace);
                }
            }
        }

    }

    public static String roundString(String number) {
        return String.valueOf(Math.round(Float.parseFloat(number)));
    }

    public static String removeAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
                int count=is.read(bytes, 0, buffer_size);
                if(count==-1)
                    break;
                os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }

}
