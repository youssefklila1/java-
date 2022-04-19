/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import middlefeastdes.entity.Course;
import middlefeastdes.entity.User;
import middlefeastdes.service.CourseService;
import middlefeastdes.service.UserService;
import middlefeastdes.utils.Mail;

public class MainUser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        // User
        UserService userService = new UserService();
        // Show users list
        System.out.println("Show all starts");
        System.out.println("---------------");
        if (userService.findAll().isEmpty()) {
            System.out.println("No data found");
        } else {
            userService.findAll().forEach(System.out::println);
        }
        System.out.println("---------------");
        System.out.println("Show all ends");
        System.out.println("---------------");
        
        
        // Add user and show
        System.out.println("Add starts");
        System.out.println("---------------");
        userService.add(new User("mail@test.com", "testpass", "user", "user"));
        Mail mailler = new Mail();
        mailler.sendEmail("youssef.klila@esprit.tn", "User Created Successfully");
        System.out.println("Sending Email to User");
        userService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Add Ends");
        System.out.println("---------------");
        // Get items Ids
        showAvailableIds(userService);
        // Show by id
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select a user id that you want to show the details of : ");
        int opt = scanner.nextInt();
        System.out.println("Showing by id start");
        System.out.println("---------------");
        System.out.println(userService.findById(opt));
        System.out.println("---------------");
        System.out.println("Showing by id ended");
        // Delete
        showAvailableIds(userService);
        System.out.println("Select an id that you want to delete : ");
        opt = scanner.nextInt();
        System.out.println("Deleting by id start");
        System.out.println("---------------");
        System.out.println("List before deletion");
        System.out.println("---------------");
        userService.findAll().forEach(System.out::println);
        userService.delete(opt);
        System.out.println("---------------");
        System.out.println("List after deletion");
        System.out.println("---------------");
        userService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Deleting by id ended");
        // Update
        showAvailableIds(userService);
        System.out.println("Select an id that you want to update : ");
        opt = scanner.nextInt();
        System.out.println("Updating by id start");
        System.out.println("---------------");
        System.out.println("Item before update");
        System.out.println("---------------");
        System.out.println(userService.findById(opt));
        userService.update(new User(opt, "mail1@test.com", "testpass1", "user1", "user1"));
        System.out.println("---------------");
        System.out.println("Item after update");
        System.out.println("---------------");
        System.out.println(userService.findById(opt));
        System.out.println("---------------");
        System.out.println("Updating by id ended");
    }

    private static void showAvailableIds(UserService userService) throws SQLException {
        System.out.println("Getting ids start");
        System.out.println("---------------");
        userService.findAll().forEach(x -> System.out.println(x.getId()));
        System.out.println("---------------");
        System.out.println("Getting ids ends");
        System.out.println("---------------");
    }
}
