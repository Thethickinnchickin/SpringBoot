package org.springboardLogin.Entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsEntity implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    // Getters and Setters

    /**
     * Get the username of the user.
     * @return The username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Get the password of the user.
     * @return The password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Set the username of the user.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Set the password of the user.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the authorities (roles) assigned to the user.
     * @return The authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Set the authorities (roles) for the user.
     * @param authorities The authorities to set.
     */
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    // Other necessary methods from UserDetails interface

    /**
     * Check if the user account is non-expired.
     * @return true if the account is non-expired.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Check if the user account is non-locked.
     * @return true if the account is non-locked.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Check if the user credentials are non-expired.
     * @return true if the credentials are non-expired.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Check if the user account is enabled.
     * @return true if the account is enabled.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
