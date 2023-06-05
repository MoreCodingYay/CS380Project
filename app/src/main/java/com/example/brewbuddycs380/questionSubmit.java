package com.example.brewbuddycs380;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.EnumSet;

/**
 * A fragment that handles the submission of user preferences in the BrewBuddy app.
 */
public class questionSubmit extends Fragment {

    private SharedViewModel viewModel;
    private static final EnumSet<Properties> preferences = EnumSet.noneOf(Properties.class);

    /**
     * Default constructor for the questionSubmit fragment.
     * Required empty public constructor.
     */
    public questionSubmit() {
    }

    /**
     * Called when the fragment is created.
     * Initializes the viewModel using the ViewModelProvider.
     *
     * @param savedInstanceState the saved instance state Bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    /**
     * Called when the fragment's view is created.
     * Inflates the layout for the fragment and sets an OnClickListener for the submit button.
     *
     * @param inflater           the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the saved instance state Bundle
     * @return the inflated View for the fragment
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_submit, container, false);
        Button submitBtn = view.findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when the submit button is clicked.
             * Retrieves the state of toggle buttons and adds the selected preferences to the preferences set.
             * Starts the RecommendationScreen activity and passes the selected preferences as an extra.
             *
             * @param v the View object that was clicked
             */
            public void onClick(View v) {
                Boolean icedState = viewModel.getIcedToggle().getValue();
                if (icedState != null && icedState) {
                    preferences.add(Properties.ICED);
                }

                Boolean hotState = viewModel.getHotToggle().getValue();
                if (hotState != null && hotState) {
                    preferences.add(Properties.HOT);
                }

                Boolean lightRoastState = viewModel.getLightRoastToggle().getValue();
                if (lightRoastState != null && lightRoastState) {
                    preferences.add(Properties.WEAK);
                }

                Boolean darkRoastState = viewModel.getDarkRoastToggle().getValue();
                if (darkRoastState != null && darkRoastState) {
                    preferences.add(Properties.STRONG);
                }

                Boolean sweetState = viewModel.getSweetToggle().getValue();
                if (sweetState != null && sweetState) {
                    preferences.add(Properties.SWEET);
                }

                Boolean mildSweetState = viewModel.getMildSweetToggle().getValue();
                if (mildSweetState != null && mildSweetState) {
                }

                Boolean darkSweetState = viewModel.getDarkSweetToggle().getValue();
                if (darkSweetState != null && darkSweetState) {
                    preferences.add(Properties.BITTER);
                }

                Boolean creamState = viewModel.getCreamToggle().getValue();
                if (creamState != null && creamState) {
                    preferences.add(Properties.CREAMY);
                }

                Boolean blackState = viewModel.getBlackToggle().getValue();
                if (blackState != null && blackState) {
                    preferences.add(Properties.BLACK);
                }

                Boolean decafState = viewModel.getDecafToggle().getValue();
                if (decafState != null && decafState) {
                    preferences.add(Properties.DECAF);
                }

                Boolean foamState = viewModel.getFoamToggle().getValue();
                if (foamState != null && foamState) {
                    preferences.add(Properties.FOAM);
                }

                Boolean vanillaState = viewModel.getVanillaToggle().getValue();
                if (vanillaState != null && vanillaState) {
                    preferences.add(Properties.FLAVOR_VANILLA);
                }

                Boolean chocolateState = viewModel.getChocolateToggle().getValue();
                if (chocolateState != null && chocolateState) {
                    preferences.add(Properties.FLAVOR_CHOCOLATE);
                }

                Boolean caramelState = viewModel.getCaramelToggle().getValue();
                if (caramelState != null && caramelState) {
                    preferences.add(Properties.FLAVOR_CARAMEL);
                }

                Boolean fruityState = viewModel.getFruityToggle().getValue();
                if (fruityState != null && fruityState) {
                    preferences.add(Properties.FLAVOR_FRUIT);
                }

                Intent intent = new Intent(getActivity(), RecommendationScreen.class);
                UserService.updatePreferences(CoffeeRecommender.userPreferenceToString(preferences));
                intent.putExtra("selectedProperties", preferences.toArray(new Properties[0]));
                startActivity(intent);
            }
        });
        return view;
    }
}
