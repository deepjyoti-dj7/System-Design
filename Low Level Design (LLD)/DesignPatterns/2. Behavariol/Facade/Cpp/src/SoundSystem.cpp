#include "../include/SoundSystem.h"
#include <iostream>

void SoundSystem::on() {
    std::cout << "Sound system is ON" << std::endl;
}

void SoundSystem::setVolume(int level) {
    std::cout << "Sound volume set to " << level << std::endl;
}

void SoundSystem::off() {
    std::cout << "Sound system is OFF" << std::endl;
}
