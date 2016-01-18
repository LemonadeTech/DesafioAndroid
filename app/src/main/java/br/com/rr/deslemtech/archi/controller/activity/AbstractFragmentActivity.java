package br.com.rr.deslemtech.archi.controller.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;
import java.util.Map;

import br.com.rr.deslemtech.R;
import br.com.rr.deslemtech.archi.controller.listener.IBackPressListener;
import br.com.rr.deslemtech.archi.view.fragment.AbstractFragment;
import br.com.rr.deslemtech.archi.view.fragment.Frags;
import br.com.rr.deslemtech.model.LemTechBO;
import br.com.rr.deslemtech.utils.DisplayUtil;
import br.com.rr.deslemtech.utils.Fonts;
import br.com.rr.deslemtech.utils.WrapperLog;


public abstract class AbstractFragmentActivity extends FragmentActivity implements View.OnClickListener {

    public FragmentManager fragMan = null;

    private Map<String, AbstractFragment> mapFrags = new HashMap<String, AbstractFragment>();

    protected IBackPressListener backPressListener;

    private boolean onPause;

    private Fragment frag;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

		// Check if the device is a Tablet or a Smartphone
		if ((getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
			LemTechBO.getInstance().setDeviceType(LemTechBO.DeviceType.TABLET);
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
		}
		else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
			LemTechBO.getInstance().setDeviceType(LemTechBO.DeviceType.PHONE);
		}

		DisplayUtil.init(this, LemTechBO.getInstance().isTablet());
        Fonts.initFonts(this);
		LemTechBO.context = this;

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        fragMan = getSupportFragmentManager();

		// return saved instance fragmentssssss to mapfragsss
		if(savedInstance != null) {

//			savedInst = savedInstance.getBoolean(KEY_SAVED_INSTANCE, false);

			// register fragments
			if(fragMan != null && fragMan.getFragments() != null) {
				int size = fragMan.getFragments().size();
				//WrapperLog.error("savedInstance::size:"+fragMan.getFragments().size());
				Frags frag;
				for(int count = 0; count < size; count++) {
//					WrapperLog.error("getName():"+fragMan.getFragments().get(count).getTag());

//					if(fragMan.getFragments().get(count) != null && (frag = Frags.getFragByName(fragMan.getFragments().get(count).getTag())) != null) {
//						registerFragment(frag);
//					}

					if(fragMan.getFragments().get(count) != null) {

						String fragTag = fragMan.getFragments().get(count).getTag();

						try {
//							WrapperLog.error("fragTag: "+fragTag);
							frag = Frags.valueOf(fragTag.toUpperCase());

//							WrapperLog.error("frag: "+frag);
							registerFragment(frag);

						} catch(IllegalArgumentException e) { // if fragment tag is different from anything in Frags, log

							WrapperLog.error("Error registering fragment: " + fragTag);

						}

					}

				}
			}
		}
    }

    public void registerBackPress(final IBackPressListener iBackPressListener) {
        this.backPressListener = iBackPressListener;
    }

    public IBackPressListener getBackPressListener() {
        return backPressListener;
    }

    public AbstractFragment registerFragment(final Frags frag) {
        return registerFragment(frag, null);
    }

    public AbstractFragment registerFragment(final Frags frag, final String tagFragMan) {

        String tag = tagFragMan;
        if (tag == null) {
            tag = frag.getName();
        }

        final Fragment fragment = fragMan.findFragmentByTag(tag);

        AbstractFragment abstractFragment = null;

        if (fragment == null) {
            try {
                abstractFragment = frag.getClassFrag().newInstance();
            } catch (InstantiationException e) {
                WrapperLog.error("InstantiationException " + e.getMessage(), getClass(), "registerFragment");
            } catch (IllegalAccessException e) {
                WrapperLog.error("IllegalAccessException " + e, getClass(), "registerFragment");
            }
        } else {
            if (fragment instanceof AbstractFragment) {
                abstractFragment = (AbstractFragment) fragment;
            }
        }

        if (abstractFragment != null) {
            this.frag = abstractFragment;
            mapFrags.put(frag.getName(), abstractFragment);
        }

        return abstractFragment;
    }



    public void removeFragment(final Frags frag) {
        Fragment fragment = getFragment(frag);
        if(frag != null) {
            removeFragment(fragment);
        }
    }

    protected void removeFragment(Fragment fragment) {

        FragmentTransaction fragTransa = fragMan.beginTransaction();

        fragTransa.remove(fragment);

        fragTransa.commitAllowingStateLoss();
    }

    public boolean replaceFragment(final Frags frag, final int resId) {
        return replaceFragment(frag, resId, true);
    }

    public void replaceFragment(final Frags frag, final boolean addBackStack) {
      replaceFragment(frag, R.id.content_frame, addBackStack);
    }

    public boolean replaceFragment(final Frags frag, final int resId, final boolean addBackStack) {
        return replaceFragment(frag, resId, addBackStack, null, null);
    }

    public boolean replaceFragment(final Frags frag, final int resId, final boolean addBackStack, final Integer animResIdEnter, final Integer animResIdExit) {

        boolean ret = false;

        Fragment fragment = mapFrags.get(frag.getName());

        if (fragment != null) {

            FragmentTransaction fragTransa = fragMan.beginTransaction();

            // setting animation
            if (animResIdEnter != null && animResIdExit != null) {
                fragTransa.setCustomAnimations(animResIdEnter, animResIdExit);
            }
			else {
				fragTransa.setCustomAnimations(R.anim.frag_in, R.anim.frag_stay, R.anim.frag_stay, R.anim.frag_out);
			}

            fragTransa.replace(resId, fragment, frag.getName());


            // adding backstack
            if (addBackStack) {
                fragTransa.addToBackStack(frag.getName());
            }

            fragTransa.commitAllowingStateLoss();

            ret = true;
        }

        return ret;
    }

    public synchronized AbstractFragment getFragment(final Frags frag) {

        if (!mapFrags.containsKey(frag.getName())) {
            return null;
        }

        return mapFrags.get(frag.getName());
    }

    @Override
    public void onBackPressed() {

        if (backPressListener != null) {
            backPressListener.onBackPress();
        }
		else {
//            ApplicationLifeCycle.deleteLifeCycle(this);//?
            super.onBackPressed();
        }
    }

    public AbstractFragment getFrag() {

        AbstractFragment fragment = null;

        // REMOVED BY PREVENT (BAD CASTS OF OBJECT REFERENCES)
        // IN CASE OF NEW FRAGMENT VERSIONS IT WILL BECOME NECESSARY THE USE OF INSTANCE OF VALIDATION
        fragment = (AbstractFragment) frag;
        return fragment;
    }

    public void popBackStack() {
        fragMan.popBackStackImmediate();
    }

    public void popBackStack(Frags frags) {
        fragMan.popBackStackImmediate(frags.getName(), 0);
    }

	public void popAllBackStack() {
		try {
			fragMan.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		} catch(IllegalStateException e) {
			WrapperLog.error(e.getLocalizedMessage(), e);
		}
	}

    /**
     * Set argument in a fragment
     *
     * @param frag - frag to set arguments
     * @param args - arguments bundle
     * @return Return true if set arguments, false otherwise
     */
    public boolean putArguments(final Frags frag, final Bundle args) {
        boolean ret = false;
        Fragment fragment = this.frag;

        if (fragment != null && args != null) {
            fragment.setArguments(args);
            ret = true;
        }

        return ret;
    }

    public boolean findFragment(Frags frag) {
        if (fragMan.findFragmentByTag(frag.getName()) != null) {
            return true;

        }
        return false;
    }


    public String lastFragmentName() {
        if (fragMan.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry entry = fragMan.getBackStackEntryAt(fragMan.getBackStackEntryCount() - 1);
            return entry.getName();
        }
        return null;
    }

    public AbstractFragment lastFragmentFrags() {
        String nameFrag = lastFragmentName();
        if (nameFrag != null) {
            return mapFrags.get(nameFrag);
        }
        return null;
    }

	public Context getContext() {
		return this;
	}

}
