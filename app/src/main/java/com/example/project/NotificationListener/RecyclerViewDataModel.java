package com.example.project.NotificationListener;

public class RecyclerViewDataModel {
    int logoId;
    String dataModelTitle;
    String dataModelText;

    public RecyclerViewDataModel(int logoId, String dataModelTitle, String dataModelText) {
        this.logoId = logoId;
        this.dataModelTitle = dataModelTitle;
        this.dataModelText = dataModelText;
    }

    public int getLogoId() {
        return logoId;
    }

    public String getDataModelTitle() {
        return dataModelTitle;
    }

    public String getDataModelText() {
        return dataModelText;
    }
}//public class RecyclerViewDataModel
