package workers;

/**
 * A full time worker is a worker that is able to get any benefits from the company as well as to get paid leave
 * @version 28/03/2020/v1
 *  @author Aleksandra Rezetka
 */
public class FullTimeWorker extends Worker {

    private int paidLeave;
    private boolean multisport;
    private boolean healthcareLeave;
    private int childCareLeave;

    /**
     * This constructor needs all the data about Workers.Worker
     * @param login Worekrs Login
     * @param password Worker's password
     * @param name -Workers name
     * @param surname His surname
     * @param cashPerHour amount paid per hour
     * @param paidLeave amount of full paid free days due to the legal acts
     * @param multisport company sport voucher True if Employee uses it
     * @param healthcareLeave a company healthcare True if Employee uses it
     * @param childCareLeave no of days that one can have for a sick child
     */
    public FullTimeWorker(String login, String password, String name, String surname, int cashPerHour, int paidLeave, boolean multisport, boolean healthcareLeave, int childCareLeave) {
        super(login, password, name, surname, cashPerHour);
        this.paidLeave = paidLeave;
        this.multisport = multisport;
        this.healthcareLeave = healthcareLeave;
        this.childCareLeave = childCareLeave;
    }

    /**
     * This constructor automatically creates login
     * @param password Workers.Worker's password
     * @param name -Workers name
     * @param surname His surname
     * @param cashPerHour amount paid per hour
     * @param paidLeave amount of full paid free days due to the legal acts
     * @param multisport company sport voucher True if Employee uses it
     * @param healthcareLeave a company healthcare True if Employee uses it
     * @param childCareLeave no of days that one can have for a sick child
     */
    public FullTimeWorker(String password, String name, String surname, int cashPerHour, int paidLeave, boolean multisport, boolean healthcareLeave, int childCareLeave) {
        super(password, name, surname, cashPerHour);
        this.paidLeave = paidLeave;
        this.multisport = multisport;
        this.healthcareLeave = healthcareLeave;
        this.childCareLeave = childCareLeave;
    }

    //settery oraz gettery (Hermetyzacja pkt 2):

    public int getChildCareLeave() {
        return childCareLeave;
    }

    public void setChildCareLeave(int childCareLeave) {
        this.childCareLeave = childCareLeave;
    }

    public int getPaidLeave() {
        return paidLeave;
    }

    public void setPaidLeave(int paidLeave) {
        this.paidLeave = paidLeave;
    }

    public boolean isMultisport() {
        return multisport;
    }

    public void setMultisport(boolean multisport) {
        this.multisport = multisport;
    }

    public boolean isHealthcareLeave() {
        return healthcareLeave;
    }

    public void setHealthcareLeave(boolean healthcareLeave) {
        this.healthcareLeave = healthcareLeave;
    }


    /**
     * This method gives whole amount of paid Leave that an Empoyee has
     * @return it returns this value
     */
    public int returnNOOfFullPaidLeaveDays() {
        return getPaidLeave()+ getChildCareLeave();
    }

    /**
     * This method is an extension to Workers.Worker introduction implemented in Workers.Worker class
     */
    @Override
    public void introduceWorker ()
    {
        super.introduceWorker();
        System.out.println ("He is a full-time Workers.Worker");
    }

    /**
     * This method is used for test
     * @param args
     */
    public static void main(String[] args) {
        FullTimeWorker p1 = new FullTimeWorker("Passw0rd", "Mikolaj", "Trapek", 34, 5, false, true,13);
        System.out.println(p1.getName());
        System.out.println(p1.getSurname());
        p1.changePassword("Passw0rd"); // changing password
        p1.changePassword("Passw0rd"); // not working
    }
}
