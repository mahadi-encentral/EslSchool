package com.mamt4real.drivers;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GeneralMenu {

    /**
     * A prompt for multiple choice
     * @param menus List of options to choose from
     * @param in a Scanner instance
     * @return an integer representing the selected option
     */
    static int showMenu(String[] menus , Scanner in) {

        int choice = 0;
        int L = menus.length;

        for (int i = 0; i < L; i++)
            System.out.printf("%3d. %s%n", (i+1), menus[i]);

        while (choice < 1 || choice > L) {
            try{
                System.out.print("\nyour choice: ");
                choice = in.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Invalid Choice!!");
            }

        }
        in.nextLine();
        return  choice;
    }
}
