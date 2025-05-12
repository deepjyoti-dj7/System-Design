#pragma once

class LightningPort {
public:
    virtual void connectWithLightning() = 0;
    virtual ~LightningPort() = default;
};
