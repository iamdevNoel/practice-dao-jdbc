package src.application;

import src.model.dao.DaoFactory;
import src.model.dao.SellerDao;
import src.model.entities.Department;
import src.model.entities.Seller;

import java.time.LocalDate;

public class Program {

    public static void main(String[] args) {

        Department obj = new Department(1, "Human Resources");
        Seller seller = new Seller(1, "Bob", "bob@gmail.com", LocalDate.parse("2023-12-27"), 3000.00, obj);
        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println(seller);

    }

}
