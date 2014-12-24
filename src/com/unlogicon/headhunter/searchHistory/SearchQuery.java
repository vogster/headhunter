package com.unlogicon.headhunter.searchHistory;

/**
 * Created by Valo on 16.12.14.
 */
public class SearchQuery {
    private long m_nId;
    private String m_strJobName;
    private String m_strQuery;

    //details fields
    private String m_strCurrency;
    private String m_strSalary;
    private String m_strSpecialization;
    private String m_strProf;
    private String m_strExperience;
    private String m_strEmployment;
    private String m_strSchedule;
    private String m_strPeriod;
    private String m_strOrderBy;
    private String m_strArea;

    private Boolean m_bAgency       = false;
    private Boolean m_bHandicapped  = false;
    private Boolean m_bSalaryOnly   = false;

    public SearchQuery(){}

    //getters
    public long getId() {
        return m_nId;
    }

    public String getJobName() {
        return m_strJobName;
    }

    public String getQuery() {
        return m_strQuery;
    }

    public String getCurrency() {
        return m_strCurrency;
    }

    public String getSalary() {
        return m_strSalary;
    }

    public String getProf() {
        return m_strProf;
    }

    public String getSpecialization() {
        return m_strSpecialization;
    }

    public String getExperience() {
        return m_strExperience;
    }

    public String getEmployment() {
        return m_strEmployment;
    }

    public String getSchedule() {
        return m_strSchedule;
    }

    public String getPeriod() {
        return m_strPeriod;
    }

    public String getOrderBy() {
        return m_strOrderBy;
    }

    public String getArea() {
        return m_strArea;
    }

    public Boolean getAgency() {
        return m_bAgency;
    }

    public Boolean getHandicapped() {
        return m_bHandicapped;
    }

    public Boolean getSalaryOnly() {
        return m_bSalaryOnly;
    }


    //setters
    public void setId(long m_nId) {
        this.m_nId = m_nId;
    }

    public void setJobName(String strJobName) {
        this.m_strJobName = strJobName;
    }

    public void setQuery(String strQuery) {
        this.m_strQuery = strQuery;
    }

    public void setSalaryOnly(Boolean bSalaryOnly) {
        this.m_bSalaryOnly = bSalaryOnly;
    }

    public void setCurrency(String strCurrency) {
        this.m_strCurrency = strCurrency;
    }

    public void setSalary(String strSalary) {
        this.m_strSalary = strSalary;
    }

    public void setProf(String strProf) {
        this.m_strProf = strProf;
    }

    public void setSpecialization(String strSpecialization) {
        this.m_strSpecialization = strSpecialization;
    }

    public void setExperience(String strExperience) {
        this.m_strExperience = strExperience;
    }

    public void setEmployment(String strEmployment) {
        this.m_strEmployment = strEmployment;
    }

    public void setSchedule(String strSchedule) {
        this.m_strSchedule = strSchedule;
    }

    public void setPeriod(String strPeriod) {
        this.m_strPeriod = strPeriod;
    }

    public void setOrderBy(String strOrderBy) {
        this.m_strOrderBy = strOrderBy;
    }

    public void setArea(String strArea) {
        this.m_strArea = strArea;
    }

    public void setAgency(Boolean bAgency) {
        this.m_bAgency = bAgency;
    }

    public void setHandicapped(Boolean bHandicapped) {
        this.m_bHandicapped = bHandicapped;
    }

}
