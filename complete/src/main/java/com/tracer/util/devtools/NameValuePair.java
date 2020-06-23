package com.tracer.util.devtools;

public class NameValuePair<K, V> {

    K name;
    V value;

    public K getName() {
        return name;
    }

    public void setName(K name) {
        this.name = name;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
