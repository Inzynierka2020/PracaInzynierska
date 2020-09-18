# Praca Inżynierska 2020

## Aplikacja do komunikacji z centralną bazą danych zawodów w modelarstwie lotniczym
- [dokumentacja projektu](https://inzynierka2020.github.io/PracaInzynierska/)
- [UI aplikacji](https://inzynierka2020.github.io/PracaInzynierskaUI/App/)
## Użyte technologie:
- Angular 7

### Angular
Część angularowa jest dostępna do wglądu pod tym [adresem](https://inzynierka2020.github.io/PracaInzynierskaUI/App/).

W celu uruchomienia lokalnie projektu angularowego należy:
- pobrać repozytorium
- posiadać [Node.js](https://nodejs.org/en/) i instalowany wraz z nim npm 
- poleceniem `npm install -g @angular/cli` zainstalować Angulara
- w folderze APP.UI wpisać polecenie `npm install`, które na podstawie pliku **package.json** zainstaluje wszystkie wymagane pakiety
- od teraz do uruchomienia projektu służy polecenie `ng serve`

### Spring
<<<<<<< HEAD
W celu uruchomienia lokalnie API aplikacji należy:
- pobrać repozytorium
- posiadać zainstalowanego [Mavena](https://maven.apache.org/download.cgi) oraz [JDK](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- przejść do katalogu APP.API i poleceniem `mvn clean install` skompilować program
- przejść do utworzonego katalogu target i poleceniem `java -jar nazwa-utworzonego-pliku-jar.jar` uruchomić API
Dokumentacja api jest dostępna bezpośrednio w aplikacji pod adresem .../api/swagger-ui.html

=======
Dokumentacja api jest dostępna bezpośrednio w aplikacji pod adresem **.../swagger-ui.html**
>>>>>>> a72f7431eb721ac26efd59c76efb584c6a74a83f
## Docker

## Linki
- [dokumentacja Angulara](https://angular.io/docs)
- [baza danych zawodów w modelarstwie lotniczym](https://www.f3xvault.com/) i jej [api](https://www.f3xvault.com/?action=api_docs)
