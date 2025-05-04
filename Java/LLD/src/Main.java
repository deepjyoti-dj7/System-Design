import DesignPatterns.AbstractFactoryPattern.Factory.ComputerFactory;
import DesignPatterns.AbstractFactoryPattern.Factory.PCFactory;
import DesignPatterns.AbstractFactoryPattern.Factory.ServerFactory;

import DesignPatterns.FactoryPattern.PC;
import DesignPatterns.FactoryPattern.Server;

public class Main {

    public static void main(String[] args) {
        // Call the test methods
        testAbstractFactoryPattern();
        testFactoryPattern();
    }

    public static void testAbstractFactoryPattern() {
        DesignPatterns.AbstractFactoryPattern.Computer pc = ComputerFactory.getComputer(new PCFactory("2 GB", "500 GB", "2.4 GHz"));
        DesignPatterns.AbstractFactoryPattern.Computer server = ComputerFactory.getComputer(new ServerFactory("10 GB", "1 TB", "2.9 GHz"));

        System.out.println("Abstract Factory PC Config::" + pc);
        System.out.println("Abstract Factory Server Config::" + server);
    }

    public static void testFactoryPattern() {
        DesignPatterns.FactoryPattern.Computer pc = new PC("4 GB", "1 TB", "2.5 GHz");
        DesignPatterns.FactoryPattern.Computer server = new Server("16 GB", "2 TB", "3.0 GHz");

        System.out.println("Factory Pattern PC Config::" + pc);
        System.out.println("Factory Pattern Server Config::" + server);
    }
}