package com.example.demo;

public interface IVariableStorage<T> {
    public void set(T var);
    public T get();
}
