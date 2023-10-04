package service;

import model.Banknote;

import java.util.List;

public interface ATMInterface {
    void insertValue(List<Banknote> banknotes);
    void withdrawValue(long amount);
    long getBalance();
}
