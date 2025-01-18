package com.example.netmetering.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
    The class to represents the energy usage in kilowatts,
    and posses the functions to calculate the dollar usage based on the kWh
 */
public class Energy {

    private BigDecimal kilowatts;

    private BigDecimal dollar;

    private final BigDecimal PRICE = new BigDecimal("0.076");

    public Energy(double usage){
        this.kilowatts = BigDecimal.valueOf(usage);
        this.dollar = this.kilowatts.multiply(PRICE).setScale(3, RoundingMode.HALF_UP);
    }

    public BigDecimal getKilowatts(){
        return this.kilowatts;
    }

    public BigDecimal getDollars(){
        return this.dollar;
    }

    @Override
    public String toString(){
        return "The amount of electricity you've produced is: $" + this.dollar;
    }

    public static void main(String[] args) {
        Energy energy = new Energy(3000d);
        System.out.println(energy.getDollars());
        System.out.println(energy);
    }

}
