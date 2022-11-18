package com.example.project_group_6;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import  android.content.Context;
import com.example.java_classes.Cook;

import org.junit.Test;
public class CheckCookStatus2Test {
    @Test
    public void checkstatus(){
        Cook cook = new Cook("Charlie","Zeng","123@gmail.com","123","123rideau");


        assertFalse("Account currently banned",cook.get_suspend());
    }
}


