# PayDayBank

Three separate api are designed for the case study, relevant technologies such as SQL / NoSQL Databases and Message Brokers (RabbitMQ) were used in three separate cases related to the case. In PayDayBankApi, you should come to the relevant directory in the terminal and run containers by using 'docker-compose up -d' command as running service in the background.

InternalKycApi : Returning the customer detail information that works with basic authentication (also can be tested with a client such as Postman with InternalUser and InternalPassword with port '8088/details'), the internal service to the Bank is connected to this service with the KycDetailService on the service layer in the Mobile Backend API project and according to tckn.  2 test customer with 10001 and 10002 tckn added in CustomerDetailController constructor that customer detail information is kept in Mongo NoSql.

ExternalIncomeApi: Returning customer income information to be consumed by Mobile Backend API on port '8084/incomes' that income information is kept in Postgresql. Similarly, 2 customers with 10001 and 10002 tckn were added to the constructor in the first call. In the Backend API, if the income information was previously taken from this external service, it is recorded to internal Mongo db for the next time taken from internal db for the existing tckn in the internal db.Caching structure can also be set up for certain periods in a NoSql like Redis because the information may change over time.

PayDayBankApi:  (8090 / loan) The service running as Mobile Backend API is returned here according to different situations such as lack of income information from the paid income service, etc. It is assumed that the calculation process will take a long time, the requests are queued with rabbitmq and evaluated by conditions, and the result is returned by mail. The bank customer information is kept in Mongo NoSql as its internal db, jwt token authentication structure can also be used for Mobile Backend API instead of LDAP system

I wanted to turn each of these services into separate images (upload to docker.hub, etc.) and turn them into a microservice solution. Although I did not receive  errors  for running services int the local port and buiilding the images, I could not run the images, I encountered many errors, although I researched and solved most of them, I could not run the image because I got errors concerning to java such as following error when try to run images in my local place.

C:\Users\umit.acikgoz\Desktop\PayDayBank\ExternalIncomeApi>docker run -t internalkycapi:1.0.5 . Exception in thread "main" java.lang.NoClassDefFoundError: org/springframework/boot/SpringApplication at InternalKycApi.InternalKycApplication.main(InternalKycApplication.java:12) Caused by: java.lang.ClassNotFoundException: org.springframework.boot.SpringApplication at java.net.URLClassLoader.findClass(URLClassLoader.java:382) at java.lang.ClassLoader.loadClass(ClassLoader.java:424) at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349) at java.lang.ClassLoader.loadClass(ClassLoader.java:357)

In addition, simple solutions have been introduced in java spring boot for architectural structures such as IoC DI, which is a bit challenging as someone working with .Net.


