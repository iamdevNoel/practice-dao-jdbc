package src.model.dao.impl;

import src.db.DbException;
import src.model.entities.Department;
import src.model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements src.model.dao.SellerDao {
    private final Connection connectionDB;

    public SellerDaoJDBC(Connection connectionDB) {
        this.connectionDB = connectionDB;
    }

    @Override
    public void insert(Seller seller) {

    }

    @Override
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement prStatement;
        ResultSet resultSet;

        try {
            prStatement = connectionDB.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                        "FROM seller INNER JOIN department " +
                        "ON seller.DepartmentId = department.Id " +
                        "WHERE seller.ID = ?"
            );
            prStatement.setInt(1, id);
            resultSet = prStatement.executeQuery();

            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                return instantiateSeller(resultSet, department);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement prStatement;
        ResultSet resultSet;
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        try {
            prStatement = connectionDB.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                    "FROM seller INNER JOIN department " +
                    "ON seller.DepartmentId = department.Id " +
                    "WHERE DepartmentID = ? " +
                    "ORDER BY Name"
            );
            prStatement.setInt(1, department.getId());
            resultSet = prStatement.executeQuery();

            while (resultSet.next()) {
                Department dep = map.get(resultSet.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                sellers.add(seller);
            }

            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        List<Seller> sellers = new ArrayList<>();
        Map<Integer, Department> map = new HashMap<>();

        try {
            preparedStatement = connectionDB.prepareStatement(
                    "SELECT seller.*, department.Name as DepName " +
                        "FROM seller INNER JOIN department " +
                        "ON seller.DepartmentID = department.Id " +
                        "ORDER BY Name"
            );
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Department department = map.get(resultSet.getInt("DepartmentId"));
                if (department == null) {
                    department = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), department);
                }
                Seller seller = instantiateSeller(resultSet, department);
                sellers.add(seller);
            }

            return sellers;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    
    private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBirthDate(resultSet.getDate("BirthDate").toLocalDate());
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setDepartment(department);
        return seller;
    }
}
