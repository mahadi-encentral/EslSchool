package com.mamt4real.drivers;

import com.google.common.collect.Lists;
import com.mamt4real.models.Course;
import com.mamt4real.models.Student;
import com.mamt4real.repositories.*;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppDriverHandler {
    private static final UserRepository userRepository = new UserRepository();
    private static final Logger logger = Logger.getLogger(AppDriverHandler.class);
    private static final HashMap<Integer, Runnable> appHandlers = new HashMap<>(){
        {
            put(1, AppDriverHandler::handleRegisterStudent);
            put(2, AppDriverHandler::handleRegisterCourseToStudent);
            put(3, AppDriverHandler::handleAddNewCourse);
            put(4, AppDriverHandler::handleViewAllCourses);
            put(5, AppDriverHandler::handleViewAllStudents);
            put(6, AppDriverHandler::handleViewStudentCourses);
            put(7, AppDriverHandler::handleAddNewTeacher);
            put(8, AppDriverHandler::handleViewAllTeachers);
            put(9, AppDriverHandler::handleGetStudentTeacher);
        }
    };

    public static Optional<Runnable> getAppHandler(int n){
        return Optional.of(appHandlers.get(n));
    }

    private final static StudentRepository studentRepository = new StudentRepository();
    private final static CourseRepository courseRepository = new CourseRepository();
    private final static TeacherRepository teacherRepository = new TeacherRepository();

    public static long handleLogin() {
        System.out.print("Username: ");
        String username = AppDriver.in.nextLine();
        System.out.print("Password: ");
        String password = AppDriver.in.nextLine();
        long success = userRepository.login(username, password);
        if (success == 0) logger.error("Invalid Credentials");
        return success;
    }

    public static long handleSignup() {
        System.out.print("Name: ");
        String name = AppDriver.in.nextLine();
        System.out.print("Username: ");
        String username = AppDriver.in.nextLine();
        System.out.print("Password: ");
        String password = AppDriver.in.nextLine();
        return userRepository.signup(name, username, password);
    }

    public  static void handleExit() {
        logger.info("User logout successfully");
        BaseRepository.close();
    }

    private static void handleRegisterStudent(){
//        System.out.println("Handling registering student...");
        System.out.print("Name: ");
        String name = AppDriver.in.nextLine();
        System.out.print("Gender: ");
        String gender = AppDriver.in.nextLine();
        System.out.print("Class :");
        int classId = AppDriver.in.nextInt();
        AppDriver.in.nextLine();
        long newId = studentRepository.createOne(new Student(name, gender, classId));
        logger.info(String.format("Student (%s) registered successfully!", newId));
    }

    private static void handleRegisterCourseToStudent(){
//        System.out.println("Handling registering course to student...");
        System.out.print("Student ID: ");
        long studentId = AppDriver.in.nextLong();
        AppDriver.in.nextLine();

        Student student = studentRepository.getOne(studentId);
        if(student == null){
            logger.error(String.format("No student registered with id: %d", studentId));
            return;
        }

        System.out.print("Course Ids (separate with space): ");
        List<Long> longIds = Arrays.stream(AppDriver.in.nextLine().split("\\s")).map(Long::new).collect(Collectors.toList());
        final var courses = courseRepository.getCoursesByIds(longIds);


    }

    private static void handleAddNewCourse(){
//        System.out.println("Handling adding new course...");
        System.out.print("Course title: ");
        String title = AppDriver.in.nextLine();
        long newId = courseRepository.createOne(new Course(title));
        logger.info(String.format("Course (%s) created successfully!", newId));

    }

    private static void handleViewAllCourses(){
        System.out.println("Handling viewing courses...");
    }

    private static void handleViewAllStudents(){
        System.out.println("Handling vewing all students...");
    }

    private static void handleViewStudentCourses(){
        System.out.println("Handling viewing student courses...");
    }

    private static void handleAddNewTeacher(){
        System.out.println("Handling adding new teacher...");
    }

    private static void handleViewAllTeachers(){
        System.out.println("Handling viewing all teachers...");
    }

    private static void handleGetStudentTeacher(){
        System.out.println("Handling getting student teacher guide...");
    }

}
