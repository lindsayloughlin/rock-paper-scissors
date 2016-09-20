package com.loffa.ofstest.auth;

import com.google.common.collect.ImmutableSet;
import com.loffa.ofstest.core.User;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;

/**
 * Created by lloughlin on 20/09/2016.
 */
public class UserAuthenticator implements Authenticator<BasicCredentials, User> {

    // Replace with players.
    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        System.out.println("user " + basicCredentials.getUsername() + " password " + basicCredentials.getPassword());
        return Optional.of(User.newBuilder()
        .withName("test-user")
        .withRoles(ImmutableSet.of("valid_player")).build());
    }
}
