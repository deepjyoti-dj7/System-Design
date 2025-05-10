#include "../include/LenovoDesktopBuilder.hpp"

// Build the monitor
void LenovoDesktopBuilder::buildMonitor() {
    desktop->setMonitor("Lenovo 1440p 27-inch 144hz IPS");
};

// Build the mouse
void LenovoDesktopBuilder::buildMouse() {
    desktop->setMouse("Lenovo Ergonomic Mouse");
};

// Build the keyboard
void LenovoDesktopBuilder::buildKeyboard() {
    desktop->setKeyboard("Lenovo Mechanical Keyboard");
};

// Build the speaker
void LenovoDesktopBuilder::buildSpeaker() {
    desktop->setSpeaker("Dolby Audio Speakers");
};

// Build the RAM
void LenovoDesktopBuilder::buildRam() {
    desktop->setRam("24GB DDR5");
};

// Build the processor
void LenovoDesktopBuilder::buildProcessor() {
    desktop->setProcessor("Intel i7 13650hx");
};

// Build the motherboard
void LenovoDesktopBuilder::buildMotherboard() {
    desktop->setMotherboard("Lenovo Legion 5");
};

// Build the graphics card
void LenovoDesktopBuilder::buildGraphicsCard() {
    desktop->setGraphicsCard("Nvidia RTX 4060");
};
