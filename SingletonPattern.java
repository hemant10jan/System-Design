package Practise;


class Singleton{
    private static Singleton instance=null;
    private Singleton(){

    }
    public static synchronized Singleton getInstance(){
        if(instance==null){
            instance=new Singleton();
        }
        return instance;
    }
}

class MyThread extends Thread{
    @Override
    public void run(){
        System.out.println(Singleton.getInstance());
    }
}

class SingletonMain{
    public static void main(String[] args){
        MyThread t1=new MyThread();
        MyThread t2=new MyThread();
        t1.start();
        t2.start();
    }
}
