package entities;

public class Candidate {

    private String firm;
    private String position;
    private String fName;
    private String mName;
    private String sName;
    private String phoneNumber;
    private int workExperience;
    private String coverLetter;

    public Candidate
            (String fName,
             String mName,
             String sName,
             String phoneNumber,
             int workExperience,
             String coverLetter) {
        this.fName = fName;
        this.mName = mName;
        this.sName = sName;
        this.phoneNumber = phoneNumber;
        this.workExperience = workExperience;
        this.coverLetter = coverLetter;
    }


    public String getFName() {
        return this.fName;
    }

    public String getMName() {
        return this.mName;
    }

    public String getSName() {
        return this.sName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public int getWorkExperience() {
        return this.workExperience;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getFirm() {
        return firm;
    }

    public String getPosition() {
        return this.position;
    }
}
