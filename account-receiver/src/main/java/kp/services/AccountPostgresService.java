package kp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

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

/**
 * The service for the PostgreSQL database.
 */
@ApplicationScoped
public class AccountPostgresService {
	@SuppressWarnings("java:S6813")
	// switch off Sonarqube rule 'Avoid field dependency injection'
	@Inject
	private AccountRepository accountRepository;
	@SuppressWarnings("java:S6813")
	// switch off Sonarqube rule 'Avoid field dependency injection'
	@Inject
	private MoneyRepository moneyRepository;
	@SuppressWarnings("java:S6813")
	// switch off Sonarqube rule 'Avoid field dependency injection'
	@Inject
	private OwnerRepository ownerRepository;

	/**
	 * The constructor.
	 */
	public AccountPostgresService() {
		super();
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
	 * Creates the {@link AccountP} from the {@link AccountM}.<br/>
	 * <p>
	 * {@link AccountP} is in PostgreSQL database.<br/>
	 * {@link AccountM} is in MongoDB database.
	 *
	 * @param accountM the account with source data
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

		final MoneyP moneyP = createMoney(accountM.money, accountP);
		accountP.setMoney(moneyP);
		final List<OwnerP> ownerList = createOwners(accountM.owners, accountP);
		accountP.setOwners(ownerList);
		accountRepository.persist(accountP);
		return Optional.of(accountP);
	}

	/**
	 * Creates the {@link MoneyP} from the {@link MoneyM}.<br/>
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
	 * Creates the {@link OwnerP} from the {@link OwnerM}.<br/>
	 *
	 * @param ownerMList the owner list with source data
	 * @param accountP   the created new account
	 * @return the created new owner list
	 */
	private List<OwnerP> createOwners(List<OwnerM> ownerMList, AccountP accountP) {

		final List<OwnerP> ownerPList = new ArrayList<>();
		final OwnerP ownerP = new OwnerP();
		Optional.ofNullable(ownerMList).filter(Predicate.not(List::isEmpty)).map(List::getFirst).map(own -> own.name)
				.ifPresent(ownerP::setName);
		ownerP.setAccount(accountP);
		ownerRepository.persist(ownerP);
		ownerPList.add(ownerP);
		return ownerPList;
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

}
