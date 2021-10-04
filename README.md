# Парсер сайтів міських рад. 
## Java версія

Для збірки потрібні:
1. [OpenJDK-13](https://www.debugpoint.com/2020/04/install-latest-java-14-in-ubuntu-18-04-20-04-linux-mint/)
2. [Git](https://linuxhint.com/install-git-linux-mint/)
3. [Maven 3.8.3](https://idroot.us/install-apache-maven-linux-mint-20/)

Після встановлення всіх компонентів потрібно скачати [hidden-interests-java](https://github.com/RuslanAbakarov/hidden-interests-java) проєкт.

> git clone https://github.com/RuslanAbakarov/hidden-interests-java.git

Зібрати за допомогою maven. У папці проєкту на рівні, де лежить файл pom.xml запустить

> mvn clean package

В папці target матимемо 2 jar файли. Наш - hidden-interests-dl-0.0.1-jar-with-dependencies.jar

Із нього потрібно створити Docker image на основі openjdk.

Запуск програми

> java -jar hidden-interests-dl-0.0.1-jar-with-dependencies.jar [список парсерів, розділених пробілами]

Наразі, доступні парсери:

1. CHERKASY_PLENARY_SESSION - [Протоколи пленарних засідань сесій](http://chmr.gov.ua/ua/files.php?s=2&s1=168&s2=391)
2. CHERKASY_BY_NAME_VOTING - [Поіменне голосування міської ради](http://chmr.gov.ua/ua/files.php?s=2&s1=168&s2=391)

