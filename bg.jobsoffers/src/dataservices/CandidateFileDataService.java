package dataservices;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import entities.Candidate;
import managers.DataService;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CandidateFileDataService implements DataService<Candidate> {
    public String fileForCandidates = "candidatesInfo.txt";
    public Gson gson = new Gson();

    //insert into file

    public void insertInto(String myData) throws IOException {
        File candidatesFile = new File(fileForCandidates);
        if (!candidatesFile.exists()) {
            candidatesFile.createNewFile();
        }
        candidatesFile.delete();
        //Writes with a buffer, must use try-with-resources
        try (FileWriter adWriter = new FileWriter(candidatesFile.getAbsoluteFile(), true);
             BufferedWriter bufferedWriter = new BufferedWriter(adWriter)) {
            bufferedWriter.write(myData);
        }
    }


    public ArrayList<Candidate> readData() {
        File candidatesFile = new File(fileForCandidates);
        if (!candidatesFile.exists()) {
            System.out.println("File doesn't exist");
        }

        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(candidatesFile),
                StandardCharsets.UTF_8); JsonReader jReader = new JsonReader(reader)) {

            Type mapType = new TypeToken<ArrayList<Candidate>>() {
            }.getType();
            //returns a Map<JobAdvertisement, ArrayList<Candidate>>>
            log("Data loaded successfully" + fileForCandidates);
            return new Gson().fromJson(reader, mapType);
        } catch (IOException npe) {
            log("an exception occurred.");
            return null;
        }
    }

    public void removeFrom(Candidate c) {
        File adFile = new File(fileForCandidates);
        if (!adFile.exists()) {
            log("File not existing");
        }
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(adFile), "UTF-8");
             JsonReader jsReader = new JsonReader(isr)) {

            Type listType = new TypeToken<ArrayList<Candidate>>() {
            }.getType();
            ArrayList<Candidate> candidates = new Gson().fromJson(jsReader, listType);
            for (Candidate c1 : candidates) {
                if (c1.getFName().equals(c.getFName())) {
                    candidates.remove(c1);
                    insertInto(gson.toJson(candidates));
                    log("Removed:" + c1.getSName() + " " + c1.getFirm() + "from database");
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

