package com.example.navkaur;

import java.math.BigDecimal;

public class Ordering {
    private int id;
    private String customerName;
    private String mobileNumber;
    private String pizzaSize;
    private int numOfToppings;
    private BigDecimal totalBill;

    public Ordering(int id, String customerName, String mobileNumber, String pizzaSize, int numOfToppings, BigDecimal totalBill) {
        this.id = id;
        this.customerName = customerName;
        this.mobileNumber = mobileNumber;
        this.pizzaSize = pizzaSize;
        this.numOfToppings = numOfToppings;
        this.totalBill = totalBill;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPizzaSize() {
        return pizzaSize;
    }

    public void setPizzaSize(String pizzaSize) {
        this.pizzaSize = pizzaSize;
    }

    public int getNumOfToppings() {
        return numOfToppings;
    }

    public void setNumOfToppings(int numOfToppings) {
        this.numOfToppings = numOfToppings;
    }

    public BigDecimal getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(BigDecimal totalBill) {
        this.totalBill = totalBill;
    }
}
