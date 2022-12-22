package com.irinalyamina.InsuranceAgency;

import com.irinalyamina.InsuranceAgency.models.Policy;
import com.irinalyamina.InsuranceAgency.modelsForLayout.PolicyForList;

import java.util.ArrayList;
import java.util.List;

public class Parse {

    public static List<PolicyForList> listPolicyToListPolicyForList(List<Policy> listPolicies){
        List<PolicyForList> list = new ArrayList<>();

        for (var i = 0; i < listPolicies.size(); i++){

            list.add(new PolicyForList(
                    listPolicies.get(i),
                    listPolicies.get(i).getPolicyholder().getFullName(),
                    listPolicies.get(i).getCar().getModel()
            ));
        }

        return list;
    }
}
