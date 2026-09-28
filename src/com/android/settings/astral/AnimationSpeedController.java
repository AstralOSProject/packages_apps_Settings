package com.android.settings.astral;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

public class AnimationSpeedController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {

    private static final float DEFAULT_SCALE = 1.0f;
    private static final String[] SCALE_KEYS = {
            Settings.Global.WINDOW_ANIMATION_SCALE,
            Settings.Global.TRANSITION_ANIMATION_SCALE,
            Settings.Global.ANIMATOR_DURATION_SCALE,
    };

    private ListPreference mListPreference;

    public AnimationSpeedController(Context context, String key) {
        super(context, key);
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    private float getCurrentScale() {
        return Settings.Global.getFloat(getContentResolver(),
                Settings.Global.TRANSITION_ANIMATION_SCALE, DEFAULT_SCALE);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mListPreference = screen.findPreference(getPreferenceKey());
        if (mListPreference != null) {
            mListPreference.setValue(Float.toString(getCurrentScale()));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final float scale;
        try {
            scale = Float.parseFloat(String.valueOf(newValue));
        } catch (NumberFormatException e) {
            return false;
        }
        if (scale < 0f) {
            return false;
        }
        for (String key : SCALE_KEYS) {
            Settings.Global.putFloat(getContentResolver(), key, scale);
        }
        return true;
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
