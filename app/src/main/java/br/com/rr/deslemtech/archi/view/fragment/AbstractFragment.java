package br.com.rr.deslemtech.archi.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import br.com.rr.deslemtech.archi.controller.activity.AbstractFragmentActivity;
import br.com.rr.deslemtech.archi.controller.listener.IBackPressListener;
import br.com.rr.deslemtech.archi.controller.listener.IFragmentListener;
import br.com.rr.deslemtech.archi.exception.MissingLayoutException;
import br.com.rr.deslemtech.utils.DisplayUtil;
import br.com.rr.deslemtech.utils.WrapperLog;


public abstract class AbstractFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    protected transient View rootView;

	protected AbstractFragmentActivity activityFragment;

    /* to disable button on press */
    private static View currentLock;
    protected transient Boolean buttonEnabled = true;


    public IFragmentListener fragmentListener;

	protected int layoutResId;
//    public Context getContext(){
//        return getActivity();
//    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);

		if(layoutResId != 0) {

			rootView = inflater.inflate(layoutResId, container, false);

			DisplayUtil.setLayoutParams((ViewGroup) rootView);
		}
		else {
			WrapperLog.debug("Layout resId is missing, make 'this.layoutResId = R.layout.your_frag_layout;' in fragment constructor.");
			throw new MissingLayoutException();
		}

		return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getActivity() instanceof IFragmentListener) {
            fragmentListener = (IFragmentListener) getActivity();
        }

		if(getActivity() instanceof AbstractFragmentActivity) {
			activityFragment = (AbstractFragmentActivity) getActivity();
		}

		restoreSavedInstance(savedInstanceState);

		// user init screen
		initView(savedInstanceState);
    }

	protected void restoreSavedInstance(Bundle savedInstanceState) {}
	abstract protected void initView(Bundle savedInstanceState);

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

		setRetainInstance(true);

    }

	public Context getContext() {
		return activityFragment;
	}

	public AbstractFragmentActivity getFragmentActivity() {
		return activityFragment;
	}

    public void registerBackPress(final IBackPressListener backPressListener) {
        if(getActivity() instanceof AbstractFragmentActivity) {
            ((AbstractFragmentActivity) getActivity()).registerBackPress(backPressListener);
        }
    }

    public IBackPressListener getBackPressListener() {
        if(getActivity() instanceof AbstractFragmentActivity) {
            return ((AbstractFragmentActivity) getActivity()).getBackPressListener();
        }

        return null;
    }

    public View findViewById(int resId) {

        View objRet = null;

        if(rootView != null) {
            objRet = rootView.findViewById(resId);
        }

        return objRet;
    }

	protected void activateButtonsDelayed() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                buttonEnabled = true;
            }
        }, 600);
    }

    @Override
    public boolean onTouch(final View view, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (currentLock != null && currentLock.getId() != view.getId()) {
                return true;
            } else {
                setCurrentLock(view);
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            if (currentLock != null && currentLock.getId() == view.getId()) {
                setCurrentLock(null);

                if (buttonEnabled) {

                    buttonEnabled = false;
                    activateButtonsDelayed();

                    this.onClick(view);

                }

            }

        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            setCurrentLock(null);
            activateButtonsDelayed();
        }

        return false;
    }

    public static void setCurrentLock(final View currentLock) {
        AbstractFragment.currentLock = currentLock;
    }

    @Override
    public void onClick(final View view) {
        // must be overridden in child classes
    }
}