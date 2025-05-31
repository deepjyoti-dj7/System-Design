#include "IDGenerator.h"
#include <boost/uuid/uuid.hpp>
#include <boost/uuid/uuid_generators.hpp>
#include <boost/uuid/uuid_io.hpp>

std::string IDGenerator::generateID() {
    boost::uuids::random_generator generator;
    boost::uuids::uuid uuid = generator();
    return to_string(uuid);
}
