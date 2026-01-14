package com.investmentbanking.dealpipeline.user.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserStatusRequestTest {

    @Test
    void shouldCreateRecordWithCorrectValue() {
        UpdateUserStatusRequest request = new UpdateUserStatusRequest(true);

        assertTrue(request.active());
    }

    @Test
    void shouldSupportFalseValue() {
        UpdateUserStatusRequest request = new UpdateUserStatusRequest(false);

        assertFalse(request.active());
    }

    @Test
    void equalsAndHashCodeShouldWork() {
        UpdateUserStatusRequest r1 = new UpdateUserStatusRequest(true);
        UpdateUserStatusRequest r2 = new UpdateUserStatusRequest(true);
        UpdateUserStatusRequest r3 = new UpdateUserStatusRequest(false);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void toStringShouldContainField() {
        UpdateUserStatusRequest request = new UpdateUserStatusRequest(true);

        assertTrue(request.toString().contains("true"));
    }
}
