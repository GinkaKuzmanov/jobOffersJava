package views;

import com.google.gson.Gson;
import entities.JobAdvertisement;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;


public class CandidatePanel extends JPanel {
    public MainFrame mainFrame;

    public JTextField firstNameField;
    public JTextField midNameField;
    public JTextField surnameField;
    public JTextField phoneNumberField;
    public JTextField experienceField;
    public JTextArea coverLetterArea;


    public JButton apply;
    public JButton returnBtn;

    public CandidatePanel(MainFrame mainFrame) {
        super();
        this.mainFrame = mainFrame;
        firstNameField = new JTextField("First Name:");
        midNameField = new JTextField("Middle Name");
        surnameField = new JTextField("Last Name:");
        phoneNumberField = new JTextField("Phone Number:");
        experienceField = new JTextField("Work experience:");
        coverLetterArea = new JTextArea("Enter cover letter:");
        add(firstNameField);
        add(midNameField);
        add(surnameField);
        add(phoneNumberField);
        add(experienceField);
        add(coverLetterArea);

//Dobavqne na kandidata kym file s ofertata
        apply = new JButton("Submit");
        this.apply.addActionListener(e -> {
            createCandidate();
            saveIncrementedValues();
            resetContent();
        });


        add(apply);

        returnBtn = new JButton("RETURN");
        returnBtn.addActionListener(e -> {
                    mainFrame.hideCandidatePanel();
                    mainFrame.showJobsPanel();
                }
        );

        add(returnBtn);
    }


    private void createCandidate() {
        if (checkAllFields() && checkContent()) {
            String fName = this.firstNameField.getText();
            String mName = this.midNameField.getText();
            String sName = this.surnameField.getText();
            String phone = this.phoneNumberField.getText();
            String xp = this.experienceField.getText();
            String cover = this.coverLetterArea.getText();

            mainFrame.candidateDataManager.addCandidate(fName,
                    mName,
                    sName,
                    phone,
                    Integer.parseInt(xp),
                    cover);
        } else {
            JOptionPane.showMessageDialog(null, "Enter years in experience and 10 digits as phone number",
                    "Input information", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void saveIncrementedValues() {
        ArrayList<JobAdvertisement> refresh = mainFrame.dataManager.getJobAds();
        JobAdvertisement value = mainFrame.candidateDataManager.adKeyToConnect;
        refresh.set(MainFrame.currentIdxAd, value);
        mainFrame.dataManager.setJobAds(refresh);

        try {
            mainFrame.dataManager.database.insertInto(new Gson().toJson(refresh));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean checkAllFields() {
        return !firstNameField.getText().isBlank() &&
                !midNameField.getText().isBlank() &&
                !surnameField.getText().isBlank() &&
                !phoneNumberField.getText().isBlank() &&
                !experienceField.getText().isBlank() &&
                !coverLetterArea.getText().isBlank();
    }

    private boolean checkContent() {
        return experienceField.getText().matches("\\d+") && phoneNumberField.getText().matches("[0-9]{10}");
    }

    private void resetContent() {
        firstNameField.setText("First Name:");
        midNameField.setText("Middle Name");
        surnameField.setText("Last Name:");
        phoneNumberField.setText("Phone Number:");
        experienceField.setText("Work experience:");
        coverLetterArea.setText("Enter cover letter:");
    }


}
