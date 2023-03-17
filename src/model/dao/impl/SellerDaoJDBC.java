package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.List;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/coursejdbc", "root", "1234567");
           preparedStatement = connection.prepareStatement("SELECT seller.*,department.Name as DepName " +
                   "FROM seller INNER JOIN department " +
                   "ON seller.DepartmentId = department.Id " +
                   "WHERE seller.Id = ?");
           preparedStatement.setInt(1,id);
           resultSet = preparedStatement.executeQuery();

           if(resultSet.next()){
               Department department = new Department();
               Seller seller = new Seller();
               department.setId(resultSet.getInt("DepartmentId"));
               department.setName(resultSet.getString("DepName"));
               seller.setId(resultSet.getInt("Id"));
               seller.setName(resultSet.getString("Name"));
               seller.setEmail(resultSet.getString("Email"));
               seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
               seller.setBirthdate(resultSet.getDate("BirthDate"));
               seller.setDepartment(department);
               return seller;
           }
           return null;

        }catch (SQLException e1) {
            throw  new DbException(e1.getMessage());
        }finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findAll(Seller obj) {
        return null;
    }
}
