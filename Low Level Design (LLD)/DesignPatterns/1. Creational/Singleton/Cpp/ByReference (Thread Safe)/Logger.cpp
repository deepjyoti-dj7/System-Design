#include "./Logger.h"

int Logger::counter = 0;

Logger::Logger() {
    counter++;
    std::cout << "Instance created, no of instance: " << counter << std::endl;
}

Logger& Logger::getLogger() {
    static Logger loggerInstance;
    return loggerInstance;
}

void Logger::Log(std::string message) {
    std::cout << message << std::endl;
}