package com.example.projetjparelations.one_to_many_uni;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "OneToManyUniStudent")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // Changement ici
    private List<StudentCourse> enrollments = new ArrayList<>();

    public Student() {}
    public Student(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<StudentCourse> getEnrollments() { return enrollments; }
    public void setEnrollments(List<StudentCourse> enrollments) { this.enrollments = enrollments; }
    public void addCourse(Course course) {
        StudentCourse enrollment = new StudentCourse(this, course);
        this.enrollments.add(enrollment);
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', enrollments=" + enrollments + "}";
    }
}