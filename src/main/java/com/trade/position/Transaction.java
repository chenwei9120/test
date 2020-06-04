package com.trade.position;

public class Transaction {
    private int TransactionID;
    private int TradeID;
    private int Version;
    private String SecurityCode;
    private int Quantity;
    //Insert/Update/Cancel
    private TranAction action;
    //Buy/Sell
    private TranType tranType;

    public int getTransactionID() {
        return TransactionID;
    }

    public Transaction(int transactionID, int tradeID, int version, String securityCode, int quantity, TranAction action, TranType tranType) {
        TransactionID = transactionID;
        TradeID = tradeID;
        Version = version;
        SecurityCode = securityCode;
        Quantity = quantity;
        this.action = action;
        this.tranType = tranType;
    }

    public void setTransactionID(int transactionID) {
        TransactionID = transactionID;
    }

    public int getTradeID() {
        return TradeID;
    }

    public void setTradeID(int tradeID) {
        TradeID = tradeID;
    }

    public int getVersion() {
        return Version;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public String getSecurityCode() {
        return SecurityCode;
    }

    public void setSecurityCode(String securityCode) {
        SecurityCode = securityCode;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public TranAction getAction() {
        return action;
    }

    public void setAction(TranAction action) {
        this.action = action;
    }

    public TranType getTranType() {
        return tranType;
    }

    public void setTranType(TranType tranType) {
        this.tranType = tranType;
    }
}
