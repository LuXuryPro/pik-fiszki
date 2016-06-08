package pik.dao;

import pik.dto.CourseInfo;
import pik.dto.UserInfo;

import java.math.BigInteger;
import java.util.List;

/**
 * Interfejs DAO Kursu.
 */
public interface CourseDao {

    /**
     * Tworzy nowy kurs, nadaje mu ID i zapisuje go do bazy danych
     *
     * @param course kurs do zapisania
     * @return Obiekt, ktory zapisano do bazy danych lub null w przypadku niepowodzenia
     */
    CourseInfo create(CourseInfo course);

    /**
     * Znajduje i nadpisuje kurs w baze danych
     *
     * @param course kurs z nowymi danymi
     * @return nadpisany kurs lub null w przypadku niepowodzenia
     */
    CourseInfo update(CourseInfo course);

    /**
     * Znajduje i usuwa podany kurs
     *
     * @param course Kurs do usuniecia
     * @return Czy operacja powiodla sie
     */
    Boolean delete(CourseInfo course);

    /**
     * Pobiera z bazy kurs o podanym id
     *
     * @param id Id kursu
     * @return Znaleziony kurs lub null w przypadku niepowodzenia
     */
    CourseInfo get(BigInteger id);

    /**
     * Znajduje kurs o podanym id uzytkownika i nazwie kursu
     *
     * @param userId     Id uzytkownika
     * @param CourseName Nazwa kursu
     * @return the course info
     */
    CourseInfo get(String userId, String CourseName);

    /**
     * Zwraca liste kursow nalezacych do danego uzytkownika
     *
     * @param userId Id uzytkownika
     * @return Lista kursow danego uzytkownika
     */
    List<CourseInfo> getOwnedCourses(String userId);

    /**
     * Zwraca liste kursow, z ktorych danych uzytkownik korzysta
     *
     * @param user Uzytkownik
     * @return Lista kursow
     */
    List<CourseInfo> getSubscribedCourses(UserInfo user);

    /**
     * Sprawdza czy kurs o podanym id istnieje
     *
     * @param id Id kursu
     * @return Czy kurs o podanym id istnieje
     */
    Boolean exists(BigInteger id);

    /**
     * Sprawdza czy kurs o podanym id wlasciciela i nazwie
     *
     * @param username Id wlasciciela
     * @param name     Nazwa
     * @return Czy dany kurs istnieje
     */
    Boolean exists(String username, String name);

    /**
     * Gets all.
     *
     * @return the all
     */
    List<CourseInfo> getAll();

    /**
     * Zwraca liste kursow, do ktorych dany uzytkownik nie jest zapisany
     *
     * @param user Uzytkownik
     * @return Lista kursow
     */
    List<CourseInfo> getUnsubscribed(UserInfo user);
}
