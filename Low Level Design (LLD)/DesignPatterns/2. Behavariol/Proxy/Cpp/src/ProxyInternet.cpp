#include "../include/ProxyInternet.h"
#include <iostream>
#include <algorithm>

ProxyInternet::ProxyInternet() {
    realInternet = new RealInternet();
    bannedSites = {"abc.com", "xyz.com", "banned.com"};
}

ProxyInternet::~ProxyInternet() {
    delete realInternet;
}

void ProxyInternet::connectTo(const std::string& serverHost) {
    if (std::find(bannedSites.begin(), bannedSites.end(), serverHost) != bannedSites.end()) {
        std::cerr << "Access Denied to " << serverHost << std::endl;
    } else {
        realInternet->connectTo(serverHost);
    }
}
