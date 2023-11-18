package pt.isec.pd.projetopd.server.Remote;

import java.util.List;
import java.util.Map;

public class DataBaseCopy {

    private List<Map<String, Object>> database;
    public DataBaseCopy(List<Map<String, Object>> database) {
        this.database = database;
    }

    public List<Map<String, Object>> getDatabase() {
        return database;
    }
}

