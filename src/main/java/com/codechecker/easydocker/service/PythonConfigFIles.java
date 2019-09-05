package com.codechecker.easydocker.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PythonConfigFIles {

    public String createRequirementsTxt(List<String> selectedPackages) {
        StringBuilder content = new StringBuilder();
        selectedPackages.forEach(x -> content.append(x).append("\n"));
        return content.toString();
    }

    public String createRuntimeTxt(String selectedVersion) {
        return selectedVersion;
    }
}
