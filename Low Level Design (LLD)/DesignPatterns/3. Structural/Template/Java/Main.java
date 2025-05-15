public class Main {
    public static void main(String[] args) {
        DataParser csvParser = new CSVDataParser();
        System.out.println("---- Parsing CSV ----");
        csvParser.parseData();

        System.out.println();

        DataParser xmlParser = new XMLDataParser();
        System.out.println("---- Parsing XML ----");
        xmlParser.parseData();
    }
}
