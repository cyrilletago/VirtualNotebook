package com.example.cyrille.virtualnotebook;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;

import static android.support.test.espresso.matcher.ViewMatchers.withId;





@RunWith(AndroidJUnit4.class)
public class LanguageQuizTest {


    @Rule
    public ActivityTestRule<LanguageQuiz> activity = new ActivityTestRule<>(LanguageQuiz.class);


    @Test
    public void checkboxesAreUnchecked(){
        onView(withId(R.id.choice1)).check(matches(isNotChecked()));
        onView(withId(R.id.choice2)).check(matches(isNotChecked()));
        onView(withId(R.id.choice3)).check(matches(isNotChecked()));
    }

}
