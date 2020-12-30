package com.nuramov.hw07_ATM_Department;

public class AtmDepartment {
    DepartmentRequest departmentRequest;

    double checkingTheBalance() {
        departmentRequest = new SumOfAtmBalances();
        return 1.0;
    }

    int numberOfAllAtm() {
        return 2;
    }

}
