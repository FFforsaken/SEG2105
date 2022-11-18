package com.example.project_group_6;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import  android.content.Context;
import com.example.java_classes.Cook;

import org.junit.Test;

public class CheckCookStatusTest {
    @Test
    public void checkstatus(){
        Cook cook = new Cook("Charlie","Zeng","123@gmail.com","123","123rideau");
        cook.set_suspend();

        assertTrue("Account currently banned",cook.get_suspend());
    }
}
