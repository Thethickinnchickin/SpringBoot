package org.springboardLogin.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // Constructors

    /**
     * Default constructor for Role.
     */
    public Role() {
        // Default constructor
    }

    /**
     * Constructor for Role with name.
     * @param name The name of the role.
     */
    public Role(String name) {
        this.name = name;
    }

    // Getters and Setters

    /**
     * Get the ID of the role.
     * @return The ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the ID of the role.
     * @param id The ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the name of the role.
     * @return The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the role.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
