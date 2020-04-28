package views;

import javax.swing.*;

public class AdvertAdditionPanel extends JPanel {
    public MainFrame mainFrame;
    //firmName,
    //position,
    //jobDuration,
    //jobDescription

    public JTextField firmNameField;
    public JTextField positionField;

    public JComboBox<String> jobDurationBox;

    public JTextArea descriptionField;

    public JButton addAdvertisementButton;
    public JButton returnBtn;


    public AdvertAdditionPanel(MainFrame frame) {
        this.mainFrame = frame;
//substitute radiobtn for checkbox

        this.firmNameField = new JTextField("Firm Name:");
        this.positionField = new JTextField("Position:");

        this.jobDurationBox = new JComboBox<>();
        this.jobDurationBox.addItem("Permanent");
        this.jobDurationBox.addItem("Temporary");

        this.descriptionField = new JTextArea("Description:");
        this.addAdvertisementButton = new JButton("Add advert");
        this.returnBtn = new JButton("Return");


        add(this.firmNameField);
        add(this.positionField);
        add(this.jobDurationBox);
        add(this.descriptionField);
        add(this.addAdvertisementButton);
        add(this.returnBtn);


        this.addAdvertisementButton.addActionListener(e -> {
            createAdvertisement();
            resetTextFields();
        });

        this.returnBtn.addActionListener(e -> returnToAllAds());
    }


    public void createAdvertisement() {
        if (checkAllFields() && checkContent()) {
            String name = this.firmNameField.getText();
            String position = this.positionField.getText();
            String duration = (String) this.jobDurationBox.getSelectedItem();
            String description = this.descriptionField.getText();

            mainFrame.dataManager.addAdvertisement(name, position, duration, description);
        } else {
            JOptionPane.showMessageDialog(null, "There is no information in the fields.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean checkAllFields() {
        return !this.firmNameField.getText().isBlank() &&
                !this.positionField.getText().isBlank()
                && this.jobDurationBox.getSelectedIndex() != -1
                && !this.descriptionField.getText().isBlank();
    }

    private boolean checkContent() {
        return !this.firmNameField.getText().equals("Firm Name:") &&
                !this.positionField.getText().equals("Position:") &&
                !this.descriptionField.getText().equals("Description:");
    }

    private void returnToAllAds() {
        mainFrame.hideAdvertPanel();
        mainFrame.showJobsPanel();
    }

    private void resetTextFields() {
        this.firmNameField.setText("Firm Name:");
        this.positionField.setText("Position:");
        this.descriptionField.setText("Description:");
    }


}
