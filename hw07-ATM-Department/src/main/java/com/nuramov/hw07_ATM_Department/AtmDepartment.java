package com.nuramov.hw07_ATM_Department;

public class AtmDepartment {
    DepartmentRequest departmentRequest;

    public AtmDepartment(DepartmentRequest checkingTheBalance) {
        this.departmentRequest = checkingTheBalance;
    }

    public void CheckingTheBalanceRequest() {
        departmentRequest.execute();
    }
}
