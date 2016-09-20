package com.loffa.ofstest.auth;

import com.loffa.ofstest.core.User;
import io.dropwizard.auth.Authorizer;

/**
 * Created by lloughlin on 20/09/2016.
 */
public class UserAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String roleName) {
        return user.getRoles() != null && user.getRoles().contains(roleName);
    }
}
