package DesignPatterns.AbstractFactoryPattern.Factory;

import DesignPatterns.AbstractFactoryPattern.Computer;
import DesignPatterns.AbstractFactoryPattern.Server;

public class ServerFactory implements ComputerAbstractFactory {

    private String ram;
    private String hdd;
    private String cpu;

    public ServerFactory(String ram, String hdd, String cpu) {
        this.ram = ram;
        this.hdd = hdd;
        this.cpu = cpu;
    }

    @Override
    public Computer createComputer() {
        return new Server(this.ram, this.hdd, this.cpu);
    }
}