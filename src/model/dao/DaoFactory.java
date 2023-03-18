package model.dao;

import db.DB;
import db.DbException;
import model.dao.impl.DeparmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
    public static DepartmentDao createDepartmentDao(){
        return new DeparmentDaoJDBC(DB.getConnection());
    }
}
