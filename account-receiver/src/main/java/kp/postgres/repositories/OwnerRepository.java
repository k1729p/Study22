package kp.postgres.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import kp.postgres.entities.OwnerP;

/**
 * The {@link PanacheRepository} implementation for the {@link OwnerP}.
 */
@ApplicationScoped
public class OwnerRepository implements PanacheRepository<OwnerP> {
}
