public abstract class DataParser {

    // Template method
    public final void parseData() {
        readData();
        processData();
        writeData();
    }

    protected abstract void readData();
    protected abstract void processData();

    // Concrete method
    protected void writeData() {
        System.out.println("Writing data to output file");
    }
}
