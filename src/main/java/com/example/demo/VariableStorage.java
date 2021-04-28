package com.example.demo;

public class VariableStorage<T> implements IVariableStorage<T> {
    private T t;

    @Override
    public void set(T var) {
        t = var;
    }

    @Override
    public T get() {
        return t;
    }
}