package com.dt0622.thetoolrental.service;

import org.springframework.stereotype.Service;
import java.lang.Math;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

@Service
public class DateService {
  SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yy");

  public static int numberOfHolidays(Date startDate, Date endDate) {
    return 0;
  }

}
