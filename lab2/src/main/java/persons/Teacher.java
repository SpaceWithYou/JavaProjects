package persons;

public class Teacher extends Person {
    private final Subjects subject;
    private int[] hours;

    public Teacher(String name, String surName, String secondName, int birthYear, String telephoneNumber, Subjects subject, int[] hours) {
        super(name, surName, secondName, birthYear, telephoneNumber);
        this.subject = subject;
        this.hours = hours;
    }

    public Teacher() {
        super();
        subject = Subjects.NODATA;
        hours = new int[]{};
    }

    public Subjects getSubject() {
        return subject;
    }

    public int[] getHours() {
        int[] copyHours = new int[this.hours.length];
        System.arraycopy(this.hours, 0, copyHours, 0, this.hours.length);
        return copyHours;
    }

    public void setHours(int[] newHours) {
        this.hours = newHours;
    }
}
