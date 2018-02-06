package com.example.hoanganh.stickynote;

import java.io.Serializable;

/**
 * Created by gilzard123 on 03/01/2018.
 */

public class Record implements Serializable {
    private int recordId;
    private String recordName;
    private String recordFilepath;
    public Record()  {

    }

    public Record(  String recordName, String recordFilepath) {
        this.recordName = recordName;
        this.recordFilepath = recordFilepath;
    }

    public Record(int recordId, String recordName, String recordFilepath) {
        this.recordId= recordId;
        this.recordName = recordName;
        this.recordFilepath = recordFilepath;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordFilepath() {
        return recordFilepath;
    }

    public void setRecordFilepath(String recordFilepath) {
        this.recordFilepath = recordFilepath;
    }
}
