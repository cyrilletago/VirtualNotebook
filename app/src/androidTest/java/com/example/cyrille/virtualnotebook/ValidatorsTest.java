package com.example.cyrille.virtualnotebook;

/**
 * Created by cyrille on 14/11/17.
 */
import android.support.test.runner.AndroidJUnit4;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class ValidatorsTest {
    private static Validators V;

    @BeforeClass
    public static void setUpClass(){
        V = new Validators();
    }
    @Test
    public void isAlpha(){
        assertTrue(V.isAlpha("pomme"));
        assertFalse(V.isAlpha("apple&Juice"));
    }
}
