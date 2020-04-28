package views;


import dataservices.AdFileDataService;
import dataservices.CandidateFileDataService;
import managers.AdDataManager;
import managers.CandidateDataManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


public class MainFrame extends JFrame {

    public static int currentIdxAd;

    final Dimension dimension = new Dimension(800, 600);

    public JobsPanel jobsPanel;
    public CandidatePanel candidatePanel;
    public AdvertAdditionPanel advertAdditionPanel;
    public SeeAllCandidatesPanel seeAllCandidatesPanel;


    public AdDataManager dataManager =
            new AdDataManager(new AdFileDataService());

    public CandidateDataManager candidateDataManager =
            new CandidateDataManager(new CandidateFileDataService());

    public MainFrame() {
        super("Job offers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(dimension);
        setLayout(new GridLayout());


        //models tables
        modelForTheAdTable();
        modelForFirmTable();
        modelForCandidateTable();


        //deklaraciite v konstruktora za da se sazdade samo edna instanciq
        this.jobsPanel = new JobsPanel(this);
        this.candidatePanel = new CandidatePanel(this);
        this.advertAdditionPanel = new AdvertAdditionPanel(this);
        this.seeAllCandidatesPanel = new SeeAllCandidatesPanel(this);


        showJobsPanel();
    }

    private void modelForCandidateTable() {
        this.candidateDataManager.candidatesModel = new DefaultTableModel();
        String[] columnsCandidate = {"Names", "Phone", "Work Experience", "Cover Letter"};
        this.candidateDataManager.candidatesModel.setColumnIdentifiers(columnsCandidate);
    }

    private void modelForFirmTable() {
        this.dataManager.firmsOnlyModel = new DefaultTableModel();
        String[] firmIdentifier = {"FILTER /FIRM/"};
        this.dataManager.firmsOnlyModel.setColumnIdentifiers(firmIdentifier);
    }

    private void modelForTheAdTable() {
        this.dataManager.model = new DefaultTableModel();

        String[] columnIdentifiers =
                {"Name", "Position", "Duration", "Description", "Candidates"};
        this.dataManager.model.setColumnIdentifiers(columnIdentifiers);
    }


    public void showJobsPanel() {
        //Panel with options menu
        this.jobsPanel.setVisible(true);
        this.jobsPanel.setSize(getSize());
        add(jobsPanel);
    }

    public void hideJobsPanel() {
        jobsPanel.setVisible(false);
        remove(this.jobsPanel);
    }

    public void showCandidatePanel() {
        this.candidatePanel.setVisible(true);
        this.candidatePanel.setSize(getSize());
        add(candidatePanel);
    }

    public void hideCandidatePanel() {
        this.candidatePanel.setVisible(false);
        remove(this.candidatePanel);
    }

    public void showAdvertPanel() {
        this.advertAdditionPanel.setVisible(true);
        this.advertAdditionPanel.setSize(getSize());
        add(advertAdditionPanel);
    }


    public void hideAdvertPanel() {
        this.advertAdditionPanel.setVisible(false);
        remove(this.advertAdditionPanel);
    }

    public void showAllCandidatesPanel() {
        this.seeAllCandidatesPanel.setVisible(true);
        this.seeAllCandidatesPanel.setSize(getSize());
        add(this.seeAllCandidatesPanel);
    }

    public void hideAllCandidatesPanel() {
        this.seeAllCandidatesPanel.setVisible(false);
        remove(this.seeAllCandidatesPanel);
    }


}
