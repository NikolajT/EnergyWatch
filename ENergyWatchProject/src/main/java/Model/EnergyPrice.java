package Model;

import java.util.Date;
import java.util.UUID;
/**
 * A Model of the EnergyPrice object
 **/

public class EnergyPrice {
    private UUID id;
    private double spotPrice;
    private String priceArea;
    private Date date;

    public EnergyPrice(UUID id, double spotPrice, String priceArea, Date date) {
        this.id = id;
        this.spotPrice = spotPrice;
        this.priceArea = priceArea;
        this.date = date;
    }

    public EnergyPrice() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getSpotPrice() {
        return spotPrice;
    }

    public void setSpotPrice(double spotPrice) {
        this.spotPrice = spotPrice;
    }

    public String getPriceArea() {
        return priceArea;
    }

    public void setPriceArea(String priceArea) {
        this.priceArea = priceArea;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
