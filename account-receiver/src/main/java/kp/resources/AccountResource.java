package kp.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import kp.Constants;
import kp.mongo.entities.AccountM;
import kp.postgres.entities.AccountP;
import kp.services.AccountMongoService;
import kp.services.AccountPostgresService;
import org.jboss.logging.Logger;

import java.util.Optional;

/**
 * Quarkus uses JAX-RS to define REST-ful web APIs.
 * <p>
 * It is now recommended to use RESTEasy Reactive, which supports both
 * traditional blocking workloads and reactive workloads equally well.
 * </p>
 * <ul>
 * <li><a href="https://quarkus.io/guides/rest-json">
 *     Writing JSON REST Services</a></li>
 * <li><a href="https://quarkus.io/guides/resteasy-reactive">
 *     Writing REST Services with RESTEasy Reactive</a></li>
 * </ul>
 */
@Path("")
public class AccountResource {
    private final Logger logger;
    private final AccountMongoService accountMongoService;
    private final AccountPostgresService accountPostgresService;

    /**
     * Parameterized constructor.
     *
     * @param logger                 the {@link Logger}
     * @param accountMongoService    the {@link AccountMongoService}
     * @param accountPostgresService the {@link AccountPostgresService}
     */
    @Inject
    public AccountResource(Logger logger,
                           AccountMongoService accountMongoService,
                           AccountPostgresService accountPostgresService) {
        this.logger = logger;
        this.accountMongoService = accountMongoService;
        this.accountPostgresService = accountPostgresService;
    }

    /**
     * Reads the single {@link AccountP} from PostgreSQL database.
     * <p>
     * If account with this name does not exist in PostgreSQL database, then
     * search the account with this name in MongoDB database and
     * from that MongoDB account create the new account in PostgreSQL database.
     * </p>
     *
     * @param name the name of the account
     * @return the {@link Response} with the {@link AccountP}
     */
    @GET
    @Path("accounts/{name}")
    public Response readAccount(@PathParam("name") String name) {

        final Optional<AccountP> accountPFoundOpt = accountPostgresService.findAccount(name);
        if (accountPFoundOpt.isPresent()) {
            final AccountP accountP = accountPFoundOpt.get();
            logger.infof("readAccount(): found account in PostgreSQL, id[%d], name[%s]",
                    accountP.getId(), accountP.getName());
            return Response.ok(accountP).build();
        }
        final Optional<AccountM> accountMOpt = accountMongoService.findAccount(name);
        if (accountMOpt.isEmpty()) {
            logger.errorf("readAccount(): account not found in MongoDB, name[%s]", name);
            throw new NotFoundException();
        }
        final AccountM accountM = accountMOpt.get();
        logger.infof("""
                        readAccount(): found account in MongoDB, %s, name[%s],
                        number[%s], status[%s], createdAt[%tF %<tT], money amount[%s], currency[%s], owner[%s]""",
                Constants.MONGO_ID_FUN.apply(accountM), accountM.name, accountM.number, accountM.status,
                accountM.createdAt, accountM.money.amount, accountM.money.currency, accountM.owners.getFirst().name);
        final Optional<AccountP> accountPCreatedOpt = accountPostgresService.createAccount(accountM);
        if (accountPCreatedOpt.isEmpty()) {
            logger.errorf("readAccount(): account not created in PostgreSQL, name[%s]", name);
            throw new NotFoundException();
        }
        final AccountP accountP = accountPCreatedOpt.get();
        logger.infof("""
                        readAccount(): created account in PostgreSQL, id[%d], name[%s],
                        number[%d], status[%s], createdAt[%tF %<tT], money amount[%s], currency[%s], owner[%s]""",
                accountP.getId(), accountP.getName(), accountP.getNumber(), accountP.getStatus(),
                accountP.getCreatedAt(), accountP.getMoney().getAmount(), accountP.getMoney().getCurrency(),
                accountP.getOwners().getFirst().getName());
        return Response.ok(accountP).build();
    }

    /**
     * Deletes all accounts in databases: PostgreSQL and MongoDB.
     *
     * @return the {@link Response}
     */
    @DELETE
    @Path("delete")
    public Response deleteAllAccounts() {

        accountPostgresService.deleteAllAccounts();
        accountMongoService.deleteAllAccounts();
        logger.info("deleteAllAccounts():");
        return Response.noContent().build();
    }

}