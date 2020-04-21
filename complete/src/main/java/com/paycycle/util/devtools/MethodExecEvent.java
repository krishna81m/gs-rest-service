package com.paycycle.util.devtools;

import com.paycycle.util.devtools.trace.Method;

public class MethodExecEvent extends Event {

    public static enum METHOD_EVENT_TYPES {
        METHOD_BEFORE,
        METHOD_ENTER,
        METHOD_INSIDE,
        METHOD_AFTER,
    }

    MethodExecEventData data;

    public MethodExecEventData getData() {
        return data;
    }

    public void setData(MethodExecEventData data) {
        this.data = data;
    }
}
