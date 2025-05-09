package AbstractFactoryPattern;

import AbstractFactoryPattern.Factory.ComputerFactory;
import AbstractFactoryPattern.Factory.PCFactory;
import AbstractFactoryPattern.Factory.ServerFactory;

public class Main {

    public static void main(String[] args) {
        testAbstractFactoryPattern();
    }

    public static void testAbstractFactoryPattern() {
        Computer pc = ComputerFactory.getComputer(new PCFactory("2 GB", "500 GB", "2.4 GHz"));
        Computer server = ComputerFactory.getComputer(new ServerFactory("10 GB", "1 TB", "2.9 GHz"));

        System.out.println("Abstract Factory PC Config::" + pc);
        System.out.println("Abstract Factory Server Config::" + server);
    }
}