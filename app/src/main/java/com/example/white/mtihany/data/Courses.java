package com.example.white.mtihany.data;

import java.io.Serializable;

public class Courses implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;


    private boolean isSelected;

    public Courses() {

    }

    public Courses(String name) {

        this.name = name;


    }

    public Courses(String name, boolean isSelected) {

        this.name = name;

        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

}
