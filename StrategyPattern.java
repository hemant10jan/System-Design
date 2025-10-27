package Practise;


interface PaymentStrategy{
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Paid " + amount + " amount via credit card payment way.");
    }
}

class UPIPayment implements PaymentStrategy{
    public void pay(int amount){
        System.out.println("Paid "+amount+ "amount via UPI payment way.");
    }
}

class ShoppingCart{
    PaymentStrategy p;

    public void setPaymentStrategy(PaymentStrategy p){
        this.p=p;
    }

    public void checkout(int amount){
        p.pay(amount);
    }
}

class StrategyMain{
    public static void main(String[] args){
        ShoppingCart sc=new ShoppingCart();
        sc.setPaymentStrategy(new CreditCardPayment());
        sc.checkout(100);
    }
}


