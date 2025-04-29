# Étapes de Lancement

## 1. Démarrer ta base de données (MySQL/phpMyAdmin)

- Lance ton serveur local (XAMPP, WAMP, MAMP…).
- Vérifie que ta base de données existe dans phpMyAdmin.
- Vérifie les identifiants dans application.properties (username, password, nom_de_base).

## 2. Démarrer ton BackEnd (Spring Boot)

###Deux possibilités :

####Depuis IntelliJ IDEA :

- Clic droit sur QuizOnlineApplication.java

- ➔ Run 'QuizOnlineApplication.main()'

####Ou via le terminal :

bash
CopierModifier
cd quizBackEnd
./mvnw spring-boot:run    # (ou mvnw.cmd sous Windows)
Ton API Spring Boot sera lancée sur :
👉 http://localhost:8080/

## 3. Démarrer ton FrontEnd (Vite.js)
Dans un terminal, dans ton dossier quizFrontEnd :

bash
CopierModifier
cd quizFrontEnd
npm install       # (à faire une seule fois si pas encore fait)
npm run dev
Ton FrontEnd sera disponible sur :
👉 http://localhost:5177/


## 4. Vérifier que tout fonctionne
- Accède à http://localhost:5177/ dans ton navigateur.

- Ton FrontEnd devrait communiquer avec ton BackEnd.

- Si tu as configuré le proxy dans vite.config.js, les appels /api/* sont redirigés automatiquement vers Spring Boot.