package kp.postgres.repositories;

import java.util.Optional;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import kp.postgres.entities.AccountP;

/**
 * The {@link PanacheRepository} implementation for the {@link AccountP}.
 */
@ApplicationScoped
public class AccountRepository implements PanacheRepository<AccountP> {
	/**
	 * The constructor.
	 */
	public AccountRepository() {
		super();
	}

	/**
	 * Finds the {@link AccountP} by name.
	 *
	 * @param name the {@link AccountP} name
	 * @return the {@link Optional} with the {@link AccountP}
	 */
	public Optional<AccountP> findByName(String name) {
		return Optional.ofNullable(find("name", name).firstResult());
	}
}