public class ATM {
    private double availableCash;

    public ATM(double initialCash) {
        this.availableCash = initialCash;
    }

    public boolean dispenseCash(double amount) {
        if (amount <= availableCash) {
            availableCash -= amount;
            return true;
        }
        return false;
    }

    public void loadCash(double amount) {
        availableCash += amount;
    }

    public double getAvailableCash() {
        return availableCash;
    }
}
