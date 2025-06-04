package com.autoparts.ua.utils;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class AdMobManager {

    private static final String INTERSTITIAL_AD_UNIT_ID = "ca-app-pub-3940256099942544/1033173712";
    private static final String REWARDED_AD_UNIT_ID = "ca-app-pub-3940256099942544/5224354917";

    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;
    private Context context;

    public AdMobManager(Context context) {
        this.context = context;
        loadInterstitialAd();
        loadRewardedAd();
    }

    public void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(context, INTERSTITIAL_AD_UNIT_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        interstitialAd = ad;
                        setupInterstitialCallbacks();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstitialAd = null;
                    }
                });
    }

    public void showInterstitialAd(Activity activity) {
        if (interstitialAd != null) {
            interstitialAd.show(activity);
        } else {
            loadInterstitialAd();
        }
    }

    public void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(context, REWARDED_AD_UNIT_ID, adRequest,
                new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedAd = null;
                    }
                });
    }

    public void showRewardedAd(Activity activity, RewardCallback callback) {
        if (rewardedAd != null) {
            rewardedAd.show(activity, rewardItem -> {
                // Користувач отримав винагороду
                callback.onRewardEarned();
                loadRewardedAd(); // Завантажити нову рекламу
            });
        } else {
            loadRewardedAd();
        }
    }

    private void setupInterstitialCallbacks() {
        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                interstitialAd = null;
                loadInterstitialAd(); // Завантажити нову рекламу
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                interstitialAd = null;
            }
        });
    }

    public interface RewardCallback {
        void onRewardEarned();
    }
}