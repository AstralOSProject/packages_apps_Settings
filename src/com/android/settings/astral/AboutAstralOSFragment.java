package com.android.settings.astral;

import android.app.settings.SettingsEnums;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.List;

@SearchIndexable
public class AboutAstralOSFragment extends DashboardFragment {

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.about_astralos;
    }

    @Override
    protected String getLogTag() {
        return "AboutAstralOSFragment";
    }

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.DEVICEINFO;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preference versionPref = findPreference("astralos_version");
        if (versionPref != null) {
            String version = SystemProperties.get("ro.astral.version",
                    SystemProperties.get("ro.build.display.id", "Unknown"));
            versionPref.setSummary(version);
        }

        Preference devicePref = findPreference("astralos_device");
        if (devicePref != null) {
            String device = Build.MANUFACTURER + " " + Build.MODEL;
            devicePref.setSummary(device);
        }

        Preference maintainerPref = findPreference("astralos_maintainer");
        if (maintainerPref != null) {
            maintainerPref.setSummary("bearbeing | Official");
        }
    }

    @Override
    protected List<com.android.settingslib.core.AbstractPreferenceController> createPreferenceControllers(Context context) {
        return new ArrayList<>();
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.about_astralos);
}
