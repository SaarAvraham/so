package com.example.demo;

public interface ITextStorage<T extends CharSequence> extends IVariableStorage<T> {
    /**
     * Counts the number of equal characters in same positions of
     * the texts stored in this ITextStorage and the other storage .
     * Example : ’abcdef ’ and ’abba ’ have two matching characters
     * in the first two positions .
     *
     * @param other the other text storage
     * @return the number of matching characters
     */
    int countMatchingCharacters(ITextStorage<?> other);
}