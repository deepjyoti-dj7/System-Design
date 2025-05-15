#include "../include/BitcoinPayment.h"
#include <iostream>

BitcoinPayment::BitcoinPayment(const std::string& walletAddress)
    : walletAddress(walletAddress) {}

void BitcoinPayment::pay(int amount) {
    std::cout << "Paid $" << amount << " using Bitcoin Wallet [" << walletAddress << "]" << std::endl;
}
