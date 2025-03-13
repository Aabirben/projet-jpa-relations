package com.example.projetjparelations.one_to_many_uni;

import jakarta.persistence.*;

@Entity(name = "OneToManyUniStudentCourse")
@Table(name = "student_course")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Course course;

    public StudentCourse() {}
    public StudentCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return "StudentCourse{id=" + id + ", student=" + (student != null ? student.getName() : "null") + ", course=" + (course != null ? course.getTitle() : "null") + "}";
    }
}