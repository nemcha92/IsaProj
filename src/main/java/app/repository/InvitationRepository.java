package app.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.model.Invitation;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

}
