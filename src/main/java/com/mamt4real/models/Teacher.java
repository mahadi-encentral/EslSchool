package com.mamt4real.models;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @Column(name="teacherId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long teacherId;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, targetEntity = Student.class)
    private List<Student> assignedStudents;

    public Teacher() {
    }

    public Teacher(String name) {
        this.name = name;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(List<Student> assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return teacherId == teacher.teacherId && Objects.equals(name, teacher.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teacherId, name);
    }
}
