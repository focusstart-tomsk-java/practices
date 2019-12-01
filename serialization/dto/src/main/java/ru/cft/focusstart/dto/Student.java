package ru.cft.focusstart.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Student extends Person {

    @JsonBackReference
    private Teacher teacher;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "fullName='" + getFullName() + '\'' +
                ", birthDate=" + getBirthDate() +
                ", teacher=" + teacher +
                "}";
    }
}
