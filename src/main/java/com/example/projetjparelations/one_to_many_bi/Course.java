package com.example.projetjparelations.one_to_many_bi;

import jakarta.persistence.*;

@Entity(name = "OneToManyBiCourse")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "student_id")
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
    public String toString() { return "Course{id=" + id + ", title='" + title + "', student=" + (student != null ? student.getName() : "null") + "}"; }
}