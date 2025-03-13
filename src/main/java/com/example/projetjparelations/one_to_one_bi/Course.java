package com.example.projetjparelations.one_to_one_bi;

import jakarta.persistence.*;

@Entity(name = "OneToOneBiCourse")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToOne(mappedBy = "course")
    private Student student;

    public Course() {}
    public Course(String title) { this.title = title; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    @Override
    public String toString() {
        return "Course{id=" + id + ", title='" + title + "', studentId=" + (student != null ? student.getId() : null) + "}";
    }
}