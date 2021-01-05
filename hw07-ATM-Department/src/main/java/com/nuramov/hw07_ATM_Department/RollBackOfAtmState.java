package com.nuramov.hw07_ATM_Department;

import java.util.Deque;
import java.util.LinkedList;

public class RollBackOfAtmState implements DepartmentRequest{

    // Надо многое доработать!!!
    // Необходимо записывать операции "выдачи" Atm для возможности вернуть состояние Atm на один шаг назад
    // Ниже показан пример записи
    //-------------------------------------------------------------------------
    private final Deque<DepartmentRequest> history = new LinkedList<>();

    public void storeAndExecute(DepartmentRequest request) {
        this.history.add(request); // optional
        request.execute();
    }

    public void undo() {
        if (history.size() > 1) {
            history.removeLast();
            history.getLast().execute();
        }
    }
    //---------------------------------------------------------------------------

    Atm atm;

    public RollBackOfAtmState(Atm atm) {
        this.atm = atm;
    }

    @Override
    public void execute() {

    }
}
