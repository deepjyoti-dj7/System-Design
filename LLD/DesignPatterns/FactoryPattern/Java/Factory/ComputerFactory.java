package DesignPatterns.FactoryPattern.Factory;

import DesignPatterns.FactoryPattern.Computer;
import DesignPatterns.FactoryPattern.PC;
import DesignPatterns.FactoryPattern.Server;

public class ComputerFactory {
    public static Computer getComputer(String type, String ram, String hdd, String cpu) {
        if ("PC".equals(type)){
            return new PC(ram, hdd, cpu);
        }
        else if ("SERVER".equals(type)) {
            return new Server(ram, hdd, cpu);
        }
        return null;
    }
}
