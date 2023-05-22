package com.example.brewbuddycs380;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;


public class question4 extends Fragment {
    private SharedViewModel viewModel;

    public question4() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question4, container, false);

        // Set a listener on the creamToggle button
        ToggleButton creamToggle = view.findViewById(R.id.creamButton);
        creamToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setCreamToggle(isChecked);
            }
        });

        // Set a listener on the blackToggle button
        ToggleButton blackToggle = view.findViewById(R.id.blackButton);
        blackToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setBlackToggle(isChecked);
            }
        });
        return view;
    }
}
