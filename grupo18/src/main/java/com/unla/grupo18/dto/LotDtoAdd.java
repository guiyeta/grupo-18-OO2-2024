package com.unla.grupo18.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LotDtoAdd {

    private Long id;
    private Long purchaseOrderId;
    private LocalDate receptionDate;

}
