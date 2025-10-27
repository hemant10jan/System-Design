package Practise;

abstract class Shape{
    abstract int area();
}

class Rectangle extends Shape{
    private int length;
    private int breadh;

    public Rectangle(int length,int breadh){
        this.length=length;
        this.breadh=breadh;
    }
    @Override
    public int area(){
        return length*breadh;
    }
}

class AreaCalculator{
    public int calculate(Shape shape){
        return shape.area();
    }
}

class Square extends Shape{
    private int side;

    public Square(int side){
        this.side=side;
    }
    @Override
    public int area(){
        return side*side;
    }
}

class Main{
    public static void main(String[] args){
        Rectangle r=new Rectangle(10,20);
        Square s=new Square(10);
        AreaCalculator a=new AreaCalculator();

        System.out.println();
    }
}
