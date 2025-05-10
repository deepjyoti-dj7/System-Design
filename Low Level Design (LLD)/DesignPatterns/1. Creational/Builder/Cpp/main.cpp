#include "./include/LenovoDesktopBuilder.hpp"
#include "./include/AsusDesktopBuilder.hpp"
#include "./include/DesktopDirector.hpp"

int main() {
    LenovoDesktopBuilder* lenovoDesktopBuilder = new LenovoDesktopBuilder();
    AsusDesktopBuilder* asusDesktopBuilder = new AsusDesktopBuilder();

    DesktopDirector* director1 = new DesktopDirector(lenovoDesktopBuilder);
    DesktopDirector* director2 = new DesktopDirector(asusDesktopBuilder);

    Desktop* desktop1 = director1->BuildDesktop();
    Desktop* desktop2 = director2->BuildDesktop();

    desktop1->showSpecifications();
    desktop2->showSpecifications();

    return 0;
}