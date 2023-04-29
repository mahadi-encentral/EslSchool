package com.mamt4real.models;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @Column(name = "courseId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long courseId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(targetEntity = Student.class, fetch = FetchType.LAZY)
    private List<Student> registeredStudents;


    public Course() {
    }

    public Course(String title) {
        this.title = title;
    }

    public long getCourseId() {
        return courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public List<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<Student> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && Objects.equals(title, course.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, title);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title='" + title + '\'' +
                '}';
    }
}
