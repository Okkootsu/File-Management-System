# Projenin Amacı

Bu proje, kullanıcıların uygulama içinde dosya yedeklemesi yapmasını ve sistemdeki diğer kullanıcılar ile takımlaşma özelliği ile dosya paylaşımı yapmayı ve düzenlemeyi sağlar.
# Projede Kullanılan Teknolojiler

- **Programlama Dilleri:** Proje Java kullanılarak geliştirilmiştir.
- **Kullanılan Kütüphaneler:** Swing, java.io, java.time, java.nio, java.awt, java.util, Spring Framework
- **API:** JDBC MySQL Connector
- **Veritabanı:** MySQL
- **Diğer Araçlar:** Git

# Çalışma Prensibi


- Kullanıcıdan alınan veriler bir mysql server'ında depolanır.
- Mysql connector ile server arasında veri aktarımına izin verilir ve bu sayede sisteme veri akışı sağlanıp sistemden veriler alınabilir.
- Arkadaşlık ilişkileri, kullanıcı, admin bilgileri gibi bilgiler mysql serverındaki uygun tablolarda saklanır ve gerektiğinde bilgiler buradan çekilir.
- Admin sistemdeki loglara erişme, kullanıcıların yedek klasörlerini inceleme, kullanıcıların max depolama sınırını değiştirme gibi yetkilere sahiptir.
- Müşteri ise sisteme dosya yükleyebilmekte bu dosyayı düzenleyebilmekte ve yedekleyebilmektedir. Başka kullanıcılara arkadaşlık isteği göndererek arkdaşı olduğu kullanıcılar ile dosya paylaşabilme yetkisine sahiptir.
- Her kullanıcının genel hesap operasyonları vardır, bunlar: Hesaplarını silme, kullanıcı bilgilerini güncelleyebilme.

# Nasıl Çalışır


1. **Projeyi Kurma:** Projeyi doğrudan indirerek Proje dizininde bulunan DosyaYedeklemeUygulaması.jar dosyasını açarak uygulamaya erişebilirsiniz

- Açabilmek için Java yüklü olmak zorundadır.


2. **Konfigürasyon:** src/main/java/org/example/utils/MysqlConnector.java dosyasındaki sqlConnection , sqlUsername , sqlPassword kısımlarını kendi sunucunuza bağlayın ve gerekli mysql tablolarınızı oluşturun.


3. **SQL Tabloları:** kod dosyasındaki SQL_tables.sql dosyasında gerekli tablo oluşturma kodları verilmiştir.
