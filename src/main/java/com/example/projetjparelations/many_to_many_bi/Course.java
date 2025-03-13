package com.example.projetjparelations.many_to_many_bi;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ManyToManyBiCourse")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<StudentCourse> enrollments = new ArrayList<>();

    public Course() {}
    public Course(String title) { this.title = title; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<StudentCourse> getEnrollments() { return enrollments; }
    public void setEnrollments(List<StudentCourse> enrollments) { this.enrollments = enrollments; }
    public void addEnrollment(StudentCourse enrollment) {
        this.enrollments.add(enrollment);
        enrollment.setCourse(this);
    }

    @Override
    public String toString() { return "Course{id=" + id + ", title='" + title + "', enrollments=" + enrollments + "}"; }
}