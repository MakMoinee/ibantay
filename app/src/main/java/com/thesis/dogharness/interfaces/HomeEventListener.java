package com.thesis.dogharness.interfaces;

import com.github.MakMoinee.library.interfaces.DefaultEventListener;

public interface HomeEventListener extends DefaultEventListener {
    default void onClickMenu(String option) {

    }
}
