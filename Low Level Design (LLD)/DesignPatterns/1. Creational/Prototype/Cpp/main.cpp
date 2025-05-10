// main.cpp
#include "TShirt.h"

int main() {
    TShirt* baseTShirt = new TShirt("M", "Black");

    TShirt* customer1Shirt = static_cast<TShirt*>(baseTShirt->clone());
    TShirt* customer2Shirt = static_cast<TShirt*>(baseTShirt->clone());

    customer1Shirt->setColor("Red");
    customer2Shirt->setSize("L");

    baseTShirt->showDetails();
    customer1Shirt->showDetails();
    customer2Shirt->showDetails();

    // Clean up
    delete baseTShirt;
    delete customer1Shirt;
    delete customer2Shirt;

    return 0;
}
