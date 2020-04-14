package com.paycycle.util.devtools;

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

    DBExecEventData data;

    public DBExecEventData getData() {
        return data;
    }

    public void setData(DBExecEventData data) {
        this.data = data;
    }
}
