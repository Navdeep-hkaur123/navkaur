package com.example.navkaur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Label welcomeText;

    @FXML
    private TextField customerName;

    @FXML
    private TextField mobileNumber;

    @FXML
    private TextField pizzaSize;

    @FXML
    private TextField numOfToppings;

    @FXML
    private TextField totalBill;

    @FXML
    private TableView<Ordering> orderTable;

    @FXML
    private TableColumn<Ordering, Integer> idColumn;

    @FXML
    private TableColumn<Ordering, String> customerNameColumn;

    @FXML
    private TableColumn<Ordering, String> mobileNumberColumn;

    @FXML
    private TableColumn<Ordering, String> pizzaSizeColumn;

    @FXML
    private TableColumn<Ordering, Integer> numOfToppingsColumn;

    @FXML
    private TableColumn<Ordering, BigDecimal> totalBillColumn;

    private ObservableList<Ordering> orderList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        mobileNumberColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNumber"));
        pizzaSizeColumn.setCellValueFactory(new PropertyValueFactory<>("pizzaSize"));
        numOfToppingsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfToppings"));
        totalBillColumn.setCellValueFactory(new PropertyValueFactory<>("totalBill"));

        orderTable.setItems(orderList);
        fetchOrders();
    }

    @FXML
    protected void createOrder(ActionEvent event) {
        String name = customerName.getText();
        String mobile = mobileNumber.getText();
        String size = pizzaSize.getText();
        int toppings = Integer.parseInt(numOfToppings.getText());
        BigDecimal bill = new BigDecimal(totalBill.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/nav";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "INSERT INTO pizzaorders (CustomerName, MobileNumber, PizzaSize, NumberOfToppings, TotalBill) " +
                    "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, mobile);
            statement.setString(3, size);
            statement.setInt(4, toppings);
            statement.setBigDecimal(5, bill);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);
                orderList.add(new Ordering(orderId, name, mobile, size, toppings, bill));
            }

            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void readOrder(ActionEvent event) {
        fetchOrders();
    }

    @FXML
    protected void updateOrder(ActionEvent event) {
        Ordering selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            return;
        }
        int orderId = selectedOrder.getId();
        String name = customerName.getText();
        String mobile = mobileNumber.getText();
        String size = pizzaSize.getText();
        int toppings = Integer.parseInt(numOfToppings.getText());
        BigDecimal bill = new BigDecimal(totalBill.getText());

        String jdbcUrl = "jdbc:mysql://localhost:3306/nav";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "UPDATE pizzaorders SET CustomerName=?, MobileNumber=?, PizzaSize=?, NumberOfToppings=?, TotalBill=? " +
                    "WHERE OrderID=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, mobile);
            statement.setString(3, size);
            statement.setInt(4, toppings);
            statement.setBigDecimal(5, bill);
            statement.setInt(6, orderId);
            statement.executeUpdate();

            selectedOrder.setCustomerName(name);
            selectedOrder.setMobileNumber(mobile);
            selectedOrder.setPizzaSize(size);
            selectedOrder.setNumOfToppings(toppings);
            selectedOrder.setTotalBill(bill);

            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void deleteOrder(ActionEvent event) {
        Ordering selectedOrder = orderTable.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            return;
        }
        int orderId = selectedOrder.getId();

        String jdbcUrl = "jdbc:mysql://localhost:3306/nav";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "DELETE FROM pizzaorders WHERE OrderID=?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            statement.executeUpdate();

            orderList.remove(selectedOrder);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void fetchOrders() {
        orderList.clear();

        String jdbcUrl = "jdbc:mysql://localhost:3306/nav";
        String dbUser = "root";
        String dbPassword = "";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword)) {
            String query = "SELECT * FROM pizzaorders";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("OrderID");
                String name = resultSet.getString("CustomerName");
                String mobile = resultSet.getString("MobileNumber");
                String size = resultSet.getString("PizzaSize");
                int toppings = resultSet.getInt("NumberOfToppings");
                BigDecimal bill = resultSet.getBigDecimal("TotalBill");

                orderList.add(new Ordering(id, name, mobile, size, toppings, bill));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        customerName.clear();
        mobileNumber.clear();
        pizzaSize.clear();
        numOfToppings.clear();
        totalBill.clear();
    }
}



