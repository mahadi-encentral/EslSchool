package com.mamt4real.models;


import jakarta.persistence.*;

import java.util.List;


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

    @ManyToMany
    private List<Course> registeredCourses;


}
