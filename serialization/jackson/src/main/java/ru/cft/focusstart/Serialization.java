package ru.cft.focusstart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ru.cft.focusstart.dto.Person;
import ru.cft.focusstart.dto.Student;
import ru.cft.focusstart.dto.Teacher;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;

public class Serialization {

    public static void main(String[] args) throws IOException {
        Teacher source = new Teacher();
        source.setFullName("Филатов Антон");
        source.setBirthDate(LocalDate.of(1988, 1, 13));

        Student student1 = new Student();
        student1.setTeacher(source);
        student1.setFullName("Колесников Руслан");

        Student student2 = new Student();
        student2.setTeacher(source);
        student2.setFullName("Александров Кирилл Валентинович");

        Student student3 = new Student();
        student3.setTeacher(source);
        student3.setFullName("Литвинова Альбина");

        source.setStudents(Arrays.asList(student1, student2, student3));
        System.out.println(source);
        System.out.println(source.getStudents());
        System.out.println("-------------------------------------------------------------------");
        System.out.println();

        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

        String serialized = mapper.writeValueAsString(source);
        System.out.println(serialized);
        System.out.println("-------------------------------------------------------------------");
        System.out.println();

        Teacher deserialized = mapper.readValue(serialized, Teacher.class);
        System.out.println(deserialized);
        System.out.println(deserialized.getStudents());
        System.out.println("-------------------------------------------------------------------");
        System.out.println();

        Teacher teacher = new Teacher();
        teacher.setFullName("I am teacher");

        Student student = new Student();
        student.setFullName("I am student");

        String sTeacher = mapper.writeValueAsString(teacher);
        String sStudent = mapper.writeValueAsString(student);

        Person person1 = mapper.readValue(sTeacher, Person.class);
        Person person2 = mapper.readValue(sStudent, Person.class);

        System.out.println(person1);
        System.out.println(person2);
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }
}
