package com.loffa.ofstest.core;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

/**
 * Created by lloughlin on 20/09/2016.
 */
public class User implements Principal {
    private final String name;

    private User(Builder builder) {
        name = builder.name;
        roles = builder.roles;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Set<String> getRoles() {
        return roles;
    }

    private final Set<String> roles;

    public User(String name) {
        this.name = name;
        this.roles = null;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean implies(Subject subject) {
        return false;
    }

    public static final class Builder {
        private String name;
        private Set<String> roles;

        private Builder() {
        }

        public Builder withName(String val) {
            name = val;
            return this;
        }

        public Builder withRoles(Set<String> val) {
            roles = val;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
