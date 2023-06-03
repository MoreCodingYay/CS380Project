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
 * A fragment that represents question 7 in the BrewBuddy app.
 */
public class question7 extends Fragment {
    private SharedViewModel viewModel;

    /**
     * Required empty public constructor.
     */
    public question7() {
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
        View view = inflater.inflate(R.layout.fragment_question7, container, false);

        // Set a listener on the vanillaToggle button
        ToggleButton vanillaToggle = view.findViewById(R.id.vanillaButton);
        vanillaToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setVanillaToggle(isChecked);
            }
        });

        // Set a listener on the chocolateToggle button
        ToggleButton chocolateToggle = view.findViewById(R.id.chocolateButton);
        chocolateToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setChocolateToggle(isChecked);
            }
        });

        // Set a listener on the caramelToggle button
        ToggleButton caramelToggle = view.findViewById(R.id.caramelButton);
        caramelToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setCaramelToggle(isChecked);
            }
        });

        // Set a listener on the fruityToggle button
        ToggleButton fruityToggle = view.findViewById(R.id.fruityButton);
        fruityToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                viewModel.setFruityToggle(isChecked);
            }
        });

        return view;
    }
}
