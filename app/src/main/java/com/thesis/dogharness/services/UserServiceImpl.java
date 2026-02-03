package com.thesis.dogharness.services;

import android.content.Context;

import com.github.MakMoinee.library.interfaces.DefaultBaseListener;
import com.thesis.dogharness.interfaces.UserService;
import com.thesis.dogharness.models.Users;
import com.thesis.dogharness.repository.MySqlite;

public class UserServiceImpl implements UserService {
    MySqlite sqlite;

    public UserServiceImpl(Context mContext) {
        this.sqlite = new MySqlite(mContext, "ibantay");
    }

    @Override
    public void login(Users users, DefaultBaseListener listener) {
        if (users.getEmail() != null || users.getPassword() != null) {
            listener.onError(new Error("empty email or password"));
        }else{

        }
    }
}
