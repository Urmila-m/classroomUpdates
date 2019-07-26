package com.myapp.classroomupdates.model;

public class ProgrammeModel {
        private String short_form;

        private String name;

//        private int id;

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

//        public int getId ()
//        {
//            return id;
//        }
//
//        public void setId (int id)
//        {
//            this.id = id;
//        }

        @Override
        public String toString()
        {
            return "ClassPojo [short_form = "+short_form+", name = "+name+"]";
        }

}
