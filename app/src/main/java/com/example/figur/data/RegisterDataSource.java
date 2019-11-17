package com.example.figur.data;

import com.example.figur.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class RegisterDataSource {

    public Result<LoggedInUser> register(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LoggedInUser user =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "username");
            return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error registering", e));
        }
    }
}
