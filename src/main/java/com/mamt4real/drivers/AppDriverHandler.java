package com.mamt4real.drivers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mamt4real.exceptions.RegisteredCoursesExceedLimitException;
import com.mamt4real.exceptions.StudentNotFoundException;
import com.mamt4real.models.Course;
import com.mamt4real.models.Student;
import com.mamt4real.models.Teacher;
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
    private static final HashMap<Integer, Runnable> appHandlers = new HashMap<>() {
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

    public static Optional<Runnable> getAppHandler(int n) {
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

    public static void handleExit() {
        logger.info("User logout successfully");
        BaseRepository.close();
    }

    private static void handleRegisterStudent() {
//        System.out.println("Handling registering student...");
        System.out.print("Name: ");
        String name = AppDriver.in.nextLine();
        System.out.print("Gender: ");
        String gender = AppDriver.in.nextLine();
        System.out.print("Class :");
        int classId = AppDriver.in.nextInt();
        AppDriver.in.nextLine();
        Student newStudent = new Student(name, gender, classId);
        Teacher guide = teacherRepository.findRandom();
        newStudent.setPersonalGuide(guide);
        long newId = studentRepository.createOne(newStudent);
        logger.info(String.format("Student (%s) registered successfully!", newId));
    }

    private static void handleRegisterCourseToStudent() {
//        System.out.println("Handling registering course to student...");
        System.out.print("Student ID: ");
        long studentId = AppDriver.in.nextLong();
        AppDriver.in.nextLine();
        System.out.print("Course Ids (separate with space): ");
        List<Long> longIds = Arrays.stream(AppDriver.in.nextLine().split("\\s")).map(Long::parseLong).collect(Collectors.toList());
        final var courses = courseRepository.getCoursesByIds(longIds);
        try {
            studentRepository.registerCourses(studentId, courses);
            logger.info("Courses registered successfully");
        } catch (StudentNotFoundException | RegisteredCoursesExceedLimitException e) {
            logger.error(e.getMessage());
        }

    }

    private static void handleAddNewCourse() {
//        System.out.println("Handling adding new course...");
        System.out.print("Course title: ");
        String title = AppDriver.in.nextLine();
        long newId = courseRepository.createOne(new Course(title));
        logger.info(String.format("Course (%s) created successfully!", newId));

    }

    private static void handleViewAllCourses() {
//        System.out.println("Handling viewing courses...");
        courseRepository.getAll().forEach(System.out::println);
    }

    private static void handleViewAllStudents() {
//        System.out.println("Handling vewing all students...");
        studentRepository.getAll().forEach(System.out::println);
    }

    private static void handleViewStudentCourses() {
//        System.out.println("Handling viewing student courses...");
        System.out.print("Student ID: ");
        Student student = studentRepository.getOne(AppDriver.in.nextLong());
        if (student == null) {
            logger.error("Student not found!");
            return;
        }
        student.getRegisteredCourses().forEach(System.out::println);
    }

    private static void handleAddNewTeacher() {
//        System.out.println("Handling adding new teacher...");
        System.out.print("Name: ");
        String name = AppDriver.in.nextLine();
        long newId = teacherRepository.createOne(new Teacher(name));
        logger.info(String.format("Teacher (%d) added successfully!", newId));
    }

    private static void handleViewAllTeachers() {
//        System.out.println("Handling viewing all teachers...");
        teacherRepository.getAll().forEach(System.out::println);
    }

    private static void handleGetStudentTeacher() {
//        System.out.println("Handling getting student teacher guide...");
        System.out.print("Student ID: ");
        Student student = studentRepository.getOne(AppDriver.in.nextLong());
        if (student == null) {
            logger.error("Student not found!");
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            Teacher guide = teacherRepository.getOne(student.getPersonalGuide().getTeacherId());
            System.out.print(guide);
//            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(guide);
            System.out.print(jsonStr);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }

}
