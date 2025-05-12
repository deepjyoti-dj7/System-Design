#include "include/HomeTheaterFacade.h"

int main() {
    DVDPlayer dvdPlayer;
    Projector projector;
    SoundSystem soundSystem;

    HomeTheaterFacade homeTheater(&dvdPlayer, &projector, &soundSystem);

    homeTheater.watchMovie("Inception");
    homeTheater.endMovie();

    return 0;
}
