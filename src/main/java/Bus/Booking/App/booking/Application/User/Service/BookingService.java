package com.RedBus.User.Service;


import com.RedBus.Operators.entity.BusOperator;
import com.RedBus.Operators.entity.TicketCost;
import com.RedBus.Operators.repository.BusOperatorRepository;
import com.RedBus.Operators.repository.TicketCostRepository;
import com.RedBus.User.Entity.Booking;
import com.RedBus.User.Payload.BookingDetailsDto;
import com.RedBus.User.Payload.PassengerDetails;
import com.RedBus.User.Repository.BookingRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class BookingService {
    @Value("${stripe.secret.key}")
    private String secretKey;
    @Value("${stripe.publishable.key}")
    private String publishableKey;
    private BusOperatorRepository busOperatorRepository;
    private TicketCostRepository ticketCostRepository;
    private BookingRepository bookingRepository;

    public BookingService(BusOperatorRepository busOperatorRepository, TicketCostRepository ticketCostRepository, BookingRepository bookingRepository) {
        this.busOperatorRepository = busOperatorRepository;
        this.ticketCostRepository = ticketCostRepository;
        this.bookingRepository = bookingRepository;
    }


    public BookingDetailsDto createBooking(String busId, String ticketId, PassengerDetails passengerDetails) {
        BusOperator bus = busOperatorRepository.findById(busId).orElseThrow(() -> new NoSuchElementException("BusOperator not found with ID: " + busId));
        TicketCost ticketCost = ticketCostRepository.findById(ticketId).orElseThrow(() -> new NoSuchElementException("TicketCost not found with ID: " + ticketId));
        String PaymentIntent = createPaymentIntent(ticketCost.getCost());
        if (PaymentIntent != null) {
            Booking booking = new Booking();
            String bookingId = UUID.randomUUID().toString();
            booking.setBookingId(bookingId);
            booking.setBusId(bus.getBusId());
            booking.setTicketId(ticketCost.getTicketId());
            booking.setTo(bus.getArrivalCity());
            booking.setFrom(bus.getDepartureCity());
            booking.setBusCompany(bus.getBusOperatorCompanyName());
            booking.setPrice(ticketCost.getCost());
            booking.setFirstName(passengerDetails.getFirstName());
            booking.setLastName(passengerDetails.getLastName());
            booking.setEmail(passengerDetails.getEmail());
            booking.setMobile(passengerDetails.getMobile());
            Booking ticketCreatedDetails = bookingRepository.save(booking);

            BookingDetailsDto dto = new BookingDetailsDto();
            dto.setBookingId(ticketCreatedDetails.getBookingId());
            dto.setFrom(ticketCreatedDetails.getFrom());
            dto.setTo(ticketCreatedDetails.getTo());
            dto.setEmail(ticketCreatedDetails.getEmail());
            dto.setFirstName(ticketCreatedDetails.getFirstName());
            dto.setLastName(ticketCreatedDetails.getLastName());
            dto.setPrice(ticketCreatedDetails.getPrice());
            dto.setEmail(ticketCreatedDetails.getEmail());
            dto.setMobile(ticketCreatedDetails.getMobile());
            dto.setBusCompany(ticketCreatedDetails.getBusCompany());
            dto.setMessage("booking is confirmed");
            return dto;
        } else {
            System.out.println("Error!!");
        }
        return null;
    }

    public String createPaymentIntent(Double amount) {
        Stripe.apiKey = secretKey;
        try {
            PaymentIntent intent = PaymentIntent.create(
                    new PaymentIntentCreateParams.Builder()
                            .setCurrency("usd")
                            .setAmount((long) (amount * 100))
                            .build()
            );

            return generateResponse(intent.getClientSecret());
        } catch (StripeException e) {
            return generateResponse("Payment failed: " + e.getMessage());
        }
    }


    private String generateResponse(String clientSecret) {
        return "{\"clientSecret\":\"" + clientSecret + "\"}";
    }
}


