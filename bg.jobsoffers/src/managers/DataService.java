package managers;

import java.io.IOException;
import java.util.ArrayList;

public interface DataService<T> {

    void insertInto(String record) throws IOException;

    ArrayList<T> readData();

    void removeFrom(T record);

}
