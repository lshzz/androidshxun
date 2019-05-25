//package com.ash.transport.utils;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//
//public class FragmentUtils {
//    public static void going(int content, Fragment fragment) {
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(content, fragment)
//                .commit();
//    }
//
//    public static void going(int content, Fragment fragment, Bundle bundle) {
//        fragment.setArguments(bundle);
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(content, fragment)
//                .commit();
//
//    }
//}
