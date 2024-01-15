package org.springboardLogin.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    // To prevent exposing password in response
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // Many-to-many relationship with roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    // One-to-many relationship with tasks
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> tasks;

    // Constructors
    public AppUser() {
        // Default constructor
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters

    /**
     * Get the user ID.
     * @return The user ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the user ID.
     * @param id The user ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the username.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // UserDetails interface methods

    @Override
    public boolean isAccountNonExpired() {
        return false;  // You may customize this based on your requirements
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;  // You may customize this based on your requirements
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;  // You may customize this based on your requirements
    }

    @Override
    public boolean isEnabled() {
        return false;  // You may customize this based on your requirements
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;  // You may customize this based on your requirements
    }

    /**
     * Get the password.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the list of tasks associated with the user.
     * @return The list of tasks.
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Set the list of tasks associated with the user.
     * @param tasks The list of tasks to set.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    // Equals and HashCode (based on unique field)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser user = (AppUser) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    /**
     * Get the set of roles associated with the user.
     * @return The set of roles.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Set the set of roles associated with the user.
     * @param roles The set of roles to set.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
