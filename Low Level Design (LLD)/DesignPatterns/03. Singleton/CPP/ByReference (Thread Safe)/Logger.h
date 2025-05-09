#pragma once
#include <mutex>
#include <iostream>

class Logger {
private: 
    static int counter;
    Logger();

public:
    Logger(const Logger&) = delete;
    Logger operator=(const Logger&) = delete;
    static Logger& getLogger();
    void Log(std::string message);
};