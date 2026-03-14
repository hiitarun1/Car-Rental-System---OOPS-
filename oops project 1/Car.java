import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car{
    private String carId;
    private String brand;
    private String model;
    private double basePrice;
    private boolean isAvailable;
    public Car(String carId, String brand, String model, double basePrice, boolean isAvailable) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePrice = basePrice;
        this.isAvailable = isAvailable;
    }
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentDays){
        return rentDays*basePrice;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent(){
        isAvailable=false;
    }

    public void returnCar(){
        isAvailable=true;
    }
}

class Customer{
    private String customerId;
    private String name;

    public Customer(String customerId, String name){
        this.customerId=customerId;
        this.name=name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental{
    private Car car;
    private Customer customer;
    private int days;
    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    public Car getCar() {
        return car;
    }
    public Customer getCustomer() {
        return customer;
    }
    public int getDays() {
        return days;
    }
}

class CarRentalSystem{
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem(){
        cars=new ArrayList<>();
        customers=new ArrayList<>();
        rentals=new ArrayList<>();
    }

    public void addCar(Car car){
        cars.add(car);
    }

    public void addCustomer(Customer cust){
        customers.add(cust);
    }
    
    public void rentCar(Car car, Customer cust, int days){
        if(car.isAvailable()){
            car.rent();
            rentals.add(new Rental(car, cust, days));
        }else{
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car){
        car.returnCar();
        Rental removeRental=null;
        for(Rental rental : rentals){
            if(rental.getCar()==car){
                removeRental=rental;
                break;
            }
        }
        if(removeRental!=null){
            rentals.remove(removeRental);
        }else{
            System.out.println("Car was not rented");
        }
    }

    public void menu(){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("****** Car Rental System ******");
            System.out.println("1.Rent a Car");
            System.out.println("2.Return a Car");
            System.out.println("3.Exit");
            System.out.println("Enter your choice: ");
            int ch=sc.nextInt();
            sc.nextLine();
            switch(ch){

                case 1: System.out.println("**** Rent a Car ****");
                System.out.println("Enter your Name: ");
                String customerName=sc.nextLine();
                System.out.println("\nAvailable Cars");
                for(Car car : cars){
                    if(car.isAvailable()){
                        System.out.println(car.getCarId()+" - "+car.getBrand()+" - "+" "+car.getModel());
                    }
                }
                System.out.println("Enter the carId you want to rent: ");
                String carId=sc.nextLine();
                System.out.println("Enter the number of days of rental: ");
                int days=sc.nextInt();
                sc.nextLine();
                Customer customer=new Customer("CUS"+(customers.size()+1),customerName);
                addCustomer(customer);
                Car selectedCar=null;
                for(Car car:cars){
                    if(car.getCarId().equals(carId) && car.isAvailable()){
                        selectedCar=car;
                        break;
                    }
                }

                if(selectedCar !=null){
                    double totalPrice=selectedCar.calculatePrice(days);
                    System.out.println("\nRental Information");
                    System.out.println("Customer Id: "+customer.getCustomerId());
                    System.out.println("Customer Name: "+customer.getName());
                    System.out.println("Car: "+selectedCar.getBrand()+" "+selectedCar.getModel());
                    System.out.println("Rental days: "+days);
                    System.out.printf("Total Price: $%.2f%n",totalPrice);

                    System.out.println("Confirm rental (y/n): ");
                    String confirm=sc.nextLine();
                    if(confirm.toLowerCase().equals("y")){
                        rentCar(selectedCar, customer, days);
                        System.out.println("Car Rented Successfully");
                    }else{
                        System.out.println("\n Rental Cancelled");
                    }
                }else{
                    System.out.println("\nInvalid car selection or car not available to rent. ");
                }
                break;
                case 2: System.out.println("\n** Return a Car **");
                System.out.println("Enter carID you want to return: ");
                String carid=sc.nextLine();

                Car returnCar=null;
                for(Car car : cars){
                    if(car.getCarId().equals(carid) && !car.isAvailable()){
                        returnCar=car;
                        break;
                    }
                }
                if(returnCar!=null){
                    returnCar(returnCar);
                    System.out.println("Car returned successfully");
                }else{
                    System.out.println("Car was not rented or rental information is missing");
                }
                break;

                case 3: System.out.println("Thankyou for choosing Car Rental System.");
                        return;

                default:System.out.println("Invalid choice, Please enter a valid choice");
            }
        }
    }
}

class Main{
    public static void main(String[] args) {
        CarRentalSystem carRentalSystem=new CarRentalSystem();
        Car car1=new Car("C001", "Honda", "Civic", 80.0, true);
        Car car2=new Car("C002", "Hundai", "i20", 30.0, true);
        Car car3=new Car("C003", "Maruti", "celerio", 100.0, true);

        carRentalSystem.addCar(car1);
        carRentalSystem.addCar(car2);
        carRentalSystem.addCar(car3);

        carRentalSystem.menu();
        
    }
}