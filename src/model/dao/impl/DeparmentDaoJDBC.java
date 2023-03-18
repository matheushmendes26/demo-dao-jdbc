package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeparmentDaoJDBC implements DepartmentDao {
    private Connection connection;

    public DeparmentDaoJDBC(Connection connection){
        this.connection = connection;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement("INSERT INTO department "+
                    "(Name) "+
                    "VALUES " +
                    "(?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,obj.getName());

            int rowsAffected  = preparedStatement.executeUpdate();
            if(rowsAffected>0){
                resultSet = preparedStatement.getGeneratedKeys();
                System.out.println(rowsAffected+" rows affected");
                if(resultSet.next()){
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            }else{
                throw new DbException("Unexpected error! No rows affected");
            }
        }catch (SQLException e1) {
            throw  new DbException(e1.getMessage());
        }finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement("UPDATE department " +
                    "SET Name = ? " +
                    "WHERE Id = ?");
            preparedStatement.setString(1,obj.getName());
            preparedStatement.setInt(2,obj.getId());
            preparedStatement.executeUpdate();

        }catch (SQLException e1) {
            throw  new DbException(e1.getMessage());
        }finally {
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        try{
            preparedStatement = connection.prepareStatement("DELETE FROM department " +
                    "WHERE Id = ?");
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

        }catch (SQLException e1) {
            throw  new DbException(e1.getMessage());
        }finally {
            DB.closeStatement(preparedStatement);
        }

    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT * FROM department WHERE Id = ?");
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Department department = instanciateDepartment(resultSet);
                return department;
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
    public List<Department> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            preparedStatement = connection.prepareStatement("SELECT * " +
                    "FROM department " +
                    "ORDER BY Name");
            resultSet = preparedStatement.executeQuery();
            List<Department> list = new ArrayList<>();

            while(resultSet.next()){
                Department department = instanciateDepartment(resultSet);
                list.add(department);
            }
            return list;

        }catch (SQLException e1) {
            throw  new DbException(e1.getMessage());
        }finally {
            DB.closeStatement(preparedStatement);
            DB.closeResultSet(resultSet);
        }
    }

    private Department instanciateDepartment(ResultSet resultSet) throws SQLException{
        Department department = new Department();
        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
