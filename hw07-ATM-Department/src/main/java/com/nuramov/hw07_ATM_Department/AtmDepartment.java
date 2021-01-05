package com.nuramov.hw07_ATM_Department;

public class AtmDepartment {
    DepartmentRequest sumOfAllBalances;
    DepartmentRequest rollBackOfAtmState;

    public AtmDepartment(DepartmentRequest sumOfAllBalances, DepartmentRequest rollBackOfAtmState) {
        this.sumOfAllBalances = sumOfAllBalances;
        this.rollBackOfAtmState = rollBackOfAtmState;
    }

    public void sumOfAllBalancesRequest() {
        sumOfAllBalances.execute();
    }

    public void rollBackOfAtmStateRequest() {
        rollBackOfAtmState.execute();
    }
}
