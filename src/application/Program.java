package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("=== Test 1: Seller findById===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
        System.out.println();

        System.out.println("=== Test 2: Seller findByDepartment===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        for(Seller s: list){
            System.out.println(s);
        }
        System.out.println();

        System.out.println("=== Test 3: Seller findAll===");
        list = sellerDao.findAll();
        for(Seller s: list){
            System.out.println(s);
        }
        System.out.println();

        System.out.println("=== Test 4: Seller insert===");
        Seller seller2  = new Seller(null,"Diogo","diogo@gmail.com", new Date(),2000.00,department);
        //sellerDao.insert(seller2);
        System.out.println();

        System.out.println("=== Test 5: Seller update===");
        seller  = sellerDao.findById(9);
        seller.setName("Titi");
        System.out.println();
        //sellerDao.update(seller);

        System.out.println("=== Test 6: Seller delete===");
        sellerDao.deleteById(8);
        sellerDao.deleteById(10);
        sellerDao.deleteById(11);
        System.out.println();
    }
}
