package ua.com.alevel.check;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatRecognitionTest {

    @Test
    public void shouldRecognizeFormats() {
        Assertions.assertTrue(CheckFormat.firstFormat("13/05/21 23:59:00:999"));
        Assertions.assertFalse(CheckFormat.firstFormat("13/13/21 22:59:00:000"));
        Assertions.assertTrue(CheckFormat.firstFormat("13-05-21 23:59:00:999"));
        Assertions.assertTrue(CheckFormat.firstFormat("13-05-21 23:59"));

        Assertions.assertTrue(CheckFormat.secondFormat("5/8/2021"));
        Assertions.assertTrue(CheckFormat.secondFormat("5/8/2021 00:23:00:000"));
        Assertions.assertFalse(CheckFormat.secondFormat("13/24/21"));

        Assertions.assertTrue(CheckFormat.thirdFormat("July 16 2003"));
        Assertions.assertFalse(CheckFormat.thirdFormat("Julty 16 2003"));
        Assertions.assertTrue(CheckFormat.thirdFormat("July-1-2003 23:59:34:645"));

        Assertions.assertTrue(CheckFormat.fourthFormat("16 July 2003"));
        Assertions.assertTrue(CheckFormat.fourthFormat("16 July 2003 23:59"));
    }
}