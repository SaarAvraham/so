package com.example.demo;

public class ITextStorageImpl<T extends CharSequence> implements ITextStorage<T> {
    @Override
    public int countMatchingCharacters(ITextStorage<?> other) {
        return 0;
    }

    @Override
    public void set(T var) {

    }

    @Override
    public T get() {
        return null;
    }
}
