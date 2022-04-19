/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package middlefeastdes;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;
import middlefeastdes.entity.Tutorial;
import middlefeastdes.service.TutorialService;


public class MainTutorial {
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
         // Tutorial
        TutorialService tutorialService = new TutorialService();
        // Show courses list
        System.out.println("Show all starts");
        System.out.println("---------------");
        if (tutorialService.findAll().isEmpty()) {
            System.out.println("No data found");
        } else {
            tutorialService.findAll().forEach(System.out::println);
        }
        System.out.println("---------------");
        System.out.println("Show all ends");
        System.out.println("---------------");
        // Add course and show
        System.out.println("Add starts");
        System.out.println("---------------");
        tutorialService.add(new Tutorial("vid", "img", new Date(), "Starters", "10 starters", "Michelin quality starters", 200));
        tutorialService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Add Ends");
        System.out.println("---------------");
        // Get items Ids
        showAvailableIds(tutorialService);
        // Show by id
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an id that you want to show the details of : ");
        int opt = scanner.nextInt();
        System.out.println("Showing by id start");
        System.out.println("---------------");
        System.out.println(tutorialService.findById(opt));
        System.out.println("---------------");
        System.out.println("Showing by id ended");
        // Delete
        showAvailableIds(tutorialService);
        System.out.println("Select an id that you want to delete : ");
        opt = scanner.nextInt();
        System.out.println("Deleting by id start");
        System.out.println("---------------");
        System.out.println("List before deletion");
        System.out.println("---------------");
        tutorialService.findAll().forEach(System.out::println);
        tutorialService.delete(opt);
        System.out.println("---------------");
        System.out.println("List after deletion");
        System.out.println("---------------");
        tutorialService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Deleting by id ended");
        // Update
        showAvailableIds(tutorialService);
        System.out.println("Select an id that you want to update : ");
        opt = scanner.nextInt();
        System.out.println("Updating by id start");
        System.out.println("---------------");
        System.out.println("Item before update");
        System.out.println("---------------");
        System.out.println(tutorialService.findById(opt));
        tutorialService.update(new Tutorial(opt,"updated_vid", "updated_img", new Date(), "updated_Starters", "13 starters", "Michelin quality starters", 230));
        System.out.println("---------------");
        System.out.println("Item after update");
        System.out.println("---------------");
        System.out.println(tutorialService.findById(opt));
        System.out.println("---------------");
        System.out.println("Updating by id ended");
    }

    private static void showAvailableIds(TutorialService tutorialService) throws SQLException {
        System.out.println("Getting ids start");
        System.out.println("---------------");
        tutorialService.findAll().forEach(x -> System.out.println(x.getId()));
        System.out.println("---------------");
        System.out.println("Getting ids ends");
        System.out.println("---------------");
    }
    }
    

