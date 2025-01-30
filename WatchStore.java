/** 
     * WatchStore --- program that allows a user to add/create, remove, or print a list of Watches using an interactive menu;
     * @author Silvia Arjona Garcia
*/

import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Arrays;

public class WatchStore {
   
   // define  maximum watches
   private static final int MAX_WATCHES = 100;
   private static Watch[] watchArray = new Watch[MAX_WATCHES];
   private static int watchCount = 0;
   
   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      int choice = -1;
      
      //keep looping menu until user selects 0
      while (choice != 0) {
      printMenu();
        try {
            System.out.print("Please enter your choice: ");
            choice = sc.nextInt();
         
            switch (choice) {
               case 1:
                  addWatch(sc);
                  break;
               case 2:
                  removeWatch(sc);
                  break;
               case 3:
                printWatchesUnder(sc);
                  break;
               case 4:
                  printAllWatches();
                  break;
               case 0:
                  System.out.println("Thank you for using the Watch Store!");
                  break;
               default:
                  System.out.println("Invalid option. Please choose a valid option.");
            }
        } 
        catch (InputMismatchException e) {
          System.out.println("Invalid input. Please enter a number.");
          sc.next(); 
        }
      }
      sc.close();
   }  
   
   //method to print menu
   public static void printMenu() {
      System.out.println("\nMenu:");
      System.out.println("1. Add Watch");
      System.out.println("2. Remove Watch");
      System.out.println("3. Print watches that cost less than a given amount");
      System.out.println("4. Print all watches in stock");
      System.out.println("0. End this program");
      System.out.println();
   } 
   
   //method to add a watch
   public static void addWatch(Scanner sc) {
      try {
         if (watchCount >= MAX_WATCHES) {
            System.out.println("Watch store is full. Cannot add more watches.");
            return;
         }
         
         System.out.print("Enter watch serial number (10001-99999): ");
         int wserial = sc.nextInt();
         
         System.out.print("Enter watch price (0-500000): ");
         double wprice = sc.nextDouble();
         
         sc.nextLine();
         System.out.print("Enter watch description: ");
         String wdesc = sc.nextLine();
         
         System.out.print("Enter watch type (1: Quartz, 2: Solar, 3: Mechanical, 4: Automatic, 5: Digital): ");
         int wtype = sc.nextInt();
         
         //check for duplicate serial number
         for (int i = 0; i < watchCount; i++) {
            if (watchArray[i].getSerial() == wserial) {
               System.out.println("A watch with the same serial number already exists.");
               return;
            }
         }
         
         //create and add watch
         Watch newWatch = new Watch(wserial, wprice, wdesc, wtype);
         watchArray[watchCount++] = newWatch;
         System.out.println("Watch added successfully.");
         
      } 
      catch (InputMismatchException e) {
         System.out.println("Invalid input. Please enter the correct data type.");
         sc.next();
      }
      catch (WatchException e) {
         System.out.println(e.getMessage());
      }
   }  
   
   //method to remove a watch
   public static void removeWatch(Scanner sc) {
      try {
         System.out.print("Enter watch serial number to remove: ");
         int wserial = sc.nextInt();
         
         for (int i = 0; i < watchCount; i++) {
            if (watchArray[i].getSerial() == wserial) {
               //remove watch by shifting following watches
               for (int j = i; j < watchCount - 1; j++) {
                  watchArray[j] = watchArray[j + 1];
               }
               watchArray[--watchCount] = null;
               System.out.println("Watch removed successfully.");
               return;
            }
         }
         System.out.println("Watch not found.");
         
      }
      catch (InputMismatchException e) {
         System.out.println("Invalid input. PLease enter a valid serial number.");
         sc.next();
      }
   }
   
   //method to print watches under a given price
   public static void printWatchesUnder(Scanner sc) {
      try {
         System.out.print("Enter a price to list watches under: ");
         double priceLimit = sc.nextDouble();
         
         boolean found = false;
         for (Watch watch : watchArray) {
            if (watch != null && watch.getPrice() < priceLimit) {
               System.out.println(watch);
               found = true;
            }
         }
         
         if (!found) {
            System.out.println("No watches found under the specified price.");
         }
      }
      catch (InputMismatchException e) {
         System.out.println("Invalid input. Please enter a valid price.");
         sc.next();
      }
   }
   
   //method to print all watches in stock
   public static void printAllWatches() {
      if (watchCount == 0) {
         System.out.println("No watches in stock.");
      } else {
           System.out.println("All watches in stock:");
           System.out.println();
           
           for (Watch watch : watchArray) {
              if (watch != null) {
               System.out.println(watch);
               System.out.println();
              }
           }
      }
   }
}