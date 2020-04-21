package com.paycycle.util.devtools;

import java.util.ArrayList;
import java.util.List;

public class MethodExecEventData {

//    "data": {
//        "signature": "",
//        "params": [
//         {
//            "name": "",
//            "value": ""
//         }
//       ]
//    }

    String signature = "";
    List<MethodParam> inputs = null;
    MethodParam output;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<MethodParam> getInputs() {
        if(inputs == null){
            inputs = new ArrayList<>();
            // keepp inputs null for output methods
        }
        return inputs;
    }

    public void setInputs(List<MethodParam> inputs) {
        this.inputs = inputs;
    }

    public MethodParam getOutput() {
        return output;
    }

    public void setOutput(MethodParam output) {
        this.output = output;
    }
}
