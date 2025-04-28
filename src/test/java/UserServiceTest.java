import exercice1.User;
import exercice1.UserRepository;
import exercice1.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Test
    void testGetUserById() {
        // Création du mock pour UserRepository
        UserRepository userRepository = mock(UserRepository.class);

        // Création d'un objet User fictif
        User mockUser = new User(1, "Amine Se");

        // Définir le comportement du mock pour findUserById
        when(userRepository.findUserById(1)).thenReturn(mockUser);

        // Création de l'instance de UserService avec le mock UserRepository
        UserService userService = new UserService(userRepository);

        // Appel de la méthode à tester
        User user = userService.getUserById(1);

        // Vérification que la méthode findUserById a été appelée avec le bon argument
        verify(userRepository).findUserById(1);

        // Vérification que l'utilisateur retourné est bien celui attendu
        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("Amine Se", user.getName());
    }
}
