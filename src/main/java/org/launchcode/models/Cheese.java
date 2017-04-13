package org.launchcode.models;



import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by LaunchCode
 */

@Entity

public class Cheese {



    @Id
    @GeneratedValue

    private int id;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;




    public Cheese(String name, String description) {
        this();
        this.name = name;
        this.description = description;
    }



/*
By setting up the field this way, Hibernate will create a column named
category_id (based on the field name) and when a Cheese object is
stored, this column will contain the id of its category object.
The data for the category object itself will go in the table for the Category class.
 */

    @ManyToOne
    private Category category;

    public Cheese() {

    }

    public int getId() {
        return id;

    }


    @ManyToMany(mappedBy = "cheeses")
//    private List<Menu> menus;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;

    }
}
