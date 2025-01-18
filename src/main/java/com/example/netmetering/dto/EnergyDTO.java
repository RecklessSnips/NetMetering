package com.example.netmetering.dto;

import com.example.netmetering.util.Energy;

import java.math.BigDecimal;

public class EnergyDTO {

    private BigDecimal kilowatts;

    private BigDecimal dollar;

    public EnergyDTO(Energy energy){
        this.kilowatts = energy.getKilowatts();
        this.dollar = energy.getDollars();
    }

    public BigDecimal getKilowatts() {
        return kilowatts;
    }

    public void setKilowatts(BigDecimal kilowatts) {
        this.kilowatts = kilowatts;
    }

    public BigDecimal getDollar() {
        return dollar;
    }

    public void setDollar(BigDecimal dollar) {
        this.dollar = dollar;
    }

    @Override
    public String toString() {
        return "EnergyDTO{" +
                "kilowatts=" + kilowatts +
                ", dollar=" + dollar +
                '}';
    }
}
