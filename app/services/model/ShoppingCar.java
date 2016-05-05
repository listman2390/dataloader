package services.model;

/**
 * Created by listman on 5/5/16.
 */
public class ShoppingCar {
    public int id;
    public double total_after_discounts;
    public double total_discount;
    public String status;
    public int longitude;
    public int latitude;
    public Store store;
    public Platform platform;
    public Device device;
    public User user;
    public OsVersion osVersion;
    public Resolution resolution;
    public DateInfo date;
    public int dateHour;
    public int dateMinute;
    public int dateSecond;

    public String address;
    public DateInfo transactionDate;
    public int transactionHour;
    public int transactionMinute;
    public int transactionSecond;
    public String deliverySlot;
    public Delivery delivery;
    public DateInfo deliveryDate;
    public int deliveryHour;
    public int deliveryMinute;
    public int deliverySecond;
    public PaymentMethod paymentMethod;
}
