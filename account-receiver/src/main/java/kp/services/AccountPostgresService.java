package kp.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import kp.mongo.entities.AccountM;
import kp.mongo.entities.MoneyM;
import kp.mongo.entities.OwnerM;
import kp.postgres.entities.AccountP;
import kp.postgres.entities.MoneyP;
import kp.postgres.entities.OwnerP;
import kp.postgres.repositories.AccountRepository;
import kp.postgres.repositories.MoneyRepository;
import kp.postgres.repositories.OwnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The service for the PostgreSQL database.
 */
@ApplicationScoped
public class AccountPostgresService {
    private final AccountRepository accountRepository;
    private final MoneyRepository moneyRepository;
    private final OwnerRepository ownerRepository;

    /**
     * Parameterized constructor.
     *
     * @param accountRepository the {@link AccountRepository}
     * @param moneyRepository   the {@link MoneyRepository}
     * @param ownerRepository   the {@link OwnerRepository}
     */
    @Inject
    public AccountPostgresService(AccountRepository accountRepository,
                                  MoneyRepository moneyRepository,
                                  OwnerRepository ownerRepository) {
        this.accountRepository = accountRepository;
        this.moneyRepository = moneyRepository;
        this.ownerRepository = ownerRepository;
    }

    /**
     * Finds the {@link AccountP}.
     *
     * @param name the {@link AccountP} name
     * @return the {@link AccountP}
     */
    @Transactional
    public Optional<AccountP> findAccount(String name) {
        return accountRepository.findByName(name);
    }

    /**
     * Creates the {@link AccountP} from the {@link AccountM}.
     * <ul>
     * <li>{@link AccountP} is in PostgreSQL database.</li>
     * <li>{@link AccountM} is in MongoDB database.</li>
     * </ul>
     *
     * @param accountM the account with the source data
     * @return the created new account
     */
    @Transactional
    public Optional<AccountP> createAccount(AccountM accountM) {

        if (Objects.isNull(accountM)) {
            return Optional.empty();
        }
        final AccountP accountP = new AccountP();
        accountP.setName(accountM.name);
        accountP.setNumber(accountM.number);
        accountP.setStatus(accountM.status);
        accountP.setCreatedAt(accountM.createdAt);

        accountP.setMoney(createMoney(accountM.money, accountP));
        accountP.setOwners(createOwners(accountM.owners, accountP));
        accountRepository.persist(accountP);
        return Optional.of(accountP);
    }

    /**
     * Deletes all accounts in PostgreSQL database.
     */
    @Transactional
    public void deleteAllAccounts() {

        ownerRepository.deleteAll();
        accountRepository.deleteAll();
        moneyRepository.deleteAll();
    }

    /**
     * Creates the {@link MoneyP} from the {@link MoneyM}.
     *
     * @param moneyM   the money with source data
     * @param accountP the created new account
     * @return the created new money
     */
    private MoneyP createMoney(MoneyM moneyM, AccountP accountP) {

        final MoneyP moneyP = new MoneyP();
        moneyP.setAmount(moneyM.amount);
        moneyP.setCurrency(moneyM.currency);
        moneyP.setAccount(accountP);
        moneyRepository.persist(moneyP);
        return moneyP;
    }

    /**
     * Creates the {@link OwnerP} from the {@link OwnerM}.
     *
     * @param ownerMList the owner list with source data
     * @param accountP   the created new account
     * @return the created new owner list
     */
    private List<OwnerP> createOwners(List<OwnerM> ownerMList, AccountP accountP) {

        final List<OwnerP> ownerPList = new ArrayList<>();
        final Consumer<OwnerM> consumer = ownerM -> {
            final OwnerP ownerP = new OwnerP();
            ownerP.setName(ownerM.name);
            ownerP.setAccount(accountP);
            ownerRepository.persist(ownerP);
            ownerPList.add(ownerP);
        };
        Optional.ofNullable(ownerMList).filter(Predicate.not(List::isEmpty))
                .ifPresent(owners -> owners.forEach(consumer));
        return ownerPList;
    }

}
