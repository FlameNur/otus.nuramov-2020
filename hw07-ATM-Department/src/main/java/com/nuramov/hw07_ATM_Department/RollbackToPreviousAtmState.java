package com.nuramov.hw07_ATM_Department;

import java.util.List;

    /*
    class RollbackToPreviousAtmState - выполняет запрос на возврат предыдущего состояния всех Atm
     */

public class RollbackToPreviousAtmState implements DepartmentRequest {
    List<Atm> listOfAtms;

    public RollbackToPreviousAtmState(List<Atm> listOfAtms) {
        this.listOfAtms = listOfAtms;
    }

    @Override
    public void execute() {
        for(Atm atm : listOfAtms) {
            atm.setAccessRightsToAtm(true);
            atm.load(atm.getVersionController().getSave());
            System.out.println("Atm возвращен к предыдущему состоянию");
        }
    }
}
