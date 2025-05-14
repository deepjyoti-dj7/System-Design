#include "../include/SupportRequest.h"

SupportRequest::SupportRequest(const std::string& type) : type(type) {}

std::string SupportRequest::getType() const {
    return type;
}
