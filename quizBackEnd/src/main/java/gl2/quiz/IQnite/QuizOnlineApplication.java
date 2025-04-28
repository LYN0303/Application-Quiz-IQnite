/*
 * -------------------------------------------------------------------------------
 * Spécifications de la classe `QuizOnlineApplication`
 * -------------------------------------------------------------------------------
 * Cette classe est le point d'entrée principal de l'application Spring Boot. 
 * Elle contient la méthode `main` qui démarre l'application en lançant le serveur 
 * web intégré et charge le contexte Spring, en utilisant l'annotation 
 * `@SpringBootApplication` pour activer la configuration automatique de Spring Boot.
 *
 * -------------------------------------------------------------------------------
 * Points clés :
 * -------------------------------------------------------------------------------
 * - La classe est annotée avec `@SpringBootApplication`, une combinaison de trois annotations :
 *   - `@Configuration` : Indique que cette classe contient des configurations pour l'application.
 *   - `@EnableAutoConfiguration` : Permet à Spring Boot de configurer automatiquement les beans nécessaires à l'application.
 *   - `@ComponentScan` : Active la recherche de composants Spring dans le paquet et sous-paquets.
 *
 * - La méthode `main` lance l'application en appelant `SpringApplication.run()`, ce qui démarre le serveur 
 *   et initialise le contexte Spring. Elle permet à Spring Boot de gérer l'application.
 *
 * -------------------------------------------------------------------------------
 * Utilisation :
 * -------------------------------------------------------------------------------
 * Cette classe n'a pas besoin d'être modifiée manuellement dans la plupart des cas. 
 * Elle est responsable du démarrage de l'application et de l'initialisation du 
 * contexte Spring, et donc elle joue un rôle central dans la configuration et 
 * l'exécution de l'application Spring Boot.
 *
 * -------------------------------------------------------------------------------
 * La classe `QuizOnlineApplication` est un composant essentiel du démarrage de l'application 
 * Spring Boot. Elle permet à Spring de configurer automatiquement les différents 
 * composants du système (comme les contrôleurs, services et dépôts), tout en servant 
 * de point d'entrée à l'application.
 */

package gl2.quiz.IQnite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuizOnlineApplication.class, args);
    }

}
