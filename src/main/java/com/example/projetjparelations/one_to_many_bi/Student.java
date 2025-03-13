package com.example.projetjparelations.one_to_many_bi;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "OneToManyBiStudent")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Course> courses = new ArrayList<>();

    public Student() {}
    public Student(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
    public void addCourse(Course course) {
        this.courses.add(course);
        course.setStudent(this);
    }

    @Override
    public String toString() { return "Student{id=" + id + ", name='" + name + "', courses=" + courses + "}"; }
}