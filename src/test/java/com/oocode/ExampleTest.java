package com.oocode;

import org.junit.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

public class ExampleTest {
    @Test
    public void canAnswerTheUniversalQuestion() {
        assertThat(new Example().answer(), is(42));
    }
}
