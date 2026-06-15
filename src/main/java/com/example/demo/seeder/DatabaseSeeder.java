package com.example.demo.seeder;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@Profile("!test & !noseed")
@RequiredArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final TerrainRepository terrainRepository;
    private final TerrainImageRepository terrainImageRepository;
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final ReviewRepository reviewRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (userRepository.count() > 0) return;

        User owner = new User();
        owner.setName("Alice Dupont");
        owner.setEmail("alice@example.com");
        owner.setPassword("password123");
        userRepository.save(owner);

        User renter = new User();
        renter.setName("Bob Martin");
        renter.setEmail("bob@example.com");
        renter.setPassword("password456");
        userRepository.save(renter);

        Terrain t1 = new Terrain();
        t1.setOwner(owner);
        t1.setTitle("Green Valley Field");
        t1.setDescription("A beautiful football field in the valley.");
        t1.setLocation("Phnom Penh, Cambodia");
        t1.setAreaSize(new BigDecimal("5000.00"));
        t1.setPricePerDay(new BigDecimal("150.00"));
        t1.setIsAvailable(true);
        terrainRepository.save(t1);

        Terrain t2 = new Terrain();
        t2.setOwner(owner);
        t2.setTitle("Sunny Side Stadium");
        t2.setDescription("Full-size pitch with floodlights.");
        t2.setLocation("Siem Reap, Cambodia");
        t2.setAreaSize(new BigDecimal("7000.00"));
        t2.setPricePerDay(new BigDecimal("200.00"));
        t2.setIsAvailable(true);
        terrainRepository.save(t2);

        TerrainImage img1 = new TerrainImage();
        img1.setTerrain(t1);
        img1.setImagePath("/images/t1-main.jpg");
        terrainImageRepository.save(img1);

        TerrainImage img2 = new TerrainImage();
        img2.setTerrain(t1);
        img2.setImagePath("/images/t1-side.jpg");
        terrainImageRepository.save(img2);

        TerrainImage img3 = new TerrainImage();
        img3.setTerrain(t2);
        img3.setImagePath("/images/t2-main.jpg");
        terrainImageRepository.save(img3);

        t1.setMainImage(img1);
        terrainRepository.save(t1);
        t2.setMainImage(img3);
        terrainRepository.save(t2);

        Booking b1 = new Booking();
        b1.setTerrain(t1);
        b1.setRenter(renter);
        b1.setStartDate(LocalDate.now().plusDays(1));
        b1.setEndDate(LocalDate.now().plusDays(3));
        b1.setTotalPrice(new BigDecimal("300.00"));
        b1.setStatus(Booking.Status.approved);
        bookingRepository.save(b1);

        Booking b2 = new Booking();
        b2.setTerrain(t2);
        b2.setRenter(renter);
        b2.setStartDate(LocalDate.now().plusDays(5));
        b2.setEndDate(LocalDate.now().plusDays(6));
        b2.setTotalPrice(new BigDecimal("200.00"));
        b2.setStatus(Booking.Status.pending);
        bookingRepository.save(b2);

        Payment p1 = new Payment();
        p1.setBooking(b1);
        p1.setPaymentMethod("Credit Card");
        p1.setAmountPaid(new BigDecimal("300.00"));
        p1.setPaymentDate(LocalDateTime.now());
        p1.setStatus(Payment.Status.paid);
        p1.setTransactionId("TXN-001");
        paymentRepository.save(p1);

        Review r1 = new Review();
        r1.setTerrain(t1);
        r1.setUser(renter);
        r1.setRating(5);
        r1.setComment("Excellent field, well maintained!");
        reviewRepository.save(r1);

        Review r2 = new Review();
        r2.setTerrain(t2);
        r2.setUser(renter);
        r2.setRating(4);
        r2.setComment("Great stadium, good lighting.");
        reviewRepository.save(r2);

        Favorite fav = new Favorite();
        fav.setUser(renter);
        fav.setTerrain(t1);
        favoriteRepository.save(fav);
    }
}
