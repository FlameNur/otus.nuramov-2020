package com.nuramov.hw07_ATM_Department;

public class AtmDepartment {
    DepartmentRequest sumOfAllBalances;
    DepartmentRequest rollbackToPreviousAtmState;
    DepartmentRequest rollbackToInitialAtmState;

    public AtmDepartment(DepartmentRequest sumOfAllBalances,
                         DepartmentRequest rollbackToPreviousAtmState,
                         DepartmentRequest rollbackToInitialAtmState) {
        this.sumOfAllBalances = sumOfAllBalances;
        this.rollbackToPreviousAtmState = rollbackToPreviousAtmState;
        this.rollbackToInitialAtmState = rollbackToInitialAtmState;
    }

    public void sumOfAllBalancesRequest() {
        sumOfAllBalances.execute();
    }

    public void rollbackToPreviousAtmStateRequest() {
        rollbackToPreviousAtmState.execute();
    }

    public void rollbackToInitialAtmStateRequest() {
        rollbackToInitialAtmState.execute();
    }
}
