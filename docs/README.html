<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<body>
<a href="https://github.com/k1729p/Study22/tree/main/docs"><img src="images/ColorScheme.png" height="25" width="800" alt=""/></a>
<h2 id="contents">Study22 README Contents</h2>

<p>
Topics: Kubernetes ● Docker ● Quarkus ● Kafka ● MongoDB ● PostgreSQL
</p>

<h3 id="top">Research Kubernetes and Quarkus</h3>
<img src="images/MermaidFlowchart1.png" height="400" width="430" alt=""/>

<p>
The Account Receiver is implemented as a Quarkus application with Kafka consumer and JSON REST service.
</p>
<p>
A big list of Kafka records is quickly consumed and stored in the MongoDB document database.<br/>
<img src="images/MermaidFlowchart2.png" height="75" width="485" alt=""/><br>
<img src="images/blackArrowUp.png" alt="">
<i>The data caching strategy is implemented in the Account Receiver.</i>
</p>

<hr>

<p>
The sections of this project:
</p>
<ol>
<li><a href="#ONE"><b>Docker and Kubernetes Build</b></a></li>
<li><a href="#TWO"><b>Account Receiver</b></a></li>
<li><a href="#THREE"><b>Account Sender</b></a></li>
<li><a href="#FOUR"><b>Curl Client</b></a></li>
</ol>

<p>
Java source code. Packages in modules 'account-receiver', 'account-sender':<br>
<img src="images/aquaHR-500.png" alt=""><br>
<img src="images/aquaSquare.png" alt="">
    <i>module 'account-receiver', application sources</i>&nbsp;:&nbsp;
	<a href="https://github.com/k1729p/Study22/tree/main/account-receiver/src/main/java/kp">kp</a><br>
<img src="images/aquaSquare.png" alt="">
    <i>module 'account-receiver', test sources</i>&nbsp;:&nbsp;
	<a href="https://github.com/k1729p/Study22/tree/main/account-receiver/src/test/java/kp">kp</a><br>
<img src="images/aquaSquare.png" alt="">
    <i>module 'account-sender', application sources</i>&nbsp;:&nbsp;
	<a href="https://github.com/k1729p/Study22/tree/main/account-sender/src/main/java/kp/sender">kp.sender</a><br>
<img src="images/aquaHR-500.png" alt="">
</p>

<p>
<img src="images/yellowHR-500.png" alt=""><br>
<img src="images/yellowSquare.png" alt="">
    <a href="http://htmlpreview.github.io/?https://github.com/k1729p/Study22/blob/main/docs/apidocs/index.html">
	Java API Documentation</a>&nbsp;●&nbsp;
    <a href="http://htmlpreview.github.io/?https://github.com/k1729p/Study22/blob/main/docs/testapidocs/index.html">
	Java Test API Documentation</a><br>
<img src="images/yellowHR-500.png" alt="">
</p>

<hr>
<h3 id="ONE">❶ Docker and Kubernetes Build</h3>

<p>Action:<br>
<img src="images/orangeHR-500.png" alt=""><br>
<img src="images/orangeSquare.png" alt=""> 1. With batch file
<a href="https://github.com/k1729p/Study22/blob/main/0_batch/01%20Docker%20run%20Kafka%20MongoDB%20PostgreSQL.bat">
<i>"01 Docker run Kafka MongoDB PostgreSQL.bat"</i></a> create and run<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt="">Docker containers 'kp-kafka', 'kp-mongodb', 'kp-postgresql'.<br>
<img src="images/orangeSquare.png" alt=""> 2. With batch file
<a href="https://github.com/k1729p/Study22/blob/main/0_batch/02%20Docker%20build%20sender%20and%20start.bat">
<i>"02 Docker build sender and start.bat"</i></a> build the Docker image<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt=""> for the Account Sender application and 
start the Docker container 'study22-acc-sender'.<br>
<img src="images/orangeSquare.png" alt=""> 3. With batch file
<a href="https://github.com/k1729p/Study22/blob/main/0_batch/03%20Docker%20build%20receiver%20and%20start%20Quarkus.bat">
<i>"03 Docker build receiver and start Quarkus.bat"</i></a> build the Docker image<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt=""> for the Account Receiver application and 
start the Docker container 'study22-acc-receiver'.<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt="">It compiles the Account Receiver application 
to a native executable and packages this in a container.<br>
<img src="images/orangeSquare.png" alt=""> 4. With batch file
<a href="https://github.com/k1729p/Study22/blob/main/0_batch/04%20Kubernetes%20build.bat">
<i>"04 Kubernetes build.bat"</i></a> create a kind cluster, install Kafka, MongoDB, and PostgreSQL, and<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt="">load Docker images 
for the applications: Account Sender and Account Receiver.<br>
<img src="images/orangeSquare.png" alt=""> 5. With batch file
<a href="https://github.com/k1729p/Study22/blob/main/0_batch/05%20show%20Kubernetes%20logs.bat">
<i>"05 show Kubernetes logs.bat"</i></a> show a Kubernetes logs tail for<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt="">Kafka, MongoDB, PostgreSQL, Account Sender, and Account Receiver.<br>
<img src="images/orangeHR-500.png" alt="">
</p>

<p><img src="images/greenCircle.png" alt="">
1.1. The Docker configuration files are in the directory
<a href="https://github.com/k1729p/Study22/tree/main/docker-config">docker-config</a>.
</p>

<p><img src="images/greenCircle.png" alt="">
1.2. The Kubernetes configuration files are in the directory
<a href="https://github.com/k1729p/Study22/tree/main/kubernetes-config">kubernetes-config</a>.
</p>

<p><img src="images/greenCircle.png" alt="">
1.3. The <a href="images/ScreenshotDockerContainers.png">screenshot</a> of the created Docker containers.
</p>

<p><img src="images/greenCircle.png" alt="">
1.4. The information about Kubernetes (Helm charts, Docker images, Kubernetes cluster info, namespaces,<br/>
services, persistent volumes, deployments, kind-control-plane node, pods) was extracted from the log<br/>
of the batch file
 <a href="https://github.com/k1729p/Study22/blob/main/0_batch/04%20Kubernetes%20build.bat"><i>"04 Kubernetes build.bat"</i></a> 
 and it is <a href="https://github.com/k1729p/Study22/blob/main/docs/texts/KubernetesInformation.txt">here</a>.
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<h3 id="TWO">❷ Account Receiver</h3>
<p><img src="images/greenCircle.png" alt="">
2.1. The Docker container 'study22-acc-receiver' with the Account Receiver application could be started
</p>
<ul>
<li>or directly in the containerization tool <b>docker desktop</b></li>
<li>or with the batch file <a href="https://github.com/k1729p/Study22/blob/main/0_batch/07%20start%20Docker%20Quarkus.bat">
 <i>"07 start Docker Quarkus.bat"</i></a></li>
</ul>

<p>Action:<br>
<img src="images/orangeHR-500.png" alt=""><br>
<img src="images/orangeSquare.png" alt=""> 1. With batch file
 <a href="https://github.com/k1729p/Study22/blob/main/0_batch/07%20start%20Docker%20Quarkus.bat">
 <i>"07 start Docker Quarkus.bat"</i></a>
 start the Docker container with the Account Receiver application.<br>
<img src="images/orangeSquare.png" alt=""><img src="images/spacer-32.png" alt="">Before this batch execution, the application should not be running.<br>
<img src="images/orangeHR-500.png" alt="">
</p>
<p>
The <a href="images/AccountReceiverStart.png">screenshot</a> 
of the Kubernetes pod 'study22-acc-receiver' log from the Quarkus application start.
</p>

<p><img src="images/greenCircle.png" alt="">
2.2. Receive the accounts from Kafka.
</p>
<img src="images/MermaidSequenceDiagram1.png" height="560" width="885" alt=""/>

<p>
The consumer method 
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/java/kp/kafka/consumers/AccountConsumer.java#L49">
kp.kafka.consumers.AccountConsumer::consume</a> consumes the Kafka records.<br/>
The payload with JSON content is deserialized and persisted as an Account entity in the MongoDB database.<br/>
The service method for the MongoDB database 
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/java/kp/services/AccountMongoService.java#L54">
kp.services.AccountMongoService::processPayload</a> creates the MongoDB entity from the Kafka record payload.
</p>
<p>
The <a href="images/AccountReceiverProcessPayload.png">screenshot</a> 
of the Kubernetes pod 'study22-acc-receiver' log from the 26 Kafka records payload processing.
</p>

<p><img src="images/greenCircle.png" alt="">
2.3 Read the account, which is <b>absent</b> in PostgreSQL. 
</p>
<img src="images/MermaidSequenceDiagram2.png" height="925" width="1000" alt=""/>
<p>
The JSON REST service is implemented in class 
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/java/kp/resources/AccountResource.java">
kp.resources.AccountResource</a>.
</p>
<p>
The endpoint method 
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/java/kp/resources/AccountResource.java#L63">
kp.resources.AccountResource::readAccount</a> reads the account from the PostgreSQL database.
In this case, the account is absent in the PostgreSQL database.
It causes that in the next step, the account is read from the MongoDB database and added to the PostgreSQL database.<br/>
The service method for the MongoDB database
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/java/kp/services/AccountMongoService.java#L85">
kp.services.AccountMongoService::findAccount</a> finds the MongoDB entity by name.<br/>
The service method for the PostgreSQL database 
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/java/kp/services/AccountPostgresService.java#L70">
kp.services.AccountPostgresService::createAccount</a> creates a new PostgreSQL entity from the existing MongoDB entity.
</p>
<p>
The <a href="images/AccountReceiverReadAccount.png">screenshot</a> 
of the Kubernetes pod 'study22-acc-receiver' log from two accounts reading.
</p>

<p><img src="images/greenCircle.png" alt="">
2.4. Read the account, which is <b>present</b> in PostgreSQL. 
</p>
<img src="images/MermaidSequenceDiagram3.png" height="615" width="810" alt=""/>

<p><img src="images/greenCircle.png" alt="">
2.5. The Account Receiver frontend.
</p>
<p>
The web resources for Quarkus were placed in the directory
<a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/resources/META-INF/resources"
>'src/main/resources/META-INF/resources'</a>.
<p>
The <a href="https://github.com/k1729p/Study22/blob/main/account-receiver/src/main/resources/META-INF/resources/index.html">home page</a> URL:
</p>
<ul>
<li>on Kubernetes - <a href="http://localhost:32123/">http://localhost:32123/</a></li>
<li>on Docker - <a href="http://localhost:8080/">http://localhost:8080/</a></li>
</ul>

<p>
<img src="images/ScreenshotHomePage.png" height="310" width="515" alt=""/><br>
<img src="images/blackArrowUp.png" alt="">
<i>The screenshot of the home page fragment.</i>
</p>

<p>
Reading the account with the given name.
</p>
<p>
<img src="images/EndpointReadAccount.png" height="320" width="305" alt=""/><br>
<img src="images/blackArrowUp.png" alt="">
<i>The JSON result in the Firefox Browser from the endpoint path '/accounts/a-a-a-a'.</i>
</p>

<p>
The <a href="images/ScreenshotSwaggerUI.png">screenshot</a> of the Swagger UI page.  
</p>
<p>
The <a href="images/ScreenshotOpenApiJson.png">screenshot</a> of the OpenAPI document page.
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<h3 id="THREE">❸ Account Sender</h3>
<p><img src="images/greenCircle.png" alt="">
3.1 The Account Sender application runs the Kafka producer in an endless loop.
</p>

<p>
<img src="images/MermaidFlowchart3.png" height="135" width="325" alt=""/><br>
<img src="images/blackArrowUp.png" alt="">
<i>The Account Sender generates accounts and feeds them to the Kafka broker.</i>
</p>

<p>
The producer method: 
<a href="https://github.com/k1729p/Study22/blob/main/account-sender/src/main/java/kp/sender/kafka/producers/AccountProducer.java#L39">
kp.sender.kafka.producers.AccountProducer::produceRecords</a> produces Kafka records.
</p>
<p>
The <a href="images/AccountSender.png">screenshot</a> of the Kubernetes pod 'study22-acc-sender' log.
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<h3 id="FOUR">❹ Curl Client</h3>

<p>Action:<br>
<img src="images/orangeHR-500.png" alt=""><br>
<img src="images/orangeSquare.png" alt=""> 1. With batch file
 <a href="https://github.com/k1729p/Study22/blob/main/0_batch/06%20CURL%20call%20Quarkus.bat">
 <i>"06 CURL call Quarkus.bat"</i></a>
 call the endpoints on the Quarkus server using the command-line tool curl.<br>
<img src="images/orangeHR-500.png" alt="">
</p>

<p><img src="images/greenCircle.png" alt=""> 4.1. This batch calls the 'read account' endpoint and optionally the 'delete accounts' endpoint.
The endpoint 'delete accounts' deletes all data from all databases.
As a result of that action, the Account Receiver will start anew consuming and processing the Kafka records.
</p>
<p>
<img src="images/CurlDeleteAccounts.png" height="50" width="200" alt=""/><br>
<img src="images/blackArrowUp.png" alt="">
<i>The result from the 'delete accounts' endpoint.</i>
</p>

<a href="#top">Back to the top of the page</a>
<hr>
<h3>Dictionary</h3>
<table style="border:solid">
<tbody>
<tr><td style="border:solid"><b><a href="https://quarkus.io">Quarkus</a></b></td>
<td style="border:solid">The Java framework tailored for deployment on Kubernetes</td></tr>
<tr><td style="border:solid"><b><a href="https://quarkus.io/guides/hibernate-orm-panache">Panache</a></b></td>
<td style="border:solid">The Quarkus-specific library for the development of the Hibernate-based persistence layer (similar to Spring Data JPA)</td></tr>
<tr><td style="border:solid"><b><a href="https://github.com/smallrye/smallrye-open-api">SmallRye OpenAPI</a></b></td>
<td style="border:solid">The extension compliant with the <a href="https://github.com/OAI/OpenAPI-Specification/blob/main/versions/3.0.0.md">OpenAPI v3 specification</a></td></tr>
<tr><td style="border:solid"><b><a href="https://docs.docker.com/engine/reference/run/">Docker CLI</a></b></td>
<td style="border:solid">The Docker command-line interface</td></tr>
<tr><td style="border:solid"><b><a href="https://kubernetes.io/docs/home/">Kubernetes</a></b></td>
<td style="border:solid">The container-orchestration system for automating container deployment, scaling, and management</td></tr>
<tr><td style="border:solid"><b><a href="https://kubernetes.io/docs/reference/kubectl/">kubectl</a></b></td>
<td style="border:solid">The command line tool for communicating with a Kubernetes cluster's control plane</td></tr>
<tr><td style="border:solid"><b><a href="https://kind.sigs.k8s.io/">kind</a></b></td>
<td style="border:solid">The tool for running the local Kubernetes cluster in a Docker container<br/>('Kubernetes in Docker')</td></tr>
<tr><td style="border:solid"><b><a href="https://helm.sh/">Helm</a></b></td>
<td style="border:solid">Helm is the package manager for Kubernetes. <a href="https://helm.sh/docs/glossary/">Helm glossary</a></td></tr>
<tr><td style="border:solid"><b><a href="https://github.com/bitnami/containers">Bitnami Images</a></b></td>
<td style="border:solid">The Bitnami Containers Library is used as a source of Kubernetes images</td></tr>
</tbody>
</table>

<a href="#top">Back to the top of the page</a>
<hr>
</body>
</html>