package com.trade.position;

public enum  TranType {
    Buy(0), Sell(1);

    private int type;
    TranType(int type) {
        this.type = type;
    }
}
