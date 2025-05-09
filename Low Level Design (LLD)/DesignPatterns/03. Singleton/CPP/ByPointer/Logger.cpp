#include "./Logger.h"

int Logger::counter = 0;
Logger* Logger::loggerInstance = nullptr;
std::mutex Logger::mtx;

Logger::Logger() {
    counter++;
    std::cout << "Instance created, no of instance: " << counter << std::endl;
}

Logger* Logger::getLogger() {
    if (loggerInstance == nullptr) {
        mtx.lock();
        if (loggerInstance == nullptr) {
            loggerInstance = new Logger();
        }
        mtx.unlock();
    }
    return loggerInstance;
}

void Logger::Log(std::string message) {
    std::cout << message << std::endl;
}