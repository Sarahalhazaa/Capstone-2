package com.example.chinese.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id ;

    @NotNull(message = "It must not be empty")
   // @Column(columnDefinition = " int not null unique ")
    private Integer customerId ;

    @NotNull(message = "It must not be empty")
  //  @Column(columnDefinition = " int not null ")
    private Integer productId1 ;


    //  @Column(columnDefinition = " int ")
  private Integer deliverId1 ;

    @NotNull(message = "It must not be empty")
   @Column(columnDefinition = " double not null ")
    private Double tax ;

    @NotNull(message = "It must not be empty")
  //  @Column(columnDefinition = " int not null ")
    private Integer deliveryPrice=50 ;

    @NotNull(message = "It must not be empty")
   // @Column(columnDefinition = " double not null ")
    private Double totalPrice ;

    @NotEmpty(message = "It must not be empty")
  //  @Column(columnDefinition = "varchar(20) not null ")
    @Pattern(regexp ="^(Order is being executed|Shipment has arrived at warehouse|Delivery is in progress|Delivered)$")
    private String status ;



//    @Pattern(regexp = "YYYY/MM/DD")
//    private LocalDate orderDate;

//    @Pattern(regexp = "YYYY/MM/DD")
//    private Date deliveryDate;


}
