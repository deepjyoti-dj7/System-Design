#include "include/Directory.h"
#include "include/File.h"
#include "include/NamePrinterVisitor.h"

int main() {
    Directory* root = new Directory("Root");

    File* file1 = new File("Resume.pdf");
    File* file2 = new File("Photo.png");

    Directory* docs = new Directory("Documents");
    docs->add(file1);

    Directory* images = new Directory("Images");
    images->add(file2);

    root->add(docs);
    root->add(images);

    NamePrinterVisitor* visitor = new NamePrinterVisitor();
    root->accept(visitor);

    delete visitor;
    delete root;

    return 0;
}
