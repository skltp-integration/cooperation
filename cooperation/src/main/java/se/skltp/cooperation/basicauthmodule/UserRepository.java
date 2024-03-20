package se.skltp.cooperation.basicauthmodule;

import org.springframework.data.jpa.repository.JpaRepository;
import se.skltp.cooperation.basicauthmodule.model.ServiceUser;

public interface UserRepository extends JpaRepository<ServiceUser, String> {
}
