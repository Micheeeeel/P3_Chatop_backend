Projet Spring Boot Chatop Backend
===============================

Ce projet est le backend de l'application Chatop.

Configuration
-------------

1. Clonez ce dépôt sur votre machine locale.
2. Importez le projet dans votre IDE préféré (par exemple, Eclipse, IntelliJ IDEA).
3. Configurez les informations de la base de données en utilisant vos propres variables d'environnement :

   - `DATABASE_URL` : URL de la base de données
   - `DATABASE_USERNAME` : Nom d'utilisateur de la base de données
   - `DATABASE_PASSWORD` : Mot de passe de la base de données

   Vous pouvez définir ces variables d'environnement dans votre système d'exploitation.

Installation
------------

1. Ouvrez une fenêtre de terminal ou une invite de commandes.
2. Accédez au répertoire racine du projet.
3. Exécutez la commande suivante pour compiler et construire le projet :
mvn clean install

Exécution
---------

Dans la même fenêtre de terminal ou d'invite de commandes, exécutez la commande suivante pour démarrer l'application :
mvn spring-boot:run


Assurez-vous que le port 3001 est disponible sur votre machine. L'application sera accessible à l'adresse suivante : [http://localhost:3001](http://localhost:3001).

Documentation de l'API
----------------------

La documentation de l'API est générée automatiquement et peut être consultée à l'adresse suivante : [http://localhost:3001/v2/api-docs](http://localhost:3001/v2/api-docs).

Auteur
------

Ce projet a été créé par Thomas MICHEL. Vous pouvez le contacter à l'adresse e-mail suivante : michelt.thomas@gmail.com.
 
