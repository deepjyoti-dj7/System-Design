#pragma once
#include "Internet.h"
#include "RealInternet.h"
#include <vector>
#include <string>

class ProxyInternet : public Internet {
private:
    RealInternet* realInternet;
    std::vector<std::string> bannedSites;

public:
    ProxyInternet();
    ~ProxyInternet();
    void connectTo(const std::string& serverHost) override;
};
