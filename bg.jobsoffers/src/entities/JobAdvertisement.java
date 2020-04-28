package entities;

public class JobAdvertisement {
    private String firmName;
    private String position;
    private JobDuration jobDuration;
    private String jobDescription;
    private int candidatesCount;

    public JobAdvertisement
            (String firmName,
             String position,
             String jobDuration,
             String jobDescription) {
        this.firmName = firmName;
        this.position = position;
        this.jobDuration = JobDuration.valueOf(jobDuration.toUpperCase());
        this.jobDescription = jobDescription;
        this.candidatesCount = 0;
    }

    public String getFirmName() {
        return this.firmName;
    }

    public String getPosition() {
        return this.position;
    }

    public JobDuration getJobDuration() {
        return this.jobDuration;
    }

    public String getJobDescription() {
        return this.jobDescription;
    }

    public int getCandidatesCount() {
        return this.candidatesCount;
    }

    public void increaseCandidatesCount() {
        this.candidatesCount++;
    }
}
