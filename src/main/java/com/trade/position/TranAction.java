package com.trade.position;

public enum TranAction {

    INSERT(1L), UPDATE(2L),CANCEL(3L);

    private Long action;

    TranAction(Long action) {
        this.action = action;
    }
}
