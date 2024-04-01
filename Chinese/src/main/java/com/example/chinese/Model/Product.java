package com.example.chinese.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id ;

    @NotEmpty(message = "name must not be empty")
   // @Column(columnDefinition = "varchar(20) not null ")
    private String name ;

    @NotEmpty(message = "description must not be empty")
    @Size(min = 5,message ="You must enter at least 5 characters" )
   // @Column(columnDefinition = "varchar(20) not null ")
    private String description ;

    @NotNull(message = "Supplier Id must not be empty")
  //  @Column(columnDefinition = " int not null unique ")
    private Integer supplierId ;

    @NotEmpty(message = " Category must not be empty")
    @Size(min = 5,message ="You must enter at least 5 characters" )
   // @Column(columnDefinition = "varchar(20) not null unique")
    @Pattern(regexp ="^(Living room|bedroom|tables|lighting|outdoor furniture|carpets|dining room and kitchen)$")
    private String category ;

    @NotNull(message = "evaluation must not be empty")
   // @Column(columnDefinition = " int not null unique ")
    @Max(5)
    @Min(0)
    private Double evaluation;

    @NotNull(message = "Price must not be empty")
    @Column(columnDefinition = " double not null ")
    private Double price ;

    private Integer sales=0;



}
