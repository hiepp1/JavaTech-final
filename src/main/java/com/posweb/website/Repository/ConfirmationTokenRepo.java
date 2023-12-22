package com.posweb.website.Repository;

import com.posweb.website.Model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("confirmationTokenRepo")
public interface ConfirmationTokenRepo extends CrudRepository<ConfirmationToken, Long> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);


}
