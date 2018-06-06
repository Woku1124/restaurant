# README


Wymagane oprogramowanie:
1. JDK 8 lub 9 (na 10 nie testowałem)
2. Maven 3.3.9+ (albo ten wbudowany w IntelliJ)
3. PostgreSQL (https://www.enterprisedb.com/downloads/postgres-postgresql-downloads)


Instrukcja:
1. Przed zainstalowaniem PostgreSQL ręcznie utworzyć katalog o nazwie "PostgreSQL", a w nim utworzyć katalog "data".
Następnie nadać grupie Users (Windows) pełne uprawnienia do katalogu data. Wtedy można przejść do instalacji w utworzonym wcześniej katalogu "PostgreSQL". Hasło przy instalacji ustawić na "postgres", nazwę bazy danych na "restaurant", reszta domyślnie.
2. Po instalacji można wejść w panel sterowania -> narzędzia administracyjne -> usługi i wyłączyć domyślne włączanie serwera postgres z systemem, jak kto woli. Ale wtedy trzeba pamiętać żeby przed uruchomieniem aplikacji włączać ten serwer ręcznie (można to robić z tego samego miejsca).
3. Po pobraniu projektu wykonujemy "mvn clean install" lub "clean install" jesli korzystamy z wbudowanego mavena.
4. Po pomyślnym zbudowaniu uruchamiamy aplikację (w IntelliJ pierwsze uruchomienie to kliknięcie zielonej strzałki po lewo od "public class RestaurantApplication" w RestaurantApplication.java).
5. Wchodzimy na http://localhost:8080/api/swagger-ui.html i weryfikujemy czy wszystko działa.