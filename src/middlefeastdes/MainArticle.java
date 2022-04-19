package middlefeastdes;



import middlefeastdes.entity.Article;
import middlefeastdes.entity.Course;
import middlefeastdes.service.ArticleService;
import middlefeastdes.service.CourseService;
import middlefeastdes.service.TutorialService;

import java.sql.SQLException;
import java.util.Date;
import java.util.Scanner;

public class MainArticle {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        // Article
        ArticleService articleService = new ArticleService();
        // Show courses list
        System.out.println("Show all starts");
        System.out.println("---------------");
        if (articleService.findAll().isEmpty()) {
            System.out.println("No data found");
        } else {
            articleService.findAll().forEach(System.out::println);
        }
        System.out.println("---------------");
        System.out.println("Show all ends");
        System.out.println("---------------");
        // Add course and show
        System.out.println("Add starts");
        System.out.println("---------------");
        articleService.add(new Article("Cook like a pro", "Learn how to cook like a michelin starred chef", "no_img.png", new Date(), "test_recette", 0));
        articleService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Add Ends");
        System.out.println("---------------");
        // Get items Ids
        showAvailableIds(articleService);
        // Show by id
        System.out.println("Select an id that you want to show the details of : ");
        int opt = scanner.nextInt();
        System.out.println("Showing by id start");
        System.out.println("---------------");
        System.out.println(articleService.findById(opt));
        System.out.println("---------------");
        System.out.println("Showing by id ended");
        // Delete
        showAvailableIds(articleService);
        System.out.println("Select an id that you want to delete : ");
        opt = scanner.nextInt();
        System.out.println("Deleting by id start");
        System.out.println("---------------");
        System.out.println("List before deletion");
        System.out.println("---------------");
        articleService.findAll().forEach(System.out::println);
        articleService.delete(opt);
        System.out.println("---------------");
        System.out.println("List after deletion");
        System.out.println("---------------");
        articleService.findAll().forEach(System.out::println);
        System.out.println("---------------");
        System.out.println("Deleting by id ended");
        // Update
        showAvailableIds(articleService);
        System.out.println("Select an id that you want to update : ");
        opt = scanner.nextInt();
        System.out.println("Updating by id start");
        System.out.println("---------------");
        System.out.println("Item before update");
        System.out.println("---------------");
        System.out.println(articleService.findById(opt));
        articleService.update(new Article(opt,"Cook like a pro_updated", "Learn how to cook like a michelin starred chef_updated", "no_img_updated.png", new Date(), "test_recette_updated", 0));
        System.out.println("---------------");
        System.out.println("Item after update");
        System.out.println("---------------");
        System.out.println(articleService.findById(opt));
        System.out.println("---------------");
        System.out.println("Updating by id ended");
        
        // Search
        System.out.println("Search ...");
        System.out.println("Select column to search by : ");
        String col = scanner.next();
        
        System.out.println("Select pattern to search : ");
        String pat = scanner.next();
        
        articleService.searchBy(col,pat).forEach(System.out::println);
        System.out.println("Search Ended");
        // Order
        System.out.println("Order ...");
        System.out.println("Select column to order by : ");
        col = scanner.next();
        System.out.println("type true for descending sort, false for ascending sort : ");
        Boolean ord = scanner.nextBoolean();
        System.out.println(ord);
        articleService.sortBy(col,ord).forEach(System.out::println);
        System.out.println("Order Ended");
    }

    private static void showAvailableIds(ArticleService articleService) throws SQLException {
        System.out.println("Getting ids start");
        System.out.println("---------------");
        articleService.findAll().forEach(x -> System.out.println(x.getId()));
        System.out.println("---------------");
        System.out.println("Getting ids ends");
        System.out.println("---------------");
    }
}
