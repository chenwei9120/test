package com.trade.position;

public class MainClass {
    public static void main(String[] args) {
        Caculator caculator = Caculator.getInstance();
        Thread threadREL = new Thread(() -> {
            Transaction transaction = new Transaction(1, 1, 1, "REL",
                    50, TranAction.INSERT, TranType.Buy);
            caculator.caculate(transaction);
            transaction = new Transaction(4, 1, 2, "REL",
                    60, TranAction.UPDATE, TranType.Buy);
            caculator.caculate(transaction);
        });
        threadREL.start();
        Thread threadITC = new Thread(() -> {
            Transaction transaction = new Transaction(2, 2, 1, "ITC",
                    40, TranAction.INSERT, TranType.Sell);
            caculator.caculate(transaction);
            transaction = new Transaction(5,2,2 ,"ITC",
                    30, TranAction.CANCEL, TranType.Buy);
            caculator.caculate(transaction);
        });
        threadITC.start();
        Thread threadINF = new Thread(() -> {
            Transaction transaction = new Transaction(3, 3, 1, "INF",
                    70, TranAction.INSERT, TranType.Buy);
            caculator.caculate(transaction);
            transaction = new Transaction(6,4, 1, "INF",
                    20 , TranAction.INSERT, TranType.Sell);
            caculator.caculate(transaction);
        });
        threadINF.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        caculator.print();
    }
}
