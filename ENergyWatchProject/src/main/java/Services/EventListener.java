package Services;

import java.sql.SQLException;

public interface EventListener {

    void update(String eventtype, String energyData) throws SQLException;

}
