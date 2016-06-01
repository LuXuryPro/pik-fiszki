package dao;

import exceptions.SequenceException;

public interface SequenceDao {

    long getNext(String key) throws SequenceException;

}