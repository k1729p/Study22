package kp.postgres.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import kp.postgres.entities.AccountP;

import java.util.Optional;

/**
 * The {@link PanacheRepository} implementation for the {@link AccountP}.
 */
@ApplicationScoped
public class AccountRepository implements PanacheRepository<AccountP> {
    /**
     * Finds the {@link AccountP} by the name.
     *
     * @param name the {@link AccountP} name
     * @return the {@link Optional} with the {@link AccountP}
     */
    public Optional<AccountP> findByName(String name) {
        return Optional.ofNullable(find("name", name).firstResult());
    }
}