package com.RedBus.User.Controller;

import com.RedBus.User.Payload.BookingDetailsDto;
import com.RedBus.User.Payload.PassengerDetails;
import com.RedBus.User.Service.BookingService;
import com.RedBus.User.Util.EmailService;
import com.RedBus.User.Util.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/user")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PdfGenerationService pdfGenerationService;

    @PostMapping ("/bookings")
    public ResponseEntity<String> bookBus(
            @RequestParam("busId") String busID,
            @RequestParam("ticketId") String ticketId,
            @RequestBody PassengerDetails passengerDetails) {

        // Assuming you have logic to validate and process the booking
        BookingDetailsDto  bookingDetails = bookingService.createBooking(busID, ticketId, passengerDetails);

        // Generate PDF for booking details
        byte[] pdfAttachment = pdfGenerationService.generatePassengerDetailsPdf(bookingDetails);

        // Send email with the PDF attachment
        String toEmail = passengerDetails.getEmail();
        String subject = "Bus Booking Details";
        String body = "Thank you for booking with us! Find your booking details attached.";

        emailService.sendEmailWithAttachment(toEmail, subject, body, pdfAttachment, "BookingDetails.pdf");

        return ResponseEntity.ok("Booking successful. Confirmation email sent.");
    }
}