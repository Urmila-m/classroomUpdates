package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class ProgrammeModel implements Serializable {
    private int id;

    private String short_form;

    private String name;

    private String groups;

    private int department;

    public ProgrammeModel(String short_form, String name, String groups, int department, int id) {
        this.short_form = short_form;
        this.name = name;
        this.groups = groups;
        this.department = department;
        this.id = id;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }


        public String getShort_form ()
        {
            return short_form;
        }

        public void setShort_form (String short_form)
        {
            this.short_form = short_form;
        }

        public String getName ()
        {
            return name;
        }

        public void setName (String name)
        {
            this.name = name;
        }

        public int getId ()
        {
            return id;
        }

        public void setId (int id)
        {
            this.id = id;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [short_form = "+short_form+", name = "+name+"]";
        }

}
