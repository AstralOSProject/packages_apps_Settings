package com.android.settings.astral;

import android.app.settings.SettingsEnums;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.search.SearchIndexable;

import java.util.ArrayList;
import java.util.List;

@SearchIndexable
public class AstralFeaturesFragment extends DashboardFragment {

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.astral_features;
    }

    @Override
    protected String getLogTag() {
        return "AstralFeaturesFragment";
    }

    @Override
    public int getMetricsCategory() {
        return SettingsEnums.DEVICEINFO;
    }

    @Override
    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        List<AbstractPreferenceController> controllers = new ArrayList<>();
        controllers.add(new AlwaysOnDisplayController(context, "feature_aod"));
        controllers.add(new NightLightController(context, "feature_night_light"));
        controllers.add(new AdaptiveSleepController(context, "feature_adaptive_sleep"));
        return controllers;
    }

    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.astral_features);
}
