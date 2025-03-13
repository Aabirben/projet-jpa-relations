package com.example.projetjparelations.many_to_one_bi;

import jakarta.persistence.*;

@Entity(name = "ManyToOneBiStudent")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id")

    private Course course;

    public Student() {}
    public Student(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', course=" + (course != null ? "Course{id=" + course.getId() + "}" : "null") + "}";
    }
}