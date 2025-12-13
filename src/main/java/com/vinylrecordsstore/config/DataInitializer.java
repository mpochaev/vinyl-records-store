package com.vinylrecordsstore.config;

import com.vinylrecordsstore.entities.User;
import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.entities.Order;
import com.vinylrecordsstore.enums.Role;
import com.vinylrecordsstore.enums.Genre;
import com.vinylrecordsstore.enums.OrderStatus;
import com.vinylrecordsstore.repositories.UserRepository;
import com.vinylrecordsstore.repositories.VinylRecordRepository;
import com.vinylrecordsstore.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NullMarked
@Component
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final VinylRecordRepository vinylRecordRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Если данные в БД уже есть, не заполняем
        if (userRepository.count() > 0 || vinylRecordRepository.count() > 0) {
            return;
        }
        log.info("Начинаем заполнение БД!");

        // Создаем юзеров
        List<User> users = new ArrayList<>();
        // Тестовый админ
        User admin = new User();
        admin.setLogin("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(Role.ADMIN);
        users.add(admin);
        // Тестовый юзер
        User defaultUser = new User();
        defaultUser.setLogin("user");
        defaultUser.setEmail("user@example.com");
        defaultUser.setPassword(passwordEncoder.encode("user"));
        defaultUser.setRole(Role.USER);
        users.add(defaultUser);
        // Еще 10 юзеров
        for (int i = 1; i <= 10; i++) {
            User u = new User();
            u.setLogin("user" + i);
            u.setEmail("user" + i + "@example.com");
            u.setPassword(passwordEncoder.encode("user" + i));
            u.setRole(Role.USER);
            users.add(u);
        }
        userRepository.saveAll(users);

        // Создаем виниловые пластинки
        List<VinylRecord> vinylRecords = new ArrayList<>();
        vinylRecords.add(new VinylRecord(null, "Thriller", "Michael Jackson", Genre.COUNTRY, LocalDate.of(2019, 3, 3), "COUNTRY music album by Michael Jackson, released in 2019.", new BigDecimal("40.65"), 2, null, "album1.png"));
        vinylRecords.add(new VinylRecord(null, "Kind of Blue", "Miles Davis", Genre.BLUES, LocalDate.of(1979, 2, 3), "BLUES music album by Miles Davis, released in 1979.", new BigDecimal("20.77"), 3, null, "album2.jpg"));
        vinylRecords.add(new VinylRecord(null, "B*tches Brew", "Miles Davis", Genre.HIPHOP, LocalDate.of(2007, 1, 15), "HIPHOP music album by Miles Davis, released in 2007.", new BigDecimal("22.75"), 17, null, "album3.jpg"));
        vinylRecords.add(new VinylRecord(null, "Symphony No. 5", "Beethoven", Genre.JAZZ, LocalDate.of(1961, 9, 7), "JAZZ music album by Beethoven, released in 1961.", new BigDecimal("39.10"), 11, null, "album4.jpg"));
        vinylRecords.add(new VinylRecord(null, "1989", "Taylor Swift", Genre.JAZZ, LocalDate.of(1998, 3, 10), "JAZZ music album by Taylor Swift, released in 1998.", new BigDecimal("39.59"), 15, null, "album5.jpg"));
        vinylRecords.add(new VinylRecord(null, "Fearless", "Taylor Swift", Genre.JAZZ, LocalDate.of(1984, 5, 24), "JAZZ music album by Taylor Swift, released in 1984.", new BigDecimal("19.56"), 15, null, "album6.png"));
        vinylRecords.add(new VinylRecord(null, "A Night at the Opera", "Queen", Genre.FUNK, LocalDate.of(2015, 9, 9), "FUNK music album by Queen, released in 2015.", new BigDecimal("18.09"), 4, null, "album7.png"));
        vinylRecords.add(new VinylRecord(null, "Requiem", "Mozart", Genre.METAL, LocalDate.of(2000, 1, 20), "METAL music album by Mozart, released in 2000.", new BigDecimal("18.91"), 16, null, "album8.jpg"));
        vinylRecords.add(new VinylRecord(null, "Sketches of Spain", "Miles Davis", Genre.CLASSICAL, LocalDate.of(2009, 4, 27), "CLASSICAL music album by Miles Davis, released in 2009.", new BigDecimal("9.81"), 0, null, "album9.jpg"));
        vinylRecords.add(new VinylRecord(null, "Blue Hawaii", "Elvis Presley", Genre.JAZZ, LocalDate.of(1987, 7, 25), "JAZZ music album by Elvis Presley, released in 1987.", new BigDecimal("31.95"), 15, null, "album10.jpg"));
        vinylRecords.add(new VinylRecord(null, "The Magic Flute", "Mozart", Genre.ROCK, LocalDate.of(1982, 2, 22), "ROCK music album by Mozart, released in 1982.", new BigDecimal("29.68"), 2, null, "album11.jpg"));
        vinylRecords.add(new VinylRecord(null, "Led Zeppelin IV", "Led Zeppelin", Genre.HIPHOP, LocalDate.of(1982, 7, 4), "HIPHOP music album by Led Zeppelin, released in 1982.", new BigDecimal("17.71"), 20, null, "album12.jpg"));
        vinylRecords.add(new VinylRecord(null, "Elvis Is Back!", "Elvis Presley", Genre.BLUES, LocalDate.of(1988, 7, 15), "BLUES music album by Elvis Presley, released in 1988.", new BigDecimal("24.25"), 8, null, "album13.jpg"));
        vinylRecords.add(new VinylRecord(null, "Physical Graffiti", "Led Zeppelin", Genre.POP, LocalDate.of(1956, 12, 12), "POP music album by Led Zeppelin, released in 1956.", new BigDecimal("7.70"), 2, null, "album14.jpg"));
        vinylRecords.add(new VinylRecord(null, "19", "Adele", Genre.COUNTRY, LocalDate.of(1955, 4, 15), "COUNTRY music album by Adele, released in 1955.", new BigDecimal("7.43"), 8, null, "album15.png"));
        vinylRecords.add(new VinylRecord(null, "Back in Black", "AC/DC", Genre.JAZZ, LocalDate.of(1973, 9, 11), "JAZZ music album by AC/DC, released in 1973.", new BigDecimal("19.36"), 11, null, "album16.png"));
        vinylRecords.add(new VinylRecord(null, "Milestones", "Miles Davis", Genre.ELECTRONIC, LocalDate.of(1988, 5, 10), "ELECTRONIC music album by Miles Davis, released in 1988.", new BigDecimal("31.04"), 16, null, "album17.jpg"));
        vinylRecords.add(new VinylRecord(null, "Nocturnes", "Chopin", Genre.COUNTRY, LocalDate.of(2016, 10, 21), "COUNTRY music album by Chopin, released in 2016.", new BigDecimal("15.20"), 15, null, "album18.jpg"));
        vinylRecords.add(new VinylRecord(null, "News of the World", "Queen", Genre.CLASSICAL, LocalDate.of(1970, 5, 14), "CLASSICAL music album by Queen, released in 1970.", new BigDecimal("23.88"), 12, null, "album19.jpg"));
        vinylRecords.add(new VinylRecord(null, "Like a Prayer", "Madonna", Genre.HIPHOP, LocalDate.of(2018, 5, 4), "HIPHOP music album by Madonna, released in 2018.", new BigDecimal("32.82"), 15, null, "album20.jpg"));
        vinylRecords.add(new VinylRecord(null, "21", "Adele", Genre.POP, LocalDate.of(2002, 7, 14), "POP music album by Adele, released in 2002.", new BigDecimal("22.01"), 15, null, "album21.png"));
        vinylRecords.add(new VinylRecord(null, "The Dark Side of the Moon", "Pink Floyd", Genre.POP, LocalDate.of(2011, 11, 28), "POP music album by Pink Floyd, released in 2011.", new BigDecimal("29.55"), 6, null, "album22.png"));
        vinylRecords.add(new VinylRecord(null, "Exodus", "Bob Marley", Genre.FUNK, LocalDate.of(2010, 8, 1), "FUNK music album by Bob Marley, released in 2010.", new BigDecimal("24.33"), 11, null, "album23.png"));
        vinylRecords.add(new VinylRecord(null, "Jazz", "Queen", Genre.BLUES, LocalDate.of(2013, 2, 4), "BLUES music album by Queen, released in 2013.", new BigDecimal("6.75"), 17, null, "album24.png"));
        vinylRecords.add(new VinylRecord(null, "25", "Adele", Genre.ELECTRONIC, LocalDate.of(1959, 8, 9), "ELECTRONIC music album by Adele, released in 1959.", new BigDecimal("30.49"), 8, null, "album25.png"));
        vinylRecords.add(new VinylRecord(null, "Symphony No. 9", "Beethoven", Genre.JAZZ, LocalDate.of(1993, 3, 16), "JAZZ music album by Beethoven, released in 1993.", new BigDecimal("19.35"), 13, null, "album26.jpg"));
        vinylRecords.add(new VinylRecord(null, "30", "Adele", Genre.BLUES, LocalDate.of(1996, 12, 7), "BLUES music album by Adele, released in 1996.", new BigDecimal("12.38"), 19, null, "album27.png"));
        vinylRecords.add(new VinylRecord(null, "Live at the Royal Albert Hall", "Adele", Genre.FUNK, LocalDate.of(1951, 11, 23), "FUNK music album by Adele, released in 1951.", new BigDecimal("7.35"), 5, null, "album28.png"));
        vinylRecords.add(new VinylRecord(null, "Piano Concerto No. 21", "Mozart", Genre.JAZZ, LocalDate.of(1970, 4, 7), "JAZZ music album by Mozart, released in 1970.", new BigDecimal("26.91"), 8, null, "album29.jpg"));
        vinylRecords.add(new VinylRecord(null, "Moonlight Sonata", "Beethoven", Genre.ELECTRONIC, LocalDate.of(2017, 9, 28), "ELECTRONIC music album by Beethoven, released in 2017.", new BigDecimal("37.66"), 18, null, "album30.jpg"));
        vinylRecords.add(new VinylRecord(null, "Wish You Were Here", "Pink Floyd", Genre.CLASSICAL, LocalDate.of(2017, 2, 5), "CLASSICAL music album by Pink Floyd, released in 2017.", new BigDecimal("44.85"), 8, null, "album31.png"));
        vinylRecords.add(new VinylRecord(null, "Master of Puppets", "Metallica", Genre.JAZZ, LocalDate.of(1991, 6, 11), "JAZZ music album by Metallica, released in 1991.", new BigDecimal("41.46"), 16, null, "album32.jpg"));
        vinylRecords.add(new VinylRecord(null, "Discovery", "Daft Punk", Genre.COUNTRY, LocalDate.of(2003, 6, 4), "COUNTRY music album by Daft Punk, released in 2003.", new BigDecimal("45.62"), 8, null, "album33.png"));
        vinylRecords.add(new VinylRecord(null, "In the Wee Small Hours", "Frank Sinatra", Genre.JAZZ, LocalDate.of(1999, 1, 17), "JAZZ music album by Frank Sinatra, released in 1999.", new BigDecimal("17.85"), 14, null, "album34.jpg"));
        vinylRecords.add(new VinylRecord(null, "Ray of Light", "Madonna", Genre.BLUES, LocalDate.of(2019, 6, 10), "BLUES music album by Madonna, released in 2019.", new BigDecimal("11.58"), 15, null, "album35.jpg"));
        vinylRecords.add(new VinylRecord(null, "Random Access Memories", "Daft Punk", Genre.ELECTRONIC, LocalDate.of(1989, 8, 17), "ELECTRONIC music album by Daft Punk, released in 1989.", new BigDecimal("22.78"), 1, null, "album36.png"));
        vinylRecords.add(new VinylRecord(null, "Ride the Lightning", "Metallica", Genre.METAL, LocalDate.of(1972, 3, 25), "METAL music album by Metallica, released in 1972.", new BigDecimal("43.61"), 1, null, "album37.png"));
        vinylRecords.add(new VinylRecord(null, "Skyfall", "Adele", Genre.ROCK, LocalDate.of(1953, 3, 27), "ROCK music album by Adele, released in 1953.", new BigDecimal("8.72"), 0, null, "album38.png"));
        vinylRecords.add(new VinylRecord(null, "Hello", "Adele", Genre.JAZZ, LocalDate.of(1953, 8, 24), "JAZZ music album by Adele, released in 1953.", new BigDecimal("11.29"), 13, null, "album39.jpg"));
        vinylRecords.add(new VinylRecord(null, "My Way", "Frank Sinatra", Genre.JAZZ, LocalDate.of(1984, 4, 7), "JAZZ music album by Frank Sinatra, released in 1984.", new BigDecimal("5.76"), 18, null, "album40.jpg"));
        vinylRecords.add(new VinylRecord(null, "The Wall", "Pink Floyd", Genre.COUNTRY, LocalDate.of(2015, 6, 8), "COUNTRY music album by Pink Floyd, released in 2015.", new BigDecimal("22.90"), 15, null, "album41.jpg"));
        vinylRecords.add(new VinylRecord(null, "Songs for Swingin' Lovers!", "Frank Sinatra", Genre.FUNK, LocalDate.of(1981, 9, 26), "FUNK music album by Frank Sinatra, released in 1981.", new BigDecimal("18.76"), 1, null, "album42.jpg"));
        vinylRecords.add(new VinylRecord(null, "Houses of the Holy", "Led Zeppelin", Genre.ELECTRONIC, LocalDate.of(1964, 9, 26), "ELECTRONIC music album by Led Zeppelin, released in 1964.", new BigDecimal("22.61"), 14, null, "album43.jpg"));
        vinylRecords.add(new VinylRecord(null, "Nevermind", "Nirvana", Genre.BLUES, LocalDate.of(1972, 5, 7), "BLUES music album by Nirvana, released in 1972.", new BigDecimal("16.61"), 19, null, "album44.jpg"));
        vinylRecords.add(new VinylRecord(null, "Highway to Hell", "AC/DC", Genre.METAL, LocalDate.of(1998, 2, 13), "METAL music album by AC/DC, released in 1998.", new BigDecimal("32.22"), 16, null, "album45.jpg"));
        vinylRecords.add(new VinylRecord(null, "Round About Midnight", "Miles Davis", Genre.CLASSICAL, LocalDate.of(1964, 4, 26), "CLASSICAL music album by Miles Davis, released in 1964.", new BigDecimal("28.80"), 2, null, "album46.jpg"));
        vinylRecords.add(new VinylRecord(null, "Tutu", "Miles Davis", Genre.METAL, LocalDate.of(1987, 2, 8), "METAL music album by Miles Davis, released in 1987.", new BigDecimal("47.27"), 9, null, "album47.jpg"));
        vinylRecords.add(new VinylRecord(null, "In Utero", "Nirvana", Genre.ELECTRONIC, LocalDate.of(1991, 4, 13), "ELECTRONIC music album by Nirvana, released in 1991.", new BigDecimal("46.00"), 6, null, "album48.jpg"));
        vinylRecords.add(new VinylRecord(null, "Set Fire to the Rain", "Adele", Genre.FUNK, LocalDate.of(2009, 6, 26), "FUNK music album by Adele, released in 2009.", new BigDecimal("35.35"), 14, null, "album49.png"));
        vinylRecords.add(new VinylRecord(null, "Bleach", "Nirvana", Genre.BLUES, LocalDate.of(1988, 4, 24), "BLUES music album by Nirvana, released in 1988.", new BigDecimal("32.51"), 7, null, "album50.jpg"));

        // Задаем всем SupplyDate
        LocalDateTime now = LocalDateTime.now();
        vinylRecords.forEach(v -> v.setLastSupplyDate(now));
        vinylRecordRepository.saveAll(vinylRecords);

        // Создаем заказы
        List<Order> orders = new ArrayList<>();
        Order order;
        order = new Order(null, users.get(11), vinylRecords.get(7), vinylRecords.get(7).getPrice(), LocalDateTime.of(2025, 12, 3, 17, 51), OrderStatus.PURCHASED);
        orders.add(order);
        // Если не CANCELLED - вычитаем пластинку из общего количества
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(3), vinylRecords.get(47), vinylRecords.get(47).getPrice(), LocalDateTime.of(2025, 11, 26, 8, 0), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(10), vinylRecords.get(27), vinylRecords.get(27).getPrice(), LocalDateTime.of(2025, 12, 6, 18, 51), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(9), vinylRecords.get(38), vinylRecords.get(38).getPrice(), LocalDateTime.of(2025, 12, 4, 2, 24), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(9), vinylRecords.get(26), vinylRecords.get(26).getPrice(), LocalDateTime.of(2025, 11, 28, 16, 14), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(1), vinylRecords.get(48), vinylRecords.get(48).getPrice(), LocalDateTime.of(2025, 12, 2, 11, 44), OrderStatus.PLACED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(5), vinylRecords.get(9), vinylRecords.get(9).getPrice(), LocalDateTime.of(2025, 11, 25, 14, 59), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(2), vinylRecords.get(24), vinylRecords.get(24).getPrice(), LocalDateTime.of(2025, 11, 24, 13, 27), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(5), vinylRecords.get(2), vinylRecords.get(2).getPrice(), LocalDateTime.of(2025, 11, 25, 21, 6), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(7), vinylRecords.get(5), vinylRecords.get(5).getPrice(), LocalDateTime.of(2025, 11, 24, 4, 26), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(6), vinylRecords.get(36), vinylRecords.get(36).getPrice(), LocalDateTime.of(2025, 12, 6, 23, 23), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(4), vinylRecords.get(49), vinylRecords.get(49).getPrice(), LocalDateTime.of(2025, 12, 6, 17, 10), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(2), vinylRecords.get(24), vinylRecords.get(24).getPrice(), LocalDateTime.of(2025, 11, 27, 13, 55), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(6), vinylRecords.get(22), vinylRecords.get(22).getPrice(), LocalDateTime.of(2025, 12, 3, 2, 6), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(11), vinylRecords.get(41), vinylRecords.get(41).getPrice(), LocalDateTime.of(2025, 11, 27, 19, 31), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(4), vinylRecords.get(10), vinylRecords.get(10).getPrice(), LocalDateTime.of(2025, 12, 3, 4, 21), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(9), vinylRecords.get(14), vinylRecords.get(14).getPrice(), LocalDateTime.of(2025, 11, 23, 23, 51), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(1), vinylRecords.get(20), vinylRecords.get(20).getPrice(), LocalDateTime.of(2025, 12, 6, 18, 7), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(10), vinylRecords.get(45), vinylRecords.get(45).getPrice(), LocalDateTime.of(2025, 11, 27, 9, 40), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(11), vinylRecords.get(29), vinylRecords.get(29).getPrice(), LocalDateTime.of(2025, 12, 5, 17, 18), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(9), vinylRecords.get(34), vinylRecords.get(34).getPrice(), LocalDateTime.of(2025, 11, 28, 11, 8), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(10), vinylRecords.get(25), vinylRecords.get(25).getPrice(), LocalDateTime.of(2025, 12, 5, 8, 34), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(2), vinylRecords.get(48), vinylRecords.get(48).getPrice(), LocalDateTime.of(2025, 12, 6, 20, 25), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(3), vinylRecords.get(43), vinylRecords.get(43).getPrice(), LocalDateTime.of(2025, 12, 6, 12, 41), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(10), vinylRecords.get(29), vinylRecords.get(29).getPrice(), LocalDateTime.of(2025, 11, 30, 0, 22), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(2), vinylRecords.get(43), vinylRecords.get(43).getPrice(), LocalDateTime.of(2025, 12, 1, 16, 16), OrderStatus.PLACED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(11), vinylRecords.get(21), vinylRecords.get(21).getPrice(), LocalDateTime.of(2025, 12, 1, 19, 36), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(1), vinylRecords.get(46), vinylRecords.get(46).getPrice(), LocalDateTime.of(2025, 12, 5, 8, 17), OrderStatus.PLACED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(3), vinylRecords.get(32), vinylRecords.get(32).getPrice(), LocalDateTime.of(2025, 12, 1, 4, 46), OrderStatus.PLACED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(11), vinylRecords.get(32), vinylRecords.get(32).getPrice(), LocalDateTime.of(2025, 11, 28, 13, 17), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(3), vinylRecords.get(34), vinylRecords.get(34).getPrice(), LocalDateTime.of(2025, 12, 4, 0, 27), OrderStatus.PLACED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(6), vinylRecords.get(31), vinylRecords.get(31).getPrice(), LocalDateTime.of(2025, 11, 23, 13, 9), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(5), vinylRecords.get(15), vinylRecords.get(15).getPrice(), LocalDateTime.of(2025, 11, 23, 7, 0), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(2), vinylRecords.get(46), vinylRecords.get(46).getPrice(), LocalDateTime.of(2025, 12, 6, 7, 16), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(3), vinylRecords.get(8), vinylRecords.get(8).getPrice(), LocalDateTime.of(2025, 11, 25, 19, 49), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(9), vinylRecords.get(38), vinylRecords.get(38).getPrice(), LocalDateTime.of(2025, 12, 4, 7, 17), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(4), vinylRecords.get(45), vinylRecords.get(45).getPrice(), LocalDateTime.of(2025, 11, 27, 4, 42), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(8), vinylRecords.get(33), vinylRecords.get(33).getPrice(), LocalDateTime.of(2025, 12, 4, 18, 1), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(6), vinylRecords.get(1), vinylRecords.get(1).getPrice(), LocalDateTime.of(2025, 12, 4, 6, 51), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(1), vinylRecords.get(4), vinylRecords.get(4).getPrice(), LocalDateTime.of(2025, 11, 29, 18, 1), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(1), vinylRecords.get(21), vinylRecords.get(21).getPrice(), LocalDateTime.of(2025, 12, 4, 16, 23), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(8), vinylRecords.get(13), vinylRecords.get(13).getPrice(), LocalDateTime.of(2025, 11, 26, 6, 29), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(8), vinylRecords.get(15), vinylRecords.get(15).getPrice(), LocalDateTime.of(2025, 11, 23, 11, 53), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(2), vinylRecords.get(6), vinylRecords.get(6).getPrice(), LocalDateTime.of(2025, 11, 27, 11, 39), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(8), vinylRecords.get(46), vinylRecords.get(46).getPrice(), LocalDateTime.of(2025, 11, 27, 4, 59), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(1), vinylRecords.get(25), vinylRecords.get(25).getPrice(), LocalDateTime.of(2025, 11, 23, 21, 50), OrderStatus.CANCELLED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(4), vinylRecords.get(12), vinylRecords.get(12).getPrice(), LocalDateTime.of(2025, 12, 5, 11, 54), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(5), vinylRecords.get(29), vinylRecords.get(29).getPrice(), LocalDateTime.of(2025, 11, 23, 22, 37), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(9), vinylRecords.get(6), vinylRecords.get(6).getPrice(), LocalDateTime.of(2025, 11, 30, 1, 0), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        order = new Order(null, users.get(4), vinylRecords.get(10), vinylRecords.get(10).getPrice(), LocalDateTime.of(2025, 11, 30, 18, 10), OrderStatus.PURCHASED);
        orders.add(order);
        if (order.getStatus() != OrderStatus.CANCELLED) {
            order.getVinyl().setQuantity(order.getVinyl().getQuantity() - 1);
        }
        orderRepository.saveAll(orders);

        log.info("БД заполнено с {} пользователями, {} пластинками, {} заказами.",
                users.size(), vinylRecords.size(), orders.size());
    }
}