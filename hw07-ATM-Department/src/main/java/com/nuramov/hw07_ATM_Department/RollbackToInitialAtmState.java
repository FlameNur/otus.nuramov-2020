package com.nuramov.hw07_ATM_Department;

import java.util.List;

    /*
    class RollbackToInitialAtmState - выполняет запрос на возврат первичного состояния всех Atm
     */

public class RollbackToInitialAtmState implements DepartmentRequest {
    List<Atm> listOfAtms;

    public RollbackToInitialAtmState (List<Atm> listOfAtms) {
        this.listOfAtms = listOfAtms;
    }

    @Override
    public void execute() {
        for(Atm atm : listOfAtms) {
            atm.setAccessRightsToAtm(true);
            atm.load(atm.getVersionController().getInitialSave());
            System.out.println("Atm возвращен к начальному состоянию");
        }
    }
}
