package com.paycycle.util.devtools;

import java.util.Date;

public class DBExecEvent extends Event {

//    "method": {
//        "sql": "",
//                "params": [
//        {
//            "name": "",
//                "value": ""
//        }
//        ]
//    }
    public static enum DB_EXEC_EVENT_TYPES {
        SQL_QUERY,
        SQL_INSERT,
    }

    public DBExecEvent() {
        this.eventType = DB_EXEC_EVENT_TYPES.SQL_QUERY.name();
        this.startTime = new Date();
    }

    public DBExecEvent(DBExecEventData data) {
        this.data = data;
    }

    DBExecEventData data;

    public DBExecEventData getData() {
        return data;
    }

    public void setData(DBExecEventData data) {
        this.data = data;
    }
}
