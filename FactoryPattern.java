package Practise;

interface Burger{
    void prepare();
}

class Standard implements Burger{
    @Override
    public void prepare(){
        System.out.println("Standard Burger is prepared.");
    }
}

class Classic implements Burger{
    @Override
    public void prepare(){
        System.out.println("Classic Burger is prepared.");
    }
}

class Premium implements Burger{
    @Override
    public void prepare(){
        System.out.println("Premium Burger is prepared.");
    }
}

class StandardWheat implements Burger{
    @Override
    public void prepare(){
        System.out.println("StandardWheat Burger is prepared.");
    }
}

class ClassicWheat implements Burger{
    @Override
    public void prepare(){
        System.out.println("ClassicWheat Burger is prepared.");
    }
}

class PremiumWheat implements Burger{
    @Override
    public void prepare(){
        System.out.println("PremiumWheat Burger is prepared.");
    }
}

interface Factory{
    Burger getBurger(String type);
}

class kingBurferFactory implements Factory{
    public Burger getBurger(String type){
        if(type.equals("Standard")){
            return new Standard();
        }
        else if(type.equals("Classic")){
            return new Classic();
        }
        else if(type.equals("Premium")){
            return new Premium();
        }
        else{
            return null;
        }
    }
}

class singhBurferFactory implements Factory{
    public Burger getBurger(String type){
        if(type.equals("StandardWheat")){
            return new StandardWheat();
        }
        else if(type.equals("ClassicWheat")){
            return new ClassicWheat();
        }
        else if(type.equals("PremiumWheat")){
            return new PremiumWheat();
        }
        else{
            return null;
        }
    }
}

class FactoryMain {
    public static void main(String[] args){
        Factory f1=new kingBurferFactory();
        Factory f2=new singhBurferFactory();

        Burger b1=f1.getBurger("Classic");
        b1.prepare();

        Burger b2=f2.getBurger("PremiumWheat");
        b2.prepare();
    }
}
