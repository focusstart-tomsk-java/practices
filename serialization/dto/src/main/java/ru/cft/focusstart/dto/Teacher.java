package ru.cft.focusstart.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

public class Teacher extends Person {

    @JsonManagedReference
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.writeObject(getBirthDate().toString());
        stream.writeObject(getFullName());
        stream.writeObject(students);
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        setBirthDate(LocalDate.parse((String) stream.readObject()));
        setFullName((String) stream.readObject());
        students = (List<Student>) stream.readObject();
    }

    @Override
    public String toString() {
        return super.toString() + "{" +
                "fullName='" + getFullName() + '\'' +
                ", birthDate=" + getBirthDate() +
                "}";
    }
}
