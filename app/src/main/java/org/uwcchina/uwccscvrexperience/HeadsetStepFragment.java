package org.uwcchina.uwccscvrexperience;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

public class HeadsetStepFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headset_step,
                container, false);

        SearchableSpinner searchableSpinner = view.findViewById(R.id.vr_headset_searchable_spinner);

        searchableSpinner.setTitle("Your VR Headset");
        searchableSpinner.setPositiveButton("OK");

        return view;
    }
}
