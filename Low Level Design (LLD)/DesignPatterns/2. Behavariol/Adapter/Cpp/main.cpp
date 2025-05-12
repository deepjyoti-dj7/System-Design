#include "./include/USBCCharger.h"
#include "./include/USBCToLightningAdapter.h"
#include "./include/IPhone.h"

int main() {
    USBCCharger* usbCCharger = new USBCCharger();
    LightningPort* adapter = new USBCToLightningAdapter(usbCCharger);

    IPhone* iphone = new IPhone(adapter);
    iphone->charge();

    delete iphone;
    delete adapter;
    delete usbCCharger;

    return 0;
}
