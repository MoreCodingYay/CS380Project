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
 * A fragment that represents question 2 in the BrewBuddy app.
 */
public class question2 extends Fragment {
    private SharedViewModel viewModel;

    /**
     * Required empty public constructor.
     */
    public question2() {
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
        View view = inflater.inflate(R.layout.fragment_question2, container, false);

        // set a listener on the toggle button
        ToggleButton lightToggle = view.findViewById(R.id.lightButton);
        // checks if the toggle is on or off. If on, sets it to on in the SharedViewModel class
        lightToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setLightRoastToggle(isChecked);
            }
        });

        // set a listener on the toggle button
        ToggleButton darkToggle = view.findViewById(R.id.darkButton);
        // checks if the toggle is on or off. If on, sets it to on in the SharedViewModel class
        darkToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setDarkRoastToggle(isChecked);
            }
        });

        return view;
    }
}
