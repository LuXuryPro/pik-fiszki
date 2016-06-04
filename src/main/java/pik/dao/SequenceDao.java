package pik.dao;

import pik.exceptions.SequenceException;

public interface SequenceDao {

    long getNext(String key) throws SequenceException;

}