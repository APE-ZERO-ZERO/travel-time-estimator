package com.oocode;

import com.oneeyedmen.okeydoke.junit.*;
import org.junit.*;

public class ExampleApprovalTest {
    @Rule
    public final ApprovalsRule approver = ApprovalsRule.fileSystemRule("src/test/resources/approval");

    @Test
    public void shouldProduceExpectedOutput() {
        approver.assertApproved("Mary had a little lamb");
    }
}
