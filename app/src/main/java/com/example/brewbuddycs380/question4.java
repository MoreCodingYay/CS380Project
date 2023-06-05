package com.example.brewbuddycs380;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

/**
 * A fragment that represents question 4 in the BrewBuddy app.
 */
public class question4 extends Fragment {
    private SharedViewModel viewModel;

    /**
     * Required empty public constructor.
     */
    public question4() {
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
