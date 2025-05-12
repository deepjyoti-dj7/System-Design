#pragma once
#include <string>

class DVDPlayer {
public:
    void on();
    void play(const std::string& movie);
    void off();
};
