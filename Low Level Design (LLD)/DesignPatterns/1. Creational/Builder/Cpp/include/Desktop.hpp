#pragma once

#include <iostream>
#include <string>

class Desktop {
private:
    std::string monitor;
    std::string mouse;
    std::string keyboard;
    std::string speaker;
    std::string ram;
    std::string processor;
    std::string motherboard;

public: 
    void setMonitor(std::string aMonitor);
    void setMouse(std::string aMouse);
    void setKeyboard(std::string aKeyboard);
    void setSpeaker(std::string aSpeaker);
    void setRam(std::string aRam);
    void setProcessor(std::string aProcessor);
    void setMotherboard(std::string aMotherboard);
    void showSpecifications();
};