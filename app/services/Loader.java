package services;


import model.Municipalities;
import model.Regions;
import model.Stores;
import play.db.jpa.JPAApi;
import services.model.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Singleton
public class Loader {
    public int dato =0;
    private JPAApi jpaApi;
    private List<Store> stores;
    private List<User> users;
    private List<Resolution> resolutions;
    private List<Device> devices;
    private List<Platform> platforms;
    private List<PaymentMethod> paymentMethods;
    private List<OsVersion> osVersions;
    private List<Delivery> deliveries;

    @Inject
    public Loader(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }

    public void excecute() {
        try {
            loadStores();
            loadResolution();
            loadDevices();
            loadPlatforms();
            loadOsVersions();
            loadPaymentMethods();
            loadDeliveries();
            loadUsers();
            Thread hilo1 = new Thread(() -> {
                excuteDeliveriesQueryM1();
            });
            hilo1.start();

            Thread hilo2 = new Thread(() -> {
                excuteDevicesQueryM1();
            });
            hilo2.start();

            Thread hilo3 = new Thread(() -> {
                excuteOsVersionsQueryM1();
            });
            hilo3.start();

            Thread hilo4 = new Thread(() -> {
                excutePaymentsQueryM1();
            });
            hilo4.start();


            Thread hilo6 = new Thread(() -> {
                excutePlatformsQueryM1();
            });
            hilo6.start();

            Thread hilo7 = new Thread(() -> {
                excuteResolutionsQueryM1();
            });
            hilo7.start();

            Thread hilo8 = new Thread(() -> {
                excuteStoresQueryM1();
            });
            hilo8.start();

            Thread hilo9 = new Thread(() -> {
                excuteUsersQueryM1();
            });
            hilo9.start();

            Thread hilo10 = new Thread(() -> {
                excuteUsersQueryM2();
            });
            hilo10.start();

            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
            hilo6.join();
            hilo6.join();
            hilo7.join();
            hilo8.join();
            hilo9.join();
            hilo10.join();

            generateCars();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void loadStores() {
        List<Stores> storesList = this.jpaApi.em().createNamedQuery("Stores.findAll").getResultList();
        this.stores = storesList.stream().map(store -> {
            Municipalities municipalities = store.getMunicipalitiesList().get(0);
            Regions region = municipalities.getRegionId();
            return new Store(store.getId(), store.getName(), municipalities.getName(), region.getName(), "chile");
        }).collect(Collectors.toList());
    }

    public void loadResolution() {
        this.resolutions = new ArrayList<>();
        this.resolutions.add(new Resolution(1, "640 x 960"));
        this.resolutions.add(new Resolution(2, "640 x 1136"));
        this.resolutions.add(new Resolution(3, "320 x 240"));
        this.resolutions.add(new Resolution(4, "1920 x 1080"));
        this.resolutions.add(new Resolution(5, "240 x 320"));
        this.resolutions.add(new Resolution(6, "768 x 1280"));
        this.resolutions.add(new Resolution(7, "720 x 1280"));
        this.resolutions.add(new Resolution(8, "480 x 854"));
        this.resolutions.add(new Resolution(9, "320 x 480"));
        this.resolutions.add(new Resolution(10, "540 x 960"));
        this.resolutions.add(new Resolution(11, "480 x 800"));
    }

    public void loadDevices() {
        this.devices = new ArrayList<>();
        this.devices.add(new Device(1, "TABLET"));
        this.devices.add(new Device(2, "SMART PHONE"));
    }

    public void loadPlatforms() {
        this.platforms = new ArrayList<>();
        this.platforms.add(new Platform(1, "ANDRID"));
        this.platforms.add(new Platform(2, "IOS"));
    }

    public void loadPaymentMethods() {
        this.paymentMethods = new ArrayList<>();
        this.paymentMethods.add(new PaymentMethod(1, "CASH"));
        this.paymentMethods.add(new PaymentMethod(2, "CREDIT CARD"));
        this.paymentMethods.add(new PaymentMethod(3, "DEBIT CARD"));
    }

    public void loadOsVersions() {
        this.osVersions = new ArrayList<>();
        this.osVersions.add(new OsVersion(1, "OS VERSION 1"));
        this.osVersions.add(new OsVersion(2, "OS VERSION 2"));
        this.osVersions.add(new OsVersion(3, "OS VERSION 3"));
        this.osVersions.add(new OsVersion(4, "OS VERSION 4"));
        this.osVersions.add(new OsVersion(5, "OS VERSION 5"));
        this.osVersions.add(new OsVersion(6, "OS VERSION 6"));
        this.osVersions.add(new OsVersion(7, "OS VERSION 7"));
    }

    public void loadDeliveries() {
        this.deliveries = new ArrayList<>();
        this.deliveries.add(new Delivery(1, "Click & al Auto"));
        this.deliveries.add(new Delivery(2, "Domicilio"));
    }

    public void loadUsers() {
        this.users = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            String sexo = "F";
            if (Math.random() > 0.7) {
                sexo = "M";
            }
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, new Random().nextInt(30) + 1970);
            c.set(Calendar.MONTH, new Random().nextInt(12));
            c.set(Calendar.DAY_OF_MONTH, new Random().nextInt(28) + 1);
            Date birthDay = new Date(c.getTimeInMillis());
            User user = new User(i, "Name " + i, "Last Name" + i, "email" + i + "@grability.com", "320" + i, sexo, birthDay, "Address #" + i);
            user.platform = this.platforms.get(new Random().nextInt(this.platforms.size()));
            user.resolution = this.resolutions.get(new Random().nextInt(this.resolutions.size()));
            user.device = this.devices.get(new Random().nextInt(this.devices.size()));
            user.osVersion = this.osVersions.get(new Random().nextInt(this.osVersions.size()));
            this.users.add(user);
        }
    }

    public void generateCars() {
        try {
            Connection cM1 = getConnectionM1();
            Connection cM2 = getConnectionM2();
            Calendar c = Calendar.getInstance();
            c.set(2014, 1, 1, 0, 0, 0);
            Random randomTime = new Random();
            for (int i = 1; i <= 100000000; i++) {
                dato++;
                User user = this.users.get(new Random().nextInt(this.users.size()));
                ShoppingCar shoppingCar = new ShoppingCar();
                shoppingCar.id = 1;
                shoppingCar.total_after_discounts = new Random().nextInt(50000) + 5000;
                shoppingCar.total_discount = new Random().nextInt(1000) + 1000;
                ;
                shoppingCar.status = getStatus();
                shoppingCar.longitude = new Random().nextInt(100);
                shoppingCar.latitude = new Random().nextInt(100);
                ;
                shoppingCar.store = this.stores.get(new Random().nextInt(this.stores.size()));
                shoppingCar.platform = user.platform;
                shoppingCar.device = user.device;
                shoppingCar.user = user;
                shoppingCar.osVersion = user.osVersion;
                shoppingCar.resolution = user.resolution;
                shoppingCar.date = new DateInfo(c);
                excuteDateQueryM1(shoppingCar.date);
                shoppingCar.dateHour = c.get(Calendar.HOUR);
                shoppingCar.dateMinute = c.get(Calendar.MINUTE);
                shoppingCar.dateSecond = c.get(Calendar.SECOND);
                c.add(Calendar.SECOND, randomTime.nextInt(1000) + 1);
                if ("SOLD".equals(shoppingCar.status)) {
                    Calendar c1 = (Calendar) c.clone();
                    c1.add(Calendar.SECOND, new Random().nextInt(100));
                    shoppingCar.address = user.address;
                    shoppingCar.transactionDate = new DateInfo(c1);
                    excuteDateQueryM1(shoppingCar.transactionDate);
                    shoppingCar.transactionHour = c1.get(Calendar.HOUR);
                    shoppingCar.transactionMinute = c1.get(Calendar.MINUTE);
                    shoppingCar.transactionSecond = c1.get(Calendar.SECOND);
                    shoppingCar.deliverySlot = "SLOT";
                    shoppingCar.delivery = this.deliveries.get(new Random().nextInt(this.deliveries.size()));
                    c1.add(Calendar.SECOND, new Random().nextInt(200) + 1);
                    shoppingCar.deliveryDate = new DateInfo(c1);
                    excuteDateQueryM1(shoppingCar.deliveryDate);
                    shoppingCar.deliveryHour = c1.get(Calendar.HOUR);

                    shoppingCar.deliveryMinute = c1.get(Calendar.MINUTE);
                    shoppingCar.deliverySecond = c1.get(Calendar.SECOND);
                    shoppingCar.paymentMethod = this.paymentMethods.get(new Random().nextInt(this.paymentMethods.size()));

                    excecuteSoldShoppingCarM1(shoppingCar, cM1);
                    excecuteSoldShoppingCarM2(shoppingCar, cM2);
                } else if ("DISCARDED".equals(shoppingCar.status)) {
                    excecuteDiscadedShoppingCarM1(shoppingCar, cM1);
                    excecuteDiscadedShoppingCarM2(shoppingCar, cM2);
                } else {
                    excecuteProgressShoppingCarM1(shoppingCar, cM1);
                    excecuteProgressShoppingCarM2(shoppingCar, cM2);
                }
            }
            cM1.close();
            cM2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excecuteProgressShoppingCarM2(ShoppingCar shoppingCar, Connection connection) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into progress_shopping_cars_m2 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, shoppingCar.id);
            ps.setDouble(2, shoppingCar.total_after_discounts);
            ps.setDouble(3, shoppingCar.total_discount);
            ps.setString(4, shoppingCar.status);
            ps.setInt(5, shoppingCar.longitude);
            ps.setInt(6, shoppingCar.latitude);

            ps.setInt(7, shoppingCar.store.id);
            ps.setString(8, shoppingCar.store.name);
            ps.setString(9, shoppingCar.store.city);
            ps.setString(10, shoppingCar.store.state);
            ps.setString(11, shoppingCar.store.country);

            ps.setInt(12, shoppingCar.platform.id);
            ps.setString(13, shoppingCar.platform.name);

            ps.setInt(14, shoppingCar.device.id);
            ps.setString(15, shoppingCar.device.name);

            ps.setInt(16, shoppingCar.user.id);

            ps.setInt(17, shoppingCar.osVersion.id);
            ps.setString(18, shoppingCar.osVersion.name);


            ps.setInt(19, shoppingCar.resolution.id);
            ps.setString(20, shoppingCar.resolution.name);

            ps.setDate(21, shoppingCar.date.dateValue);
            ps.setInt(22, shoppingCar.date.dateInteger);
            ps.setInt(23, shoppingCar.date.year);
            ps.setInt(24, shoppingCar.date.month);
            ps.setInt(25, shoppingCar.date.day);
            ps.setInt(26, shoppingCar.date.quarter);
            ps.setString(27, shoppingCar.date.quarterName);
            ps.setInt(28, shoppingCar.date.dayOfYear);
            ps.setInt(29, shoppingCar.date.dayOfWeek);
            ps.setInt(30, shoppingCar.date.weekOfYear);
            ps.setInt(31, shoppingCar.dateHour);
            ps.setInt(32, shoppingCar.dateMinute);
            ps.setInt(33, shoppingCar.dateSecond);


            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excecuteDiscadedShoppingCarM2(ShoppingCar shoppingCar, Connection connection) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into discarted_shopping_cars_m2 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, shoppingCar.id);
            ps.setDouble(2, shoppingCar.total_after_discounts);
            ps.setDouble(3, shoppingCar.total_discount);
            ps.setString(4, shoppingCar.status);
            ps.setInt(5, shoppingCar.longitude);
            ps.setInt(6, shoppingCar.latitude);

            ps.setInt(7, shoppingCar.store.id);
            ps.setString(8, shoppingCar.store.name);
            ps.setString(9, shoppingCar.store.city);
            ps.setString(10, shoppingCar.store.state);
            ps.setString(11, shoppingCar.store.country);

            ps.setInt(12, shoppingCar.platform.id);
            ps.setString(13, shoppingCar.platform.name);

            ps.setInt(14, shoppingCar.device.id);
            ps.setString(15, shoppingCar.device.name);

            ps.setInt(16, shoppingCar.user.id);

            ps.setInt(17, shoppingCar.osVersion.id);
            ps.setString(18, shoppingCar.osVersion.name);


            ps.setInt(19, shoppingCar.resolution.id);
            ps.setString(20, shoppingCar.resolution.name);

            ps.setDate(21, shoppingCar.date.dateValue);
            ps.setInt(22, shoppingCar.date.dateInteger);
            ps.setInt(23, shoppingCar.date.year);
            ps.setInt(24, shoppingCar.date.month);
            ps.setInt(25, shoppingCar.date.day);
            ps.setInt(26, shoppingCar.date.quarter);
            ps.setString(27, shoppingCar.date.quarterName);
            ps.setInt(28, shoppingCar.date.dayOfYear);
            ps.setInt(29, shoppingCar.date.dayOfWeek);
            ps.setInt(30, shoppingCar.date.weekOfYear);
            ps.setInt(31, shoppingCar.dateHour);
            ps.setInt(32, shoppingCar.dateMinute);
            ps.setInt(33, shoppingCar.dateSecond);


            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excecuteSoldShoppingCarM2(ShoppingCar shoppingCar, Connection connection) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into sold_shopping_cars_m2 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, shoppingCar.id);
            ps.setDouble(2, shoppingCar.total_after_discounts);
            ps.setDouble(3, shoppingCar.total_discount);
            ps.setString(4, shoppingCar.status);
            ps.setInt(5, shoppingCar.longitude);
            ps.setInt(6, shoppingCar.latitude);

            ps.setInt(7, shoppingCar.store.id);
            ps.setString(8, shoppingCar.store.name);
            ps.setString(9, shoppingCar.store.city);
            ps.setString(10, shoppingCar.store.state);
            ps.setString(11, shoppingCar.store.country);

            ps.setInt(12, shoppingCar.platform.id);
            ps.setString(13, shoppingCar.platform.name);

            ps.setInt(14, shoppingCar.device.id);
            ps.setString(15, shoppingCar.device.name);

            ps.setInt(16, shoppingCar.user.id);

            ps.setInt(17, shoppingCar.osVersion.id);
            ps.setString(18, shoppingCar.osVersion.name);


            ps.setInt(19, shoppingCar.resolution.id);
            ps.setString(20, shoppingCar.resolution.name);

            ps.setDate(21, shoppingCar.date.dateValue);
            ps.setInt(22, shoppingCar.date.dateInteger);
            ps.setInt(23, shoppingCar.date.year);
            ps.setInt(24, shoppingCar.date.month);
            ps.setInt(25, shoppingCar.date.day);
            ps.setInt(26, shoppingCar.date.quarter);
            ps.setString(27, shoppingCar.date.quarterName);
            ps.setInt(28, shoppingCar.date.dayOfYear);
            ps.setInt(29, shoppingCar.date.dayOfWeek);
            ps.setInt(30, shoppingCar.date.weekOfYear);
            ps.setInt(31, shoppingCar.dateHour);
            ps.setInt(32, shoppingCar.dateMinute);
            ps.setInt(33, shoppingCar.dateSecond);


            ps.setString(34,shoppingCar.address);

            ps.setDate(35, shoppingCar.transactionDate.dateValue);
            ps.setInt(36, shoppingCar.transactionDate.dateInteger);
            ps.setInt(37, shoppingCar.transactionDate.year);
            ps.setInt(38, shoppingCar.transactionDate.month);
            ps.setInt(39, shoppingCar.transactionDate.day);
            ps.setInt(40, shoppingCar.transactionDate.quarter);
            ps.setString(41, shoppingCar.transactionDate.quarterName);
            ps.setInt(42, shoppingCar.transactionDate.dayOfYear);
            ps.setInt(43, shoppingCar.transactionDate.dayOfWeek);
            ps.setInt(44, shoppingCar.transactionDate.weekOfYear);
            ps.setInt(45, shoppingCar.transactionHour);
            ps.setInt(46, shoppingCar.transactionMinute);
            ps.setInt(47, shoppingCar.transactionSecond);

            ps.setString(48,shoppingCar.deliverySlot);
            ps.setInt(49,shoppingCar.delivery.id);
            ps.setString(50,shoppingCar.delivery.name);


            ps.setDate(51, shoppingCar.deliveryDate.dateValue);
            ps.setInt(52, shoppingCar.deliveryDate.dateInteger);
            ps.setInt(53, shoppingCar.deliveryDate.year);
            ps.setInt(54, shoppingCar.deliveryDate.month);
            ps.setInt(55, shoppingCar.deliveryDate.day);
            ps.setInt(56, shoppingCar.deliveryDate.quarter);
            ps.setString(57, shoppingCar.deliveryDate.quarterName);
            ps.setInt(58, shoppingCar.deliveryDate.dayOfYear);
            ps.setInt(59, shoppingCar.deliveryDate.dayOfWeek);
            ps.setInt(60, shoppingCar.deliveryDate.weekOfYear);
            ps.setInt(61, shoppingCar.deliveryHour);
            ps.setInt(62, shoppingCar.deliveryMinute);
            ps.setInt(63, shoppingCar.deliverySecond);

            ps.setInt(64, shoppingCar.paymentMethod.id);
            ps.setString(65, shoppingCar.paymentMethod.name);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excecuteProgressShoppingCarM1(ShoppingCar shoppingCar, Connection connection) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into progress_shopping_cars_m1 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, shoppingCar.id);
            ps.setDouble(2, shoppingCar.total_after_discounts);
            ps.setDouble(3, shoppingCar.total_discount);
            ps.setString(4, shoppingCar.status);
            ps.setInt(5, shoppingCar.longitude);
            ps.setInt(6, shoppingCar.latitude);
            ps.setInt(7, shoppingCar.store.id);
            ps.setInt(8, shoppingCar.user.platform.id);
            ps.setInt(9, shoppingCar.user.device.id);
            ps.setInt(10, shoppingCar.user.id);
            ps.setInt(11, shoppingCar.user.osVersion.id);
            ps.setInt(12, shoppingCar.user.resolution.id);
            ps.setInt(13, shoppingCar.date.id);
            ps.setInt(14, shoppingCar.dateHour);
            ps.setInt(15, shoppingCar.dateMinute);
            ps.setInt(16, shoppingCar.dateSecond);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excecuteDiscadedShoppingCarM1(ShoppingCar shoppingCar, Connection connection) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into discarted_shopping_cars_m1 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, shoppingCar.id);
            ps.setDouble(2, shoppingCar.total_after_discounts);
            ps.setDouble(3, shoppingCar.total_discount);
            ps.setString(4, shoppingCar.status);
            ps.setInt(5, shoppingCar.longitude);
            ps.setInt(6, shoppingCar.latitude);
            ps.setInt(7, shoppingCar.store.id);
            ps.setInt(8, shoppingCar.user.platform.id);
            ps.setInt(9, shoppingCar.user.device.id);
            ps.setInt(10, shoppingCar.user.id);
            ps.setInt(11, shoppingCar.user.osVersion.id);
            ps.setInt(12, shoppingCar.user.resolution.id);
            ps.setInt(13, shoppingCar.date.id);
            ps.setInt(14, shoppingCar.dateHour);
            ps.setInt(15, shoppingCar.dateMinute);
            ps.setInt(16, shoppingCar.dateSecond);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excecuteSoldShoppingCarM1(ShoppingCar shoppingCar, Connection connection) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into sold_shopping_cars_m1 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, shoppingCar.id);
            ps.setDouble(2, shoppingCar.total_after_discounts);
            ps.setDouble(3, shoppingCar.total_discount);
            ps.setString(4, shoppingCar.status);
            ps.setInt(5, shoppingCar.longitude);
            ps.setInt(6, shoppingCar.latitude);
            ps.setInt(7, shoppingCar.store.id);
            ps.setInt(8, shoppingCar.user.platform.id);
            ps.setInt(9, shoppingCar.user.device.id);
            ps.setInt(10, shoppingCar.user.id);
            ps.setInt(11, shoppingCar.user.osVersion.id);
            ps.setInt(12, shoppingCar.user.resolution.id);
            ps.setInt(13, shoppingCar.date.id);
            ps.setInt(14, shoppingCar.dateHour);
            ps.setInt(15, shoppingCar.dateMinute);
            ps.setInt(16, shoppingCar.dateSecond);

            ps.setString(17, shoppingCar.address);
            ps.setInt(18, shoppingCar.transactionDate.id);
            ps.setInt(19, shoppingCar.transactionHour);
            ps.setInt(20, shoppingCar.transactionMinute);
            ps.setInt(21, shoppingCar.transactionSecond);
            ps.setString(22, shoppingCar.deliverySlot);
            ps.setInt(23, shoppingCar.delivery.id);
            ps.setInt(24, shoppingCar.deliveryDate.id);
            ps.setInt(25, shoppingCar.deliveryHour);
            ps.setInt(26, shoppingCar.deliveryMinute);
            ps.setInt(27, shoppingCar.deliverySecond);
            ps.setInt(28, shoppingCar.paymentMethod.id);

            ps.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getStatus() {
        Random random = new Random();
        String status = "P";
        switch (random.nextInt(3)) {
            case 0:
                status = "PROGRESS";
                break;
            case 1:
                status = "SOLD";
                break;
            case 2:
                status = "DISCARDED";
                break;
        }
        return status;
    }

    public void excuteStoresQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (Store store : this.stores) {
                PreparedStatement ps = connection.prepareStatement("insert into stores_m1 VALUES (?,?)");
                ps.setInt(1, store.id);
                ps.setString(2, store.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excutePlatformsQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (Platform platform : this.platforms) {
                PreparedStatement ps = connection.prepareStatement("insert into platforms_m1 VALUES (?,?)");
                ps.setInt(1, platform.id);
                ps.setString(2, platform.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excuteDevicesQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (Device device : this.devices) {
                PreparedStatement ps = connection.prepareStatement("insert into devices_m1 VALUES (?,?)");
                ps.setInt(1, device.id);
                ps.setString(2, device.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excuteOsVersionsQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (OsVersion osVersion : this.osVersions) {
                PreparedStatement ps = connection.prepareStatement("insert into os_versions_m1 VALUES (?,?)");
                ps.setInt(1, osVersion.id);
                ps.setString(2, osVersion.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excuteResolutionsQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (Resolution resolution : this.resolutions) {
                PreparedStatement ps = connection.prepareStatement("insert into resolutions_m1 VALUES (?,?)");
                ps.setInt(1, resolution.id);
                ps.setString(2, resolution.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excuteDeliveriesQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (Delivery delivery : this.deliveries) {
                PreparedStatement ps = connection.prepareStatement("insert into delivery_type_m1 VALUES (?,?)");
                ps.setInt(1, delivery.id);
                ps.setString(2, delivery.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excutePaymentsQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (PaymentMethod paymentMethod : this.paymentMethods) {
                PreparedStatement ps = connection.prepareStatement("insert into payment_methods_m1 VALUES (?,?)");
                ps.setInt(1, paymentMethod.id);
                ps.setString(2, paymentMethod.name);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excuteUsersQueryM1() {
        try {
            Connection connection = getConnectionM1();
            for (User user : this.users) {
                PreparedStatement ps = connection.prepareStatement("insert into users_m1 VALUES (?,?,?,?,?,?,?)");
                ps.setInt(1, user.id);
                ps.setString(2, user.firstName);
                ps.setString(3, user.lastName);
                ps.setString(4, user.email);
                ps.setString(5, user.phone);
                ps.setString(6, user.sexo);
                ps.setDate(7, user.birthDay);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void excuteUsersQueryM2() {
        try {
            Connection connection = getConnectionM1();
            for (User user : this.users) {
                PreparedStatement ps = connection.prepareStatement("insert into users_m2 VALUES (?,?,?,?,?,?,?)");
                ps.setInt(1, user.id);
                ps.setString(2, user.firstName);
                ps.setString(3, user.lastName);
                ps.setString(4, user.email);
                ps.setString(5, user.phone);
                ps.setString(6, user.sexo);
                ps.setDate(7, user.birthDay);
                ps.executeUpdate();
            }
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void excuteDateQueryM1(DateInfo dateInfo) {

        try {
            Connection connection = getConnectionM1();
            try {
                PreparedStatement ps = connection.prepareStatement("insert into date_info_m1 VALUES (?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, dateInfo.id);
                ps.setDate(2, dateInfo.dateValue);
                ps.setInt(3, dateInfo.year);
                ps.setInt(4, dateInfo.month);
                ps.setInt(5, dateInfo.day);
                ps.setInt(6, dateInfo.quarter);
                ps.setString(7, dateInfo.quarterName);
                ps.setInt(8, dateInfo.dayOfYear);
                ps.setInt(9, dateInfo.dayOfWeek);
                ps.setInt(10, dateInfo.weekOfYear);
                ps.executeUpdate();
            } catch (Exception e) {
              //  e.printStackTrace();
            }
            connection.close();
        } catch (Exception e) {

        }
    }

    public Connection getConnectionM1() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/testbi", "postgres", "secret");
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    public Connection getConnectionM2() {
        try {
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/testbi", "postgres", "secret");
        }catch (Exception e){
            throw  new RuntimeException(e);
        }
    }
}
