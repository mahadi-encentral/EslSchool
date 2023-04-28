package com.mamt4real.drivers;

import java.util.Optional;
import java.util.Scanner;

import static com.mamt4real.drivers.GeneralMenu.showMenu;
import static com.mamt4real.drivers.AppDriverHandler.*;

public class AppDriver {

    static final Scanner in = new Scanner(System.in);
    static Long LOGGED_USER;

    public static void main(String[] args) {
        String[] authMenus = {"Signup", "Login", "Exit"};

        int choice = 0;
        long userId;
        while (choice != authMenus.length) {
            choice = showMenu(authMenus, in);
            switch (choice) {
                case 1:
                    userId = handleSignup();
                    if (userId > 0) {
                        LOGGED_USER = userId;
                        appMenu();
                    }
                    break;
                case 2:
                    userId = handleLogin();
                    if (userId > 0) {
                        LOGGED_USER = userId;
                        appMenu();
                    }
                    break;
                case 3:
                    in.close();
                    handleExit();
                    System.out.println("\nGood Bye");
                    break;
            }
        }
    }

    static void appMenu() {
        String[] appMenus = {
                "Register Student", "Register Course to Student", "Add new Course",
                "View all Courses", "View All Students", "View Student Courses",
                "Add new teacher", "View all Teachers", "Get Student teacher", "Logout"
        };

        int choice = 0;
        while (choice != appMenus.length) {

            choice = showMenu(appMenus, in);

            if (choice == appMenus.length) {
                in.close();
                System.out.println("\nLogout Successfully");
                LOGGED_USER = null;
            } else {
                Optional<Runnable> handler = AppDriverHandler.getAppHandler(choice);
                handler.ifPresent(Runnable::run);
            }
        }
    }
}
