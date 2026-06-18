package com.tracker.internship_tracker;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Track {
    private final SimpleStringProperty companyname;
    private final SimpleStringProperty applicationopen;
    private final SimpleBooleanProperty apply;
    private final SimpleBooleanProperty status;

    public Track(String companyname, String applicationopen){
        this.companyname=new SimpleStringProperty(companyname);
        this.applicationopen=new SimpleStringProperty(applicationopen);
        this.apply=new SimpleBooleanProperty(false);
        this.status=new SimpleBooleanProperty(false);
    }

    public String getCompanyname(){
        return companyname.get();
    }
    public void setCompanyname(String value){
        this.companyname.set(value);
    }
    public SimpleStringProperty companynameProperty(){
        return companyname;
    }

    public String getApplicationopen(){
        return applicationopen.get();
    }
    public void setApplicationopen(String value){
        this.applicationopen.set(value);
    }
    public SimpleStringProperty applicationopenProperty(){
        return applicationopen;
    }

    public boolean getApply(){
        return apply.get();
    }
    public void setApply(boolean value){
        this.apply.set(value);
    }
    public SimpleBooleanProperty applyProperty(){
        return apply;
    }


    public boolean getStatus(){
        return status.get();
    }
    public void setStatus(boolean value){
        this.status.set(value);
    }
    public SimpleBooleanProperty statusProperty(){
        return status;
    }
}