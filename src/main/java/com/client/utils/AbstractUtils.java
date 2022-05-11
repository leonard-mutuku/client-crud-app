/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author leonard
 */
public abstract class AbstractUtils {

    public List<LocalDate> getDatesBetweenTwoDates(String from, String to) {
        LocalDate startDate = convertToLocaleDate(from);
        LocalDate endDate = convertToLocaleDate(to);
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(i -> startDate.plusDays(i))
                .collect(Collectors.toList());
    }
    
    public List<String> getDates(String from, String to) {
        List<String> dates = getDatesBetweenTwoDates(from, to).stream().map(dt -> formatLocalDate(dt)).collect(Collectors.toList());
        return dates;
    }
    
    private LocalDate convertToLocaleDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date, formatter);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }        
    }
    
    private String formatLocalDate(LocalDate date) {
        try {
            return date.format(DateTimeFormatter.ofPattern("d MMM yy"));
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
