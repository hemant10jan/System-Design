package Problems;


import java.util.*;

interface State{
    void insertMoney(VendingMachine machine,int amount);
    void selectProduct(VendingMachine machine,String name);
    void dispense(VendingMachine machine);
}

class Product{
    private int count;
    private String name;
    private int price;

    public Product(int count,String name,int price) {
        this.count = count;
        this.name = name;
        this.price=price;
    }

    public String getName(){
        return name;
    }

    public int getCount(){
        return count;
    }

    public int getPrice(){
        return price;
    }

    public void reduceCount(){
        if(count>0){
            count--;
        }
    }
}


class IdleState implements State{
    public void insertMoney(VendingMachine machine,int amount){
        machine.setBalance(amount);
        System.out.println("Money inserted: "+amount);
        machine.setState(new HasMoneyState());
    }

    public void selectProduct(VendingMachine machine,String name){
        System.out.println("Please insert money first!");
    }
    public void dispense(VendingMachine machine){
        System.out.println("Please insert money and then select Product.");
    }
}

class HasMoneyState implements State{
    public void insertMoney(VendingMachine machine,int amount){
        machine.setBalance(amount);
        System.out.println("Additional money added"+ amount);
    }
    public void selectProduct(VendingMachine machine,String name){
        Product product = machine.getProduct(name);
        if(product==null){
            System.out.println("Product not found.");
        }
        else if(product.getCount()==0){
            System.out.println("Product out of stock.");
        }
        else if(machine.getBalance() < product.getPrice()){
            System.out.println("Insufficient Balance");
        }
        else{
            machine.setSelectedProduct(product);
            machine.setState(new DispenseState());
            machine.dispense();
        }
    }
    public void dispense(VendingMachine machine){
        System.out.println("Select product first!");
    }
}

class DispenseState implements State{
    public void insertMoney(VendingMachine machine,int price){
        System.out.println("Please wait, dispensing in progress...");
    }
    public void selectProduct(VendingMachine machine,String name){
        System.out.println("Product already selected");
    }
    public void dispense(VendingMachine machine){
        Product product=machine.getSelectedProduct();
        product.reduceCount();

        int change=machine.getBalance()-product.getPrice();
        System.out.println("Dispensing "+product.getName());

        if(change > 0){
            System.out.println("Returning change: "+ change);
        }

        machine.resetBalance();
        machine.setSelectedProduct(null);
        machine.setState(new IdleState());

    }
}

class VendingMachine {
    private Map<String,Product> inventory;
    private State state;
    private int balance;
    private Product selectedProduct;

    public VendingMachine(){
        this.state=new IdleState();
        this.balance=0;
        this.inventory=new HashMap<>();
    }

    public void setSelectedProduct(Product p){
        this.selectedProduct=p;
    }

    public Product getSelectedProduct(){
        return this.selectedProduct;
    }

    public void addProduct(Product p){
        inventory.put(p.getName(),p);
    }

    public Product getProduct(String name){
        return inventory.get(name);
    }

    public void setBalance(int amount){
        this.balance=amount;
    }

    public void addBalance(int amount){
        this.balance+=amount;
    }

    public void resetBalance(){
        this.balance=0;
    }

    public void setState(State s){
        this.state=s;
    }

    public void insertMoney(int amount){
        state.insertMoney(this,amount);
    }

    public void selectProduct(String name) {
        state.selectProduct(this, name);
    }

    public void dispense() {
        state.dispense(this);
    }

    public int getBalance(){
        return balance;
    }



}

class MainClass{
    public static void main(String[] args){
        VendingMachine machine=new VendingMachine();
        machine.addProduct(new Product(5, "Chips", 20));
        machine.addProduct(new Product(3,"Chocolate",30));

        machine.insertMoney(60);
        machine.selectProduct("Chips");
    }
}

