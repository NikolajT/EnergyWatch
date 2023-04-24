package Services;

import Model.EnergyPrice;

import java.util.List;

public interface IDBFactory {

    List<EnergyPrice> getEnergyPrice();

    void createEnergyPrice(EnergyPrice price);

    void delete(EnergyPrice id);

}
