package com.android.settings.astral;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Color;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

import org.json.JSONException;
import org.json.JSONObject;

public class LockscreenClockColorController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {

    private static final String KEY_SEED_COLOR = "seedColor";

    private ListPreference mListPreference;

    public LockscreenClockColorController(Context context, String key) {
        super(context, key);
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    /**
     * Reads the seedColor from the existing clock face settings JSON, without
     * touching any other fields (clockId, axes, metadata), so an active custom
     * clock style is never reset by picking a color.
     */
    private String readSeedColorHex() {
        final String json = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCK_SCREEN_CUSTOM_CLOCK_FACE);
        if (TextUtils.isEmpty(json)) {
            return "";
        }
        try {
            final JSONObject obj = new JSONObject(json);
            if (obj.isNull(KEY_SEED_COLOR)) {
                return "";
            }
            return String.format("#%08X", obj.getInt(KEY_SEED_COLOR));
        } catch (JSONException e) {
            return "";
        }
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mListPreference = screen.findPreference(getPreferenceKey());
        if (mListPreference == null) {
            return;
        }
        String current = readSeedColorHex();
        if (mListPreference.findIndexOfValue(current) < 0) {
            current = "";
        }
        mListPreference.setValue(current);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String value = newValue == null ? "" : newValue.toString();

        JSONObject obj;
        final String existing = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.LOCK_SCREEN_CUSTOM_CLOCK_FACE);
        if (!TextUtils.isEmpty(existing)) {
            try {
                obj = new JSONObject(existing);
            } catch (JSONException e) {
                obj = new JSONObject();
            }
        } else {
            obj = new JSONObject();
        }

        try {
            if (value.isEmpty()) {
                obj.remove(KEY_SEED_COLOR);
            } else {
                obj.put(KEY_SEED_COLOR, Color.parseColor(value));
            }
        } catch (JSONException | IllegalArgumentException e) {
            return false;
        }
        return Settings.Secure.putString(getContentResolver(),
                Settings.Secure.LOCK_SCREEN_CUSTOM_CLOCK_FACE, obj.toString());
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
