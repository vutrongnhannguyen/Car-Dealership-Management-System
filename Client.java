public class Client {
    private String clientId;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String email;
//    private Membership membership;
    private int totalSpending;

    public Client(String clientId, String fullName, String address, String phoneNumber, String email) {
        this.clientId = clientId;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
//        this.membership = Membership.NONE;
        this.totalSpending = 0;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Membership getMembership() {
//        return membership;
//    }
//
//    public void setMembership(Membership membership) {
//        this.membership = membership;
//    }

    public int getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(int totalSpending) {
        this.totalSpending = totalSpending;
    }

//    public void addSpending(int amount) {
//        this.totalSpending += amount;
//        this.membership = Membership.getMembershipBySpending(this.totalSpending);
//    }
}
