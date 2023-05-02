package Services;

import Model.EnergyPrice;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IDBFactory {

//    List<EnergyPrice> getEnergyPrice();

    List<EnergyPrice> getEnergyPrice(Connection connection) throws SQLException;

    void createEnergyPrice(EnergyPrice price);

    void delete(EnergyPrice id);

}
