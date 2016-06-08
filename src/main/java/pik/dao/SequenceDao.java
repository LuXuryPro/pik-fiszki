package pik.dao;

import pik.exceptions.SequenceException;

import java.math.BigInteger;

/**
 * Interfejs DAO obslugujacy autogeneracje id
 */
public interface SequenceDao {

    /**
     * Zwraca nastepna wartosc sekwencji o podanej nazwie
     *
     * @param key Nazwa sekwencji
     * @return Kolejna wartosc
     * @throws SequenceException W przydaku, gdy sekwencja o podanym ID nie istnieje
     */
    BigInteger getNext(String key) throws SequenceException;

    /**
     * Tworzy sekwencje o podanej nazwie
     *
     * @param key Nazwa
     */
    void insert(String key);

}