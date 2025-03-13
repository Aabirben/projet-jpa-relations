package com.example.projetjparelations.many_to_one_bi;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "ManyToOneBiCourse")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    private List<Student> students = new ArrayList<>();

    public Course() {}
    public Course(String title) { this.title = title; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
    public void addStudent(Student student) {
        this.students.add(student);
        student.setCourse(this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Course{id=").append(id).append(", title='").append(title).append("', students=[");
        for (Student student : students) {
            sb.append("Student{id=").append(student.getId()).append(", name='").append(student.getName()).append("'}, ");
        }
        if (!students.isEmpty()) {
            sb.setLength(sb.length() - 2); // Supprime la dernière virgule et l’espace
        }
        sb.append("]}");
        return sb.toString();
    }
}