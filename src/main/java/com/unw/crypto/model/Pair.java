package com.unw.crypto.model;

public enum Pair {

    BTC_USDT(0.0025), //Bitcoin
    ETH_USDT(0.080), //Ethereum
    BCHABC_USDT(0.07), //Bitcoin Cash
    TRX_USDT(400.0), //Tron
    XRP_USDT(30.0), //XRP
    LTC_USDT(0.22), //Litecoin
    EOS_USDT(3.0), //Eos
    XLM_USDT(11.0), // Stellar
    NEO_USDT(1.2), // NEO
    ADA_USDT(230.0);        // Cardano

    private double minAmountToBeInvested;

    Pair(double minAmountToBeInvested) {
        this.minAmountToBeInvested = minAmountToBeInvested;
    }


    public double getMinAmountToBeInvested() {
        return minAmountToBeInvested;
    }

    public String getName() {
        return this.name();
    }
}
