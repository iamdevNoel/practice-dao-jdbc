package src.application;

import src.model.Department;
import src.model.Seller;

import java.time.LocalDate;

public class Program {

    public static void main(String[] args) {

        Department obj = new Department(1, "Human Resources");
        Seller seller = new Seller(1, "Bob", "bob@gmail.com", LocalDate.parse("2023-12-27"), 3000.00, obj);
        System.out.println(seller);

    }

}
