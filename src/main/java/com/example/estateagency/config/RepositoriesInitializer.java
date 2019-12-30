package com.example.estateagency.config;

import com.example.estateagency.models.Property;
import com.example.estateagency.models.PropertyType;
import com.example.estateagency.models.Role;
import com.example.estateagency.models.User;
import com.example.estateagency.repositories.PropertyRepository;
import com.example.estateagency.repositories.PropertyTypeRepository;
import com.example.estateagency.repositories.RoleRepository;
import com.example.estateagency.repositories.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

@Configuration
public class RepositoriesInitializer {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private PropertyTypeRepository propertyTypeRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    InitializingBean init() {

        return () -> {

            if (propertyTypeRepository.findAll().isEmpty()) {//przyjmijmy, że jeśli repozytorium typów jest puste, to trzeba zainicjalizować bazę danych

                PropertyType pt = new PropertyType("Dom");
                propertyTypeRepository.saveAndFlush(pt);
                propertyTypeRepository.saveAndFlush(new PropertyType("Mieszkanie"));
                propertyTypeRepository.saveAndFlush(new PropertyType("Pokój"));
                propertyTypeRepository.saveAndFlush(new PropertyType("Inne"));

                Property p1 =
                        new Property(
                                "Dom Jednorodzinny",
                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur ut ligula non est scelerisque dapibus. Pellentesque aliquam vel augue ut fermentum. Sed sollicitudin mauris diam. Sed mattis convallis nisl congue interdum. ",
                                469000f,
                                new Date(110, 6, 1),
                                true,
                                pt, new Date(117, 7, 22, 4, 32, 34));
                propertyRepository.saveAndFlush(p1);

                Property p2 = new Property();
                p2.setName("Wolny od zaraz pokój 10m2");
                p2.setDescription("Pokój 1 osobowy 10m2 do wynajęcia od zaraz. Pokój umeblowany (łóżko, komoda, mała szafka nocna).  Znajduje się w mieszkaniu 3 pokojowym 72m2 w pełni umeblowanym (lodówka, kuchenka, pralka, duże szafy na korytarzu).");
                p2.setAvailableDate(new Date(107, 3, 21));
                p2.setPrice(700f);
                p2.setPropertyType(pt);
                p2.setCreationDate(new Date());
                propertyRepository.saveAndFlush(p2);

                Property p3 = new Property();
                p3.setName("Mieszkanie, 38 m², Warszawa");
                p3.setDescription("Składa się z salonu (16,5 m2) z wejściem do drugiego pokoju (7 m2), otwartej kuchni (9 m2), łazienki (4 m2) i przedpokoju (1,5 m2). Mieszkanie jest bardzo dobrze rozplanowane, a wysokość 340 cm nadaje przestrzeni. Elewacja kamienicy jest odnowiona. Dachy są wyremontowane. Wymieniona została instalacja wodno-");
                p3.setAvailableDate(new Date(104, 8, 16));
                p3.setPrice(404000f);
                p3.setPropertyType(pt);
                p3.setCreationDate(new Date());
                propertyRepository.saveAndFlush(p3);

                Property p4 = new Property();
                p4.setName("Mieszkanie 4-pokojowe 74 m2");
                p4.setDescription("W pobliżu nadwiślańskich terenów spacerowych.\n" +
                        "\n" +
                        "Powierzchnia mieszkania 74,09 m2, taras o pow. 29,0 m2.\n" +
                        "\n" +
                        "Salon z otwartą kuchnią, 3 sypialnie, łazienka, WC.\n" +
                        "\n" +
                        "Okna w kierunku południowo- zachodnim.\n" +
                        "\n" +
                        "Osiedle zamknięte z ochroną całodobową, ");
                p4.setAvailableDate(new Date(110, 3, 26));
                p4.setPrice(395000f);
                p4.setPropertyType(pt);
                p4.setCreationDate(new Date());
                propertyRepository.saveAndFlush(p4);
            }

            if (roleRepository.findAll().isEmpty() == true) {
                try {
                    Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
                    Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));

                    User user = new User("user", true);
                    user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
                    user.setPassword(passwordEncoder.encode("user"));

                    User admin = new User("admin", true);
                    admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
                    admin.setPassword(passwordEncoder.encode("admin"));

                    User test = new User("useradmin", true);
                    test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
                    test.setPassword(passwordEncoder.encode("useradmin"));

                    userRepository.save(user);
                    userRepository.save(admin);
                    userRepository.save(test);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

}
