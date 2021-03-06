package keycloak.scenario._private

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import keycloak.scenario.CommonSimulation
import org.keycloak.benchmark.Config

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
class AdminConsoleSimulation extends CommonSimulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080")
    .disableFollowRedirect
    .inferHtmlResources()
    .acceptHeader("application/json, text/plain, */*")
    .acceptEncodingHeader("gzip, deflate")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:54.0) Gecko/20100101 Firefox/54.0")

  val adminSession = new AdminConsoleScenarioBuilder()
    .openAdminConsoleHome()
    .thinkPause()
    .loginThroughLoginForm()

    .openRealmSettings()

    .thinkPause()
    .openClients()

    .thinkPause()
    .openCreateNewClient()

    .thinkPause()
    .submitNewClient()

    .thinkPause()
    .updateClient()

    .thinkPause()
    .openClients()

    .thinkPause()
    .openClientDetails()

    .thinkPause()
    .openUsers()

    .thinkPause()
    .viewAllUsers()

    .thinkPause()
    .viewTenPagesOfUsers()

    .thinkPause()
    .find20Users()

    .thinkPause()
    .findUnlimitedUsers()

    .thinkPause()
    .findRandomUser()
    .openUser()

    .thinkPause()
    .openUserCredentials()

    .thinkPause()
    .setTemporaryPassword()

    .thinkPause()
    .logout()

    .thinkPause()


  val adminScenario = scenario("AdminConsole").exec(adminSession.chainBuilder)

  setUp(adminScenario.inject(rampUsersPerSec(0.001) to Config.usersPerSec during (Config.rampUpPeriod),
    constantUsersPerSec(Config.usersPerSec) during (Config.warmUpPeriod + Config.measurementPeriod)).protocols(httpProtocol))

}
