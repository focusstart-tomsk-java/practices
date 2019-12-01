package ru.cft.focusstart;

import ru.cft.focusstart.dto.Student;
import ru.cft.focusstart.dto.Teacher;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;

public class Serialization {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
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

        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(outBytes);

        out.writeObject(source);

        String serialized = new String(outBytes.toByteArray());
        System.out.println(serialized);
        System.out.println("-------------------------------------------------------------------");
        System.out.println();

        ByteArrayInputStream inBytes = new ByteArrayInputStream(outBytes.toByteArray());
        ObjectInputStream in = new ObjectInputStream(inBytes);

        Teacher deserialized = (Teacher) in.readObject();
        System.out.println(deserialized);
        System.out.println(deserialized.getStudents());
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }
}
