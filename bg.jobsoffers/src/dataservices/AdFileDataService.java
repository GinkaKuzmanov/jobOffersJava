package dataservices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import entities.JobAdvertisement;
import managers.DataService;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class AdFileDataService implements DataService<JobAdvertisement> {

    public String fileDataForAds = "advertisementsInfo.txt";
    public Gson gson = new Gson();

    //insert into file

    public void insertInto(String myData) throws IOException {
        File adFile = new File(fileDataForAds);
        if (!adFile.exists()) {
            adFile.createNewFile();
        }
        adFile.delete();
        //Writes with a buffer, must use try-with-resources
        try (FileWriter adWriter = new FileWriter(adFile.getAbsoluteFile(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(adWriter)) {
            bufferedWriter.write(myData);
        }
    }


    public ArrayList<JobAdvertisement> readData() {
        File adFile = new File(fileDataForAds);
        if (!adFile.exists()) {
            System.out.println("File doesn't exist");
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(adFile),
                StandardCharsets.UTF_8); JsonReader jReader = new JsonReader(reader)) {

            Type listType = new TypeToken<ArrayList<JobAdvertisement>>() {
            }.getType();
            //returns an ArrayList<JobAdvertisements>;
//            log("Data loaded successfully" + fileDataForAds);
            return new Gson().fromJson(reader, listType);
        } catch (IOException npe) {
            log("an exception occurred.");
            return null;
        }
    }

    //remove user from file
    public void removeFrom(JobAdvertisement jobAd) {
        File adFile = new File(fileDataForAds);
        if (!adFile.exists()) {
            log("File not existing");
        }
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(adFile), StandardCharsets.UTF_8);
             JsonReader jsReader = new JsonReader(isr)) {

            Type listType = new TypeToken<ArrayList<JobAdvertisement>>() {
            }.getType();
            ArrayList<JobAdvertisement> jobAds = new Gson().fromJson(jsReader, listType);
            for (JobAdvertisement ad : jobAds) {
                if (ad.getFirmName().equals(jobAd.getFirmName())) {
                    jobAds.remove(ad);
                    insertInto(gson.toJson(jobAds));
                    log("Removed:" + ad.getFirmName() + " " + ad.getJobDescription() + "from database");
                    break;
                }
            }

        } catch (IOException e) {
            log("error");
        }

    }


    public void log(String string) {
        System.out.println(string);
    }

}
