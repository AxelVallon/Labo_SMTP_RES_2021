# Labo_SMTP_RES_2021

[![Java CI with Maven](https://github.com/AxelVallon/Labo_SMTP_RES_2021/actions/workflows/maven.yml/badge.svg)](https://github.com/AxelVallon/Labo_SMTP_RES_2021/actions/workflows/maven.yml)

## Description du projet

Ce projet est un client qui permet à son utilisateur d'envoyer automatiquement d'une adresse mails à plusieurs autres adresses email une blague, et sans authentification. 

## Serveur Mock

Vous trouverez dans le dossier `./docker`, les 4 fichiers qui vous permettront de mettre en place votre propre serveur Mock, qui vous permettront de tester cette application, sans avoir besoin d'envoyer les email aux adresse email ciblées. 

Les fichiers pour cette mettre en place cette configuration sont les suivants :

`docker/build_images.sh`: Script pour créer l'image docker

`docker/Dockerfile`: fichier de configuration docker

`docker/MockMock.jar`: binaires du serveur Mock utilié

`docker/run.sh`: Script pour lancer le container

### Procédure installation

**Attention** : Veuillez noter que cette procédure d'installation a été vérifiée sur Windows, mais devrait être fonctionnels sur d'autre système.

**Requirement :** Afin d'utiliser ce serveur Mock, il vous faudra préalablement avoir installé Docker. (https://docs.docker.com/get-docker/)

**Etapes ** 

1. Clonez ce repository

2. Lancer le script build_image.sh

   ```bash
   #!/bin/bash
   
   docker build --tag axel/mock-server .
   ```

   Ce script va simplement build une image en locale avec le dockerfile. La création de l'image peut prendre un peu de temps.

3. Lancer le script run.sh

   ```bash
   #!/bin/bash
   
   # we open 2 ports on the server because we need port 25 for SMTP and port 8282 
   # for the web view of the system
   docker run -p 25:25 -p 8282:8282 axel/mock-server 
   ```

   Ce script va runner le conteneur, et ouvrir les ports 25 et 8282 en local, donc faites attention au fait que vous ne les utilisez pas déjà au préalable. Si vous utilisez déjà un de ces ports, vous pouvez les changer en modifiant les ports utilisé par le serveur dans le ficher `Dockerfile`,  avec une modification de la compilation sur le serveur avec `java -jar MockMock.jar -p SMTPPORT -h WEBPAGEPORT`, et modifier donc aussi après les ouverture de port  locale dans le script `run.sh`.

4. Maintenant que le docker est allumé et fonctionnel, vous pouvez essayer d'accéder à l'interface web du serveur Mock, en accédant à l'adresse http://localhost:8282/. Vous devriez avoir accès à la page suivantes :

   ![image-20210430191616502](./figures/image-20210430191616502.png)


## Configuration

Pour utiliser ce client SMTP, vous devrez effectuer les étapes suivantes :

1. Cloner ce projet

2. Configurer les deux fichiers de configuration 

   1. `./SMTP_Client/config/mails.yaml`

      ```yaml
      messages:
        - subject: "fruits et légumes"
          content: "Comment appelle-t-on une captapulte à salade ?
      
      
          Un lance roquette !!
      
          Aller bisous"
      
        - subject: "Animalerie"
          content: "Bonjour
      
          C'est un éléphant qui rentre dans un café, et plouf!!
      
          Bonne journée!"
      
        - subject: "Cuisine"
          content: "Bonjour!
      
          C'est 2 cookies dans un micro-onde, l'un dit:
      
          - Dis-donc, il fait chaud là dedans !
      
          - Oh mon dieu, un cookie qui parle !!!
      
          Aller salut l'équipe."
      ```

      Ce exemple de fichier est destiné à configurer les message qui seront automatiquement distribué aux groupes, vous devrez suivre la structure ci-dessus, et gardant bien `messages : ` en haut, et créer un `subject` et un `content`, avec le contenu entouré de guillemet.

   2. `./SMTP_Client/config/config.yaml`

      ```yaml
      port: 25
      hostname: "localhost"
      
      groups:
        - sender: paul@paul.ch
          recipients:
            - email: martin@martin.ch
            - email: antoine@antoine.ch
            - email: dimitri@dimitri.ch
      
        - sender: martin@martin.ch
          recipients:
            - email: paul@paul.ch
            - email: antoine@antoine.ch
            - email: dimitri@dimitri.ch
      
      ```

      Ce exemple de fichier de configuration spécifie les options de connexion au serveur SMTP en premier lieu, et dessous vous aurez les différentes groupes de victime que vous définissez, avec expéditive du message, et dessous toutes les adresses mail ciblées. Vous devrez garder la même structure que ci-dessus.

3. Pour exécuter le projet, placer vous dans le dossier `./SMTP_Client ` et effectuer les commandes suivantes :

   ```bash
    mvn clean compile assembly:single #il faut avoir Maven installé, compilation
    java -jar target/SMTP_Client-1.0-SNAPSHOT-jar-with-dependencies.jar
   ```

   Si vos fichiers de configuration sont bien configuré, tout devrait être ok.

   Ainsi l'invite de commande devrait vous afficher ceci:
   
   ![photo_2021-05-02_20-30-33](./figures/gitBashResult.PNG)
   
   Si vous avez 3 groupes vous aurez 3 fois le messages qui s'affiche.
   
   Et après refresh de la page, le serveur mockmock devrait afficher ceci.
   ![photo_2021-05-02_20-30-33](./figures/MockMockResult.PNG)
   
   Si vous cliquez sur le sujet, vous pourrez consulter l'email envoyer. Vous aurez normalement une page similaire à la suivante:
   ![photo_2021-05-02_20-30-33](./figures/ExampleMail.PNG)
   

## Détails techniques

La schéma relationnel de cette application est le suivant :  

![photo_2021-05-02_20-30-33](./figures/diagrammeClasse.PNG)

Vous pouvez observer les éléments suivants sur le schéma : 

- Le client récupère les paramètres de connexion, et la liste des message qui choisit aléatoirement
- sendMails va envoyer les mails pour tous les groupes présents dans le fichier de configuration.
- ConfigPranker récupère les infos du fichier de config et crée les groupes
- MessageSet récupère les messages et donne un message aléatoire quand demandé

#### sendMails

Nous allons seulement présenter un élément important de ce projet, et il s'agit de la fonction qui permet d'envoyer des mail à tous les groupes configurés.

```java
/**
     * Permet d'envoyer les mails à tous les groupes configuré avec des contenu de mail alléatoires
     * @throws IOException
     */
    public void sendMails() throws IOException {
        Random rand = new Random();

        //Parcours des groupes, pour chaque groupe un email est envoyé
        for(Group group : config.getGroups()) {
            /** Configuration des entrées sorties **/
            Socket socket = new Socket(config.getHostname(), config.getPort());
            PrintWriter os =
                    new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8 ));
            /** Fin configuration entrées sorties **/

            /** Convertation d'envoi de mail avec le serveur SMTP **/
            startWith(in.readLine(), "220 ");
            os.println("EHLO server");
            //attente sur 250 HELP
            String tmp;
            do {
                tmp = in.readLine();
                startWith(tmp, "250");
            } while (!tmp.startsWith("250 "));

            os.println("MAIL FROM: <" + group.getSender().getEmail() + ">");
            startWith(in.readLine(), "250 ");

            for (Victim recipient : group.getRecipients()) {
                os.println("RCPT TO: <" + recipient.getEmail() + ">");
                startWith(in.readLine(), "250 ");
            }
            os.println("DATA");
            startWith(in.readLine(), "354 ");

            // Séléection aléatoire d'un message
            Message message = messages.get(rand.nextInt(messages.size()));

            os.println(forgeEmailContent(group, message));
            os.println("\r\n.\r\n");
            startWith(in.readLine(), "250 ");
            os.println("QUIT");
            startWith(in.readLine(), "221 ");
            /** Fin de conversation avec le serveur SMTP **/

            socket.close();
            in.close();
            os.close();
        }
    }
```

Cette fonction permet d'établir des connexions au serveur SMTP, est en fait des conversations avec le serveur SMTP. Ce protocole est soumis à une quantité de norme qui nous assure un comportement à peu près fixe, et l'avantage de cette norme est de pouvoir implémenter ce client qui est fonctionnel dans n'importe quel contexte. 

Donc pour développer cette fonction, nous avons suivi la norme disponible ici : https://tools.ietf.org/html/rfc5321

Elle contient toute les détails du protocole défini par la RFC 5321, soit du protocole SMTP. 

Vous trouverez un exemple tiré du document ci-dessus, qui vous montreras comment fonctionne ce protocole, et vous permettra donc aussi de comprendre comment a été mis en place le client ci-dessus : 

```
S: 220 foo.com Simple Mail Transfer Service Ready
      C: EHLO bar.com
      S: 250-foo.com greets bar.com
      S: 250-8BITMIME
      S: 250-SIZE
      S: 250-DSN
      S: 250 HELP
      C: MAIL FROM:<Smith@bar.com>
      S: 250 OK
      C: RCPT TO:<Jones@foo.com>
      S: 250 OK
      C: RCPT TO:<Green@foo.com>
      S: 550 No such user here
      C: RCPT TO:<Brown@foo.com>
      S: 250 OK
      C: DATA
      S: 354 Start mail input; end with <CRLF>.<CRLF>
      C: Blah blah blah...
      C: ...etc. etc. etc.
      C: .
      S: 250 OK
      C: QUIT
      S: 221 foo.com Service closing transmission channel
```
