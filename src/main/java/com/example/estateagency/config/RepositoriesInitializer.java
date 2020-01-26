package com.example.estateagency.config;

import com.example.estateagency.models.*;
import com.example.estateagency.repositories.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;

@Configuration
public class RepositoriesInitializer {

    private PropertyRepository propertyRepository;
    private PropertyTypeRepository propertyTypeRepository;
    private OfferTypeRepository offerTypeRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ProvinceRepository provinceRepository;
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RepositoriesInitializer(PropertyRepository propertyRepository, PropertyTypeRepository propertyTypeRepository,
                                   OfferTypeRepository offerTypeRepository, UserRepository userRepository,
                                   RoleRepository roleRepository, ProvinceRepository provinceRepository, AddressRepository addressRepository) {
        this.propertyRepository=propertyRepository;
        this.propertyTypeRepository=propertyTypeRepository;
        this.offerTypeRepository=offerTypeRepository;
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.provinceRepository=provinceRepository;
        this.addressRepository=addressRepository;
    }

    @Bean
    public InitializingBean init() {

        return () -> {
            if ( roleRepository.findAll().isEmpty() )
            {
                roleRepository.save( new Role( Role.Types.ROLE_ADMIN ) );
                roleRepository.save( new Role( Role.Types.ROLE_USER ) );
            }
            if ( userRepository.findAll().isEmpty() )
            {
                User admin = new User();
                admin.setUsername( "admin" );
                admin.setPassword( passwordEncoder.encode( "admin" ) );
                admin.setPasswordConfirm( admin.getPassword() );
                admin.setEmail( "admin@admin.pl" );
                admin.setEnabled( true );
                admin.setRoles( new HashSet<>( Arrays.asList( roleRepository.findRoleByType( Role.Types.ROLE_ADMIN ), roleRepository.findRoleByType( Role.Types.ROLE_USER ) ) ) );
                userRepository.save( admin );


                User user = new User();
                user.setUsername( "user" );
                user.setEnabled( true );
                user.setPassword( passwordEncoder.encode( "user" ) );
                user.setPasswordConfirm( user.getPassword() );
                user.setEmail( "user@user.pl" );
                user.setRoles( new HashSet<>( Collections.singletonList( roleRepository.findRoleByType( Role.Types.ROLE_USER ) ) ) );
                userRepository.save( user );

                User user2 = new User();
                user2.setUsername( "user2" );
                user2.setEnabled( true );
                user2.setPassword( passwordEncoder.encode( "user2" ) );
                user2.setPasswordConfirm( user2.getPassword() );
                user2.setEmail( "user2@user2.pl" );
                user2.setRoles( new HashSet<>( Collections.singletonList( roleRepository.findRoleByType( Role.Types.ROLE_USER ) ) ) );
                userRepository.save( user2 );
            }
//            if (roleRepository.findAll().isEmpty() == true) {
//                try {
//                    Role roleUser = roleRepository.save(new Role(Role.Types.ROLE_USER));
//                    Role roleAdmin = roleRepository.save(new Role(Role.Types.ROLE_ADMIN));
//                    Role roleAgent = roleRepository.save(new Role(Role.Types.ROLE_AGENT));
//
//                    User user = new User("user", true);
//                    user.setRoles(new HashSet<>(Arrays.asList(roleUser)));
//                    user.setPassword(passwordEncoder.encode("user"));
//
//                    User admin = new User("admin", true);
//                    admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin)));
//                    admin.setPassword(passwordEncoder.encode("admin"));
//
//                    User test = new User("useradmin", true);
//                    test.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));
//                    test.setPassword(passwordEncoder.encode("useradmin"));
//
//                    User agent = new User("agent", true);
//                    agent.setRoles(new HashSet<>(Arrays.asList(roleAgent, roleUser)));
//                    agent.setPassword(passwordEncoder.encode("agent"));
//
//                    userRepository.save(user);
//                    userRepository.save(admin);
//                    userRepository.save(test);
//                    userRepository.save(agent);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
            if (propertyTypeRepository.findAll().isEmpty()) {//przyjmijmy, że jeśli repozytorium typów jest puste, to trzeba zainicjalizować bazę danych

                PropertyType pt = new PropertyType("Dom");
                propertyTypeRepository.saveAndFlush(pt);
                propertyTypeRepository.saveAndFlush(new PropertyType("Mieszkanie"));
                propertyTypeRepository.saveAndFlush(new PropertyType("Pokój"));
                propertyTypeRepository.saveAndFlush(new PropertyType("Inne"));


                OfferType ot1=new OfferType("Sprzedaż");
                OfferType ot2=new OfferType("Wynajem");
                offerTypeRepository.saveAndFlush(ot1);
                offerTypeRepository.saveAndFlush(ot2);

                // TODO sql script
                Province p1 =new Province("Wielkopolskie");
                provinceRepository.saveAndFlush(p1);
                provinceRepository.saveAndFlush(new Province("Kujawsko-pomorskie"));
                provinceRepository.saveAndFlush(new Province("Małopolskie"));
                provinceRepository.saveAndFlush(new Province("Dolnośląskie"));
                provinceRepository.saveAndFlush(new Province("Lubelskie"));
                provinceRepository.saveAndFlush(new Province("Lubuskie"));
                provinceRepository.saveAndFlush(new Province("Mazowieckie"));
                provinceRepository.saveAndFlush(new Province("Opolskie"));
                provinceRepository.saveAndFlush(new Province("Podlaskie"));
                provinceRepository.saveAndFlush(new Province("Pomorskie"));
                provinceRepository.saveAndFlush(new Province("Śląskie"));
                provinceRepository.saveAndFlush(new Province("Podkarpackie"));
                provinceRepository.saveAndFlush(new Province("Świętokrzyskie"));
                provinceRepository.saveAndFlush(new Province("Warmińsko-Mazurskie"));
                provinceRepository.saveAndFlush(new Province("Zachodniopomorskie"));

                Address a1 = new Address("Warszawa", p1, "Nowa", "12");
                Address a2 = new Address("Kraków", p1, "Słoneczna", "60a");
                Address a3 = new Address("Wrocława", p1, "Długa", "89");
                addressRepository.saveAndFlush(a1);
                addressRepository.saveAndFlush(a2);
                addressRepository.saveAndFlush(a3);




//
//                Property p1 =
//                        new Property(
//                                "Dom Jednorodzinny",
//                                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur ut ligula non est scelerisque dapibus. Pellentesque aliquam vel augue ut fermentum. Sed sollicitudin mauris diam. Sed mattis convallis nisl congue interdum. ",
//                                469000f,
//                                new Date(110, 6, 1),
//                                true,
//                                pt, new Date(117, 7, 22, 4, 32, 34), userRepository.findByUsername("agent"));
//                propertyRepository.saveAndFlush(p1);

                Property p2 = new Property();
                p2.setName("Wolny od zaraz pokój 10m2");
                p2.setDescription("Pokój 1 osobowy 10m2 do wynajęcia od zaraz. Pokój umeblowany (łóżko, komoda, mała szafka nocna).  Znajduje się w mieszkaniu 3 pokojowym 72m2 w pełni umeblowanym (lodówka, kuchenka, pralka, duże szafy na korytarzu).");
                p2.setAvailableDate(new Date(107, 3, 21));
                p2.setPrice(700f);
                p2.setPropertyType(pt);
                p2.setOfferType(ot2);
                p2.setNumberOfRooms(1);
                p2.setAddress(a1);
                p2.setCreationDate(new Date());
                p2.setUser(userRepository.findByUsername("admin"));
                propertyRepository.saveAndFlush(p2);

                Property p3 = new Property();
                p3.setName("Mieszkanie, 38 m², Warszawa");
                p3.setDescription("Składa się z salonu (16,5 m2) z wejściem do drugiego pokoju (7 m2), otwartej kuchni (9 m2), łazienki (4 m2) i przedpokoju (1,5 m2). Mieszkanie jest bardzo dobrze rozplanowane, a wysokość 340 cm nadaje przestrzeni. Elewacja kamienicy jest odnowiona. Dachy są wyremontowane. Wymieniona została instalacja wodno-");
                p3.setAvailableDate(new Date(104, 8, 16));
                p3.setPrice(404000f);
                p3.setPropertyType(pt);
                p3.setOfferType(ot1);
                p3.setNumberOfRooms(3);
                p3.setAddress(a2);
                p3.setCreationDate(new Date());
                p3.setUser(userRepository.findByUsername("user"));
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
                p4.setOfferType(ot1);
                p4.setNumberOfRooms(4);
                p4.setAddress(a3);
                p4.setCreationDate(new Date());
                p4.setUser(userRepository.findByUsername("admin"));
                propertyRepository.saveAndFlush(p4);
            }


        };
    }

}
