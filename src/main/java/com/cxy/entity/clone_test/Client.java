package com.cxy.entity.clone_test;

public class Client {

    public static void main(String [] args) throws CloneNotSupportedException {

        Person person1 = new Person();
        person1.setAge(10);
        person1.setName("cxy");
        person1.getList().add("aaa");


        //浅拷贝
//        Person person2 =  person1.clone();

        //深拷贝
        Person person2 =  (Person) person1.deepClone();
        person2.setName("czz");
        person2.getList().add("ccc");

        System.out.println("person1="+person1.getName()+", age="+person1.getAge());
        System.out.println("person2="+person2.getName()+", age="+person2.getAge());

    }
}
