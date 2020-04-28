package managers;

import com.google.gson.Gson;
import entities.JobAdvertisement;

import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import java.util.Set;

//class takes care of list of all companies' advertisements
public class AdDataManager {
    private ArrayList<JobAdvertisement> jobAds;

    private final Set<String> advertisementSet;

    public DefaultTableModel model;

    public DefaultTableModel firmsOnlyModel;

    public DataService<JobAdvertisement> database;

    public Gson json = new Gson();

    public AdDataManager(DataService<JobAdvertisement> jobAdvertisementDataService) {
        this.jobAds = new ArrayList<>();
        this.advertisementSet = new HashSet<>();
        this.database = jobAdvertisementDataService;
    }


    //metod za dobavqne na obqva
    public void addAdvertisement(String firmName,
                                 String position,
                                 String jobDuration,
                                 String jobDescription) {
        //CREATE THE INSTANCE OT ADVERT
        JobAdvertisement jobAdvertisement = new JobAdvertisement(
                firmName,
                position,
                jobDuration,
                jobDescription);

        //ADD TO THE LOCAL LIST
        this.jobAds.add(jobAdvertisement);
        updateAdTableData();
        updateFirmTableData();

        //INSERT IT INTO THE FILE AD DATABASE
        try {
            this.database.insertInto(json.toJson(this.jobAds));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //USAGE IN MAP FOR KEY
    public ArrayList<JobAdvertisement> getJobAds() {
        return this.jobAds;
    }

    public void setJobAds(ArrayList<JobAdvertisement> jobAds) {
        this.jobAds = jobAds;
    }

    public void loadFromDatabase() {
        this.jobAds = this.database.readData();
        updateAdTableData();

    }

    public void loadFirmsFromDatabase() {
        updateFirmTableData();
    }

    public void updateFirmTableData() {
        //za da ne se povtarq vav tablicata za filtrirane, izpolzvam HashSet
        this.firmsOnlyModel.setRowCount(0);
        for (String jobAd : this.advertisementSet) {
            Object[] row = new Object[1];
            row[0] = jobAd;
            this.firmsOnlyModel.addRow(row);
        }
    }


    //metod za obnovqvane na dannite vseki put
    public void updateAdTableData() {
        this.model.setRowCount(0);
        for (JobAdvertisement jobAd : this.jobAds) {

            this.advertisementSet.add(jobAd.getFirmName());

            updateTableModel(jobAd);
        }
    }


    public void removeAdTableData(int selectedRowIdx) {
        try {
            JobAdvertisement ad = this.jobAds.get(selectedRowIdx);

            //update the set together with the list
            this.advertisementSet.remove(ad.getFirmName());
            this.jobAds.remove(selectedRowIdx);
            this.database.removeFrom(ad);

            updateAdTableData();
            updateFirmTableData();
        } catch (IndexOutOfBoundsException iobe) {
            System.out.println(iobe.getMessage());
        }
    }

    public void searchByFirmName(String firmName) {
        this.model.setRowCount(0);
        for (JobAdvertisement ad : jobAds) {
            if (ad.getFirmName().equals(firmName)) {
                updateTableModel(ad);
            }
        }
    }


    public void searchByPosition(String searched) {
        boolean isHere = false;
        this.model.setRowCount(0);
        for (JobAdvertisement jobAdvertisement : this.jobAds) {
            if (jobAdvertisement.getPosition().toLowerCase().contains(searched.toLowerCase())) {
                isHere = true;
                updateTableModel(jobAdvertisement);
            }
        }
        isFound(isHere);
    }


    private void updateTableModel(JobAdvertisement jobAdvertisement) {
        Object[] lines = new Object[5];
        lines[0] = jobAdvertisement.getFirmName();
        lines[1] = jobAdvertisement.getPosition();
        lines[2] = jobAdvertisement.getJobDuration();
        lines[3] = jobAdvertisement.getJobDescription();
        lines[4] = jobAdvertisement.getCandidatesCount();
        this.model.addRow(lines);
    }

    private void isFound(boolean isHere) {
        if (!isHere) {
            Object[] lines = new Object[5];
            lines[0] = "NO RESULT";
            lines[1] = "NO RESULT";
            lines[2] = "NO RESULT";
            lines[3] = "NO RESULT";
            lines[4] = "NO RESULT";
            this.model.addRow(lines);
        }
    }

}
