package cse.dit012.lost;

import org.junit.Test;

import cse.dit012.lost.service.Gps;
import cse.dit012.lost.service.GpsService;

import static org.junit.Assert.*;

public class GpsTest {

    /**
     * Test if the Singelton object is returned
     */
    @Test
    public void getGps() {
        assertNotNull(GpsService.getGps());
    }
}