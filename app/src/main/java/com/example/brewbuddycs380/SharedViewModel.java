package com.example.brewbuddycs380;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel class that holds the state of toggle buttons in the BrewBuddy app.
 * It provides getters and setters for each toggle button's state.
 */
public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Boolean> icedToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hotToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> lightRoastToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> darkRoastToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> sweetToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mildSweetToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> darkSweetToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> creamToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> blackToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> decafToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> foamToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> vanillaToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> chocolateToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> caramelToggle = new MutableLiveData<>();
    private final MutableLiveData<Boolean> fruityToggle = new MutableLiveData<>();


    // it is very repetitive from this point. Nothing interesting to see here
    // if you add anymore buttons just copy and paste these two methods for it
    // and change the name
    public void setIcedToggle(boolean state) {
        icedToggle.setValue(state);
    }

    public LiveData<Boolean> getIcedToggle() {
        return icedToggle;
    }

    public void setHotToggle(boolean state) {
        hotToggle.setValue(state);
    }

    public LiveData<Boolean> getHotToggle() {
        return hotToggle;
    }

    public void setLightRoastToggle(boolean state) {
        lightRoastToggle.setValue(state);
    }

    public LiveData<Boolean> getLightRoastToggle() {
        return lightRoastToggle;
    }

    public void setDarkRoastToggle(boolean state) {
        darkRoastToggle.setValue(state);
    }

    public LiveData<Boolean> getDarkRoastToggle() {
        return darkRoastToggle;
    }

    public void setSweetToggle(boolean state) {
        sweetToggle.setValue(state);
    }

    public LiveData<Boolean> getSweetToggle() {
        return sweetToggle;
    }

    public void setMildSweetToggle(boolean state) {
        mildSweetToggle.setValue(state);
    }

    public LiveData<Boolean> getMildSweetToggle() {
        return mildSweetToggle;
    }

    public void setDarkSweetToggle(boolean state) {
        darkSweetToggle.setValue(state);
    }

    public LiveData<Boolean> getDarkSweetToggle() {
        return darkSweetToggle;
    }

    public void setCreamToggle(boolean state) {
        creamToggle.setValue(state);
    }

    public LiveData<Boolean> getCreamToggle() {
        return creamToggle;
    }

    public void setBlackToggle(boolean state) {
        blackToggle.setValue(state);
    }

    public LiveData<Boolean> getBlackToggle() {
        return blackToggle;
    }

    public void setDecafToggle(boolean state) {
        decafToggle.setValue(state);
    }

    public LiveData<Boolean> getDecafToggle() {
        return decafToggle;
    }

    public void setFoamToggle(boolean state) {
        foamToggle.setValue(state);
    }

    public LiveData<Boolean> getFoamToggle() {
        return foamToggle;
    }

    public void setVanillaToggle(boolean state) {
        vanillaToggle.setValue(state);
    }

    public LiveData<Boolean> getVanillaToggle() {
        return vanillaToggle;
    }

    public void setChocolateToggle(boolean state) {
        chocolateToggle.setValue(state);
    }

    public LiveData<Boolean> getChocolateToggle() {
        return chocolateToggle;
    }

    public void setCaramelToggle(boolean state) {
        caramelToggle.setValue(state);
    }

    public LiveData<Boolean> getCaramelToggle() {
        return caramelToggle;
    }

    public void setFruityToggle(boolean state) {
        fruityToggle.setValue(state);
    }

    public LiveData<Boolean> getFruityToggle() {
        return fruityToggle;
    }
}
