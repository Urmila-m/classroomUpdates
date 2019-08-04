package com.myapp.classroomupdates.model;

import java.io.Serializable;

public class RoutineOfModel implements Serializable{
        private String year;

        private String part;

//        private int id;

        private ProgrammeModel programme;

        private String group;

        public String getYear ()
        {
            return year;
        }

        public void setYear (String year)
        {
            this.year = year;
        }

        public String getPart ()
        {
            return part;
        }

        public void setPart (String part)
        {
            this.part = part;
        }

//        public String getId ()
//        {
//            return id;
//        }
//
//        public void setId (String id)
//        {
//            this.id = id;
//        }

        public ProgrammeModel getProgramme ()
        {
            return programme;
        }

        public void setProgramme (ProgrammeModel programme)
        {
            this.programme = programme;
        }

        public String getGroup ()
        {
            return group;
        }

        public void setGroup (String group)
        {
            this.group = group;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [year = "+year+", part = "+part+", programme = "+programme+", group = "+group+"]";
        }
}
