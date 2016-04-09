package hello;

import org.junit.Test;

import static org.junit.Assert.*;

public class ApplicationTest
{
    @Test
    public void goodTest()
    {
        int sum = 6;
        assertEquals(6, sum);
    }
}