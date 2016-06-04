package pik.dao;

import pik.exceptions.SequenceException;

import java.math.BigInteger;

public interface SequenceDao {

    BigInteger getNext(String key) throws SequenceException;

}