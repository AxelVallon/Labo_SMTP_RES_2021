# Labo_SMTP_RES_2021
## Description du projet

Ce projet est un client qui permet à son utilisateur d'envoyer automatiquement d'une adresse mails à plusieurs autres adresses email une blague, et sans authentification.

## Serveur Mock

Vous trouverez dans le dossier `docker`, 4 fichiers qui vous permettront de mettre en place votre propre serveur Mock, qui vous permettront de tester cette application, sans avoir besoin d'envoyer les email aux adresse email ciblées.

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

   Ce script va runner le conteneur, et ouvrir les ports 25 et 8282 en local, donc faites attention à au fait que vous ne les utilisez pas déjà au préalable. Si vous utilisez déjà un de ces ports, vous pouvez les changer en modifiant les ports utilisé par le serveur dans le ficher `Dockerfile`,  avec une modification de la compilation sur le serveur avec `java -jar MockMock.jar -p SMTPPORT -h WEBPAGEPORT`, et modifier donc aussi après les ouverture de port  locale dans le script `run.sh`.

4. Maintenant que le docker est allumé et fonctionnel, vous pouvez essayer d'accéder à l'interface web du serveur Mock, en accédant à l'adresse http://localhost:8282/. Vous devriez avoir accès à la page suivantes :

   ![image-20210430191616502](./figures/image-20210430191616502.png)

5. Maintenant, vous pouvez vérifier les paramètres de configuration de connexion de l'application pour qu'il se connecte au bon serveur SMTP, dans le fichier

   //TODO explication sur le fichier de configuration



### Contenu rapport

Your report MUST include the following sections:

- **A brief description of your project**: if people exploring GitHub find your repo, without a prior knowledge of the RES course, they should be able to understand what your repo is all about and whether they should look at it more closely.
- **Instructions for setting up a mock SMTP server (with Docker - which you will learn all about in the next 2 weeks)**. The user who wants to experiment with your tool but does not really want to send pranks immediately should be able to use a mock SMTP server. For people who are not familiar with this concept, explain it to them in simple terms. Explain which mock server you have used and how you have set it up.
- **Clear and simple instructions for configuring your tool and running a prank campaign**. If you do a good job, an external user should be able to clone your repo, edit a couple of files and send a batch of e-mails in less than 10 minutes.
- **A description of your implementation**: document the key aspects of your code. It is probably a good idea to start with a class diagram. Decide which classes you want to show (focus on the important ones) and describe their responsibilities in text. It is also certainly a good idea to include examples of dialogues between your client and an SMTP server (maybe you also want to include some screenshots here).