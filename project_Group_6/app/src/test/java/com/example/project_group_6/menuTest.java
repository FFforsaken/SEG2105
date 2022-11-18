package com.example.project_group_6;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
public class menuTest {
    @Test
    public void check(){
        ArrayList<String> menulist = new ArrayList<>();
        MenuAdaptor menu = new MenuAdaptor(menulist);

        assertEquals(0,menu.getItemCount());
    }
}
