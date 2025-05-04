package FactoryPattern;

import FactoryPattern.factory.ComputerFactory;

public class Main {
    public static void main(String[] args) {
        testFactoryPattern();
    }

    public static void testFactoryPattern() {
        Computer pc = ComputerFactory.getComputer("PC", "4 GB", "1 TB", "2.5 GHz");
        Computer server = ComputerFactory.getComputer("SERVER", "16 GB", "2 TB", "3.0 GHz");

        System.out.println("Factory Pattern PC Config::" + pc);
        System.out.println("Factory Pattern Server Config::" + server);
    }
}
