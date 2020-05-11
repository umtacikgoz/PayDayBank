# PayDayBank

Vaka ile ilgili üç ayrı yapıda SQL/NoSQL Databases ve Message Brokers(RabbitMQ) gibi ilgili teknolojiler kullanıldı.
PayDayBankApi'de terminalde ilgili dizine gelip 'docker-compose up -d' ile servis olarak arka tarafta ayağa kaldırılmalı.

InternalKycApi : 8088 (8088/details) portunda basic authentication(InternalUser ve InternalPassword ile Postman gibi bir client ile de test edilebilir) ile çalışan müşteri detay bilgilerini dönen Bank'a içi servis Mobile Backend API projesinde servis katmanındaki KycDetailService ile bu servise bağlanıp detay bilgiler tckn ye göre alınıyor CustomerDetailController constructor'da 10001 ve 10002 tckn'li örnek 2 müşteri eklendi detay bilgiler Mongo NoSql'de tutuluyor

ExternalIncomeApi : 8084 (8084/incomes) portunda Mobile Backend API'nin ücretli tüketeceği müşteri gelir bilgilerini dönen servis gelir bilgileri Postgresql'de tutuluyor benzer şekilde ilk çağrıda constructor'da 10001 ve 10002 tckn'li örnek 2 müşteri eklendi bu servis ücretli olduğu için Mobile Backend API'de gelir bilgisi daha önce bu external servis'den alınıp kaydedildiyse tekrar servise gidilmiyor internal Mongo Nosql'den alınıyor bu şekilde banka kendi içinde tutmuş oluyor Burada bilgiler zamana göre değişebilir diye redis gibi bir NoSql'de de belli süreler için caching yapısı da kurulabilir

PayDayBankApi : 8090 (8090/loan) Mobile Backend API olarak çalışan servis burada ücretli gelir servisinden gelir bilgisi dönmediyse vs gibi farklı durumlara göre dönüş yapılıyor hesaplama işlemlerinin uzun süreceği farz edilerek talepler rabbitmq ile kuyruğa alınıp burada koşullara göre değerlendiriliyor sonucu mail ile dönülüyor benzer şekilde constructor'da 10001 ve 10002 tckn'li örnek 2 müşteri eklendi. Banka internal db'si olarak Mongo NoSql'de tutuluyor (Burdaki docker-compose.yml dosyası çalıştırılarak ilgili image'ların servisleri çalıştırılabilir) Vaka'da belirtilen LDAP system java'ya özgü sanırım ama jwt token authentication yapısı da kullanılabilir.

Bu servislerin her birini ayrı image'lar haline getirip(docker.hub'a yükleyip vs) microservice çözümü haline getirmek istedim.Lokalde projeleri çalıştırıp (java -jar ./target/InternalKycApi-1.0-SNAPSHOT.jar gibi) image'ları oluşturmada hata almasam da oluşan image'ları çalıştıramadım birçok hata ile karşılaştım çoğunu araştırıp çözsemde image'ı lokalimde aşağıdaki gibi hatalar aldığım için çalıştıramadım

C:\Users\umit.acikgoz\Desktop\PayDayBank\ExternalIncomeApi>docker run -t internalkycapi:1.0.5 . Exception in thread "main" java.lang.NoClassDefFoundError: org/springframework/boot/SpringApplication at InternalKycApi.InternalKycApplication.main(InternalKycApplication.java:12) Caused by: java.lang.ClassNotFoundException: org.springframework.boot.SpringApplication at java.net.URLClassLoader.findClass(URLClassLoader.java:382) at java.lang.ClassLoader.loadClass(ClassLoader.java:424) at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:349) at java.lang.ClassLoader.loadClass(ClassLoader.java:357) 
  
   
   .Net'de biraz uğraştırıcı olan IoC DI gibi yapılar için java spring boot'da basit çözümler getirilmiş. Java spring boot ile ilk defa ilgilenip bu şekilde bir proje yapıyor olmamdan kaynaklı eksikliklerim de olabilir.  

