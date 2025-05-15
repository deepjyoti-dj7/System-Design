#include "include/Document.h"

int main() {
    Document doc;

    doc.edit();     // Draft: editable
    doc.publish();  // Move to Moderation

    doc.edit();     // Moderation: not editable
    doc.publish();  // Move to Published

    doc.edit();     // Published: not editable
    doc.publish();  // Already published

    return 0;
}
