package managers;

import com.google.gson.Gson;

import entities.Candidate;
import entities.JobAdvertisement;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;

public class CandidateDataManager {

    private ArrayList<Candidate> candidates;


    //set
    public JobAdvertisement adKeyToConnect;
    //set
    public DefaultTableModel candidatesModel;

    public DataService<Candidate> candidateFileDataService;

    public Gson json = new Gson();


    public CandidateDataManager(DataService<Candidate> candidateData) {
        this.candidates = new ArrayList<>();
        this.candidateFileDataService = candidateData;

    }


    public void addCandidate(String fName, String mName, String sName, String number, int jobXp,
                             String coverLetter) {

        Candidate candidate = new Candidate(fName, mName, sName, number, jobXp, coverLetter);
        candidate.setFirm(adKeyToConnect.getFirmName());
        candidate.setPosition(adKeyToConnect.getPosition());

        adKeyToConnect.increaseCandidatesCount();
        this.candidates.add(candidate);
        try {
            this.candidateFileDataService.insertInto(json.toJson(this.candidates));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadCandidatesFromDatabase() {
        this.candidates = this.candidateFileDataService.readData();

    }


    public void filterCandidatesByFirm(String firm, String position) {
        this.candidatesModel.setRowCount(0);
        for (Candidate c : candidates) {

            if (c.getFirm().equals(firm) && c.getPosition().equals(position)) {
                updateTableModelCandidates(c);
            }
        }
    }


    private void updateTableModelCandidates(Candidate c) {
        Object[] cDetails = new Object[4];
        cDetails[0] = c.getFName() + " " + c.getMName() + " " + c.getSName();
        cDetails[1] = c.getPhoneNumber();
        cDetails[2] = c.getWorkExperience();
        cDetails[3] = c.getCoverLetter();
        this.candidatesModel.addRow(cDetails);
    }


}
