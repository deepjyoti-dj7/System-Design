#pragma once
#include <string>
#include "DVDPlayer.h"
#include "Projector.h"
#include "SoundSystem.h"

class HomeTheaterFacade {
private:
    DVDPlayer* dvdPlayer;
    Projector* projector;
    SoundSystem* soundSystem;

public:
    HomeTheaterFacade(DVDPlayer* dvd, Projector* proj, SoundSystem* sound);
    void watchMovie(const std::string& movie);
    void endMovie();
};
