package org.bankapp.security;

import io.javalin.core.security.Role;

public enum AUTH implements Role {
    ANYONE, LOGGED_IN
}
