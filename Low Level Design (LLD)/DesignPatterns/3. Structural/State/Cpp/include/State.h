#pragma once

class Document;

class State {
public:
    virtual void edit(Document* doc) = 0;
    virtual void publish(Document* doc) = 0;
    virtual ~State() = default;
};
