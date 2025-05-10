#pragma once

#include "DesktopBuilder.hpp"

class LenovoDesktopBuilder : public DesktopBuilder {
    void buildMonitor();
    void buildMouse();
    void buildKeyboard();
    void buildSpeaker();
    void buildRam();
    void buildProcessor();
    void buildMotherboard();
};