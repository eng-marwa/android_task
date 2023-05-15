package me.marwa.androidtask.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String?) {
    activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_LONG).show()
    }
}