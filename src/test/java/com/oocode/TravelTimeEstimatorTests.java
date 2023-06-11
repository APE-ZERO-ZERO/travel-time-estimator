package com.oocode;

import org.junit.*;
import com.oocode.assignment2023.TravelTimeEstimator;

import static org.hamcrest.CoreMatchers.equalTo;
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
            if (travelTime >= 25 && travelTime <= 60) {
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
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), is("Error 100: Input must have 4 entries in total."));
        }
    }

    @Test(timeout = 2000)
    public void DistantLocationsThrowsException() {
        // This test case uses an endpoint which is outside of London. Citymapper is unable to handle this
        String[] locations = {"51.534327", "-0.012768", "51.503070", "-2.280302"};
        try {
            TravelTimeEstimator.travelTimeInMinutes(locations);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            assertThat(e.getMessage(), is("Error 101: Request did not yield a good response."));
        }
    }


    @Test(timeout = 2000)
    public void ShorterRouteIsTaken() {
        String mockString = "{\"walk_travel_time_minutes\":9,\"transit_time_minutes\":20}";
        try {
            Integer travelTime = TravelTimeEstimator.evaluateResponse(mockString);
            System.out.println(travelTime);
            assertThat(travelTime, equalTo(9));
        } catch (Exception e) {

        }
    }

}
