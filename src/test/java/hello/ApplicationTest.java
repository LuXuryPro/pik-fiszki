package hello;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Adrian Stachlewski
 * Created on 02-04-2016
 */

public class ApplicationTest {
    @Test
    public void sum() throws Exception {
        Application app = new Application();

        assertEquals("2 + 7 should be 9", 9, app.sum(2, 7));
        assertEquals("-2 + -7 should be -9", -9, app.sum(-2, -7));
    }

}