package services.model;

/**
 * Created by listman on 5/4/16.
 */
public class Store {
    public int id;
    public String name;
    public String city;
    public String state;
    public String country;

    public Store(int id, String name, String city, String state, String country) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.country = country;
    }
}
