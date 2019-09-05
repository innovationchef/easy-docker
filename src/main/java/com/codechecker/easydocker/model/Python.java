package com.codechecker.easydocker.model;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Python {

    private String path;
    private List<String> versions;
    private List<String> packages;

    private String selectedVersion;
    private List<String> selectedPackages;

    public Python() {
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> version) {
        this.versions = version;
    }

    public List<String> getSelectedPackages() {
        return selectedPackages;
    }

    public void setSelectedPackages(List<String> selectedPackages) {
        this.selectedPackages = selectedPackages;
    }

    public String getSelectedVersion() {
        return selectedVersion;
    }

    public void setSelectedVersion(String selectedVersion) {
        this.selectedVersion = selectedVersion;
    }

}
