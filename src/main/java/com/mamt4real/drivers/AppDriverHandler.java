package com.mamt4real.drivers;

import com.mamt4real.repositories.BaseRepository;
import com.mamt4real.repositories.UserRepository;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Optional;

public class AppDriverHandler {
    private static final UserRepository repository = new UserRepository();
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

    public static long handleLogin() {
        System.out.print("Username: ");
        String username = AppDriver.in.nextLine();
        System.out.print("Password: ");
        String password = AppDriver.in.nextLine();
        long success = repository.login(username, password);
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
        return repository.signup(name, username, password);
    }

    public  static void handleExit() {
        logger.info("User logout successfully");
        BaseRepository.close();
    }

    private static void handleRegisterStudent(){
        System.out.println("Handling registering student...");
    }

    private static void handleRegisterCourseToStudent(){
        System.out.println("Handling registering course to student...");
    }

    private static void handleAddNewCourse(){
        System.out.println("Handling adding new course...");
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
