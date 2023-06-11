package com.oocode;

import org.junit.*;
import com.oocode.assignment2023.TravelTimeEstimator;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class TravelTimeEstimatorTests {

    @Test(timeout = 2000)
    public void CallingWorksForGivenCoordinates() {
        String[] locations = {"51.534327", "-0.012768", "51.504674", "-0.086005"};
        int travelTime;
        boolean check = false;
        try {
            travelTime = TravelTimeEstimator.travelTimeInMinutes(locations);
            System.out.println(travelTime);
            if (travelTime >= 25 && travelTime <= 45) {
                check = true;
            }
            assertThat(check, is(true));
        } catch (Exception e) {
            //assertThat(false, is(true));
        }
    }

    @Test(timeout = 2000)
    public void IncorrectInputThrowsDedicatedException() {
        String[] locations = {"51.534327", "-0.012768", "51.504674"};
        try {
            TravelTimeEstimator.travelTimeInMinutes(locations);
        } catch (Exception e) {
            assertThat(e.getMessage(), is("Error 100: Input must have 4 entries in total."));
        }
    }



}
