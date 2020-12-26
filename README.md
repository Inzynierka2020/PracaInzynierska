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
W celu uruchomienia lokalnie API aplikacji należy:
- pobrać repozytorium
- posiadać zainstalowanego [Mavena](https://maven.apache.org/download.cgi) oraz [JDK](https://www.oracle.com/java/technologies/javase-jdk8-downloads.html)
- przejść do katalogu APP.API i poleceniem `mvn clean install` skompilować program
- przejść do utworzonego katalogu target i poleceniem `java -jar nazwa-utworzonego-pliku-jar.jar` uruchomić API
Dokumentacja api jest dostępna bezpośrednio w aplikacji pod adresem **.../api/swagger-ui.html**

## Wdrożenie Docker-Compose 

W celu instalacji Dockera należy odwiedzić te strony: [dla Windows](https://docs.docker.com/docker-for-windows/install/) oraz [dla Linuksa](https://docs.docker.com/engine/install/ubuntu/). W przypadku dystrybucji linuksowej, należy poczynić pewnie kroki (np. dodanie użytkownika do grupy *docker*) opisane [tutaj](https://docs.docker.com/engine/install/linux-postinstall/).

Następnie należy doinstalować mechanizm orchiestracji kontenerów [Docker-Compose](https://docs.docker.com/compose/install/).

### Lokalnie
W celu wdrożenia aplikacji lokalnie, należy użyć podanych poniżej komend dla *compose* - uruchomić je w folderze z obecnym plikiem **.yml** :

- po pierwsze, zbududować serwisy i kontenery:

`$ docker-compose build`

- po drugie, wdrożyć:

`$ docker-compose -f docker-compose.yml up`

Aplikacja dostępna jest pod adresem *https//localhost*, a pod *https//localhost/api/swagger-ui.html* dostępna jest specyfikacja API.

### TSL

Aplikacja w celu poprawnego działania musi udostępniać szyfrowane połączenie przy pomocy protokołu TSL. Umożliwia to uruchomienie jej za *reverse-proxy* przy wykorzystaniu *Nginxa*. 

Działanie szyfrowania zapewnia Certifikat CA dostarczony Nginxowi (działającemu jako server http), np. Self-Signed Certificate. Instrukcja jak wygenerować certyfikaty self-signed  znajduje się w pliku **nginx/localhost/howTo.txt**. 

Następnie, aplikacja kliencka musi zaufać temu certfikatowi. Dla systemu Windows i klienta Chrome, **podwójne kliknięcie na .pfx* -> *Install certificate*, a następnie umieścić go w magazynie *Trusted Root Certification Authorities storage*. Więcej instrukcji w zależności od różnych systemów operacyjnych znaleźć można [tutaj](http://wiki.cacert.org/FAQ/ImportRootCert).

### Zdalnie w chmurze

Procedura wygląda podobnie jak przy wdrażaniu lokalnym. Aplikacja wymaga domeny (wymóg HTTPS) i dostarczenia certyfikatów zaufanych przez Chrome. Certyfikaty [Let's Encrypt](https://letsencrypt.org/), jako że zaufane są domyślnie przez wiele systemów, mogą być dostarczone dla Nginxa w takim właśnie celu. Aby to zrobić, należy wykorzystać stworzony na tę potrzebę kolejny plik *.yml*, **docker-compose.server.yml**, którego zadaniem jest wyspecyfikowanie zmiennych środowiskowych zależnych od konkretnej chmury. W tym przypadku, będzie on nadpisywać plick Dockerowy (linia 7. pliku **docker-compose.server.yml**), na którego podstawie którego tworzony będzie kontener z Nginxem podczas *docker-compose*. Ważna również jest tutaj linia 9., która to tworzy wolumen łączący hosta i kontener - tam zachowane będą wszystkie pliki certyfikatów i to stamtąd kontener będzie je pobierał. 

Owy plik **server.Dockerfile** również został już stworzony i umieszczony w folderze **./APP.UI/**.  Jego celem jest dostarczenie odpowiednich plików konfiguracyjnych z systemu hosta i wybudowanie obrazu. Procedury *COPY* w liniach 19. i 20. kopiują pliki hosta (ścieżka po lewej) do kontenera (ścieżka po prawej). W folderze **nginx** stworzono nowy folder z plikami, **server**,w którym powinny znaleźć się specyficzne już dla chmury pliki. Są to kolejno **nginx.conf** i **mime.types**.  

W celu uzyskania plików *Let's Encrypt*, wymaganych jest kilka kroków. 

- podmiana nazwy domeny w linijce z *server_name* w pliku **nginx/server/nginx.conf**
- podmiana nazwy domeny w linijce z *allowedOrigins* w pliku **APP.API/src/main/java/aviationModelling/config/CorsConfig.java** 
- wybudowanie i uruchomienie kontenerów komendą
`$ docker-compose -f docker-compose.yml -f docker-compose.server.yml up --build -d`

- wylistowanie dostępnych kontenerów komedną
`$ docker container ls`

- wydobycie ID kontenera o nazwie obrazu *pracainzynierska_ui*, np. *40a595e7d550*
- uruchomienie shella kontenera komendą
`$ docker exec -it 40a595e7d550 bash`

- uruchomienie komendy Certbota, generującego certyfikaty i podążanie za instrukcjami (wybrać redirect)
`$ certbot --nginx`

- pobranie zawartości zmienonego przez Certbota pliku **nginx.conf** w kontenerze i podmiana na nią w pliku **nginx.conf** w folderze **nginx/server** hosta, np. komendą *cat* w kontenerze; *exit* aby opuścić shell kontenera
`$ cat /etc/nginx/nginx.conf`
\
Od teraz wszystkie wygenerowane pliki z certyfikatami znajdują się w wolumenie hosta, a plik **nginx.conf** daje do nich odpowiednie ścieżki.

Ogólne wdrożenie aplikacji wykonuje się komendą:

`$ docker-compose -f docker-compose.yml -f docker-compose.server.yml up --build -d`

Brak flagi *-d* umożliwia przejrzenie logów kontenerów.

## Linki
- [dokumentacja Angulara](https://angular.io/docs)
- [baza danych zawodów w modelarstwie lotniczym](https://www.f3xvault.com/) i jej [api](https://www.f3xvault.com/?action=api_docs)
- [Docker](https://docs.docker.com/get-started/overview/)
