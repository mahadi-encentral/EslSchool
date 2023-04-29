package com.mamt4real.models;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "studentId")
    private long studentId;

    @Column(name = "name")
    private String name;

    @Column(name = "gender")
    private String gender;

    @Column(name = "classId")
    private long classId;

    @ManyToMany(targetEntity = Course.class, fetch = FetchType.LAZY)
    private List<Course> registeredCourses;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guideId")
    private Teacher personalGuide;

    public Student() {
    }

    public Student(String name, String gender, long classId) {
        this.name = name;
        this.gender = gender;
        this.classId = classId;
    }

    public long getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getClassId() {
        return classId;
    }

    public void setClassId(long classId) {
        this.classId = classId;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<Course> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public Teacher getPersonalGuide() {
        return personalGuide;
    }

    public void setPersonalGuide(Teacher personalGuide) {
        this.personalGuide = personalGuide;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", classId=" + classId +
                ", registeredCourses=" + registeredCourses +
                ", personalGuide=" + personalGuide.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studentId == student.studentId && classId == student.classId && name.equals(student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, name, classId);
    }
}
