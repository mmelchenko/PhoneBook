package bk.springRS.repository;

import bk.springRS.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query("select c from Contact c where c.id = :id")
    Contact findById(@Param("id") Long id);

    @Query("select c from Contact c where c.name = :name")
    List<Contact> findByName(@Param("name") String name);

    @Query("select c from Contact c where c.surname = :surname")
    List<Contact> findBySurname(@Param("surname") String surname);

    @Query("select c from Contact c where c.name = :name and c.surname = :surname")
    List<Contact> findByNameAndSurname(@Param("name") String name, @Param("surname") String surname);
}
