#include "../include/ComputerFactory.h"
#include "../include/PC.h"
#include "../include/Server.h"

std::shared_ptr<Computer> ComputerFactory::getComputer(const std::string& type, 
                                                       const std::string& ram,
                                                       const std::string& hdd,
                                                       const std::string& cpu) {
    if (type == "PC") {
        return std::make_shared<PC>(ram, hdd, cpu);
    } else if (type == "SERVER") {
        return std::make_shared<Server>(ram, hdd, cpu);
    }
    return nullptr;
}
