#include "../include/Server.h"

Server::Server(const std::string& ram, const std::string& hdd, const std::string& cpu) 
    : ram(ram), hdd(hdd), cpu(cpu) {}

std::string Server::getRAM() const {
    return ram;
}

std::string Server::getHDD() const {
    return hdd;
}

std::string Server::getCPU() const {
    return cpu;
}