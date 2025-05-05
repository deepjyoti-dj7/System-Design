package AbstractFactoryPattern.Factory;

import AbstractFactoryPattern.Computer;

/** this will provide the entry point for the client classes to create subclasses */
public class ComputerFactory {
    public static Computer getComputer(ComputerAbstractFactory factory) {
        return factory.createComputer();
    }
}