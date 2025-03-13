package com.example.projetjparelations.many_to_many_uni;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ManyToManyUniStudent")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentCourse> enrollments = new ArrayList<>();

    public Student() {}
    public Student(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<StudentCourse> getEnrollments() { return enrollments; }
    public void setEnrollments(List<StudentCourse> enrollments) { this.enrollments = enrollments; }
    public void addEnrollment(StudentCourse enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    @Override
    public String toString() { return "Student{id=" + id + ", name='" + name + "', enrollments=" + enrollments + "}"; }
}