#include "../include/DVDPlayer.h"
#include <iostream>

void DVDPlayer::on() {
    std::cout << "DVD Player is ON" << std::endl;
}

void DVDPlayer::play(const std::string& movie) {
    std::cout << "Playing movie: " << movie << std::endl;
}

void DVDPlayer::off() {
    std::cout << "DVD Player is OFF" << std::endl;
}
