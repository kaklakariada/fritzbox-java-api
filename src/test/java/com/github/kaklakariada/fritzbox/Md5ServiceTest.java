package com.github.kaklakariada.fritzbox;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for {@link Md5Service}
 */
public class Md5ServiceTest {

    @Test
    public void testMd5EmptyString() {
        assertMd5Sum("", "d41d8cd98f00b204e9800998ecf8427e");
    }

    @Test
    public void testMd5Test() {
        assertMd5Sum("test", "c8059e2ec7419f590e79d7f1b774bfe6");
    }

    @Test
    public void testMd5SpecialChars() {
        assertMd5Sum("!\"§$%&/()=?ßüäöÜÄÖ-.,;:_`´+*#'<>≤|", "ad44a7cb10a95cb0c4d7ae90b0ff118a");
    }

    private void assertMd5Sum(String input, String expectedMd5) {
        assertEquals(expectedMd5, new Md5Service().md5(input));
    }
}
