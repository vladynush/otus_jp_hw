package service;

import model.Banknote;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

public class ATMService implements ATMInterface {

    private final Map<Banknote, Long> balance;

    public ATMService() {
        this.balance = new HashMap<>();
    }

    public ATMService(Map<Banknote, Long> initBalance) {
        this.balance = initBalance;
    }

    @Override
    public void insertValue(List<Banknote> banknotes) {
        updateBalance(countBanknotes(banknotes));
    }

    @Override
    public void withdrawValue(long amount) {
        List<Banknote> sortedBanknoteNominals = new ArrayList<>(getSortedNominals());
        Map<Banknote, Long> banknotesToRemove = new HashMap<>();
        if (!CollectionUtils.isEmpty(sortedBanknoteNominals)) {
            for (Banknote banknote : sortedBanknoteNominals) {
                if (amount >= banknote.getValue()) {
                    amount = amount - banknote.getValue();
                    banknotesToRemove.put(banknote, banknotesToRemove.getOrDefault(banknote, 0L) + 1);
                }
            }
            checkResultAmount(amount, sortedBanknoteNominals);
        } else {
            throw new IllegalArgumentException("Unable to withdraw cash, ATM is empty");
        }
        removeWithdrawValueFromBalance(banknotesToRemove);
    }

    @Override
    public long getBalance() {
        return balance.keySet()
                .stream()
                .mapToLong(banknote -> balance.get(banknote) * banknote.getValue())
                .sum();
    }

    private void checkResultAmount(long amount, List<Banknote> sortedBanknoteNominals) {
        if (amount != 0) {
            Banknote minimalBanknote = sortedBanknoteNominals.get(sortedBanknoteNominals.size() - 1);
            if (amount < minimalBanknote.getValue()) {
                throw new IllegalArgumentException("Unable to withdraw cash, minimal banknote in ATM is "
                        + minimalBanknote);
            } else {
                throw new IllegalArgumentException("Unable to withdraw cash, ATM balance is not enough. Maximal value is "
                        + getBalance());
            }
        }
    }

    private void removeWithdrawValueFromBalance(Map<Banknote, Long> banknotesToRemove) {
        for (Map.Entry<Banknote, Long> entry : banknotesToRemove.entrySet()) {
            balance.put(entry.getKey(), balance.get(entry.getKey()) - entry.getValue());
        }
    }

    private List<Banknote> getSortedNominals() {
        return balance.keySet()
                .stream()
                .filter(banknote -> balance.get(banknote) > 0)
                .sorted(Comparator.comparing(Banknote::ordinal).reversed())
                .toList();
    }

    private Map<Banknote, Long> countBanknotes(List<Banknote> banknotes) {
        Map<Banknote, Long> resultMap = new HashMap<>();
        for (Banknote el : banknotes) {
            resultMap.put(el, resultMap.getOrDefault(el, 0L) + 1);
        }
        return resultMap;
    }

    private void updateBalance(Map<Banknote, Long> countedBanknotes) {
        for (Map.Entry<Banknote, Long> entry : countedBanknotes.entrySet()) {
            balance.merge(entry.getKey(), entry.getValue(), Long::sum);
        }
    }
}
