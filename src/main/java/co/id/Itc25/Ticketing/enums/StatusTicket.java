package co.id.Itc25.Ticketing.enums;

public enum StatusTicket {

    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Canceled");

    private String labelSatatusTicket;

    StatusTicket(String labelSatatusTicket) {
        this.labelSatatusTicket = labelSatatusTicket;
    }

    public String getLabelSatatusTicket(){
        return this.labelSatatusTicket;
    }
}
