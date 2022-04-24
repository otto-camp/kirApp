package com.example.kirapp.activities;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.kirapp.models.Customer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RegisterActivityTest {
    @Mock
    RegisterActivity registerActivity;

    @Rule
    ActivityScenarioRule<RegisterActivity> rule = new ActivityScenarioRule<>(RegisterActivity.class);

    @Before
    public void setUp() throws Exception {
        rule.getScenario().onActivity(activity -> registerActivity = new RegisterActivity());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void registerToFirebase() {
        Customer customer = new Customer(
                "cem", "cem",
                "ismailyarar4@gmail.com", "12345678",
                "08/08/2000", "5555555", "male",
                true, "2000-01-01", "2000-01-01"
        );
        registerActivity.registerToFirebase(customer);

    }
}