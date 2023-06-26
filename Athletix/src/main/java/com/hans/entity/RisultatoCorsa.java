package com.hans.entity;

import com.hans.security.entity.Athlete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RisultatoCorsa extends Results {
    private Athlete athlete;
    private Integer lane;
    private double performance;

}