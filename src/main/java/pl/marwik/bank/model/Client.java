package pl.marwik.bank.model;

public enum Client {
    F2F("By f2f"), ATM("By ATM"), WEB("By WEB"), SHOP("By shopping"), ADMIN("By admin");

    private String description;

    Client(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
