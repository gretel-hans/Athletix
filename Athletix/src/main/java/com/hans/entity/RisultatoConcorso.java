package com.hans.entity;

import java.util.List;

import com.hans.security.entity.Athlete;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RisultatoConcorso extends Results {
    private Athlete athlete;
    private List<Double>  performance;

}