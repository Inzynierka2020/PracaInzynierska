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

## Deployment with Docker-Compose 

### Locally

Visit [this page](https://docs.docker.com/docker-for-windows/install/) in order to install *Docker for Windows*.

To deploy this server locally, use compose commands listed below - execute them in a folder with **.yml** files in it:

- firstly, to build service(s):

`$ docker-compose build`

- secondly, to deploy:

`$ docker-compose -f docker-compose.yml up`

Then, you can access *https//localhost/api/swagger-ui.html* to see API specification.

In order to TLS work properly, and therefore whole PWA application work properly, a CA Certificate needs to be provided for Nginx (https server), e.g. Self-Signed Certificate. Check **nginx/localhost** for further instructions on how to generate such one. 

Then, Client App must trust this certificate; for Windows/Chrome **double click .pfx* -> *Install certificate* and insert it in *Trusted Root Certification Authorities storage*. For more instructions how to trust certificates on certain OSs, check [this page](http://wiki.cacert.org/FAQ/ImportRootCert).

### Remotely

Same procedure as locally, but with a twist. Remote server needs to have it's own domain for PWA to work and it need to be trusted by Chrome. [Let's Encrypt](https://letsencrypt.org/) certificate can be provided for Nginx for such use. This certificate is trusted by many OS by default. To do so, another .yml file need to be created, e.g. **docker-compose.server.yml** that specifies: 
TODO

Having those, just deploy server with command:

`$ docker-compose -f docker-compose.yml -f docker-compose.server.yml up`


## Linki
- [dokumentacja Angulara](https://angular.io/docs)
- [baza danych zawodów w modelarstwie lotniczym](https://www.f3xvault.com/) i jej [api](https://www.f3xvault.com/?action=api_docs)
