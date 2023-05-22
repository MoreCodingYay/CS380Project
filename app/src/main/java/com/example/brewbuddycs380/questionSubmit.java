package com.example.brewbuddycs380;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;


public class questionSubmit extends Fragment {
    private SharedViewModel viewModel;

    public questionSubmit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    // Gets all the input for the toggle buttons in every fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_submit, container, false);
        Button submitBtn = (Button) view.findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // On click, checks every toggle
                Boolean icedState = viewModel.getIcedToggle().getValue();
                if (icedState != null && icedState) {
                    Log.d("myTag", "Iced");
                }

                Boolean hotState = viewModel.getHotToggle().getValue();
                if (hotState != null && hotState) {
                    Log.d("myTag", "Hot");
                }

                Boolean lightRoastState = viewModel.getLightRoastToggle().getValue();
                if (lightRoastState != null && lightRoastState) {
                    Log.d("myTag", "Light Roast");
                }

                Boolean darkRoastState = viewModel.getDarkRoastToggle().getValue();
                if (darkRoastState != null && darkRoastState) {
                    Log.d("myTag", "Dark Roast");
                }

                Boolean sweetState = viewModel.getSweetToggle().getValue();
                if (sweetState != null && sweetState) {
                    Log.d("myTag", "Sweet");
                }

                Boolean mildSweetState = viewModel.getMildSweetToggle().getValue();
                if (mildSweetState != null && mildSweetState) {
                    Log.d("myTag", "Mild Sweet");
                }

                Boolean darkSweetState = viewModel.getDarkSweetToggle().getValue();
                if (darkSweetState != null && darkSweetState) {
                    Log.d("myTag", "Dark Sweet");
                }

                Boolean creamState = viewModel.getCreamToggle().getValue();
                if (creamState != null && creamState) {
                    Log.d("myTag", "Cream");
                }

                Boolean blackState = viewModel.getBlackToggle().getValue();
                if (blackState != null && blackState) {
                    Log.d("myTag", "Black");
                }

                Boolean decafState = viewModel.getDecafToggle().getValue();
                if (decafState != null && decafState) {
                    Log.d("myTag", "Decaf");
                }

                Boolean foamState = viewModel.getFoamToggle().getValue();
                if (foamState != null && foamState) {
                    Log.d("myTag", "Foam");
                }

                Boolean vanillaState = viewModel.getVanillaToggle().getValue();
                if (vanillaState != null && vanillaState) {
                    Log.d("myTag", "Vanilla");
                }

                Boolean chocolateState = viewModel.getChocolateToggle().getValue();
                if (chocolateState != null && chocolateState) {
                    Log.d("myTag", "Chocolate");
                }

                Boolean caramelState = viewModel.getCaramelToggle().getValue();
                if (caramelState != null && caramelState) {
                    Log.d("myTag", "Caramel");
                }

                Boolean fruityState = viewModel.getFruityToggle().getValue();
                if (fruityState != null && fruityState) {
                    Log.d("myTag", "Fruity");
                }
                // moves to a new activity
                startActivity(new Intent(getActivity(), reccomendationScreen.class));
            }
        });
        return view;
    }
}