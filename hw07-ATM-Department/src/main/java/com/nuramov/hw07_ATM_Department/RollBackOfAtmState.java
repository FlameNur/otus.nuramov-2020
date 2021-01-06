package com.nuramov.hw07_ATM_Department;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class RollBackOfAtmState implements DepartmentRequest {
    List<Atm> listOfAtms;

    public RollBackOfAtmState(List<Atm> listOfAtms) {
        this.listOfAtms = listOfAtms;
    }

    @Override
    public void execute() {
        for(Atm atm : listOfAtms) {
            atm.setStateOFAtm(true);
        }
    }
}
