package com.nuramov.hw07_ATM_Department;

public class AtmDepartment {
    DepartmentRequest departmentRequest;

    public AtmDepartment(DepartmentRequest departmentRequest) {
        this.departmentRequest = departmentRequest;
    }

    public void sumOfAllBalancesRequest() {
        departmentRequest.execute();
    }
}
