package com.example.brewbuddycs380;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;

/**
 * A fragment that represents question 3 in the BrewBuddy app.
 */
public class question3 extends Fragment {
    private SharedViewModel viewModel;

    /**
     * Required empty public constructor.
     */
    public question3() {
    }

    /**
     * Called when the fragment is created.
     *
     * @param savedInstanceState the saved instance state Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    /**
     * Called to create the view for the fragment.
     *
     * @param inflater           the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the saved instance state Bundle
     * @return the created view for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question3, container, false);

        // Set a listener on the darkSweetToggle button
        ToggleButton darkSweetToggle = view.findViewById(R.id.darkSweetButton);
        darkSweetToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setDarkSweetToggle(isChecked);
            }
        });

        // Set a listener on the mildSweetToggle button
        ToggleButton mildSweetToggle = view.findViewById(R.id.mildSweetButton);
        mildSweetToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setMildSweetToggle(isChecked);
            }
        });

        // Set a listener on the sweetToggle button
        ToggleButton sweetToggle = view.findViewById(R.id.sweetButton);
        sweetToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setSweetToggle(isChecked);
            }
        });

        return view;
    }

}
