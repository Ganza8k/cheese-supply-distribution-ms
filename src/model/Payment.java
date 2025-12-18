package model;

import java.util.Date;

public class Payment {
    private int paymentId;
    private int orderId;
    private double amount;
    private Date paymentDate;
    private String paymentMethod;
    private String status;

    public Payment() {}

    public Payment(int orderId, double amount, Date paymentDate, String paymentMethod, String status) {
        this.orderId = orderId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return "Payment #" + paymentId + " - $" + amount + " (" + paymentMethod + ")";
    }
}