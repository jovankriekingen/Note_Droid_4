package jo.vk.notedroid4.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import jo.vk.notedroid4.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
