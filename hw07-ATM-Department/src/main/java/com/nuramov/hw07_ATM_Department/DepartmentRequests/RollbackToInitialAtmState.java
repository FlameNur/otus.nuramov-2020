package com.nuramov.hw07_ATM_Department.DepartmentRequests;

import com.nuramov.hw07_ATM_Department.Atm.Atm;

import java.util.List;

/** class RollbackToInitialAtmState - выполняет запрос на возврат первичного состояния всех Atm */

public class RollbackToInitialAtmState implements DepartmentRequest {
    private List<Atm> listOfAtms;

    public RollbackToInitialAtmState (List<Atm> listOfAtms) {
        this.listOfAtms = listOfAtms;
    }

    @Override
    public void execute() {
        for(Atm atm : listOfAtms) {
            atm.setAccessRightsToAtm(true);
            atm.loadInitialState();
            System.out.println("Atm возвращен к начальному состоянию");
        }
    }
}
