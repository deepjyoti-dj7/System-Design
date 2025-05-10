#include "../include/AsusDesktopBuilder.hpp"

// Build the monitor
void AsusDesktopBuilder::buildMonitor() {
    desktop->setMonitor("Asus 24-inch 1080 144hz LED");
};

// Build the mouse
void AsusDesktopBuilder::buildMouse() {
    desktop->setMouse("Asus Gaming Mouse");
};

// Build the keyboard
void AsusDesktopBuilder::buildKeyboard() {
    desktop->setKeyboard("Asus Mechanical Keyboard");
};

// Build the speaker
void AsusDesktopBuilder::buildSpeaker() {
    desktop->setSpeaker("Asus SonicMaster");
};

// Build the RAM
void AsusDesktopBuilder::buildRam() {
    desktop->setRam("16GB DDR4");
};

// Build the processor
void AsusDesktopBuilder::buildProcessor() {
    desktop->setProcessor("AMD Ryzen 9");
};

// Build the motherboard
void AsusDesktopBuilder::buildMotherboard() {
    desktop->setMotherboard("Asus TUF 15");
};

// Build the graphics card
void AsusDesktopBuilder::buildGraphicsCard() {
    desktop->setGraphicsCard("Nvidia RTX 4050");
};
