package com.example.projetjparelations.many_to_one_uni;

import jakarta.persistence.*;

@Entity(name = "ManyToOneUniCourse")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    public Course() {}
    public Course(String title) { this.title = title; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() { return "Course{id=" + id + ", title='" + title + "'}"; }
}