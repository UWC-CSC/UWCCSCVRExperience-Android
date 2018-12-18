package org.uwcchina.uwccscvrexperience;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.badoualy.stepperindicator.StepperIndicator;

public class ConfigurationActivity extends FragmentActivity {

    private static MixableColor firstFragmentColor;
    private static MixableColor secondFragmentColor;

    private static final int NUM_PAGES = 3;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private StepperIndicator indicator;
    private ViewGroup content;

    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        // Set up the colors
        firstFragmentColor = new MixableColor(getResources().getColor(R.color.colorBackground),
                getResources().getColor(R.color.colorPrimaryDark));
        secondFragmentColor = new MixableColor(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.colorAccent));

        nextButton = findViewById(R.id.configuration_next_button);

        content = findViewById(R.id.configuration_content);
        mPager = findViewById(R.id.configuration_pager);
        indicator = findViewById(R.id.configuration_stepper_indicator);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                switch (i) {
                    case 0:
                        content.setBackgroundColor(firstFragmentColor.setMixin(v).toInt());

                        nextButton.setTranslationX(Util.Conversion.dpToPx(-v * (56 + 28),
                                ConfigurationActivity.this));
                        break;
                    case 1:
                        content.setBackgroundColor(secondFragmentColor.setMixin(v).toInt());

                        nextButton.setRotation(v * 90);
                        break;
                    case 2:
                        break;
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        indicator.setViewPager(mPager);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void configurationWelcomeNextButtonPressed(View view) {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    public void configurationNextButtonPressed(View view) {
        int i = mPager.getCurrentItem();

        switch (i) {
            case 0:
                break;
            case 1:
                mPager.setCurrentItem(i + 1);
                break;
            case 2:
                Intent intent = new Intent(ConfigurationActivity.this,
                        VideoSelectionActivity.class);
                startActivity(intent);
                break;
            default:
                throw new RuntimeException();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new WelcomeStepFragment();
                case 1:
                    return new HeadsetStepFragment();
                case 2:
                    return new FinalStepFragment();
                default:
                    throw new RuntimeException();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
