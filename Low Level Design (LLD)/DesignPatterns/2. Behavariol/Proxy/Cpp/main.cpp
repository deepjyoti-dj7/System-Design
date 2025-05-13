#include "include/ProxyInternet.h"

int main() {
    Internet* internet = new ProxyInternet();

    internet->connectTo("example.com");
    internet->connectTo("abc.com");

    delete internet;
    return 0;
}
