package ru.sber.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sber.backend.entities.Logfile;


@Repository
public interface LoggingRepository extends JpaRepository<Logfile, Long> {


}