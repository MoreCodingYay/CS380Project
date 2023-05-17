package com.example.brewbuddycs380;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    //global variables
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ArrayList<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // find getStarted button from activity
        Button getStartedBtn = findViewById(R.id.getStarted);

        getStartedBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
            viewPager2 = findViewById(R.id.viewPager2);
            tabLayout = findViewById(R.id.tabLayout);
            titles = new ArrayList<String>();
            titles.add("Q1");
            titles.add("Q2");
            titles.add("Q3");
            setViewPagerAdapter();
                new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override

                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(titles.get(position));
                    }
                }).attach();
            getStartedBtn.setVisibility(View.GONE);
        }
    });
    }
    public void setViewPagerAdapter() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>(); //creates an ArrayList of Fragments
        fragmentList.add(new question1());
        fragmentList.add(new question2());
        fragmentList.add(new question3());
        viewPager2Adapter.setData(fragmentList); //sets the data for the adapter
        viewPager2.setAdapter(viewPager2Adapter);

    }

}