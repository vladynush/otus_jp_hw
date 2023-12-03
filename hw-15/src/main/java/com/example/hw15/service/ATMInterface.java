package com.example.hw15.service;



import com.example.hw15.model.Banknote;

import java.util.List;

public interface ATMInterface {
    void insertValue(List<Banknote> banknotes);
    void withdrawValue(long amount);
    long getBalance();
}
