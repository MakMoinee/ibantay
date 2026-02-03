package com.thesis.dogharness.interfaces;

import com.github.MakMoinee.library.interfaces.DefaultBaseListener;
import com.thesis.dogharness.models.Users;

public interface UserService {

    void login(Users users, DefaultBaseListener listener);
}
