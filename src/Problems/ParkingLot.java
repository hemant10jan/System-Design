package Problems;

import java.util.*;

enum VehicleType{
    Bike,Car,Truck
}

enum SpotType{
    small,medium,large
}

class Vehicle{
    private String number;
    private VehicleType type;

    public Vehicle(String number,VehicleType type){
        this.number=number;
        this.type=type;
    }

    public VehicleType getType(){
        return this.type;
    }

    public String getNumber(){
        return number;
    }
}

class ParkingSpot{
    private int id;
    private SpotType type;
    private Vehicle vehicle;
    private boolean isOccupied;

    public ParkingSpot(int id, SpotType type) {
        this.id = id;
        this.type = type;
        this.isOccupied = false;
    }

    public boolean canFitVehicle(Vehicle vehicle){
        if(vehicle.getType()==VehicleType.Bike){
            return true;
        }
        if(vehicle.getType()==VehicleType.Car && this.type==SpotType.medium){
            return true;
        }
        if(vehicle.getType()==VehicleType.Truck && this.type==SpotType.large){
            return true;
        }

        return false;
    }

    public void parkVehicle(Vehicle vehicle){
        this.vehicle=vehicle;
        this.isOccupied=true;
    }

    public void removeVehicle(Vehicle vehicle){
        this.vehicle=null;
        this.isOccupied=false;
    }

    public int getId(){
        return id;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public SpotType getType() {
        return type;
    }
}

class Ticket{
    private static int counter=1;
    private int ticketid;
    private Vehicle vehicle;
    private long entryTime;
    private ParkingSpot spot;

    public Ticket(Vehicle vehicle,ParkingSpot spot){
        this.ticketid=counter++;
        this.vehicle=vehicle;
        this.spot=spot;
        this.entryTime=System.currentTimeMillis();
    }

    public int getTicketid(){
        return ticketid;
    }

    public ParkingSpot getSpot(){
        return spot;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    public long getEntryTime() {
        return entryTime;
    }
}

interface PaymentStrategy {
    void pay(int amount);
}

class Cash implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid via Cash: " + amount);
    }
}

class UPI implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid via UPI: " + amount);
    }
}

class ParkingFloor{
    private int floorNumber;
    private List<ParkingSpot> spots;

    public ParkingFloor(int fn,int small,int medium,int large){
        this.floorNumber=fn;
        spots=new ArrayList<>();

        int idCounter=floorNumber*100;
        for(int i=0;i<small;i++){
            ParkingSpot ps=new ParkingSpot(idCounter++,SpotType.small);
            spots.add(ps);
        }

        for(int i=0;i<medium;i++){
            ParkingSpot ps=new ParkingSpot(idCounter++,SpotType.small);
            spots.add(ps);
        }
        for(int i=0;i<large;i++){
            ParkingSpot ps=new ParkingSpot(idCounter++,SpotType.small);
            spots.add(ps);
        }
    }

    public ParkingSpot getAvailableSpot(Vehicle vehicle){
        for(ParkingSpot spot: spots){
            if(spot.canFitVehicle(vehicle)){
                return spot;
            }
        }
        return null;
    }
}

class ParkingLot{
    private List<ParkingFloor> floors;

    public ParkingLot(int f,int small,int medium,int large){
        floors=new ArrayList<>();
        for(int i=1;i<=f;i++){
            floors.add(new ParkingFloor(i,small,medium,large));
        }
    }

    public Ticket parkVehicle(Vehicle vehicle){
        for(ParkingFloor floor: floors){
            ParkingSpot spot=floor.getAvailableSpot(vehicle);

            if(spot!=null){
                spot.parkVehicle(vehicle);
                Ticket t=new Ticket(vehicle,spot);
                System.out.println("Vehicle parked at Floor SpotID: " + spot.getId());
                return t;
            }
        }
        System.out.println("No spot available for vehicle: " + vehicle.getNumber());
        return null;
    }

    public void exitVehicle(Ticket t,PaymentStrategy strategy){
        long exitTime=System.currentTimeMillis();
        long hours=(exitTime-t.getEntryTime())/(1000*60*60);
        hours = hours == 0 ? 0 : hours;

        int rate=0;
        VehicleType vType=t.getVehicle().getType();

        if(vType==VehicleType.Bike){
            rate=10;
        }
        else if(vType==VehicleType.Car){
            rate=20;
        }
        else if(vType==VehicleType.Truck){
            rate=30;
        }

        int totalRent = (int) (rate * hours);
        strategy.pay(totalRent);

        t.getSpot().removeVehicle(t.getVehicle());
        System.out.println("Vehicle exited: " + t.getVehicle().getNumber() + " | Paid: " + totalRent);

    }
}

class Main{
    public static void main(String[] args) throws  InterruptedException{
        ParkingLot plot=new ParkingLot(2,2,2,2);

        Vehicle bike1 = new Vehicle("BIKE123", VehicleType.Bike);
        Vehicle car1 = new Vehicle("CAR123", VehicleType.Car);
        Vehicle truck1 = new Vehicle("TRUCK123", VehicleType.Truck);

        Ticket t1 = plot.parkVehicle(bike1);
        Ticket t2 = plot.parkVehicle(car1);
        Ticket t3 = plot.parkVehicle(truck1);

        Thread.sleep(2000); // simulate 2 sec parking

        plot.exitVehicle(t1, new Cash());
        plot.exitVehicle(t2, new UPI());
        plot.exitVehicle(t3, new Cash());
    }
}