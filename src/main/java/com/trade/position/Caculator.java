package com.trade.position;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Caculator {

    private static Caculator instance = new Caculator();

    private Caculator() {
    }

    public static Caculator getInstance() {
        return instance;
    }

    private ConcurrentHashMap<String, Integer> resultMap = new ConcurrentHashMap<String, Integer>();

    private ConcurrentHashMap<Integer, Map<Integer, Transaction>>
            tranActionMap = new ConcurrentHashMap<Integer, Map<Integer, Transaction>>();

    private Object lockObject = new Object();

    public boolean caculate(Transaction transaction) {
        synchronized (lockObject) {
            if (!validate(transaction)) {
                return false;
            }
            handlerTransaction(transaction);
        }
        if (!resultMap.containsKey(transaction.getSecurityCode())) {
            if (transaction.getTranType().equals(TranType.Buy)) {
                resultMap.put(transaction.getSecurityCode(), transaction.getQuantity());
            } else {
                resultMap.put(transaction.getSecurityCode(), transaction.getQuantity() * (-1));
            }
        } else {
            if (transaction.getTranType().equals(TranType.Buy)) {
                if (transaction.getAction().equals(TranAction.INSERT)) {
                    resultMap.put(transaction.getSecurityCode(),
                            resultMap.get(transaction.getSecurityCode()) + transaction.getQuantity());
                } else if (transaction.getAction().equals(TranAction.UPDATE)) {
                    resultMap.put(transaction.getSecurityCode(), transaction.getQuantity());
                } else {
                    resultMap.put(transaction.getSecurityCode(), 0);
                }
            } else {
                if (transaction.getAction().equals(TranAction.INSERT)) {
                    resultMap.put(transaction.getSecurityCode(),
                            resultMap.get(transaction.getSecurityCode()) - transaction.getQuantity());
                } else if (transaction.getAction().equals(TranAction.UPDATE)) {
                    resultMap.put(transaction.getSecurityCode(), transaction.getQuantity());
                } else {
                    resultMap.put(transaction.getSecurityCode(), 0);
                }
            }
        }
        return true;
    }

    private boolean validate(Transaction transaction) {
        //INSERT will always be 1st version of a Trade
        if (transaction.getVersion() == 1 && !transaction.getAction().equals(TranAction.INSERT)) {
            return false;
        }
        if (transaction.getAction().equals(TranAction.CANCEL)) {
            Map<Integer, Transaction> map = tranActionMap.get(transaction.getTradeID());
            if (map != null) {
                Transaction tmpTransaction = map.get(transaction.getVersion());
                if (tmpTransaction != null && tmpTransaction.getAction().equals(TranAction.CANCEL)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void handlerTransaction(Transaction transaction) {
        Map<Integer, Transaction> map = tranActionMap.get(transaction.getTradeID());
        if (map == null) {
            map = new HashMap<Integer, Transaction>();
        }
        map.put(transaction.getVersion(), transaction);
        tranActionMap.put(transaction.getTradeID(), map);
    }

    public void print() {
        System.out.println("REL " + resultMap.get("REL"));
        System.out.println("ITC " + resultMap.get("ITC"));
        System.out.println("INF " + resultMap.get("INF"));
    }
}
