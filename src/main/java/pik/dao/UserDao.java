package pik.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pik.dto.UserInfo;


import java.math.BigInteger;
import java.util.List;

/**
 * Interfejs DAO uzytkownika
 */
public interface UserDao {

    /**
     * Zapisuje uzytkownika do Bazy Danych
     *
     * @param user Uzytkownik
     * @return Zapisany obiekt lub null
     */
    UserInfo create(UserInfo user);

    /**
     * Znajduje uzytkonika o podanym ID
     *
     * @param Id Id uzytkonika
     * @return Znaleziony uzytkownik lub null
     */
    UserInfo getById(String Id);

    /**
     * Zwraca liste uzytkownikow
     *
     * @return Lista Uzytkownikow
     */
    List<UserInfo> readAll();

    /**
     * Zwraca wybrany fragment listy uzytkownikow
     *
     * @param pageable parametry fragmetu
     * @return Fragment listy
     */
    Page<UserInfo> readAll(Pageable pageable);

    /**
     * Nadpisuje dane podanego uzytkownika
     *
     * @param user Uzytkonik zapisu
     * @return Zapisanu obiekt lub null
     */
    UserInfo update(UserInfo user);

    /**
     * Usuwa wybranego uzytkownika
     *
     * @param user Uzytkownik do usuniecia
     * @return Czy operacja powiodla sie
     */
    Boolean delete(UserInfo user);

    /**
     * Czy uzytkownik o podanym id istnieje
     *
     * @param userId Id Uzytkownika
     * @return Czy id istnieje
     */
    Boolean idExists(String userId);

    /**
     * Zapisuje uzytkownika do podanego kursu
     *
     * @param user     Uzytkownik
     * @param courseId Id Kursu
     * @return Czy operacja powiodla sie
     */
    Boolean subscribe(UserInfo user, BigInteger courseId);

    /**
     * Wypisuje uzytkownika z podanego kursu
     *
     * @param user     Uzytkownik
     * @param courseId Id kursu
     * @return Czy operacja powiodla sie
     */
    Boolean unsubscribe(UserInfo user, BigInteger courseId);


}

