package com.example.hw15.controller;

import com.example.hw15.model.Banknote;
import com.example.hw15.model.BanknoteDTO;
import com.example.hw15.service.ATMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ATMController {

    private final ATMService atmService;

    @GetMapping("/")
    public ModelAndView mainView() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("balance", atmService.getBalance());
        return model;
    }

    @DeleteMapping("/balance")
    public ModelAndView takeMoney(@RequestParam Long amount) {
        atmService.withdrawValue(amount);
        return mainView();
    }

    @GetMapping("/balance")
    public ModelAndView checkBalance() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("balance", atmService.getBalance());
        return model;
    }

    @PostMapping("/balance")
    public ModelAndView putMoney(@RequestBody List<BanknoteDTO> addedBanknotes) {
        List<Banknote> receivedBanknotes = new ArrayList<>();
        for (BanknoteDTO banknoteDTO: addedBanknotes){
            receivedBanknotes.add(Banknote.valueOf(banknoteDTO.getNominal()));
        }
        atmService.insertValue(receivedBanknotes);
        return mainView();
    }
}
