package kp.postgres.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import kp.postgres.entities.MoneyP;

/**
 * The {@link PanacheRepository} implementation for the {@link MoneyP}.
 */
@ApplicationScoped
public class MoneyRepository implements PanacheRepository<MoneyP> {
}
