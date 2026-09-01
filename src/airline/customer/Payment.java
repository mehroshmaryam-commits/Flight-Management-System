package airline.customer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Payment implements Serializable {
    private static final long serialVersionUID = 5L;

    private String paymentId;
    private String paymentMethod; // "EasyPaisa", "Bank Transfer", "JazzCash"
    private String accountDetails; // IBAN or Mobile Number
    private double amount;
    private String status; // "Success", "Failed", "Pending", "Refund"
    private LocalDateTime paymentDate;

    public Payment(String paymentId, String paymentMethod, String accountDetails, double amount) {
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.accountDetails = accountDetails;
        this.amount = amount;
        this.status = "Success";
        this.paymentDate = LocalDateTime.now();
    }

    public Payment(Payment p) {
        this.paymentId = p.paymentId;
        this.paymentMethod = p.paymentMethod;
        this.accountDetails = p.accountDetails;
        this.amount = p.amount;
        this.status = p.status;
        this.paymentDate = p.paymentDate;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getAccountDetails() {
        return accountDetails;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFormattedPaymentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        return paymentDate.format(formatter);
    }
}
