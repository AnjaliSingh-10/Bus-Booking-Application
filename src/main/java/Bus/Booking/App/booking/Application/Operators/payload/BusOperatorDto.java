package com.RedBus.Operators.payload;


import com.RedBus.Operators.entity.TicketCost;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusOperatorDto {
    @Id
    private String busId;
   @NotEmpty(message = " please enter busNumber")
    private String busNumber;
    @NotEmpty(message = " please enter busOperatorCompanyName")
    private String busOperatorCompanyName;
    @NotEmpty(message = " please enter driverName")
    private String driverName;
    @NotEmpty(message = " please enter supportStaff")
    private String supportStaff;
    @NotNull(message = " please enter numberSeats")
    private int numberSeats;
   @NotNull(message = " please enter departureTime")
    private LocalTime departureTime;
   @NotNull(message = " please enter arrivalTime")
    private LocalTime arrivalTime;
    @NotNull(message = " please enter departureDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date departureDate;
    @NotNull(message = " please enter arrivalDate ")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date arrivalDate;
    @NotEmpty(message = " please enter departureCity")
    private String departureCity;
    @NotEmpty(message = " please enter arrivalCity ")
    private String arrivalCity;
    @NotEmpty(message = " please enter amenities")
    private String amenities;
    @NotEmpty(message = " please enter busType")
    private String busType;
    @NotNull
    private Double totalTravelTime;


    private TicketCost ticketCost;
    }


