#include "../include/HomeTheaterFacade.h"
#include <iostream>

HomeTheaterFacade::HomeTheaterFacade(DVDPlayer* dvd, Projector* proj, SoundSystem* sound)
    : dvdPlayer(dvd), projector(proj), soundSystem(sound) {}

void HomeTheaterFacade::watchMovie(const std::string& movie) {
    std::cout << "\nPreparing to watch movie...\n" << std::endl;
    projector->on();
    projector->setInput("DVD");
    soundSystem->on();
    soundSystem->setVolume(10);
    dvdPlayer->on();
    dvdPlayer->play(movie);
}

void HomeTheaterFacade::endMovie() {
    std::cout << "\nShutting down movie theater...\n" << std::endl;
    dvdPlayer->off();
    soundSystem->off();
    projector->off();
}
