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
import middlefeastdes.service.CourseService;


public class MainCourse {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
         // Course
        CourseService courseService = new CourseService();
        // Show courses list
        System.out.println("Show all starts");
        System.out.println("---------------");
        if (courseService.findAll().isEmpty()) {
            System.out.println("No data found");
        } else {
            courseService.findAll().forEach(System.out::println);
        }
        System.out.println("---------------");
        System.out.println("Show all ends");
        System.out.println("---------------");
        // Add course and show
        System.out.println("Add starts");
        System.out.println("---------------");
        courseService.add(new Course(300, "Online", new Date(), new Date(), "1", "Michelin quality entree"));
        courseService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Add Ends");
        System.out.println("---------------");
        // Get items Ids
        showAvailableIds(courseService);
        // Show by id
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an id that you want to show the details of : ");
        int opt = scanner.nextInt();
        System.out.println("Showing by id start");
        System.out.println("---------------");
        System.out.println(courseService.findById(opt));
        System.out.println("---------------");
        System.out.println("Showing by id ended");
        // Delete
        showAvailableIds(courseService);
        System.out.println("Select an id that you want to delete : ");
        opt = scanner.nextInt();
        System.out.println("Deleting by id start");
        System.out.println("---------------");
        System.out.println("List before deletion");
        System.out.println("---------------");
        courseService.findAll().forEach(System.out::println);
        courseService.delete(opt);
        System.out.println("---------------");
        System.out.println("List after deletion");
        System.out.println("---------------");
        courseService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Deleting by id ended");
        // Update
        showAvailableIds(courseService);
        System.out.println("Select an id that you want to update : ");
        opt = scanner.nextInt();
        System.out.println("Updating by id start");
        System.out.println("---------------");
        System.out.println("Item before update");
        System.out.println("---------------");
        System.out.println(courseService.findById(opt));
        courseService.update(new Course(opt, 0, "On-Site", new Date(), new Date(), "1", "Ramadanesque sweets"));
        System.out.println("---------------");
        System.out.println("Item after update");
        System.out.println("---------------");
        System.out.println(courseService.findById(opt));
        System.out.println("---------------");
        System.out.println("Updating by id ended");
    }

    private static void showAvailableIds(CourseService courseService) throws SQLException {
        System.out.println("Getting ids start");
        System.out.println("---------------");
        courseService.findAll().forEach(x -> System.out.println(x.getId()));
        System.out.println("---------------");
        System.out.println("Getting ids ends");
        System.out.println("---------------");
    }
    }

