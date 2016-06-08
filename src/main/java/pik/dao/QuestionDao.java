package pik.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pik.dto.CourseInfo;
import pik.dto.QuestionInfo;
import pik.dto.UserInfo;

import java.math.BigInteger;
import java.util.List;


/**
 * Interfejs DAO Fiszek
 */
public interface QuestionDao {
    /**
     * Dodaje nowe pytanie do bazy danych. Automatycznie nadaje mu ID
     *
     * @param questionInfo Obiekt do zapisania
     * @return Zapisany obiekt
     */
    QuestionInfo add(QuestionInfo questionInfo);

    /**
     * Znajduje i nadpisuje wybrane pytanie
     *
     * @param questionInfo  Pytanie
     * @return Nadpisane pytanie
     */
    QuestionInfo update(QuestionInfo questionInfo);

    /**
     * Usuwa wybrane pytanie
     *
     * @param questionInfo Pytanie do usuniecia
     * @return Czy operacja sie powiodla
     */
    Boolean remove(QuestionInfo questionInfo);

    /**
     * Zwraca liste pytan danego kursu
     *
     * @param courseId Id kursu
     * @return Lista pytan
     */
    List<QuestionInfo> getCourseQuestions(BigInteger courseId);

    /**
     * Zwraca partię pytan do odpowiedzenia
     *
     * @param user   Uzytkownik
     * @param course Kurs
     * @param page   Parametry parti
     * @return Partia pytan
     */
    Page<QuestionInfo> getQuestionToAnswer(UserInfo user, CourseInfo course, Pageable page);

    /**
     * Zwraca liczne pytan w danym kursie
     *
     * @param courseId Id kursu
     * @return Liczba pytan
     */
    Long countQuestions(BigInteger courseId);

    /**
     * Liczba pytan w danym kursie, ktore powinny byc przedstawione uzytkownikowi
     *
     * @param user     Uzytkownik
     * @param courseId Id Kursu
     * @return Liczba pytan
     */
    int countActiveQuestions(UserInfo user, BigInteger courseId);
}
