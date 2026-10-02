/*
 * Copyright (C) 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.astral;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;

public class ClockFontController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener {

    private ListPreference mListPreference;

    public ClockFontController(Context context, String key) {
        super(context, key);
    }

    private ContentResolver getContentResolver() {
        return mContext.getContentResolver();
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        mListPreference = screen.findPreference(getPreferenceKey());
        if (mListPreference == null) {
            return;
        }
        String current = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ASTRAL_CLOCK_FONT);
        if (current == null) {
            current = "";
        }
        if (mListPreference.findIndexOfValue(current) < 0) {
            current = "";
        }
        mListPreference.setValue(current);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        final String value = newValue == null ? "" : newValue.toString();
        return Settings.Secure.putString(getContentResolver(),
                Settings.Secure.ASTRAL_CLOCK_FONT, value);
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }
}
