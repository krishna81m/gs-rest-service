package com.paycycle.util.devtools;

import java.util.ArrayList;
import java.util.List;

public class DBExecEventData {
//     *       "data": {
//     *         "sql": "",
//     *         "params": [
//     *           {
//     *             "name": "",
//     *             "value": ""
//                    *           }
//     *         ]
//     *       }

    String sql;
    List<DBParam> params = new ArrayList<>();

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public List<DBParam> getParams() {
        return params;
    }

    public void setParams(List<DBParam> params) {
        this.params = params;
    }
}
