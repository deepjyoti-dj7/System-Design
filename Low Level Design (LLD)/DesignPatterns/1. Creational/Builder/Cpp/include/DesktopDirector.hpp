#pragma once

#include "DesktopBuilder.hpp"

class DesktopDirector {
private:
    DesktopBuilder* desktopBuilder;
public:
    DesktopDirector(DesktopBuilder* aDesktopBuilder) {
        desktopBuilder = aDesktopBuilder;
    }
    Desktop* BuildDesktop() {
        desktopBuilder->buildMonitor();
        desktopBuilder->buildMouse();
        desktopBuilder->buildKeyboard();
        desktopBuilder->buildSpeaker();
        desktopBuilder->buildProcessor();
        desktopBuilder->buildRam();
        desktopBuilder->buildMotherboard();
        return desktopBuilder->getDesktop();
    }
};