package com.thesis.dogharness.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private int userID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;

    public Users(UserBuilder builder) {
        this.userID = builder.userID;
        this.firstName = builder.firstName;
        this.middleName = builder.middleName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.password = builder.password;
    }

    public static class UserBuilder {
        private int userID;
        private String firstName;
        private String middleName;
        private String lastName;
        private String email;
        private String password;


        public UserBuilder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Users build() {
            return new Users(this);
        }
    }
}
